package com.example.rickandmorty.presentation.episodes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.rickandmorty.databinding.FragmentEpisodesFilterBinding
import com.example.rickandmorty.presentation.navigator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class EpisodesFilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentEpisodesFilterBinding
    private var episode: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEpisodesFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnApplyFilterEpisodes.setOnClickListener {
            navigator().openEpisodesFragmentWithArg(episode = episode)
            dismiss()
        }
        binding.btnFilterEpisodesEpisodes.setOnClickListener {
            episode = getFilter(listOf<String>("type", "2", "3", "4"), "Episodes")
        }
    }

    private fun getFilter(params: List<String>, paramsType: String): String? {

        val typesArr = params.toTypedArray()
        var result: String? = null

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(paramsType)
            .setSingleChoiceItems(
                typesArr,
                -1,
            ) { _, which ->
                result = typesArr[which]
            }
            .setPositiveButton(
                "Confirm"
            ) { _, _ ->
                Toast.makeText(requireContext(), "$result", Toast.LENGTH_LONG).show()
            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
        return result
    }
}