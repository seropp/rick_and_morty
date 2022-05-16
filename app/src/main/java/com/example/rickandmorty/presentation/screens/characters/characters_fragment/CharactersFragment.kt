package com.example.rickandmorty.presentation.screens.characters.characters_fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.presentation.adapters.characters_adapter.CharactersAdapter
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
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private var charactersAdapter: CharactersAdapter? = null
    private var gender: String? = null
    private var status: String? = null
    private var species: String? = null
    private var type: String? = null

    private lateinit var vm: CharactersViewModel

    companion object {
        private const val KEY_GENDER: String = "KEY_GENDER"
        private const val KEY_STATUS: String = "KEY_STATUS"
        private const val KEY_SPECIES: String = "KEY_SPECIES"
        private const val KEY_TYPE: String = "KEY_TYPE"

        @JvmStatic
        fun newInstance(
            gender: String?,
            status: String?,
            species: String?,
            type: String?
        ) =
            CharactersFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_GENDER, gender)
                    putString(KEY_STATUS, status)
                    putString(KEY_SPECIES, species)
                    putString(KEY_TYPE, type)
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
        binding = FragmentCharactersBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vm = ViewModelProvider(
            this,
            CharactersViewModelProvider(requireContext())
        )[CharactersViewModel::class.java]
        initView()
        collectUiState()

//        binding.swipeRefreshCharacters.setOnRefreshListener {
//            charactersAdapter?.refresh()
//        }

        binding.resetCharacters.setOnClickListener{
            charactersAdapter?.refresh()
        }

        binding.btnFilterCharter.setOnClickListener {
            navigator().openCharactersFilterFragment()
        }

    }

    private fun initView() {
        charactersAdapter = CharactersAdapter()

        with(binding.rvCharacters) {
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = charactersAdapter
        }
        charactersAdapter!!.onCharacterItem = {navigator().openCharacterDetailFragment(it.id)}
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.charactersFlow.collect { charactersAdapter?.submitData(it) }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            charactersAdapter?.loadStateFlow?.collectLatest {
                binding.swipeRefreshCharacters.isRefreshing = it.refresh is LoadState.Loading
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            charactersAdapter?.loadStateFlow?.distinctUntilChangedBy { it.refresh }
                ?.filter { it.refresh is LoadState.NotLoading }
                ?.collect { binding.rvCharacters.scrollToPosition(0) }
        }
    }

    private fun init() {
        arguments?.let {
            gender = it.getString(KEY_GENDER)
            status = it.getString(KEY_STATUS)
            species = it.getString(KEY_SPECIES)
            type = it.getString(KEY_TYPE)
        }
    }
}