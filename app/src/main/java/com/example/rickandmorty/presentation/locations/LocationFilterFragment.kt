package com.example.rickandmorty.presentation.locations

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast

import com.example.rickandmorty.databinding.FragmentLocationsFilterBinding
import com.example.rickandmorty.presentation.navigator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class LocationFilterFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentLocationsFilterBinding

    private var type: String? = null
    private var dimension: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnApplyFilterLocations.setOnClickListener {
            navigator().openLocationsFragmentWithArg(type = type, dimension = dimension)
            dismiss()
        }

        binding.btnFilterLocationsDimension.setOnClickListener {
            dimension = getFilter(listOf<String>("type", "2", "3", "4"), "Dimensions types")
        }

        binding.btnFilterLocationsType.setOnClickListener {
            type = getFilter(listOf<String>("type", "2", "3", "4"), "Location types")
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