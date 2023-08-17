package com.example.rickandmorty.presentation.favoritesdetail

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.rickandmorty.presentation.listperson.PersonListFragment
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail

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
        var currentId = arguments?.getInt(KEY)
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
        var currentId = arguments?.getInt(KEY)
        if(currentId!=null) {
            viewModel.submitUIEvent(FavoritesDetailPersonEvent.ShowPerson(currentId!!))
        }else{goToMainScreen()}
        if (state.isLoading) {
            LoaderBlock()
        }
        if (state.exit) {
            goToMainScreen()
        }

        Column(modifier = Modifier.background(AppTheme.colors.background)) {

            Toolbar(
                title = stringResource(id = R.string.about_person),
                onBackClick = { goToMainScreen() },
                actions = {
                    IconButton(onClick = {viewModel.submitUIEvent(
                        FavoritesDetailPersonEvent.AddToFavorite(
                        state.person!!
                    ))}) {
                        if (state.person?.inFavorites == true) {
                            Icon(
                                Icons.Filled.Favorite, contentDescription = ""
                            )
                        } else {
                            Icon(
                                Icons.Filled.FavoriteBorder, contentDescription = "")
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

                        shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin)
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
                            color = AppTheme.colors.rippleColor,
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
                            text = stringResource(id = R.string.id_person)?: "Not identified",
                            style = AppTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                            fontWeight = FontWeight(700),
                            fontFamily = FontFamily.Monospace
                        )
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(
                                    bottom = AppTheme.dimens.halfContentMargin,
                                    top = AppTheme.dimens.halfContentMargin
                                ),
                            text = state.person?.id.toString()?: "Not identified",
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
                                text = stringResource(id = R.string.gender_person)?: "Not identified",
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = state.person?.gender.toString()?: "Not identified",
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
                                text = stringResource(id = R.string.species_person)?: "Not identified",
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = state.person?.species.toString()?: "Not identified",
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
                                text = stringResource(id = R.string.type_person)?: "Not identified",
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = state.person?.type.toString()?: "Not identified",
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
                                text = stringResource(id = R.string.status_person)?: "Not identified",
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = state.person?.status.toString()?: "Not identified",
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
                PersonDetail(
                    "name",
                    "url",
                    "avatar",
                    "status",
                    "species",
                    "type",
                    "genderm",
                    123
                )
            val state = FavoritesDetailPersonViewState(isLoading = false, exit = false, person = Person(id=999))

            val person2 = PersonDetail(
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



