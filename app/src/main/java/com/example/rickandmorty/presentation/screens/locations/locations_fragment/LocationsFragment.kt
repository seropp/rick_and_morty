package com.example.rickandmorty.presentation.screens.locations.locations_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentLocationsBinding
import com.example.rickandmorty.di.App
import com.example.rickandmorty.presentation.adapters.characters_adapter.CharactersAdapter
import com.example.rickandmorty.presentation.adapters.episodes_adapter.EpisodesAdapter
import com.example.rickandmorty.presentation.adapters.locations_adapter.LocationsAdapter
import com.example.rickandmorty.presentation.navigator
import com.example.rickandmorty.presentation.screens.characters.characters_fragment.CharactersViewModel
import com.example.rickandmorty.presentation.screens.characters.characters_fragment.CharactersViewModelProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChangedBy
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
class LocationsFragment : Fragment() {

    private lateinit var binding: FragmentLocationsBinding
    private var locationsAdapter: LocationsAdapter = LocationsAdapter()

    private var params: MutableMap<String, String?> = mutableMapOf(
        "name" to null,
        "dimension" to null,
        "type" to null
    )

    @Inject
    lateinit var locationsViewModelProvider: LocationsViewModelProvider
    private lateinit var vm: LocationsViewModel

    companion object {
        private const val KEY_TYPE: String = "KEY_TYPE"
        private const val KEY_DIMENSION: String = "KEY_DIMENSION"

        @JvmStatic
        fun newInstance(
            types: String?,
            dimensions: String?
        ) =
            LocationsFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_TYPE, types)
                    putString(KEY_DIMENSION, dimensions)
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
        binding = FragmentLocationsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireContext().applicationContext as App).appComponent.inject(this)
        
        vm = ViewModelProvider(
            this,
            locationsViewModelProvider
        )[LocationsViewModel::class.java]

        initRecyclerView()
        collectUiState()

        if (
            params["name"] != null ||
            params["type"] != null ||
            params["dimension"] != null
        ) vm.filteredTrigger.value = params

        setUpSwipeToRefresh()

        binding.btnFilterLocations.setOnClickListener {
            navigator().openLocationsFilterFragment()
        }

//        binding.searchLocations.
        observeVM()
    }


    private fun observeVM() {

        lifecycle.coroutineScope.launch {
            vm.filteredTrigger.observe(viewLifecycleOwner, Observer {
                vm.getLocationsByParams(
                    name = vm.filteredTrigger.value?.getValue("name"),
                    type = vm.filteredTrigger.value?.getValue("type"),
                    dimension = vm.filteredTrigger.value?.getValue("dimension"),
                )
            })
        }
    }

    private fun initRecyclerView() {
        with(binding.rvLocations) {
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = locationsAdapter
        }

        locationsAdapter.onLocationItem = { navigator().openLocationsDetailFragment(it.id) }
    }

    private fun performSearchEvent(query: String) {
//        vm.getEpisodes(name = query, episode = episode)
    }

    private fun setUpSwipeToRefresh() {
        binding.swipeRefreshLocations.apply {
            setOnRefreshListener {
                vm.getLocationsByParams(null, null, null)
                binding.swipeRefreshLocations.isRefreshing = false
                binding.rvLocations.scrollToPosition(0)
            }
        }
    }

    private fun collectUiState() {

        viewLifecycleOwner.lifecycleScope.launch {
            vm.locationsFlow.collectLatest { locationsAdapter.submitData(it) }
        }

        lifecycleScope.launch {
            locationsAdapter.loadStateFlow.collectLatest { loadStates ->
                binding.progressBarLocation.isVisible = loadStates.refresh is LoadState.Loading

            }
        }
    }

    private fun init() {
        arguments?.let {
            params["type"] = it.getString(KEY_TYPE)
            params["dimension"] = it.getString(KEY_DIMENSION)
        }
    }
}