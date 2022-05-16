package com.example.rickandmorty.presentation.adapters.characters_adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.models.character.CharacterPresentation

class CharactersAdapter : PagingDataAdapter<CharacterPresentation, CharactersViewHolder>(CharactersDiffCallback()) {

    var onCharacterItem: ((CharacterPresentation) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CharactersViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_characters, parent, false))

    override fun onBindViewHolder(holderContacts: CharactersViewHolder, position: Int) {
        getItem(position)?.let { holderContacts.bind(it) }
        holderContacts.itemView.setOnClickListener {
            onCharacterItem?.invoke(getItem(position)!!)
        }
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