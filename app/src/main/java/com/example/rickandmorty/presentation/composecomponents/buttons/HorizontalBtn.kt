package com.example.rickandmorty.presentation.composecomponents.buttons

import android.content.res.Configuration
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.ButtonElevation
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme


@Composable
fun HorizontalBtn(
    modifier: Modifier = Modifier,
    text: String,
    elevation:ButtonElevation = ButtonDefaults.elevation(),
    isEnabled: Boolean = true,
    minWidth: Dp = 120.dp,
    minHeight: Dp = 38.dp,
    color: Color = AppTheme.colors.rippleColor,
    bottomPadding: Dp = AppTheme.dimens.sideMargin,
    onClick: () -> Unit,
) {
    with(AppTheme.dimens) {
        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = bottomPadding),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {

            val shape = RoundedCornerShape(contentMargin)

            Button(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(min = minHeight)
                    .border(
                        width = 2.dp,
                        color = if (isEnabled) {
                            color
                        } else {
                            AppTheme.colors.notEnabled
                        },
                        shape = shape
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = AppTheme.colors.primary,
                    contentColor = color,
                    disabledBackgroundColor = AppTheme.colors.primary,
                    disabledContentColor = AppTheme.colors.notEnabled,
                ),
                shape = shape,
                enabled = isEnabled,
                onClick = { onClick.invoke() },
            )
            {
                Text(
                    fontSize = 18.sp,
                    style = AppTheme.typography.subtitle1,
                    text = text,
                )
            }

        }
    }
}

@Preview(name = "HorizontalBtn", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun HorizontalButtonPreview() {
    RickAndMortyMainTheme {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            HorizontalBtn(modifier = Modifier.wrapContentSize(), text = "Text") {}
            HorizontalBtn(text = "Text2", isEnabled = false) {}
            HorizontalBtn(text = "Text3", color = AppTheme.colors.error) {}
        }
    }
}

