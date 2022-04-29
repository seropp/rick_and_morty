package com.example.rickandmorty.presentation.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rickandmorty.databinding.FragmentLocationsBinding
import com.example.rickandmorty.presentation.episodes.EpisodesFragment
import com.example.rickandmorty.presentation.navigator

class LocationsFragment : Fragment() {

    private lateinit var binding: FragmentLocationsBinding
    private var dimension: String? = null
    private var type: String? = null

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

        binding.btnFilterLocations.setOnClickListener {
            navigator().openLocationsFilterFragment()
        }

        binding.locationsLabel.setOnClickListener {
            Toast.makeText(requireContext(), "$type, $dimension", Toast.LENGTH_SHORT).show()

        }
    }

    private fun init() {
        arguments?.let {
            type = it.getString(KEY_TYPE)
            dimension = it.getString(KEY_DIMENSION)
        }
    }
}