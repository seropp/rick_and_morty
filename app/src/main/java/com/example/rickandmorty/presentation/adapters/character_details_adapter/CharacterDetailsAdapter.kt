package com.example.rickandmorty.presentation.adapters.character_details_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation

class CharacterDetailsAdapter(
) : ListAdapter<EpisodePresentation, CharacterDetailsViewHolder>(CharacterDetailsDiffCallback()) {

    var onEpisodeItem: ((EpisodePresentation) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharacterDetailsViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_episodes_in_characters, parent, false)
        )

    override fun onBindViewHolder(holderContacts: CharacterDetailsViewHolder, position: Int) {
        getItem(position)?.let { holderContacts.bind(it) }
        holderContacts.itemView.setOnClickListener {
            onEpisodeItem?.invoke(getItem(position)!!)
        }
    }

    private class CharacterDetailsDiffCallback : DiffUtil.ItemCallback<EpisodePresentation>() {

        override fun areItemsTheSame(
            oldItem: EpisodePresentation,
            newItem: EpisodePresentation
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: EpisodePresentation,
            newItem: EpisodePresentation
        ): Boolean {
            return oldItem == newItem
        }
    }
}