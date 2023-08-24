package com.example.rickandmorty.presentation.favoritesdetail

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.dialogs.LoaderBlock
import com.example.rickandmorty.presentation.composecomponents.shimmer.shimmerBackground
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
import com.example.rickandmorty.presentation.favorites.FavoritesListFragment
import com.example.rickandmorty.presentation.model.modelperson.Person

class FavoritesDetailPersonFragment : ComposeFragment() {
    private val viewModel: FavoritesDetailPersonViewModel by lazy {
        ViewModelProvider(this)[FavoritesDetailPersonViewModel::class.java]
    }

    companion object {
        private const val KEY = "KEY"
        fun newInstance(identifyNumber: Int) = FavoritesDetailPersonFragment().apply {
            arguments = bundleOf(KEY to identifyNumber)
        }
    }

    @Composable
    override fun GetContent() {
        val state = viewModel.viewStateObs.observeAsState().value ?: return
        RickAndMortyMainTheme {
            DetailPersonListScreen(state)
            if (state.isLoading) {
                LoaderBlock()
            }
            if (state.exit) {
                goToMainScreen()
            }
        }
    }

    @Composable
    private fun DetailPersonListScreen(state: FavoritesDetailPersonViewState) {
        val currentId = arguments?.getInt(KEY)
        if (currentId != null) {
            viewModel.submitUIEvent(FavoritesDetailPersonEvent.ShowPerson(currentId))
        } else {
            goToMainScreen()
        }
        if (state.isLoading) {
            LoaderBlock()
        }
        if (state.exit) {
            goToMainScreen()
        }
        Column(modifier = Modifier.background(AppTheme.colors.background)) {
            Toolbar(
                title = stringResource(id = R.string.about_person),
                subtitle = stringResource(id = R.string.about_person_subtitle),
                onBackClick = { goToMainScreen() },
                actions = {
                    IconButton(onClick = {
                        viewModel.submitUIEvent(
                            FavoritesDetailPersonEvent.DeletePersonFromFavorite(
                                state.person?.id!!
                            )
                        )
                        state.person.inFavorites = false
                        Toast.makeText(
                            requireContext(),
                            state.person.name + " Удален из избранного",
                            Toast.LENGTH_SHORT
                        ).show()
                        goToMainScreen()
                    }) {
                        Box(contentAlignment = Alignment.Center) {
                            if (state.person!!.inFavorites) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Filled.Favorite,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                            if (!state.person.inFavorites) {
                                Icon(
                                    modifier = Modifier.size(30.dp),
                                    imageVector = Icons.Filled.FavoriteBorder,
                                    contentDescription = null,
                                    tint = Color.Black
                                )
                            }
                        }
                    }
                },
            )
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(AppTheme.dimens.contentMargin),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = AppTheme.dimens.halfContentMargin,
                            top = AppTheme.dimens.halfContentMargin
                        ),
                    text = state.person?.name ?: "Not identified",
                    style = AppTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight(700),
                    fontFamily = FontFamily.Monospace,
                    fontSize = 25.sp,
                )
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .height(400.dp)
                            .width(400.dp)
                            .wrapContentSize()
                            .padding(AppTheme.dimens.contentMargin)
                            .clickable(indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { }),
                        shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin),
                    ) {
                        val painterImage = rememberImagePainter(data = state.person?.avatar)
                        when (painterImage.state) {
                            is ImagePainter.State.Loading -> {
                                Box(
                                    modifier = Modifier.shimmerBackground(
                                        RoundedCornerShape(
                                            AppTheme.dimens.halfContentMargin
                                        )
                                    )
                                )
                            }

                            is ImagePainter.State.Error -> {
                                Image(
                                    painter = painterResource(id = android.R.drawable.stat_notify_error),
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            else -> {
                                Image(
                                    painter = painterImage,
                                    contentDescription = null,
                                    modifier = Modifier.fillMaxSize(),
                                    contentScale = ContentScale.Fit
                                )
                            }
                        }
                    }
                }
                Column(
                    modifier = Modifier
                        .background(
                            color = Color(0x20000000),
                            shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin)
                        )
                        .fillMaxWidth()
                        .padding(AppTheme.dimens.contentMargin),
                    horizontalAlignment = Alignment.Start
                ) {
                    Row() {
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(
                                    bottom = AppTheme.dimens.halfContentMargin,
                                    top = AppTheme.dimens.halfContentMargin
                                ),
                            text = stringResource(id = R.string.id_person) ?: "Not identified",
                            style = AppTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily.SansSerif
                        )
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(
                                    bottom = AppTheme.dimens.halfContentMargin,
                                    top = AppTheme.dimens.halfContentMargin
                                ),
                            text = state.person?.id.toString() ?: "Not identified",
                            style = AppTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily.Monospace
                        )
                    }
                    Column {
                        Row {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = stringResource(id = R.string.gender_person),
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.SansSerif
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = if ((state.person.gender.toString() == "")||(state.person.gender.toString() == "unknown")) {
                                    stringResource(id = R.string.not_identify)}else{state.person.gender.toString()},
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                    Column {
                        Row {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = stringResource(id = R.string.species_person),
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.SansSerif
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = if ((state.person.species.toString() != "")) {
                                    state.person.species.toString()
                                }else{
                                    stringResource(id = R.string.not_identify)},
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                    Column {
                        Row {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = stringResource(id = R.string.type_person),
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.SansSerif
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = if ((state.person.type.toString() != "")) {
                                    state.person.type.toString()
                                }else{
                                    stringResource(id = R.string.not_identify)},
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                    Column {
                        Row {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = stringResource(id = R.string.status_person),
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.SansSerif
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = if ((state.person.status.toString() == "")||(state.person.status.toString() == "unknown")) {
                                    stringResource(id = R.string.not_identify)}else{state.person.status.toString()},
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace
                            )
                        }
                    }
                }
            }
        }
    }

    private fun goToMainScreen() = requireActivity().supportFragmentManager.beginTransaction()
        .replace(R.id.fragment_container, FavoritesListFragment()).commit()

    @Preview(name = "PersonsListScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun DetailPersonScreenPreview() {
        RickAndMortyMainTheme {
            val person =
                Person(
                    "name",
                    "url",
                    "avatar",
                    "status",
                    "species",
                    "type",
                    "genderm",
                    123
                )
            val state = FavoritesDetailPersonViewState(
                isLoading = false,
                exit = false,
                person = Person(id = 999)
            )
            val person2 = Person(
                name = "-",
                url = "-",
                avatar = "-",
                status = "-",
                species = "-",
                type = "-",
                gender = "-",
                id = 1
            )
            DetailPersonListScreen(state)
        }
    }
}



