package com.example.rickandmorty.presentation.screens.episodes.episodes_filter_fragment

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.databinding.FragmentEpisodesFilterBinding
import com.example.rickandmorty.di.App
import com.example.rickandmorty.presentation.navigator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject

class EpisodeFiltersFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEpisodesFilterBinding
    private var episode: String? = null
    private var episodesList: MutableList<String> = mutableListOf<String>()
    @Inject
    lateinit var episodeFilterViewModelProvider: EpisodeFilterViewModelProvider
    private lateinit var vm: EpisodeFiltersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)

        vm = ViewModelProvider(
            this,
            episodeFilterViewModelProvider
        )[EpisodeFiltersViewModel::class.java]
        observeVm()

        binding.btnApplyFilterEpisodes.setOnClickListener {
            navigator().openEpisodesFragmentWithArg(episode = episode)
            dismiss()
        }

        binding.btnFilterEpisodesEpisodes.setOnClickListener {
            getEpisode(episodesList)
        }
    }

    private fun observeVm() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                vm.episodeList.collect {
                    episodesList.addAll(it)
                }
            }
        }
    }

    private fun getEpisode(params: List<String>) {
        val typesArr = params.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle("Episodes")
            .setSingleChoiceItems(typesArr, 0, null)
            .setPositiveButton("Confirm") { dialog, _ ->
                dialog.dismiss()
                val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
                Log.e("checkedItem", "$selectedPosition");
                if(typesArr.isNotEmpty()){ episode = typesArr[selectedPosition] }

            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}