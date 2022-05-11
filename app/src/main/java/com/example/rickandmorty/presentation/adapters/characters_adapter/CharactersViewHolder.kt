package com.example.rickandmorty.presentation.adapters.characters_adapter

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.ItemCharactersBinding
import com.example.rickandmorty.presentation.models.character.CharacterPresentation


class CharactersViewHolder(
    itemView: View,
    private val listener: CharactersListener,
) : RecyclerView.ViewHolder(itemView) {

    private val binding = ItemCharactersBinding.bind(itemView)

    fun onBind(characterPresentation: CharacterPresentation?) = with(binding) {
        characterName.text = characterPresentation?.name
        characterSapience.text = characterPresentation?.species
        characterStatus.text = characterPresentation?.status

        when (characterPresentation?.gender) {
            "Male" -> itemGender.setImageResource(R.drawable.ic_male)
            "Female" -> itemGender.setImageResource(R.drawable.ic_female)
            "Unknown" -> itemGender.setImageResource(R.drawable.ic_unknown)
            else -> itemGender.setImageResource(R.drawable.ic_genderless)
        }


//        CoroutineScope(context = Dispatchers.IO).launch {
//            contactImage.load("https://picsum.photos/200/300?random=${(contact.id?.times(2))}") {
//                crossfade(true)
//                placeholder(R.drawable.ic_launcher_foreground)
//                transformations(CircleCropTransformation())
//            }
//        }

        characterCard.setOnClickListener {
            listener.onItemClick(id = characterPresentation!!.id)
        }

    }
}