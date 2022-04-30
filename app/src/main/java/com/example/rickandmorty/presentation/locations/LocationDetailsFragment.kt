package com.example.rickandmorty.presentation.locations

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty.databinding.FragmentLocationDetailsBinding
import com.example.rickandmorty.presentation.navigator
import kotlin.properties.Delegates


class LocationDetailsFragment : Fragment() {

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

    private lateinit var binding: FragmentLocationDetailsBinding

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

    }

    private fun openCharacterDetails(characterId: Int) {
        navigator().openCharacterDetailFragment(characterId = characterId)
    }

    private fun init() {
        arguments?.let {
            locationId = it.getInt(KEY_LOCATION_ID)
        }
    }
}