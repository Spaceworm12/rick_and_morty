package com.example.rickandmorty.presentation.category

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.buttons.HorizontalBtn
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
import com.example.rickandmorty.presentation.listperson.PersonListFragment


class CategoryListFragment : ComposeFragment() {

    @Composable
    override fun GetContent() {
        RickAndMortyMainTheme {
            CategoryListScreen(
                listOf(
                    stringResource(R.string.characters),
                )
            )
        }
    }

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    private fun CategoryListScreen(items: List<String>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .shadow(10.dp, RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp))
                .background(color = AppTheme.colors.background, shape = RoundedCornerShape(5.dp))
        ) {
            Toolbar(
                title = stringResource(id = R.string.rik_wiki),
                elevation = AppTheme.dimens.halfContentMargin,
                onBackClick = { },
                isBackArrowVisible = false
            )
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable._56),
                    modifier = Modifier.fillMaxSize(),
                    contentDescription = "Background Image",
                    contentScale = ContentScale.Crop
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(count = 1),
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Center
                ) {
                    items.forEach { category ->
                        item {
                            Category(category)
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun Category(category: String) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            HorizontalBtn(
                modifier = Modifier
                    .height(150.dp)
                    .fillMaxSize()
                    .padding(
                        bottom = AppTheme.dimens.halfContentMargin,
                        top = AppTheme.dimens.halfContentMargin
                    ),

                text = category,
                onClick = {
                    if (category == R.string.characters.toString()) {
                        requireActivity()
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                PersonListFragment()
                            )
                            .addToBackStack("")
                            .commit()
                    } else {
                        requireActivity()
                            .supportFragmentManager
                            .beginTransaction()
                            .replace(
                                R.id.fragment_container,
                                PersonListFragment()
                            )
                            .addToBackStack("")
                            .commit()

                    }
                })

        }
    }

    @Preview(name = "CategoryListFragment", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun CategoryListFragmentPreview() {
        RickAndMortyMainTheme {
            val category = listOf("1", "2", "3")
            CategoryListScreen(category)
        }
    }
}


