package com.example.rickandmorty.presentation.category

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar


class CategoryListFragment : ComposeFragment() {

    @Composable
    override fun GetContent() {
        RickAndMortyMainTheme() {
            CategoryListScreen(listOf("category1", "category2", "category3"))
        }
    }

    @Composable
    private fun CategoryListScreen(items: List<String>) {
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
                        Category(category, {})
                    }
                }
            }
        }
    }

    @Composable
    private fun Category(category: String, onItemClick: () -> Unit) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onItemClick.invoke() }
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



    @Preview(name = "CategoryListFragment", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun PokemonListScreenPreview() {
        RickAndMortyMainTheme {
            val category = listOf("1", "2", "3")
            CategoryListScreen(category)
        }
    }
}


