package com.example.rickandmorty.presentation.composecomponents.shimmer

import android.annotation.SuppressLint
import android.content.res.Configuration
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rickandmorty.presentation.composecomponents.AppTheme
import com.example.rickandmorty.presentation.composecomponents.RickAndMortyMainTheme


@SuppressLint("AutoboxingStateCreation")
@Composable
fun LoadingAnimation(
    circleColor: Color = AppTheme.colors.primary,
    animationDelay: Int = 2000,
) {

    var circleScale by remember {
        mutableStateOf(0f)
    }
    val circleScaleAnimate = animateFloatAsState(
        targetValue = circleScale,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = animationDelay
            )
        ), label = ""
    )
    LaunchedEffect(Unit) {
        circleScale = 1f
    }
    Box(
        modifier = Modifier.fillMaxSize()
            .scale(scale = circleScaleAnimate.value)
            .border(
                width = 12.dp,
                color = circleColor.copy(alpha = 1 - circleScaleAnimate.value),
                shape = CircleShape
            )
    ) {

    }
}

@Preview(name = "LoadingAnimationPreview", uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
private fun LoadingAnimationPreview() {
    RickAndMortyMainTheme() {
        LoadingAnimation()
    }
}
