package com.example.rickandmorty.presentation.detail

import android.content.res.Configuration
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.R
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.ComposeFragment
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme
import com.example.rickandmorty.presentation.composecomponents.buttons.PrimaryButton
import com.example.rickandmorty.presentation.composecomponents.toolbar.Toolbar
import com.example.rickandmorty.presentation.model.modelcharacter.ExampleModel

class DetailFragment : ComposeFragment() {

    companion object {
        private const val KEY = "KEY"

        fun newInstance(item: ExampleModel) = DetailFragment().apply {
            arguments = bundleOf(KEY to item)
        }
    }

    private val viewModel: DetailViewModel by lazy {
        ViewModelProvider(this)[DetailViewModel::class.java]
    }

    @Composable
    override fun GetContent() {

        val item =
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) requireArguments().getParcelable(
                KEY,
                ExampleModel::class.java
            ) ?: getEmptyItem() else requireArguments().getParcelable(KEY) ?: getEmptyItem()

        viewModel.submitUIEvent(DetailEvent.SetItem(item))

        val exampleModel = viewModel.exampleModel.observeAsState().value ?: return
        val currentTheme = viewModel.currentTheme.observeAsState().value ?: return
        val exit = viewModel.exit.observeAsState().value ?: return

        RickAndMortyMainTheme(themeCode = currentTheme) {
            DetailScreen(exampleModel, exit)
        }
    }

    @Composable
    private fun DetailScreen(item: ExampleModel, exit: Boolean) {

        if (exit) goBack()

        var currentName by remember { mutableStateOf("") }
        currentName = currentName.ifBlank { item.name }

        var currentDescription by remember { mutableStateOf("") }
        currentDescription = currentDescription.ifBlank { item.name }


        Column(modifier = Modifier.background(AppTheme.colors.background)) {

            Toolbar(
                title = "21",
                onBackClick = { goBack() }
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(AppTheme.dimens.halfContentMargin)
                    .border(
                        width = 1.dp,
                        color = AppTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(AppTheme.dimens.contentMargin)
                    )
            ) {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = currentName,
                    onValueChange = {
                        currentName = it
                        item.name = it
                        viewModel.submitUIEvent(DetailEvent.SetItem(item))
                    },
                    placeholder = {
                        Text(
                            text = "2",
                            style = AppTheme.typography.body1
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = AppTheme.colors.secondary
                    ),
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(1f)
                    .padding(AppTheme.dimens.halfContentMargin)
                    .border(
                        width = 1.dp,
                        color = AppTheme.colors.secondaryVariant,
                        shape = RoundedCornerShape(AppTheme.dimens.contentMargin)
                    )
            ) {
                TextField(
                    modifier = Modifier.fillMaxSize(),
                    value = currentDescription,
                    onValueChange = {
                        currentDescription = it
//                        item.description = it
                        viewModel.submitUIEvent(DetailEvent.SetItem(item))
                    },
                    placeholder = {
                        Text(
                            text = "23",
                            style = AppTheme.typography.body1
                        )
                    },
                    colors = TextFieldDefaults.textFieldColors(
                        backgroundColor = Color.Transparent,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent,
                        disabledIndicatorColor = Color.Transparent,
                        cursorColor = AppTheme.colors.secondary
                    ),
                )
            }

            PrimaryButton(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(id = R.string.save),
                isEnabled = currentName.isNotBlank() && currentDescription.isNotBlank()
            ) {
                viewModel.submitUIEvent(DetailEvent.SaveItem(item.id))
            }

        }

    }

    private fun goBack() = requireActivity().supportFragmentManager.popBackStack()

    private fun getEmptyItem(): ExampleModel {
        return ExampleModel(
            id = 0,
            name = "",
        )
    }

    @Preview(name = "DetailScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
    @Composable
    private fun DetailScreenPreview() {
        RickAndMortyMainTheme {

            val model = ExampleModel(
                id = 0,
                name = "Заметка про Витю",
            )

            DetailScreen(
                item = model,
                exit = false
            )
        }
    }

}
