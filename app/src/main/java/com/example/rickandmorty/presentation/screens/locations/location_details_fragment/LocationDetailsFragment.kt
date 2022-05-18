package com.example.rickandmorty.presentation.screens.locations.location_details_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.coroutineScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.example.rickandmorty.presentation.adapters.characters_adapter_for_details.CharactersListForDetailsAdapter
import com.example.rickandmorty.presentation.models.location.LocationPresentation
import com.example.rickandmorty.presentation.navigator
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

@ExperimentalPagingApi
class LocationDetailsFragment : Fragment() {

    private lateinit var binding: FragmentLocationDetailsBinding
    private lateinit var vm: LocationDetailsViewModel
    private var charactersListForDetailsAdapter: CharactersListForDetailsAdapter? = null

    private var locationId by Delegates.notNull<Int>()

    companion object {
        private const val KEY_LOCATION_ID: String = "KEY_LOCATION_ID"

        @JvmStatic
        fun newInstance(
            locationId: Int,
        ) =
            LocationDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_LOCATION_ID, locationId)
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
        binding = FragmentLocationDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        vm = ViewModelProvider(
            this,
            LocationDetailsViewModelProvider(requireContext())
        )[LocationDetailsViewModel::class.java]
        vm.getLocation(locationId)
        initView()
        observeVm()
    }

    private fun initView() {
        charactersListForDetailsAdapter = CharactersListForDetailsAdapter()

        with(binding.rvLocationDetail) {
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
            vm.locationDetails.observe(viewLifecycleOwner, Observer {
                vm.getEpisodesList(it.residentsIds)
                initUI(it)
            })
        }
    }

    private fun initUI(locationDetails: LocationPresentation) {
        binding.locationNameDetail.text = locationDetails.name
        binding.locationTypeDetail.text = "Type: ${locationDetails.type}"
        binding.locationDimensionsLocation.text = "Dimension: ${locationDetails.dimension}"
    }

    private fun init() {
        arguments?.let {
            locationId = it.getInt(KEY_LOCATION_ID)
        }
    }
}