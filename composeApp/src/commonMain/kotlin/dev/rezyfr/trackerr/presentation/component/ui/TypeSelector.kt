package dev.rezyfr.trackerr.presentation.component.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.presentation.component.multiselector.MultiSelector
import dev.rezyfr.trackerr.presentation.theme.typeIndicatorColor


@Composable
fun TypeSelector(
    modifier: Modifier = Modifier,
    type: String,
    onSelectType: (String) -> Unit = {},
    useDefaultColor: Boolean = true,
    backgroundColor: Color? = null,
    selectedHighlightColor: Color? = null,
) {
    MultiSelector (
        options = listOf("Expense", "Income"),
        selectedOption = type,
        onOptionSelect = onSelectType,
        backgroundColor = if(useDefaultColor) type.typeIndicatorColor() else backgroundColor!!,
        selectedHighlightColor = if(useDefaultColor) MaterialTheme.colorScheme.onPrimary else selectedHighlightColor!!,
        selectedColor = if(useDefaultColor) type.typeIndicatorColor() else backgroundColor!!,
        unselectedColor = if(useDefaultColor) MaterialTheme.colorScheme.onPrimary else selectedHighlightColor!!,
        modifier = modifier
            .height(36.dp)
            .padding(horizontal = 36.dp)
    )
}