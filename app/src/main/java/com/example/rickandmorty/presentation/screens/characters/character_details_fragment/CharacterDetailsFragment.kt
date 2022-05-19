package com.example.rickandmorty.presentation.screens.characters.character_details_fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.*
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharacterDetailsBinding
import com.example.rickandmorty.di.App
import com.example.rickandmorty.presentation.adapters.character_details_adapter.EpisodeListForDetailsAdapter
import com.example.rickandmorty.presentation.models.character.CharacterPresentation
import com.example.rickandmorty.presentation.navigator
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.properties.Delegates


@ExperimentalPagingApi
class CharacterDetailsFragment : Fragment() {

    private lateinit var binding: FragmentCharacterDetailsBinding
    private lateinit var vm: CharacterDetailsViewModel

    @Inject
    lateinit var characterDetailsViewModelProvider: CharacterDetailsViewModelProvider


    private var episodeListForDetailsAdapter: EpisodeListForDetailsAdapter? = null


    private var characterId by Delegates.notNull<Int>()
    private var lastLocationId: Int? = null
    private var originLocationId: Int? = null

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
        (requireContext().applicationContext as App).appComponent.inject(this)

        vm = ViewModelProvider(
            this,
            characterDetailsViewModelProvider
        )[CharacterDetailsViewModel::class.java]

        vm.getCharacter(characterId)
        initView()
        observeVm()
        showProgressBar()

        binding.backBtn.setOnClickListener {
            navigator().backButton()
        }

        binding.characterOrigin.setOnClickListener {
            if (originLocationId != null) {
                navigator().openLocationsDetailFragment(locationId = originLocationId!!)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Character's place of origin unknown.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        binding.characterLocation.setOnClickListener {
            if (lastLocationId != null) {
                navigator().openLocationsDetailFragment(locationId = lastLocationId!!)
            } else {
                Toast.makeText(
                    requireContext(),
                    "Character's last location is unknown.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initView() {
        episodeListForDetailsAdapter = EpisodeListForDetailsAdapter()

        with(binding.rvCharacterDetail) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = episodeListForDetailsAdapter
        }
        episodeListForDetailsAdapter!!.onEpisodeItem =
            { navigator().openEpisodesDetailFragment(it.id) }
    }

    private fun observeVm() {
        lifecycle.coroutineScope.launch {
            vm.episodesList.observe(viewLifecycleOwner, Observer {
                episodeListForDetailsAdapter!!.submitList(it)
            })
        }

        lifecycle.coroutineScope.launch {
            vm.characterDetails.observe(viewLifecycleOwner, Observer {
                vm.getEpisodesList(it.episodeIds)
                initUI(it)
            })
        }
    }

    private fun initUI(characterDetails: CharacterPresentation) {
        if (characterDetails.lastLocation.getValue("location_id").isNotEmpty()) {
            lastLocationId = characterDetails.lastLocation.getValue("location_id").toInt()
        }
        if (characterDetails.originLocation.getValue("location_id").isNotEmpty()) {
            originLocationId = characterDetails.originLocation.getValue("location_id").toInt()
        }
        Glide.with(requireContext())
            .load(characterDetails.imageUrl)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_dissconect)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .centerCrop()
            .into(binding.characterImage)

        binding.characterName.text = "Name: ${characterDetails.name}"
        binding.characterStatus.text = "Status: ${characterDetails.status}"
        binding.characterType.text = "Type: ${characterDetails.type}"
        binding.characterSpecie.text = "Species: ${characterDetails.species}"
        binding.characterGender.text = "Gender: ${characterDetails.gender}"
        binding.characterLocation.text =
            "Location: ${characterDetails.lastLocation.getValue("location_name")}"
        binding.characterOrigin.text =
            "Origin: ${characterDetails.originLocation.getValue("location_name")}"
    }

    private fun showProgressBar() {
        vm.isLoading.observe(viewLifecycleOwner) { it ->
            binding.progressBarCharacterDetails.isVisible = it
        }

    }

    private fun init() {
        arguments?.let {
            characterId = it.getInt(KEY_CHARACTER_ID)
        }
    }
}