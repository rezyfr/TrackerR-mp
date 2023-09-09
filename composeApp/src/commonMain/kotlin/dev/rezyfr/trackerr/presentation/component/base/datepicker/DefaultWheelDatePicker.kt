package dev.rezyfr.trackerr.presentation.component.base.datepicker

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import dev.rezyfr.trackerr.presentation.component.util.getCurrentLd
import dev.rezyfr.trackerr.presentation.component.wheelpicker.FVerticalWheelPicker
import dev.rezyfr.trackerr.presentation.component.wheelpicker.rememberFWheelPickerState
import kotlinx.datetime.LocalDate

@Composable
internal fun DefaultWheelDatePicker(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    textColor: Color = MaterialTheme.colorScheme.onBackground,
    items: List<DateProperty> = listOf(),
    selectedItem: DateProperty,
    onSelectItem: (DateProperty) -> Unit = {}
) {
    val dayPickerState = rememberFWheelPickerState()

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        FVerticalWheelPicker(
            count = items.size,
            dayPickerState
        ) {
            Text(items[it].text, style = textStyle, color = textColor)
            if (state.currentIndex != -1) onSelectItem(items[state.currentIndex])
        }
    }

    LaunchedEffect(key1 = selectedItem) {
        dayPickerState.animateScrollToIndex(items.indexOf(selectedItem))
    }
}