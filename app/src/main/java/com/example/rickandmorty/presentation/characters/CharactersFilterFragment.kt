package com.example.rickandmorty.presentation.characters

import android.app.AlertDialog
import android.content.DialogInterface
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
    private var species: List<String>? = null
    private var type: List<String>? = null

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
                type = null,
                species = null
            )
            dismiss()
        }

        binding.btnFilterCharactersType.setOnClickListener {
            type = getOtherParams(listOf<String>("type", "2", "3", "4"), "Characters types")
        }

        binding.btnFilterCharactersSpecies.setOnClickListener {
            species = getOtherParams(listOf<String>("species", "2", "3", "4"), "Characters species")
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