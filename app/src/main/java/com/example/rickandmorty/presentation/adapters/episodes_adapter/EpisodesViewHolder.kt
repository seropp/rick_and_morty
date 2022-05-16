package com.example.rickandmorty.presentation.adapters.episodes_adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.rickandmorty.databinding.ItemEpisodesBinding
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation


class EpisodesViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemEpisodesBinding.bind(itemView)

    fun bind(item: EpisodePresentation) = with(binding) {

        nameEpisodeItem.text = item.name
        dataEpisodeItem.text = item.air_date
        numberEpisodeItem.text = item.episode
    }
}