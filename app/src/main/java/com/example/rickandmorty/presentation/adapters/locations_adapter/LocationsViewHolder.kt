package com.example.rickandmorty.presentation.adapters.locations_adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ItemCharactersBinding
import com.example.rickandmorty.databinding.ItemEpisodesBinding
import com.example.rickandmorty.databinding.ItemLocationsBinding
import com.example.rickandmorty.presentation.adapters.characters_adapter.CharactersListener
import com.example.rickandmorty.presentation.adapters.episodes_adapter.EpisodesListener
import com.example.rickandmorty.presentation.models.character.CharacterPresentation
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation
import com.example.rickandmorty.presentation.models.location.LocationPresentation


class LocationsViewHolder(
    itemView: View,
    private val listener: LocationsListener,
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemLocationsBinding.bind(itemView)

    fun bind(item: LocationPresentation) = with(binding) {

        nameLocationItem.text = item.name
        dimensionLocationItem.text = item.dimension
        typeLocationItem.text = item.dimension

        locationCard.setOnClickListener {
            listener.onItemClick(id = item.id)
        }
    }
}