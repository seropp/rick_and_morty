package com.example.rickandmorty.presentation.characters.characters_fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.rickandmorty.databinding.FragmentCharactersBinding
import com.example.rickandmorty.presentation.adapters.characters_adapter.CharactersAdapter
import com.example.rickandmorty.presentation.navigator
import kotlinx.android.synthetic.main.fragment_characters.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@ExperimentalPagingApi
@ExperimentalCoroutinesApi
@FlowPreview
class CharactersFragment : Fragment() {

    private lateinit var binding: FragmentCharactersBinding
    private var charactersAdapter: CharactersAdapter? = null
    private var gender: String? = null
    private var status: String? = null
    private var species: String? = null
    private var type: String? = null

    @ExperimentalPagingApi
    private lateinit var vm: CharactersViewModel

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

        vm = ViewModelProvider(this, CharactersViewModelProvider(requireContext()))[CharactersViewModel::class.java]
        setupCharactersList()
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

    private fun setupCharactersList() {
        val charactersAdapter = CharactersAdapter(listener = vm)
        binding.rvCharacters.apply {
            val linearLayoutManager = LinearLayoutManager(requireContext())
            layoutManager = linearLayoutManager
            setHasFixedSize(true)
            adapter = charactersAdapter
        }

        viewLifecycleOwner.lifecycleScope.launch {
            vm.charactersFlow.collectLatest {
                Log.e("TAGGGGGGGGGGGGGGGGGG", "1")
                charactersAdapter.submitData(it)
            }
        }

    }

//    private fun setupSwipeToRefresh() {
//        binding.swipeRefreshLayout.setOnRefreshListener {
//            viewModel.refresh()
//        }
//    }

    private fun init() {
        arguments?.let {
            gender = it.getString(KEY_GENDER)
            status = it.getString(KEY_STATUS)
            species = it.getString(KEY_SPECIES)
            type = it.getString(KEY_TYPE)
        }
    }

}