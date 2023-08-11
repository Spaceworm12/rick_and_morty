package com.example.rickandmorty.presentation.detailperson

import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
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
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.dialogs.LoaderBlock
import com.example.rickandmorty.presentation.composecomponents.shimmer.shimmerBackground
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail

class DetailPersonFragment : ComposeFragment() {
    private val viewModel: DetailPersonViewModel by lazy {
        ViewModelProvider(this)[DetailPersonViewModel::class.java]
    }

    companion object {
        private const val KEY = "KEY"

        fun newInstance(identifyNumber: Int) = DetailPersonFragment().apply {
            arguments = bundleOf(KEY to identifyNumber)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val identifyNumber = arguments?.getInt(KEY)
        viewModel.submitUIEvent(DetailPersonEvent.ShowPerson(identifyNumber!!))
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    @Composable
    override fun GetContent() {
        val person = PersonDetail()
        val isLoading: Boolean = false
        val exit: Boolean = false
        val state = viewModel.viewStateObs.observeAsState().value ?: return
        RickAndMortyMainTheme {
            DetailPersonListScreen(person, isLoading, exit)
            if (state.isLoading) {
                LoaderBlock()
            }
            if (state.exit) {
                goBack()
            }
        }
    }

    @Composable
    private fun DetailPersonListScreen(person: PersonDetail, isLoading: Boolean, exit: Boolean) {
        if (isLoading) {
            LoaderBlock()
        }
        if (exit) {
            goBack()
        }

        Column(modifier = Modifier.background(AppTheme.colors.background)) {

            Toolbar(
                title = stringResource(id = R.string.about_person),
                onBackClick = { goBack() },
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
                        text = person.name ?: "Not identified",
                        style = AppTheme.typography.body1,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight(700),
                        fontFamily = FontFamily.Monospace,
                        fontSize = 25.sp,
                    )
                Column(horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .width(300.dp)
                            .height(300.dp)
                            .padding(AppTheme.dimens.contentMargin)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() },
                                onClick = { }
                            ),

                        shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin)
                    ) {

                        val painterImage = rememberImagePainter(data = person.avatar)

                        when (painterImage.state) {
                            is ImagePainter.State.Loading -> {
                                Box(
                                    modifier = Modifier
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
                            text = stringResource(id = R.string.id_person),
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
                            text = person.id.toString(),
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
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = person.gender.toString(),
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
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = person.species.toString(),
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
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = person.type.toString(),
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
                                fontFamily = FontFamily.Monospace
                            )
                            Text(
                                modifier = Modifier
                                    .wrapContentSize()
                                    .padding(
                                        bottom = AppTheme.dimens.halfContentMargin,
                                        top = AppTheme.dimens.halfContentMargin
                                    ),
                                text = person.status.toString(),
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

    private fun goBack() = requireActivity().supportFragmentManager.popBackStack()

    @Preview(name = "PersonsListScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun DetailPersonScreenPreview() {
        RickAndMortyMainTheme {
            val person =
                PersonDetail("name", "url", "avatar", "status", "species", "type", "genderm", 123)
            val isLoading: Boolean = false
            val exit: Boolean = false
            DetailPersonListScreen(person, isLoading, exit)
        }
    }
}



