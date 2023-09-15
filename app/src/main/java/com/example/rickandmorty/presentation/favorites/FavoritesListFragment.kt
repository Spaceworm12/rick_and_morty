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
                        Spacer(modifier = Modifier.height(30.dp))
                    }
                }
            }
        }
    }

//    @OptIn(ExperimentalCoilApi::class)
//    @Composable
//    private fun ShowPerson(person: Person) {
//        val textDel = stringResource(R.string.deleted_from_favorites)
//        Column(
//            modifier = Modifier
//                .padding(AppTheme.dimens.contentMargin)
//                .width(100.dp),
//            horizontalAlignment = Alignment.Start
//        ) {
//
//            Row(
//                modifier = Modifier
//                    .border(1.dp, AppTheme.colors.primary)
//                    .background(
//                        color = AppTheme.colors.rippleColor,
//                        shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin)
//                    )
//                    .clickable(
//                        indication = null,
//                        interactionSource = remember { MutableInteractionSource() },
//                        onClick = {
//                            viewModel.submitUIEvent(
//                                FavoritesListEvents.GoTo(
//                                    Screens.FavoritePersonScreen(
//                                        person.id
//                                    )
//                                )
//                            )
//                        })
//            ) {
//                Card(
//                    modifier = Modifier
//                        .size(
//                            width = 100.dp,
//                            height = 100.dp
//                        ),
//                    shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin),
//                ) {
//                    val painterImage = rememberImagePainter(data = person.avatar)
//                    when (painterImage.state) {
//                        is ImagePainter.State.Loading -> {
//                            Box(
//                                modifier = Modifier
//                                    .fillMaxSize()
//                                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.halfContentMargin))
//                            )
//                        }
//
//                        is ImagePainter.State.Error -> {
//                            Image(
//                                painter = painterResource(id = android.R.drawable.stat_notify_error),
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxSize(),
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//
//                        else -> {
//                            Image(
//                                painter = painterImage,
//                                contentDescription = null,
//                                modifier = Modifier
//                                    .fillMaxSize(),
//                                contentScale = ContentScale.Crop
//                            )
//                        }
//                    }
//                }
//                Text(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            bottom = AppTheme.dimens.halfContentMargin,
//                            top = AppTheme.dimens.halfContentMargin
//                        ),
//                    text = person.name ?: "",
//                    style = AppTheme.typography.body1,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    textAlign = TextAlign.Center,
//                )
//            }
//        }
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
//
//            val fabSize = 70.dp
//
//            FloatingActionButton(
//                modifier = Modifier
//                    .size(fabSize)
//                    .padding(AppTheme.dimens.sideMargin),
//                backgroundColor = AppTheme.colors.primary,
//                onClick = {
//                    !person.inFavorites
//                    viewModel.submitUIEvent(FavoritesListEvents.DeleteFromFavorites(person.id))
//                    Toast.makeText(
//                        requireContext(),
//                        String.format("%s%s", person.name, textDel),
//                        Toast.LENGTH_SHORT
//                    ).show()
//                }
//            ) {
//                Box(contentAlignment = Alignment.Center) {
//                    if (person.inFavorites) {
//                        Icon(
//                            modifier = Modifier.size(30.dp),
//                            imageVector = Icons.Filled.Favorite,
//                            contentDescription = null,
//                            tint = Color.Black,
//                        )
//                    }
//                    if (!person.inFavorites) {
//                        Icon(
//                            modifier = Modifier.size(30.dp),
//                            imageVector = Icons.Filled.FavoriteBorder,
//                            contentDescription = null,
//                            tint = Color.Black
//                        )
//                    }
//                }
//            }
//        }
//
//    }

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


