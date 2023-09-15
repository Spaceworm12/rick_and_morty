package com.example.rickandmorty.presentation.favorites

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.dialogs.LoaderBlock
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.navigation.Screens


class FavoritesListFragment : ComposeFragment() {
    private val viewModel: FavoritesListViewModel by lazy {
        ViewModelProvider(this)[FavoritesListViewModel::class.java]
    }

    @Composable
    override fun GetContent() {
        val state = viewModel.viewStateObs.observeAsState().value ?: return
        RickAndMortyMainTheme {
            PersonListScreen(state)
            if (state.isLoading) {
                LoaderBlock()
            }
            if (state.exit) {
                viewModel.submitUIEvent(FavoritesListEvents.GoTo(Screens.ListPersonsScreen()))
            }
        }
    }

    @Composable
    private fun PersonListScreen(state: FavoritesListViewState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background)
        ) {
            Toolbar(
                title = stringResource(id = R.string.rik_wiki),
                subtitle = stringResource(id = R.string.Favorites),
                elevation = AppTheme.dimens.halfContentMargin,
                onBackClick = { viewModel.submitUIEvent(FavoritesListEvents.GoTo(Screens.ListPersonsScreen())) },
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 1),
            ) {
                state.persons.forEach { person ->
                    item {
                        PersonCard(person) { event -> viewModel.submitUIEvent(event) }
                    }
                }
            }
        }
    }

    @Preview(name = "PersonsListScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun PersonsListScreenPreview() {
        RickAndMortyMainTheme {
            val state = FavoritesListViewState(
                isLoading = false,
                errorText = "",
                exit = false,
                person = Person(id = 999),
                persons = listOf(
                    Person(
                        "1",
                        "https://placebear.com/g/200/200",
                        "https://rickandmortyapi.com/api/character/avatar/435.jpeg",
                        "asd",
                        "asd",
                        "asd",
                        "asd",
                        1,
                        false
                    ),
                    Person(
                        "1",
                        "https://placebear.com/g/200/200",
                        "https://rickandmortyapi.com/api/character/avatar/435.jpeg",
                        "asd",
                        "asd",
                        "asd",
                        "asd",
                        1,
                        false
                    ),
                    Person(
                        "1",
                        "https://placebear.com/g/200/200",
                        "https://rickandmortyapi.com/api/character/avatar/435.jpeg",
                        "asd",
                        "asd",
                        "asd",
                        "asd",
                        1,
                        false
                    ),
                    Person(
                        "1",
                        "https://placebear.com/g/200/200",
                        "https://rickandmortyapi.com/api/character/avatar/435.jpeg",
                        "asd",
                        "asd",
                        "asd",
                        "asd",
                        1,
                        false
                    )
                )
            )
            PersonListScreen(state)

        }
    }
}


