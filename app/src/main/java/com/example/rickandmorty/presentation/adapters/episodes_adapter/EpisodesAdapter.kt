package com.example.rickandmorty.presentation.adapters.episodes_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation

class EpisodesAdapter(
    var listener: EpisodesListener
) : PagingDataAdapter<EpisodePresentation, EpisodesViewHolder>(EpisodesDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        EpisodesViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_episodes, parent, false),
            listener = listener
        )


    override fun onBindViewHolder(holderContacts: EpisodesViewHolder, position: Int) {
        getItem(position)?.let { holderContacts.bind(it) }
    }

    private class EpisodesDiffCallback : DiffUtil.ItemCallback<EpisodePresentation>() {

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