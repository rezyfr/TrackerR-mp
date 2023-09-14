package dev.rezyfr.trackerr.presentation.screens.create.category.components

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.rezyfr.trackerr.domain.model.IconModel

@Composable
fun IconPickerSheet(
    modifier: Modifier = Modifier,
    currentIcon: IconModel? = null,
    icons: List<IconModel>,
    onContinue: (Int) -> Unit = {},
) {
    var selectedIcon by remember { mutableStateOf(currentIcon) }


}