package com.example.rickandmorty.presentation.category

import android.content.res.Configuration
import androidx.compose.foundation.background
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
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.dialogs.LoaderBlock
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
import com.example.rickandmorty.presentation.model.modelcharacter.Character


class CategoryListFragment : ComposeFragment() {

    @Composable
    override fun GetContent() {
        RickAndMortyMainTheme() {
            CategoryListScreen(listOf("category1","category2","category3"))
        }
    }

    @Composable
    private fun CategoryListScreen(items:List<String>) {
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
                items.forEach { category ->
                    item {
                        Category(category)
                    }
                }
            }
        }
    }

    @Composable
    private fun Category(category: String) {

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
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(
                            bottom = AppTheme.dimens.halfContentMargin,
                            top = AppTheme.dimens.halfContentMargin
                        ),
                    text = category,
                    style = AppTheme.typography.body1,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textAlign = TextAlign.Center,
                )
            }
        }

    }

    @Preview(name = "CategoryListFragment", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun PokemonListScreenPreview() {
        RickAndMortyMainTheme {
            val category = listOf("1", "2","3")
            CategoryListScreen(category)
        }
    }
}


