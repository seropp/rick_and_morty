package com.example.rickandmorty.presentation.screens.locations.locations_filter_fragment

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
import com.example.rickandmorty.databinding.FragmentLocationsFilterBinding
import com.example.rickandmorty.di.App
import com.example.rickandmorty.presentation.navigator
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class LocationFiltersFragment : BottomSheetDialogFragment() {

    private lateinit var binding: FragmentLocationsFilterBinding

    private var type: String? = null
    private var dimension: String? = null
    private var dimensionsList: MutableList<String> = mutableListOf<String>()
    private var typesList: MutableList<String> = mutableListOf<String>()
    @Inject
    lateinit var locationFiltersViewModelProvider: LocationFiltersViewModelProvider
    private lateinit var vm: LocationFiltersViewModel


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLocationsFilterBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    @OptIn(ExperimentalPagingApi::class)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireContext().applicationContext as App).appComponent.inject(this)

        vm = ViewModelProvider(
            this,
            locationFiltersViewModelProvider
        )[LocationFiltersViewModel::class.java]

        observeVm()

        binding.btnApplyFilterLocations.setOnClickListener {
            navigator().openLocationsFragmentWithArg(type = type, dimension = dimension)
            dismiss()
        }

        binding.btnFilterLocationsDimension.setOnClickListener {
            getDimension(dimensionsList)
        }

        binding.btnFilterLocationsType.setOnClickListener {
            getType(typesList)
        }
    }

    private fun observeVm() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                vm.dimensionList.collect {
                    dimensionsList.addAll(it)
                }
            }
        }

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.RESUMED) {
                vm.typeList.collect {
                    typesList.addAll(it)
                }
            }
        }
        
    }

    private fun getType(params: List<String>) {
        val typesArr = params.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle("Location types")
            .setSingleChoiceItems(typesArr, 0, null)
            .setPositiveButton("Confirm") { dialog, _ ->
                dialog.dismiss()
                val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
                Log.e("checkedItem", "$selectedPosition");
                if(typesArr.isNotEmpty()){ type = typesArr[selectedPosition] }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun getDimension(params: List<String>) {
        val typesArr = params.toTypedArray()

        AlertDialog.Builder(requireContext())
            .setTitle("Dimensions types")
            .setSingleChoiceItems(typesArr, 0, null)
            .setPositiveButton("Confirm") { dialog, _ ->
                dialog.dismiss()
                val selectedPosition = (dialog as AlertDialog).listView.checkedItemPosition
                Log.e("checkedItem", "$selectedPosition");
                if(typesArr.isNotEmpty()){ dimension = typesArr[selectedPosition] }

            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}