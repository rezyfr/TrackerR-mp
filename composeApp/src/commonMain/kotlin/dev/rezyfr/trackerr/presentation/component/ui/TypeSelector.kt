package dev.rezyfr.trackerr.presentation.component.ui

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.presentation.component.multiselector.MultiSelector
import dev.rezyfr.trackerr.presentation.theme.typeIndicatorColor


@Composable
fun TypeSelector(
    modifier: Modifier = Modifier,
    type: CategoryType,
    onSelectType: (CategoryType) -> Unit = {},
    useDefaultColor: Boolean = true,
    backgroundColor: Color? = null,
    selectedHighlightColor: Color? = null,
) {
    MultiSelector (
        options = CategoryType.values().map { it.label },
        selectedOption = type.label,
        onOptionSelect = {
            onSelectType.invoke(CategoryType.fromString(it))
        },
        backgroundColor = if(useDefaultColor) type.typeIndicatorColor() else backgroundColor!!,
        selectedHighlightColor = if(useDefaultColor) MaterialTheme.colorScheme.onPrimary else selectedHighlightColor!!,
        selectedColor = if(useDefaultColor) type.typeIndicatorColor() else backgroundColor!!,
        unselectedColor = if(useDefaultColor) MaterialTheme.colorScheme.onPrimary else selectedHighlightColor!!,
        modifier = modifier
            .height(36.dp)
            .padding(horizontal = 36.dp)
    )
}