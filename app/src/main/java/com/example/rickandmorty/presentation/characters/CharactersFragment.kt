package com.example.rickandmorty.presentation.characters

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCharactersBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnFilterCharter.setOnClickListener {
            findNavController().navigate(R.id.action_charactersFragment_to_charactersFilterFragment)
        }

    }
}