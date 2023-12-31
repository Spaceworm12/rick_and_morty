package com.example.rickandmorty.presentation.detailperson

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
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
import coil.annotation.ExperimentalCoilApi
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.buttons.PrimaryButton
import com.example.rickandmorty.presentation.composecomponents.dialogs.LoaderBlock
import com.example.rickandmorty.presentation.composecomponents.shimmer.LoadingAnimation
import com.example.rickandmorty.presentation.composecomponents.shimmer.shimmerBackground
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.navigation.Screens
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

class DetailPersonFragment : ComposeFragment() {
    private val viewModel: DetailPersonViewModel by lazy {
        ViewModelProvider(this)[DetailPersonViewModel::class.java]
    }
    companion object {
        private const val ID = "KEY"

        fun newInstance(identifyNumber: Int) = DetailPersonFragment().apply {
            arguments = bundleOf(ID to identifyNumber)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        val identifyNumber = arguments?.getInt(ID)
        viewModel.submitUIEvent(DetailPersonEvent.ShowPerson(identifyNumber!!))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @Composable
    override fun GetContent() {
        val state = viewModel.viewStateObs.observeAsState().value ?: return
        viewModel.submitUIEvent(DetailPersonEvent.CheckStatus(state.person))
        RickAndMortyMainTheme {
            DetailPersonListScreen(state)
            if (state.isLoading) {
                LoaderBlock()
            }
            if (state.exit) {
                viewModel.submitUIEvent(DetailPersonEvent.GoTo(Screens.ListPersonsScreen()))
            }
        }
    }

    @OptIn(ExperimentalCoilApi::class)
    @Composable
    private fun DetailPersonListScreen(state: DetailPersonViewState) {
        val textDel = stringResource(R.string.deleted_from_favorites)
        val textAdd = stringResource(R.string.added_to_favorites)
        var currentId = arguments?.getInt(ID)
        Column(modifier = Modifier.background(AppTheme.colors.background)) {
            val swipeForward = SwipeAction(icon = {},
                background = AppTheme.colors.background,
                isUndo = true,
                onSwipe = {
                    if (currentId != 1) {
                        state.isLoading = true
                        currentId = currentId!! - 1
                        viewModel.submitUIEvent(DetailPersonEvent.GoTo(Screens.PersonScreen(currentId!!)))
                    }
                })
            val swipeBack = SwipeAction(icon = {},
                background = AppTheme.colors.background,
                isUndo = true,
                onSwipe = {
                    state.isLoading = true
                    currentId = currentId!! + 1
                    viewModel.submitUIEvent(DetailPersonEvent.GoTo(Screens.PersonScreen(currentId!!)))
                })
            Toolbar(
                title = stringResource(id = R.string.about_person),
                subtitle = stringResource(id = R.string.about_person_subtitle),
                onBackClick = { viewModel.submitUIEvent(DetailPersonEvent.GoTo(Screens.ListPersonsScreen())) },
                actions = {
                    IconButton(onClick = {
                        if (!state.person.inFavorites) {
                            viewModel.submitUIEvent(DetailPersonEvent.AddToFavorite(state.person))
                            Toast.makeText(
                                requireContext(),
                                String.format("%s %s", state.person.name, textAdd),
                                Toast.LENGTH_SHORT
                            ).show()
                            state.person.inFavorites = true
                        } else {
                            viewModel.submitUIEvent(DetailPersonEvent.DeleteFromFavorites(state.person.id))
                            state.person.inFavorites = false
                            Toast.makeText(
                                requireContext(),
                                String.format("%s%s", state.person.name, textDel),
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }) {
                        Box(contentAlignment = Alignment.Center) {
                            if (state.person.inFavorites) {
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
            SwipeableActionsBox(
                startActions = listOf(swipeForward),
                endActions = listOf(swipeBack),
                backgroundUntilSwipeThreshold = Color.Transparent
            ) {
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
                                bottom = AppTheme.dimens.halfContentMargin * 2,
                                top = AppTheme.dimens.halfContentMargin * 2
                            ),
                        text = state.person.name ?: stringResource(R.string.not_identify),
                        style = AppTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 30.sp,
                    )
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Card(
                            modifier = Modifier
                                .shimmerBackground()
                                .height(400.dp)
                                .width(400.dp)
                                .wrapContentSize()
                                .padding(AppTheme.dimens.contentMargin)
                                .clickable(indication = null,
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = { }),

                            shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin),
                        ) {
                            Box(
                                modifier = Modifier.shimmerBackground(
                                    RoundedCornerShape(
                                        AppTheme.dimens.halfContentMargin
                                    )
                                )
                            ) {
                                val painterImage = rememberImagePainter(data = state.person.avatar)
                                when (painterImage.state) {

                                    is ImagePainter.State.Loading -> {
                                        LoadingAnimation()
                                    }

                                    is ImagePainter.State.Error -> {
                                        LoadingAnimation()
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
                        Row {
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = stringResource(id = R.string.id_person),
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = state.person.id.toString(),
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 18.sp
                            )
                        }
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
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = if ((state.person.gender.toString() == "") || (state.person.gender.toString() == "unknown")) {
                                    stringResource(id = R.string.not_identify)
                                } else {
                                    state.person.gender.toString()
                                },
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 18.sp
                            )
                        }
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
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp
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
                                } else {
                                    stringResource(id = R.string.not_identify)
                                },
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 18.sp
                            )
                        }
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
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp
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
                                } else {
                                    stringResource(id = R.string.not_identify)
                                },
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 18.sp
                            )
                        }
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
                                fontFamily = FontFamily.SansSerif,
                                fontSize = 18.sp
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = if ((state.person.status.toString() == "") || (state.person.status.toString() == "unknown")) {
                                    stringResource(id = R.string.not_identify)
                                } else {
                                    state.person.status.toString()
                                },
                                style = AppTheme.typography.body1,
                                maxLines = 1,
                                overflow = TextOverflow.Ellipsis,
                                textAlign = TextAlign.Center,
                                fontWeight = FontWeight(700),
                                fontFamily = FontFamily.Monospace,
                                fontSize = 18.sp
                            )
                        }
                        if (state.person.id != 1) {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomStart
                            ) {
                                Row {
                                    PrimaryButton(text = stringResource(id = R.string.go_back),
                                        isEnabled = true,
                                        onClick = {
                                            state.isLoading = true
                                            currentId = currentId!! - 1
                                            viewModel.submitUIEvent(DetailPersonEvent.GoTo(Screens.PersonScreen(currentId!!)))
                                        })
                                    Spacer(Modifier.weight(1f, true))
                                    PrimaryButton(text = stringResource(id = R.string.go_next),
                                        isEnabled = true,
                                        onClick = {
                                            state.isLoading = true
                                            currentId = currentId!! + 1
                                            viewModel.submitUIEvent(DetailPersonEvent.GoTo(Screens.PersonScreen(currentId!!)))
                                        })
                                }
                            }
                        } else {
                            Box(
                                modifier = Modifier.fillMaxSize(),
                                contentAlignment = Alignment.BottomEnd
                            ) {
                                PrimaryButton(text = stringResource(id = R.string.go_next),
                                    isEnabled = true,
                                    onClick = {
                                        currentId = currentId!! + 1
                                        viewModel.submitUIEvent(DetailPersonEvent.GoTo(Screens.PersonScreen(currentId!!)))
                                    })
                            }
                        }
                    }
                }
            }
        }
    }

    @Preview(
        name = "PersonsListScreen", uiMode = Configuration.UI_MODE_NIGHT_NO, showSystemUi = true
    )
    @Composable
    private fun DetailPersonScreenPreview() {
        RickAndMortyMainTheme {
            val state = DetailPersonViewState(
                isLoading = false, exit = false, person = Person(id = 0), errorText = ""
            )
            DetailPersonListScreen(state)
        }
    }
}



