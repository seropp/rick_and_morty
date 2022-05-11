package com.example.rickandmorty.presentation.characters.characters_filter_fragment

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.children
import com.example.rickandmorty.databinding.FragmentCharactersFilterBinding
import com.example.rickandmorty.presentation.navigator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.chip.Chip


class CharactersFilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentCharactersFilterBinding
    private var species: String? = null
    private var type: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnApplyFilterCharacters.setOnClickListener {
            val gender = getGender()
            val status = getStatus()

            navigator().openCharactersFragmentWithArg(
                gender = gender,
                status = status,
                type = type,
                species = species
            )
            dismiss()
        }

        binding.btnFilterCharactersType.setOnClickListener {
            type = getFilter(listOf<String>("type", "2", "3", "4"), "Characters types")
            Toast.makeText(requireContext(), "$type", Toast.LENGTH_LONG).show()
        }

        binding.btnFilterCharactersSpecies.setOnClickListener {
            species = getFilter(listOf<String>("species", "2", "3", "4"), "Characters species")
            Toast.makeText(requireContext(), "$species", Toast.LENGTH_LONG).show()
        }

    }

    private fun getStatus(): String? {
        binding.statusFilterCharacters.children
            .toList()
            .filter { (it as Chip).isChecked }
            .forEach { return (it as Chip).text.toString() }
        return null
    }

    private fun getGender(): String? {
        binding.genderFilterCharacters.children
            .toList()
            .filter { (it as Chip).isChecked }
            .forEach { return (it as Chip).text.toString() }
        return null
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

            }
            .setNegativeButton("Cancel", null)
            .create()
        dialog.show()
        return result
    }
}