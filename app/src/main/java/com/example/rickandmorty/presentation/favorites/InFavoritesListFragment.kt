//package com.example.rickandmorty.presentation.favorites
//
//import android.content.res.Configuration
//import androidx.compose.foundation.Image
//import androidx.compose.foundation.background
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.interaction.MutableInteractionSource
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.layout.width
//import androidx.compose.foundation.lazy.grid.GridCells
//import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
//import androidx.compose.foundation.shape.RoundedCornerShape
//import androidx.compose.material.Card
//import androidx.compose.material.FloatingActionButton
//import androidx.compose.material.Icon
//import androidx.compose.material.IconButton
//import androidx.compose.material.Text
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.filled.Favorite
//import androidx.compose.material.icons.filled.FavoriteBorder
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.livedata.observeAsState
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.layout.ContentScale
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.text.style.TextOverflow
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.ViewModelProvider
//import coil.annotation.ExperimentalCoilApi
//import coil.compose.ImagePainter
//import coil.compose.rememberImagePainter
//import com.example.rickandmorty.R
//import com.example.rickandmorty.presentation.category.CategoryListFragment
//import com.example.rickandmorty.presentation.composecomponents.AppTheme
//import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
//import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
//import com.example.rickandmorty.presentation.composecomponents.dialogs.LoaderBlock
//import com.example.rickandmorty.presentation.composecomponents.shimmer.shimmerBackground
//import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
//import com.example.rickandmorty.presentation.detailperson.DetailPersonFragment
//import com.example.rickandmorty.presentation.model.modelperson.Person
//
//
//class InFavoritesListFragment : ComposeFragment() {
//
//    private val viewModel: InFavoritesListViewModel by lazy {
//        ViewModelProvider(this)[InFavoritesListViewModel::class.java]
//    }
//
//    @Composable
//    override fun GetContent() {
//        val persons = viewModel.persons.observeAsState().value ?: return
//        val state = viewModel.viewStateObs.observeAsState().value ?: return
//        RickAndMortyMainTheme {
//            PersonListScreen(persons, state.isLoading, state.exit)
//            if (state.isLoading) {
//                LoaderBlock()
//            }
//            if (state.exit) {
//                goBack()
//            }
//        }
//    }
//
//    @Composable
//    private fun PersonListScreen(persons: List<Person>, loading: Boolean, exit: Boolean) {
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .background(AppTheme.colors.background)
//        ) {
//            Toolbar(
//                title = stringResource(id = R.string.rik_wiki),
//                elevation = AppTheme.dimens.halfContentMargin,
//                onBackClick = { goBack() },
//                actions = {
//                    IconButton(onClick = {}) {
//                        Icon(
//                            Icons.Filled.Favorite, contentDescription = ""
//                        )
//                    }
//                }
//            )
//            LazyVerticalGrid(
//                columns = GridCells.Fixed(count = 1),
//            ) {
//                persons.forEach { person ->
//                    item {
//                        Person(person)
//                    }
//                }
//            }
//        }
//    }
//
//    @OptIn(ExperimentalCoilApi::class)
//    @Composable
//    private fun Person(person: Person) {
//
//        Column(
//            modifier = Modifier
//                .padding(AppTheme.dimens.contentMargin)
//                .width(100.dp),
//            horizontalAlignment = Alignment.Start
//        ) {
//
//            Row(
//                modifier = Modifier
//                    .background(
//                        color = AppTheme.colors.rippleColor,
//                        shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin)
//                    )
//                    .clickable(
//                        indication = null,
//                        interactionSource = remember { MutableInteractionSource() },
//                        onClick = { goToPerson(person.id) })
//            ) {
//                Card(
//                    modifier = Modifier
//                        .size(
//                            width = 100.dp,
//                            height = 100.dp
//                        ),
//                    shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin)
//                ) {
//
//                    val painterImage = rememberImagePainter(data = person.avatar)
//
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
//
//                Text(
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(
//                            bottom = AppTheme.dimens.halfContentMargin,
//                            top = AppTheme.dimens.halfContentMargin
//                        ),
//                    text = person.name,
//                    style = AppTheme.typography.body1,
//                    maxLines = 1,
//                    overflow = TextOverflow.Ellipsis,
//                    textAlign = TextAlign.Center,
//                )
//            }
//        }
//        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
//
//            val fabSize = 56.dp
//
//            FloatingActionButton(
//                modifier = Modifier
//                    .size(fabSize)
//                    .padding(AppTheme.dimens.sideMargin),
//                onClick = {
//                }
//            ) {
//                if(person.inFavorites)
//                Icon(
//                    imageVector = Icons.Filled.Favorite,
//                    contentDescription = null,
//                    tint = AppTheme.colors.background
//                )else{
//                    Icon(
//                        imageVector = Icons.Filled.FavoriteBorder,
//                        contentDescription = null,
//                        tint = AppTheme.colors.background)
//                }
//            }
//        }
//
//    }
//
//    private fun goBack() = requireActivity().supportFragmentManager.beginTransaction()
//        .replace(R.id.fragment_container, CategoryListFragment()).commit()
//    private fun goToPerson(id: Int) {
//        requireActivity()
//            .supportFragmentManager
//            .beginTransaction()
//            .add(R.id.fragment_container, DetailPersonFragment.newInstance(id))
//            .addToBackStack("")
//            .commit()
//    }
//    private fun goToFavorites() {
//        requireActivity()
//            .supportFragmentManager
//            .beginTransaction()
//            .add(R.id.fragment_container, DetailPersonFragment.newInstance(id))
//            .addToBackStack("")
//            .commit()
//    }
//
//
//    @Preview(name = "PersonsListScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
//    @Composable
//    private fun PersonsListScreenPreview() {
//        RickAndMortyMainTheme {
//
//            val persons = listOf(
//                Person(
//                    "1",
//                    "https://placebear.com/g/200/200",
//                    "https://rickandmortyapi.com/api/character/avatar/435.jpeg", 1
//                ),
//                Person(
//                    "2",
//                    "https://placebear.com/g/200/200",
//                    "https://rickandmortyapi.com/api/character/avatar/435.jpeg", 2,true
//                ),
//                Person(
//                    "3",
//                    "https://placebear.com/g/200/200",
//                    "https://rickandmortyapi.com/api/character/avatar/435.jpeg", 4,true
//                ),
//                Person(
//                    "4",
//                    "https://placebear.com/g/200/200",
//                    "https://rickandmortyapi.com/api/character/avatar/435.jpeg", 3
//                )
//            )
//
//            PersonListScreen(
//                persons = persons,
//                loading = false,
//                exit = false
//            )
//        }
//}
//}
//
//
