package com.example.rickandmorty.presentation.characters

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.rickandmorty.R
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.presentation.navigator

class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private var gender: String? = null
    private var status: String? = null
    private var species: String? = null
    private var type: String? = null

    companion object {
        private const val KEY_GENDER: String = "KEY_GENDER"
        private const val KEY_STATUS: String = "KEY_STATUS"
        private const val KEY_SPECIES: String = "KEY_SPECIES"
        private const val KEY_TYPE: String = "KEY_TYPE"

        @JvmStatic
        fun newInstance(
            gender: String?,
            status: String?,
            species: String?,
            type: String?
        ) =
            CharactersFragment().apply {
                arguments = Bundle().apply {
                    putString(KEY_GENDER, gender)
                    putString(KEY_STATUS, status)
                    putString(KEY_SPECIES, species)
                    putString(KEY_TYPE, type)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        init()
    }

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
            navigator().openCharactersFilterFragment()
        }

        binding.charactersLabel.setOnClickListener {
            Toast.makeText(requireContext(), "$gender, $status, $species, $type", Toast.LENGTH_SHORT).show()

        }
    }

    private fun init() {
        arguments?.let {
            gender = it.getString(KEY_GENDER)
            status = it.getString(KEY_STATUS)
            species = it.getString(KEY_SPECIES)
            type = it.getString(KEY_TYPE)
        }
    }

}