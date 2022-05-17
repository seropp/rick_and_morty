package com.example.rickandmorty.presentation.adapters.characters_adapter_for_details

import android.annotation.SuppressLint
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ItemCharactersBinding
import com.example.rickandmorty.presentation.models.character.CharacterPresentation

class CharactersListForDetailsViewHolder(
    itemView: View
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemCharactersBinding.bind(itemView)

    @SuppressLint("SetTextI18n")
    fun bind(item: CharacterPresentation) = with(binding) {
        characterName.text = item.name
        characterSapience.text = item.species
        characterStatus.text = item.status

        when (item.gender) {
            "Male" -> itemGender.setImageResource(R.drawable.ic_male)
            "Female" -> itemGender.setImageResource(R.drawable.ic_female)
            "Unknown" -> itemGender.setImageResource(R.drawable.ic_unknown)
            else -> itemGender.setImageResource(R.drawable.ic_genderless)
        }

        Glide.with(itemView)
            .load(item.imageUrl)
            .placeholder(R.drawable.ic_loading)
            .error(R.drawable.ic_dissconect)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .centerCrop()
            .into(characterImage)
    }
}