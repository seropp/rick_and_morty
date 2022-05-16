package com.example.rickandmorty.presentation.adapters.episodes_adapter

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ItemCharactersBinding
import com.example.rickandmorty.databinding.ItemEpisodesBinding
import com.example.rickandmorty.presentation.adapters.characters_adapter.CharactersListener
import com.example.rickandmorty.presentation.models.character.CharacterPresentation
import com.example.rickandmorty.presentation.models.episode.EpisodePresentation


class EpisodesViewHolder(
    itemView: View,
    private val listener: EpisodesListener,
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemEpisodesBinding.bind(itemView)

    fun bind(item: EpisodePresentation) = with(binding) {
        nameEpisodeItem.text = item.name
        dataEpisodeItem.text = item.air_date
        numberEpisodeItem.text = item.episode

//        characterSapience.text = item.species
//        characterStatus.text = item.status
//        idid.text = item.id.toString()
//        Log.e("Log", "______________page is ${item.imageUrl}")
//        Log.e("Log", "______________page is ${item.imageUrl.length}")
//        when (item.gender) {
//            "Male" -> itemGender.setImageResource(R.drawable.ic_male)
//            "Female" -> itemGender.setImageResource(R.drawable.ic_female)
//            "Unknown" -> itemGender.setImageResource(R.drawable.ic_unknown)
//            else -> itemGender.setImageResource(R.drawable.ic_genderless)
//        }

        episodeCard.setOnClickListener {
            listener.onItemClick(id = item.id)
        }
    }
}