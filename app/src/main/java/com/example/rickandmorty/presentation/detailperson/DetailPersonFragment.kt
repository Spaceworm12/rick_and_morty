package com.example.rickandmorty.presentation.detailperson

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.shimmer.shimmerBackground
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
import com.example.rickandmorty.presentation.listperson.PersonListViewModel
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.model.modelperson.PersonDetail

class DetailPersonFragment(person:Person) : ComposeFragment() {
    private val viewModel: PersonListViewModel by lazy {
        ViewModelProvider(this)[PersonListViewModel::class.java]
    }

    @Composable
    override fun GetContent() {
        val loading = false
        val exit = false

        RickAndMortyMainTheme() {
            DetailPersonListScreen(person, loading)
        }
    }

    @Composable
    private fun DetailPersonListScreen(person: PersonDetail, exit: Boolean) {
        if (exit) goBack()
        Column(modifier = Modifier.background(AppTheme.colors.background)) {

            Toolbar(
                title = person.name,
                onBackClick = { goBack() }
            )
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
                    Column() {
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(
                                    bottom = AppTheme.dimens.halfContentMargin,
                                    top = AppTheme.dimens.halfContentMargin
                                ),
                            text = person.name,
                            style = AppTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                        )
                        Text(
                            modifier = Modifier
                                .wrapContentSize()
                                .padding(
                                    bottom = AppTheme.dimens.halfContentMargin,
                                    top = AppTheme.dimens.halfContentMargin
                                ),
                            text = person.url,
                            style = AppTheme.typography.body1,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            textAlign = TextAlign.Center,
                        )
                    }
                }
            }

        }
    }

    private fun goBack() = requireActivity().supportFragmentManager.popBackStack()
    @Preview(name = "DetailCharacterScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun DetailPersonScreenPreview() {
        RickAndMortyMainTheme {

            val person = PersonDetail(
                name = "Витька",
                url = "урл",
                avatar = ""
            )

            DetailPersonListScreen(person, false)

        }
    }
}

