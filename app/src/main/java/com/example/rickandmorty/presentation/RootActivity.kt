package com.example.rickandmorty.presentation

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.FragmentManager
import androidx.paging.ExperimentalPagingApi
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.screens.characters.character_details_fragment.CharacterDetailsFragment
import com.example.rickandmorty.presentation.screens.characters.characters_filter_fragment.CharacterFiltersFragment
import com.example.rickandmorty.presentation.screens.characters.characters_fragment.CharactersFragment
import com.example.rickandmorty.presentation.screens.episodes.episode_details_fragment.EpisodeDetailsFragment
import com.example.rickandmorty.presentation.screens.episodes.episodes_filter_fragment.EpisodeFiltersFragment
import com.example.rickandmorty.presentation.screens.episodes.episodes_fragment.EpisodesFragment
import com.example.rickandmorty.presentation.screens.locations.location_details_fragment.LocationDetailsFragment
import com.example.rickandmorty.presentation.screens.locations.locations_filter_fragment.LocationFiltersFragment
import com.example.rickandmorty.presentation.screens.locations.locations_fragment.LocationsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.coroutines.ExperimentalCoroutinesApi


@ExperimentalPagingApi
class RootActivity : AppCompatActivity(), Navigator {

    private val vm: SplashScreenViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen().apply {
            setKeepOnScreenCondition {
                vm.isLoading.value
            }
        }
        setContentView(R.layout.activity_root)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .add(
                    R.id.container_navigator,
                    CharactersFragment(),
                    "ADD FIRST FRAGMENT"
                ).commit()
        } else {
            if (supportFragmentManager.backStackEntryCount == 0) {
                supportFragmentManager.popBackStack("ADD FIRST FRAGMENT", 0)
            } else {
                val backEntry: FragmentManager.BackStackEntry =
                    supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
                val tag = backEntry.name
                supportFragmentManager.popBackStack(tag, 0)
            }
        }

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.charactersFragment -> {
                    openCharactersFragment()
                    true
                }
                R.id.locationsFragment -> {
                    openLocationsFragment()
                    true
                }
                R.id.episodesFragment -> {
                    openEpisodesFragment()
                    true
                }
                else -> false
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    override fun onBackPressed() {

        val fragment1: CharactersFragment? =
            supportFragmentManager.findFragmentByTag("CHARACTERS_FRAGMENT") as CharactersFragment?
        val fragment2: CharactersFragment? =
            supportFragmentManager.findFragmentByTag("OPEN_CharactersFragmentWithArg") as CharactersFragment?
        val fragment3: CharactersFragment? =
            supportFragmentManager.findFragmentByTag("ADD FIRST FRAGMENT") as CharactersFragment?
        val fragment4: CharactersFragment? =
            supportFragmentManager.findFragmentByTag("OPEN_CHARACTERS_FRAGMENT") as CharactersFragment?
        if (fragment1 != null && fragment1.isVisible ||
            fragment2 != null && fragment2.isVisible ||
            fragment3 != null && fragment3.isVisible ||
            fragment4 != null && fragment4.isVisible ) {
            finish()
        }
        super.onBackPressed()
    }

    override fun openCharactersFragment() {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_navigator,
                CharactersFragment(),
                "CHARACTERS_FRAGMENT"
            ).addToBackStack("OPEN_CHARACTERS_FRAGMENT")
            .commit()
        supportFragmentManager.popBackStack("CHARACTERS_FRAGMENT", FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    override fun openEpisodesFragment() {
        if ( supportFragmentManager.backStackEntryCount > 0 ) {
            val backEntry: FragmentManager.BackStackEntry =
                supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
            val tag = backEntry.name
            if(tag == "OPEN_EPISODE_FRAGMENT") return
        }

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_navigator,
                EpisodesFragment(),
                "EPISODES_FRAGMENT"
            ).addToBackStack("OPEN_EPISODE_FRAGMENT")
            .commit()
    }

    override fun openLocationsFragment() {
        if ( supportFragmentManager.backStackEntryCount > 0 ) {
            val backEntry: FragmentManager.BackStackEntry =
                supportFragmentManager.getBackStackEntryAt(supportFragmentManager.backStackEntryCount - 1)
            val tag = backEntry.name
            if(tag == "OPEN_LOCATION_FRAGMENT") return
        }

        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_navigator,
                LocationsFragment(),
                "LOCATIONS_FRAGMENT"
            ).addToBackStack("OPEN_LOCATION_FRAGMENT")
            .commit()
    }

    override fun openCharactersFilterFragment() {
        CharacterFiltersFragment().show(supportFragmentManager, "CHARACTERS_FILTER_FRAGMENT")
    }

    override fun openEpisodesFilterFragment() {
        EpisodeFiltersFragment().show(supportFragmentManager, "EPISODES_FILTER_FRAGMENT")
    }

    override fun openLocationsFilterFragment() {

        LocationFiltersFragment().show(supportFragmentManager, "LOCATIONS_FILTER_FRAGMENT")
    }

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    override fun openCharactersFragmentWithArg(
        status: String?,
        gender: String?,
        species: String?,
        type: String?
    ) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_navigator,
                CharactersFragment.newInstance(
                    gender = gender,
                    status = status,
                    species = species,
                    type = type
                ),
                "CHARACTERS_FRAGMENT_ARG"
            ).addToBackStack("OPEN_CharactersFragmentWithArg")
            .commit()
    }

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    override fun openEpisodesFragmentWithArg(episode: String?) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_navigator,
                EpisodesFragment.newInstance(
                    episode = episode
                ),
                "EPISODES_FRAGMENT_ARG"
            ).addToBackStack("OPEN_EpisodesFragmentWithArg")
            .commit()
    }

    @OptIn(ExperimentalCoroutinesApi::class, kotlinx.coroutines.FlowPreview::class)
    override fun openLocationsFragmentWithArg(type: String?, dimension: String?) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_navigator,
                LocationsFragment.newInstance(
                    types = type,
                    dimensions = dimension,
                ),
                "LOCATIONS_FRAGMENT_ARG"
            ).addToBackStack("OPEN_LocationsFragmentWithArg")
            .commit()
    }

    override fun openCharacterDetailFragment(characterId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_navigator,
                CharacterDetailsFragment.newInstance(
                    characterId = characterId
                ),
                "CHARACTERS_DETAIL_FRAGMENT"
            ).addToBackStack("OPEN_CharacterDetailFragment")
            .commit()
    }

    override fun openEpisodesDetailFragment(episodeId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_navigator,
                EpisodeDetailsFragment.newInstance(
                    episodeId = episodeId
                ),
                "EPISODES_DETAIL_FRAGMENT"
            ).addToBackStack("OPEN_EpisodesDetailFragment")
            .commit()
    }

    override fun openLocationsDetailFragment(locationId: Int) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.container_navigator,
                LocationDetailsFragment.newInstance(
                    locationId = locationId
                ),
                "LOCATIONS_DETAIL_FRAGMENT"
            ).addToBackStack("OPEN_LocationsDetailFragment")
            .commit()
    }

    override fun backButton() {
        onBackPressed()
    }

}