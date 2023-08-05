package com.example.rickandmorty.presentation.listelocations

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
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
import com.example.rickandmorty.presentation.composecomponents.dialogs.LoaderBlock
import com.example.rickandmorty.presentation.composecomponents.shimmer.shimmerBackground
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
import com.example.rickandmorty.presentation.model.modellocation.Location


class LocationsListFragment : ComposeFragment() {

    private val viewModel: LocationsListViewModel by lazy {
        ViewModelProvider(this)[LocationsListViewModel::class.java]
    }

    @Composable
    override fun GetContent() {
        val locations = viewModel.locations.observeAsState().value ?: return
        val loading = viewModel.loading.observeAsState().value ?: return

        RickAndMortyMainTheme() {
            LocationListScreen(locations, loading)
        }
    }

    @Composable
    private fun LocationListScreen(locations: List<Location>, loading: Boolean) {

        if (loading) LoaderBlock()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.background)
        ) {
            Toolbar(
                title = stringResource(id = R.string.rik_wiki),
                elevation = AppTheme.dimens.halfContentMargin,
                onBackClick = { }
            )
            LazyVerticalGrid(
                columns = GridCells.Fixed(count = 1)
            ) {
                locations.forEach { location ->
                    item {
                        Location(location)
                    }
                }
            }
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    private fun Location(location: Location) {

        Column(
            modifier = Modifier
                .padding(AppTheme.dimens.contentMargin)
                .width(100.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Row(
                modifier = Modifier.background(
                    color = AppTheme.colors.rippleColor,
                    shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin)
                )
            ) {
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

                    val painterImage = rememberImagePainter(data = location.name)

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

                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = AppTheme.dimens.halfContentMargin,
                            top = AppTheme.dimens.halfContentMargin
                        ),
                    text = location.type,
                    style = AppTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.TopEnd) {

                    val fabSize = 56.dp

                    FloatingActionButton(
                        modifier = Modifier
                            .size(fabSize)
                            .padding(AppTheme.dimens.sideMargin),
                        onClick = {
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Filled.FavoriteBorder,
                            contentDescription = null,
                            tint = AppTheme.colors.background
                        )
                    }
                }

    }

    private fun goBack() = requireActivity().supportFragmentManager.popBackStack()

    @Preview(name = "LocationsListScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun LocationsListScreenPreview() {
        RickAndMortyMainTheme {

            val locations = listOf(
                Location("1", "https://placebear.com/g/200/200","https://rickandmortyapi.com/api/character/avatar/435.jpeg"),
                Location("2", "https://placebear.com/g/200/200","https://rickandmortyapi.com/api/character/avatar/435.jpeg"),
                Location("3", "https://placebear.com/g/200/200","https://rickandmortyapi.com/api/character/avatar/435.jpeg"),
                Location("4", "https://placebear.com/g/200/200","https://rickandmortyapi.com/api/character/avatar/435.jpeg")
            )

            LocationListScreen(
                locations = locations,
                loading = false
            )
        }
    }
}


