package dev.rezyfr.trackerr.presentation.screens.create.transaction.component

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.zIndex
import dev.rezyfr.trackerr.presentation.component.ui.BottomSheet


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun RevealingSheet(
    bottomSheet: BottomSheet,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val visible by bottomSheet.visibilityState
    AnimatedVisibility(
        modifier = Modifier
            .fillMaxSize()
            .zIndex(1_000f),
        visible = visible,
        enter = fadeIn(),
        exit = fadeOut()
    ) {
        val keyboardController = LocalSoftwareKeyboardController.current
        Spacer(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.5f))
                .testTag("modal_outside_blur")
                .clickable(
                    onClick = {
                        keyboardController?.hide()
                        bottomSheet.collapse()
                    },
                    enabled = visible
                )
        )
    }
    AnimatedVisibility(
        modifier = modifier.zIndex(1_100f),
        visible = visible,
        enter = slideInVertically(
            animationSpec = tween(500),
            initialOffsetY = { fullHeight -> fullHeight }
        ),
    ) {
        content()
    }
}