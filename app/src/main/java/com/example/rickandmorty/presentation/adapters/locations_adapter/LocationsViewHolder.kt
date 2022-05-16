package com.example.rickandmorty.presentation.adapters.locations_adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.rickandmorty.databinding.ItemLocationsBinding
import com.example.rickandmorty.presentation.models.location.LocationPresentation


class LocationsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemLocationsBinding.bind(itemView)

    fun bind(item: LocationPresentation) = with(binding) {

        nameLocationItem.text = item.name
        dimensionLocationItem.text = item.dimension
        typeLocationItem.text = item.dimension
    }
}