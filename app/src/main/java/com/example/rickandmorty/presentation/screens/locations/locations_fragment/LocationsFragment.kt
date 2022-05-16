package com.example.rickandmorty.presentation.screens.locations.locations_fragment

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
import com.example.rickandmorty.databinding.FragmentLocationsBinding
import com.example.rickandmorty.presentation.adapters.characters_adapter.CharactersAdapter
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

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
class LocationsFragment : Fragment() {

    private lateinit var binding: FragmentLocationsBinding
    private var locationsAdapter: LocationsAdapter? = null
    private var dimension: String? = null
    private var type: String? = null

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

        vm = ViewModelProvider(
            this,
            LocationsViewModelProvider(requireContext())
        )[LocationsViewModel::class.java]
        initView()
        collectUiState()

        binding.swipeRefreshLocations.setOnRefreshListener {
            locationsAdapter?.refresh()
        }

        binding.btnFilterLocations.setOnClickListener {
            navigator().openLocationsFilterFragment()
        }

        binding.locationsLabel.setOnClickListener {
            Toast.makeText(requireContext(), "$type, $dimension", Toast.LENGTH_SHORT).show()

        }
    }

    private fun initView() {
        locationsAdapter = LocationsAdapter()

        with(binding.rvLocations) {
            layoutManager = LinearLayoutManager(requireContext())
            layoutManager = GridLayoutManager(requireContext(), 2)
            adapter = locationsAdapter
        }
        locationsAdapter!!.onLocationItem = {navigator().openLocationsDetailFragment(it.id)}
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            vm.locationsFlow.collect { locationsAdapter?.submitData(it) }
        }
        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            locationsAdapter?.loadStateFlow?.collectLatest {
                binding.swipeRefreshLocations.isRefreshing = it.refresh is LoadState.Loading
            }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            locationsAdapter?.loadStateFlow?.distinctUntilChangedBy { it.refresh }
                ?.filter { it.refresh is LoadState.NotLoading }
                ?.collect { binding.rvLocations.scrollToPosition(0) }
        }
    }

    private fun init() {
        arguments?.let {
            type = it.getString(KEY_TYPE)
            dimension = it.getString(KEY_DIMENSION)
        }
    }
}