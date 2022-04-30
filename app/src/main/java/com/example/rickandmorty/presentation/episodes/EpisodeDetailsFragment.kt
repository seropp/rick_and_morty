package com.example.rickandmorty.presentation.episodes

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentEpisodeDetailsBinding
import com.example.rickandmorty.presentation.characters.CharacterDetailsFragment
import com.example.rickandmorty.presentation.locations.LocationDetailsFragment
import com.example.rickandmorty.presentation.navigator
import kotlin.math.E
import kotlin.properties.Delegates


class EpisodeDetailsFragment : Fragment() {

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

    private lateinit var binding: FragmentEpisodeDetailsBinding

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

    }

    private fun openCharacterDetails(characterId: Int) {
        navigator().openCharacterDetailFragment(characterId = characterId)
    }

    private fun init() {
        arguments?.let {
            episodeId = it.getInt(KEY_EPISODE_ID)
        }
    }
}