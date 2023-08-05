package com.example.rickandmorty.presentation.detail

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
import androidx.compose.runtime.livedata.observeAsState
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
import com.example.rickandmorty.presentation.listcharacter.CharacterListViewModel
import com.example.rickandmorty.presentation.model.modelcharacter.Character

class DetailCharacterFragment : ComposeFragment() {
    private val viewModel: CharacterListViewModel by lazy {
        ViewModelProvider(this)[CharacterListViewModel::class.java]
    }

    @Composable
    override fun GetContent() {
        val character = Character("1","2","3")
        val loading = false
        val exit = false

        RickAndMortyMainTheme() {
            DetailCharacterListScreen(character, loading)
        }
    }

    @Composable
    private fun DetailCharacterListScreen(character: Character, exit: Boolean) {
        if (exit) goBack()
        Column(modifier = Modifier.background(AppTheme.colors.background)) {

            Toolbar(
                title = character.name,
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

                        val painterImage = rememberImagePainter(data = character.avatar)

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
                            text = character.name,
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
                            text = character.url,
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


//    @Composable
//    private fun DetailScreen(item: ExampleModel, exit: Boolean) {
//
//        if (exit) goBack()
//
//        var currentName by remember { mutableStateOf("") }
//        currentName = currentName.ifBlank { item.name }
//
//        var currentDescription by remember { mutableStateOf("") }
//        currentDescription = currentDescription.ifBlank { item.name }
//
//
//        Column(modifier = Modifier.background(AppTheme.colors.background)) {
//
//            Toolbar(
//                title = "21",
//                onBackClick = { goBack() }
//            )
//
//            Box(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .padding(AppTheme.dimens.halfContentMargin)
//                    .border(
//                        width = 1.dp,
//                        color = AppTheme.colors.secondaryVariant,
//                        shape = RoundedCornerShape(AppTheme.dimens.contentMargin)
//                    )
//            ) {
//                TextField(
//                    modifier = Modifier.fillMaxWidth(),
//                    value = currentName,
//                    onValueChange = {
//                        currentName = it
//                        item.name = it
//                        viewModel.submitUIEvent(DetailCharacterEvent.SetItem(item))
//                    },
//                    placeholder = {
//                        Text(
//                            text = "2",
//                            style = AppTheme.typography.body1
//                        )
//                    },
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = Color.Transparent,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        disabledIndicatorColor = Color.Transparent,
//                        cursorColor = AppTheme.colors.secondary
//                    ),
//                )
//            }
//
//            Box(
//                modifier = Modifier
//                    .fillMaxSize()
//                    .weight(1f)
//                    .padding(AppTheme.dimens.halfContentMargin)
//                    .border(
//                        width = 1.dp,
//                        color = AppTheme.colors.secondaryVariant,
//                        shape = RoundedCornerShape(AppTheme.dimens.contentMargin)
//                    )
//            ) {
//                TextField(
//                    modifier = Modifier.fillMaxSize(),
//                    value = currentDescription,
//                    onValueChange = {
//                        currentDescription = it
////                        item.description = it
//                        viewModel.submitUIEvent(DetailCharacterEvent.SetItem(item))
//                    },
//                    placeholder = {
//                        Text(
//                            text = "23",
//                            style = AppTheme.typography.body1
//                        )
//                    },
//                    colors = TextFieldDefaults.textFieldColors(
//                        backgroundColor = Color.Transparent,
//                        focusedIndicatorColor = Color.Transparent,
//                        unfocusedIndicatorColor = Color.Transparent,
//                        disabledIndicatorColor = Color.Transparent,
//                        cursorColor = AppTheme.colors.secondary
//                    ),
//                )
//            }
//
//            PrimaryButton(
//                modifier = Modifier.fillMaxWidth(),
//                text = stringResource(id = R.string.save),
//                isEnabled = currentName.isNotBlank() && currentDescription.isNotBlank()
//            ) {
//                viewModel.submitUIEvent(DetailCharacterEvent.LikeCharacter(item.id))
//            }
//
//        }
//
//    }
//

    //
//    private fun getEmptyItem(): ExampleModel {
//        return ExampleModel(
//            id = 0,
//            name = "",
//        )
//    }
//
    @Preview(name = "DetailCharacterScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun DetailCharacterScreenPreview() {
        RickAndMortyMainTheme {

            val character = Character(
                name = "Витька",
                url = "урл",
                avatar = ""
            )

            DetailCharacterListScreen(character, false)

        }
    }
}

