package com.example.rickandmorty.presentation.adapters.locations_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.models.location.LocationPresentation

class LocationsAdapter(
    var listener: LocationsListener
) : PagingDataAdapter<LocationPresentation, LocationsViewHolder>(LocationsDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        LocationsViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_locations, parent, false),
            listener = listener
        )


    override fun onBindViewHolder(holderContacts: LocationsViewHolder, position: Int) {
        getItem(position)?.let { holderContacts.bind(it) }
    }

    private class LocationsDiffCallback : DiffUtil.ItemCallback<LocationPresentation>() {

        override fun areItemsTheSame(
            oldItem: LocationPresentation,
            newItem: LocationPresentation
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LocationPresentation,
            newItem: LocationPresentation
        ): Boolean {
            return oldItem == newItem
        }
    }
}