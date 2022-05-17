package com.example.rickandmorty.presentation.adapters.character_details_adapter

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ItemCharactersBinding
import com.example.rickandmorty.databinding.ItemEpisodesInCharactersBinding
import com.example.rickandmorty.presentation.models.character.CharacterPresentation
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation

class EpisodeListForDetailsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemEpisodesInCharactersBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun bind(item: EpisodePresentation) = with(binding) {
        episodeNameInItem.text = item.episode + " | " + item.name
        dataSapience.text = item.air_date
    }
}