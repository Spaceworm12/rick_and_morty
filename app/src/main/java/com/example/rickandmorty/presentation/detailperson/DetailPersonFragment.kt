package com.example.rickandmorty.presentation.detailperson

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
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import coil.compose.ImagePainter
import coil.compose.rememberImagePainter
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

        fun newInstance(id: Int) = DetailPersonFragment().apply {
            arguments = bundleOf(KEY to id)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return super.onCreateView(inflater, container, savedInstanceState)
        val id = requireArguments().getInt(KEY)
    }

    @Composable
    override fun GetContent() {
        val loading = viewModel.loading.observeAsState().value ?: return
        val exit = viewModel.exit.observeAsState().value ?: return
        val person = viewModel.person.observeAsState().value ?: return


        RickAndMortyMainTheme {
            if (loading) {
                LoaderBlock()
            }
            DetailPersonListScreen(person, loading, exit)
        }
    }
}

@Composable
private fun DetailPersonListScreen(person: PersonDetail, exit: Boolean, loading: Boolean) {
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


private fun getEmpty(): PersonDetail {
    return PersonDetail(
    )
}

private fun goBack() = requireActivity().supportFragmentManager.popBackStack()

@Preview(name = "PersonsListScreen", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun DetailPersonScreenPreview() {
    RickAndMortyMainTheme {
        val person = PersonDetail(
            name = "Витька",
            url = "урл",
            avatar = ""
        )
        DetailPersonListScreen(person, exit = false, loading = false)

    }
}
}

