package com.example.rickandmorty.presentation.listperson

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.awaitFirstDown
import androidx.compose.foundation.gestures.waitForUpOrCancellation
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DisabledByDefault
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Grade
import androidx.compose.material.icons.filled.Help
import androidx.compose.material.icons.filled.List
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.category.CategoryListFragment
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.buttons.PrimaryButton
import com.example.rickandmorty.presentation.composecomponents.dialogs.LoaderBlock
import com.example.rickandmorty.presentation.composecomponents.shimmer.shimmerBackground
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
import com.example.rickandmorty.presentation.detailperson.DetailPersonFragment
import com.example.rickandmorty.presentation.favorites.FavoritesListFragment
import com.example.rickandmorty.presentation.model.modelperson.Person


class PersonListFragment : ComposeFragment() {

    private val viewModel: PersonListViewModel by lazy {
        ViewModelProvider(this)[PersonListViewModel::class.java]
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
                goBack()
            }
        }
    }

    @Composable
    private fun PersonListScreen(state: PersonListViewState) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background)
        ) {
            Toolbar(
                title = stringResource(id = R.string.rik_wiki),
                subtitle = stringResource(id = R.string.choose_person_for_info),
                elevation = AppTheme.dimens.halfContentMargin,
                onBackClick = { goBack() },
                actions = {
                    IconButton(onClick = { goToFavorites() }) {
                        Icon(
                            Icons.Filled.Grade, contentDescription = "", Modifier.size(35.dp)
                        )
                    }
                }
            )
            PrimaryButton(
                text = stringResource(id = R.string.go_back),
                isEnabled = true,
                onClick = {
                    viewModel.submitUIEvent(PersonListEvents.ToNextPage(state.currentPage+1))
                    state.currentPage+=1
                })
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 1),
            ) {
                state.persons.forEach { person ->
                    item {
                        Person(person)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    private fun Person(person: Person) {
        viewModel.submitUIEvent(PersonListEvents.CheckStatus(person))
        var selected by remember { mutableStateOf(false) }
        val scale by animateFloatAsState(if (selected) 0.9f else 1f, label = "")
        Column(
            modifier = Modifier
                .padding(AppTheme.dimens.contentMargin)
                .width(100.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Row(
                modifier = Modifier
                    .border(1.dp, AppTheme.colors.primary)
                    .background(
                        color = AppTheme.colors.rippleColor,
                        shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin)
                    )
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { goToPerson(person.id) })
            ) {
                Card(
                    modifier = Modifier
                        .size(
                            width = 100.dp,
                            height = 100.dp
                        ),
                    shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin),
                ) {
                    val painterImage = rememberImagePainter(data = person.avatar)
                    when (painterImage.state) {
                        is ImagePainter.State.Loading -> {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .shimmerBackground(RoundedCornerShape(AppTheme.dimens.halfContentMargin))
                            )
                        }

                        is ImagePainter.State.Error -> {
                            Image(
                                painter = painterResource(id = android.R.drawable.stat_notify_error),
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }

                        else -> {
                            Image(
                                painter = painterImage,
                                contentDescription = null,
                                modifier = Modifier
                                    .fillMaxSize(),
                                contentScale = ContentScale.Crop
                            )
                        }
                    }
                }
                Column() {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                bottom = AppTheme.dimens.halfContentMargin,
                                top = AppTheme.dimens.halfContentMargin
                            ),
                        text = person.name ?: "",
                        style = AppTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                    )
                    Box(contentAlignment = Alignment.Center) {
                        Column(modifier=Modifier.wrapContentSize(), horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = person.status ?: "",
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                            )
                            if (person.status == "Alive") {
                                Icon(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(top = 10.dp),
                                    imageVector = Icons.Filled.Done,
                                    contentDescription = "Alive",
                                    tint = Color.Green
                                )
                            }
                            if (person.status == "Dead") {
                                Icon(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(top = 10.dp),
                                    imageVector = Icons.Filled.DisabledByDefault,
                                    contentDescription = "Dead",
                                    tint = Color.Red
                                )
                            }
                            if (person.status != "Dead" && person.status != "Alive") {
                                Icon(
                                    modifier = Modifier
                                        .size(40.dp)
                                        .padding(top = 10.dp),
                                    imageVector = Icons.Filled.Help,
                                    contentDescription = "Not identify",
                                    tint = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {
            val fabSize = 70.dp
            FloatingActionButton(
                modifier = Modifier
                    .size(fabSize)
                    .padding(AppTheme.dimens.sideMargin)
                    .scale(scale)
                    .pointerInput(Unit) {
                        while (true) {
                            awaitPointerEventScope {
                                awaitFirstDown(false)
                                selected = true
                                waitForUpOrCancellation()
                                selected = false
                            }
                        }
                    },
                backgroundColor = AppTheme.colors.primary,
                onClick = {
                    if (!person.inFavorites) {
                        viewModel.submitUIEvent(PersonListEvents.AddToFavorite(person))
                        person.inFavorites = true
                        Toast.makeText(
                            requireContext(),
                            person.name + " Добавлен в избранное",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        viewModel.submitUIEvent(PersonListEvents.DeleteFromFavorites(person.id))
                        person.inFavorites = false
                        Toast.makeText(
                            requireContext(),
                            person.name + " Удален из избранного",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    viewModel.submitUIEvent(PersonListEvents.CheckStatus(person))
                }) {
                Box(contentAlignment = Alignment.Center) {
                    if (person.inFavorites) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Filled.Favorite,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                    if (!person.inFavorites) {
                        Icon(
                            modifier = Modifier.size(30.dp),
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = null,
                            tint = Color.Black
                        )
                    }
                }
            }
        }
    }

    private fun goBack() = requireActivity().supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, CategoryListFragment()).commit()

    private fun goToPerson(id: Int) {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, DetailPersonFragment.newInstance(id))
            .addToBackStack("")
            .commit()
    }

    private fun goToFavorites() {
        requireActivity()
            .supportFragmentManager
            .beginTransaction()
            .add(R.id.fragment_container, FavoritesListFragment())
            .addToBackStack("")
            .commit()
    }

    @Preview(name = "PersonsListScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun PersonsListScreenPreview() {
        RickAndMortyMainTheme {
            val state = PersonListViewState(
                isLoading = false,
                exit = false,
                person = Person(id = 999),
                errorText = "",
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


