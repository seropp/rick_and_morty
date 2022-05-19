package com.example.rickandmorty.presentation

import androidx.fragment.app.Fragment


fun Fragment.navigator(): Navigator {
    return requireActivity() as Navigator
}

interface Navigator {

    fun openCharactersFragment()
    fun openEpisodesFragment()
    fun openLocationsFragment()

    fun openCharactersFilterFragment()
    fun openEpisodesFilterFragment()
    fun openLocationsFilterFragment()

    fun openCharactersFragmentWithArg(
        status: String?,
        gender: String?,
        species: String?,
        type: String?
    )

    fun openEpisodesFragmentWithArg(episode: String?)
    fun openLocationsFragmentWithArg(type: String?, dimension: String?)

    fun openCharacterDetailFragment(characterId: Int)
    fun openEpisodesDetailFragment(episodeId: Int)
    fun openLocationsDetailFragment(locationId: Int)

    fun backButton()

}