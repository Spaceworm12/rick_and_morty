package com.example.rickandmorty.presentation.favorites

import android.content.res.Configuration
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.shimmer.shimmerBackground
import com.example.rickandmorty.presentation.model.modelperson.Person
import com.example.rickandmorty.presentation.navigation.Screens

@Composable
fun PersonCard(person: Person, onUiEvent: (FavoritesListEvents) -> Unit) {
    val context = LocalContext.current
    val textDel = stringResource(R.string.deleted_from_favorites)
    Column(modifier = Modifier.padding(10.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .border(1.dp, AppTheme.colors.primary)
                .background(
                    color = AppTheme.colors.rippleColor,
                    shape = RoundedCornerShape(AppTheme.dimens.halfContentMargin)
                )
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() },
                    onClick = {
                        onUiEvent(
                            FavoritesListEvents.GoTo(
                                Screens.FavoritePersonScreen(
                                    person.id
                                )
                            )
                        )
                    })
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
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                    .padding(
                        bottom = AppTheme.dimens.halfContentMargin,
                        top = AppTheme.dimens.halfContentMargin
                    ),
                text = person.name ?: "ыыы",
                style = AppTheme.typography.body1,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                textAlign = TextAlign.Center,
                softWrap = true
            )
            Box(modifier = Modifier.wrapContentSize(), contentAlignment = Alignment.TopEnd) {

                val fabSize = 70.dp

                FloatingActionButton(
                    modifier = Modifier
                        .size(fabSize)
                        .padding(AppTheme.dimens.sideMargin),
                    backgroundColor = AppTheme.colors.primary,
                    onClick = {
                        !person.inFavorites
                        onUiEvent(FavoritesListEvents.DeleteFromFavorites(person.id))
                        Toast.makeText(
                            context,
                            String.format("%s%s", person.name, textDel),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        if (person.inFavorites) {
                            Icon(
                                modifier = Modifier.size(30.dp),
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = null,
                                tint = Color.Black,
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
    }
}

@Preview(name = "PersonCardPreview", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun PersonCardPreview() {
    RickAndMortyMainTheme {
        val pers = Person(id = 999, name = "sdasdasdas asd asdas das das as ")
        val pers2 = Person(id = 111)
        val persons = listOf(pers, pers2)
        LazyColumn {
            persons.forEach { person ->
                item {
                    PersonCard(person) {}
                }
            }
        }
    }
}
