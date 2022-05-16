package com.example.rickandmorty.presentation.screens.episodes.episodes_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentEpisodesBinding
import com.example.rickandmorty.presentation.adapters.episodes_adapter.EpisodesAdapter
import com.example.rickandmorty.presentation.navigator
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch


@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
class EpisodesFragment : Fragment() {

    private lateinit var binding: FragmentEpisodesBinding
    private var episodesAdapter: EpisodesAdapter? = null
    private var episode: String? = null

    private lateinit var vm: EpisodesViewModel

    companion object {
        private const val KEY_EPISODE: String = "KEY_EPISODE"

        @JvmStatic
        fun newInstance(
            episode: String?,
        ) =
            EpisodesFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_EPISODE, episode)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm = ViewModelProvider(
            this,
            EpisodesViewModelProvider(requireContext())
        )[EpisodesViewModel::class.java]
        initView()
        collectUiState()

//        binding.swipeRefreshLocations.setOnRefreshListener {
//            episodesAdapter?.refresh()
//        }

        binding.btnFilterEpisodes.setOnClickListener {
            navigator().openEpisodesFilterFragment()
        }

        binding.episodesLabel.setOnClickListener {
            Toast.makeText(requireContext(), "$episode", Toast.LENGTH_SHORT).show()
        }

    }

    private fun initView() {
        episodesAdapter = EpisodesAdapter(listener = vm)

        with(binding.rvEpisodes) {
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = episodesAdapter
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.episodesFlow.collect { episodesAdapter?.submitData(it) }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            episodesAdapter?.loadStateFlow?.collectLatest {
                binding.swipeRefreshLocations.isRefreshing = it.refresh is LoadState.Loading
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            episodesAdapter?.loadStateFlow?.distinctUntilChangedBy { it.refresh }
                ?.filter { it.refresh is LoadState.NotLoading }
                ?.collect { binding.rvEpisodes.scrollToPosition(0) }
        }
    }

    private fun init() {
        arguments?.let {
            episode = it.getString(KEY_EPISODE)
        }
    }
}