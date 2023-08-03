package com.example.rickandmorty.presentation.listcharacter

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.model.Character
import com.example.rickandmorty.presentation.composecomponents.dialogs.LoaderBlock
import com.example.rickandmorty.presentation.composecomponents.shimmer.shimmerBackground
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar


class CharacterListFragment : ComposeFragment() {

    private val viewModel: CharacterListViewModel by lazy {
        ViewModelProvider(this)[CharacterListViewModel::class.java]
    }

    @Composable
    override fun GetContent() {
        val characters = viewModel.characters.observeAsState().value ?: return
        val loading = viewModel.loading.observeAsState().value ?: return

        RickAndMortyMainTheme() {
            CharacterListScreen(characters, loading)
        }
    }

    @Composable
    private fun CharacterListScreen(characters: List<Character>, loading: Boolean) {

        if (loading) LoaderBlock()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background)
        ) {
            Toolbar(
                title = stringResource(id = R.string.rik_wiki),
                elevation = AppTheme.dimens.halfContentMargin,
                onBackClick = { goBack() }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 3)
            ) {
                characters.forEach { character ->
                    item {
                        Character(character)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    private fun Character(character: Character) {

        Column(
            modifier = Modifier
                .padding(AppTheme.dimens.contentMargin)
                .width(100.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                modifier = Modifier.padding(bottom = AppTheme.dimens.halfContentMargin),
                text = character.name,
                style = AppTheme.typography.body1,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Card(
                modifier = Modifier
                    .size(
                        width = 100.dp,
                        height = 100.dp
                    )
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { }
                    ),
                shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin)
            ) {

                val painterImage = rememberImagePainter(data = character.url)

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
                                .fillMaxSize()
                                .clickable(
                                    onClick = {

                                    },
                                ),
                            contentScale = ContentScale.Crop
                        )
                    }

                    else -> {
                        Image(
                            painter = painterImage,
                            contentDescription = null,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable(
                                    onClick = {
                                    }
                                ),
                            contentScale = ContentScale.Crop
                        )
                    }
                }
            }
        }
    }

    private fun goBack() = requireActivity().supportFragmentManager.popBackStack()

    @Preview(name = "CharactersListScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun PokemonListScreenPreview() {
        RickAndMortyMainTheme {

            val characters = listOf(
                Character("1", "https://placebear.com/g/200/200"),
                Character("2", "https://placebear.com/g/200/200"),
                Character("3", "https://placebear.com/g/200/200"),
                Character("4", "https://placebear.com/g/200/200")
            )

            CharacterListScreen(
                characters = characters,
                loading = false
            )
        }
    }
}


