package com.example.rickandmorty.presentation.screens.episodes.episode_details_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.observe
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.example.rickandmorty.di.App
import com.example.rickandmorty.presentation.adapters.characters_adapter_for_details.CharactersListForDetailsAdapter
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation
import com.example.rickandmorty.presentation.navigator
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates


@ExperimentalPagingApi
class EpisodeDetailsFragment : Fragment() {

    private lateinit var binding: FragmentEpisodeDetailsBinding
    @Inject
    lateinit var episodeDetailsViewModelProvider: EpisodeDetailsViewModelProvider
    private lateinit var vm: EpisodeDetailsViewModel
    private var charactersListForDetailsAdapter: CharactersListForDetailsAdapter? = null

    private var episodeId by Delegates.notNull<Int>()

    companion object {
        private const val KEY_EPISODE_ID: String = "KEY_EPISODE_ID"

        @JvmStatic
        fun newInstance(
            episodeId: Int,
        ) =
            EpisodeDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_EPISODE_ID, episodeId)
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
        binding = FragmentEpisodeDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)

        vm = ViewModelProvider(
            this,
            episodeDetailsViewModelProvider
        )[EpisodeDetailsViewModel::class.java]

        vm.getEpisode(episodeId)
        initView()
        observeVm()
        showProgressBar()

        binding.backBtn.setOnClickListener{
            navigator().backButton()
        }
    }


    private fun initView() {
        charactersListForDetailsAdapter = CharactersListForDetailsAdapter()

        with(binding.rvEpisodeDetail) {
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = charactersListForDetailsAdapter
        }
        charactersListForDetailsAdapter!!.onCharacterItem =
            { navigator().openCharacterDetailFragment(it.id) }
    }

    private fun observeVm() {
        lifecycle.coroutineScope.launch {
            vm.charactersList.observe(viewLifecycleOwner, Observer {
                charactersListForDetailsAdapter!!.submitList(it)
            })
        }

        lifecycle.coroutineScope.launch {
            vm.episodeDetails.observe(viewLifecycleOwner, Observer {
                vm.getEpisodesList(it.residentsIds)
                initUI(it)
            })
        }
    }

    private fun initUI(presentationDetails: EpisodePresentation) {
        binding.episodeAirDateDetail.text = presentationDetails.air_date
        binding.episodeNameDetail.text = presentationDetails.name
        binding.episodeNumberDetail.text = "Series ${presentationDetails.episode}"
    }

    private fun showProgressBar() {
        vm.isLoading.observe(viewLifecycleOwner) { it ->
            binding.progressBarEpisodesDetails.isVisible = it
        }
    }

    private fun init() {
        arguments?.let {
            episodeId = it.getInt(KEY_EPISODE_ID)
        }
    }
}