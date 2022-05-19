package com.example.rickandmorty.presentation.screens.characters.characters_fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.di.App
import com.example.rickandmorty.presentation.adapters.characters_adapter.CharactersAdapter
import com.example.rickandmorty.presentation.navigator
import kotlinx.android.synthetic.main.fragment_characters.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private var charactersAdapter: CharactersAdapter = CharactersAdapter()

    private var params: MutableMap<String, String?> = mutableMapOf(
        "name" to null,
        "gender" to null,
        "status" to null,
        "species" to null,
        "type" to null
    )
@Inject
lateinit var charactersViewModelProvider: CharactersViewModelProvider
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
        (requireContext().applicationContext as App).appComponent.inject(this)

        vm = ViewModelProvider(
            this,
            charactersViewModelProvider
        )[CharactersViewModel::class.java]

        initRecyclerView()
        collectUiState()

        if(
            params["gender"] != null ||
            params["status"] != null ||
            params["type"] != null ||
            params["species"] != null
        )vm.filteredTrigger.value = params

        setUpSwipeToRefresh()

        binding.btnFilterCharter.setOnClickListener {
            navigator().openCharactersFilterFragment()
        }

        binding.searchCharacter.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                performSearchEvent(query = newText)
                return false
            }

        })
        observeVM()
    }

    private fun observeVM() {

        lifecycle.coroutineScope.launch {
            vm.filteredTrigger.collect {
                vm.getCharactersByParams(
                    name = vm.filteredTrigger.value.getValue("name"),
                    gender = vm.filteredTrigger.value.getValue("gender"),
                    status = vm.filteredTrigger.value.getValue("status"),
                    species = vm.filteredTrigger.value.getValue("species"),
                    type = vm.filteredTrigger.value.getValue("type")
                )
            }
        }
    }

    private fun performSearchEvent(query: String) {
//        vm.getEpisodes(name = query, episode = episode)
    }


    private fun initRecyclerView() {
        with(binding.rvCharacters) {
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = charactersAdapter
        }
        charactersAdapter.onCharacterItem = { navigator().openCharacterDetailFragment(it.id) }
    }

    private fun setUpSwipeToRefresh() {
        binding.swipeRefreshCharacters.apply {
            setOnRefreshListener {
                vm.getCharactersByParams(null, null, null, null, null)
                binding.swipeRefreshCharacters.isRefreshing = false
                binding.rvCharacters.scrollToPosition(0)
            }
        }
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.charactersFlow.collectLatest {
                charactersAdapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            charactersAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBarCharacters.isVisible = loadStates.refresh is LoadState.Loading

            }
        }
    }


    private fun init() {
        arguments?.let {
            params["gender"] = it.getString(KEY_GENDER)
            params["status"] = it.getString(KEY_STATUS)
            params["species"] = it.getString(KEY_SPECIES)
            params["type"] = it.getString(KEY_TYPE)
        }
    }
}