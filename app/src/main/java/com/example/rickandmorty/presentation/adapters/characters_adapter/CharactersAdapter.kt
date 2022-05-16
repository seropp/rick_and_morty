package com.example.rickandmorty.presentation.adapters.characters_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.models.character.CharacterPresentation

class CharactersAdapter(
    var listener: CharactersListener
) : PagingDataAdapter<CharacterPresentation, CharactersViewHolder>(CharactersDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharactersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_characters, parent, false),
            listener = listener
        )


    override fun onBindViewHolder(holderContacts: CharactersViewHolder, position: Int) {
        getItem(position)?.let { holderContacts.bind(it) }
    }

    private class CharactersDiffCallback : DiffUtil.ItemCallback<CharacterPresentation>() {

        override fun areItemsTheSame(
            oldItem: CharacterPresentation,
            newItem: CharacterPresentation
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: CharacterPresentation,
            newItem: CharacterPresentation
        ): Boolean {
            return oldItem == newItem
        }
    }
}