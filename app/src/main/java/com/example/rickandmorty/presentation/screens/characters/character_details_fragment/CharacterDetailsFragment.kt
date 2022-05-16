package com.example.rickandmorty.presentation.screens.characters.character_details_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.example.rickandmorty.presentation.navigator
import kotlin.properties.Delegates


class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding

    private var characterId by Delegates.notNull<Int>()

    companion object {
        private const val KEY_CHARACTER_ID: String = "KEY_CHARACTER_ID"

        @JvmStatic
        fun newInstance(
            characterId: Int
        ) =
            CharacterDetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(KEY_CHARACTER_ID, characterId)
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
        binding = FragmentCharacterDetailsBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.characterOrigin.setOnClickListener {
            val id = 1
            navigator().openLocationsDetailFragment(locationId = id)
        }

        binding.characterLocation.setOnClickListener {
            val id = 1
            navigator().openLocationsDetailFragment(locationId = id)
        }
    }

    private fun init() {
        arguments?.let {
            characterId = it.getInt(KEY_CHARACTER_ID)
        }
    }
}