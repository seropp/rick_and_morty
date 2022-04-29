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
    private var episode: List<String>? = null

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
            navigator().openEpisodesFragmentWithArg(episode = null)
            dismiss()
        }
        binding.btnFilterEpisodesEpisodes.setOnClickListener {
            episode = getOtherParams(listOf<String>("type", "2", "3", "4"), "Episodes")
        }
    }

    private fun getOtherParams(params: List<String>, paramsType: String): List<String>? {

        val typesArr = params.toTypedArray()
        val checkedItems: BooleanArray = typesArr.map { false }.toBooleanArray()

        val result = ArrayList<String>()

        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(paramsType)
            .setMultiChoiceItems(
                typesArr,
                checkedItems
            ) { _, which, isChecked ->
                if (isChecked) {
                    result.add(typesArr[which])
                }
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