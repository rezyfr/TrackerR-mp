package dev.rezyfr.trackerr.presentation.screens.home.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DateProperty
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DefaultWheelDatePicker
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.FormTransactionSheet
import dev.rezyfr.trackerr.presentation.screens.home.store.HomeStore

@Composable
fun MonthPickerSheet(
    modifier: Modifier,
    month: Month,
    monthOptions: List<Month>,
    onChangeMonth: (Month) -> Unit = {},
) {
    var selectedMonth by remember { mutableStateOf(month) }

    FormTransactionSheet(
        modifier = modifier,
        label = "Date",
        onContinue = {
            onChangeMonth(selectedMonth)
        },
    ) {
        Row(Modifier.fillMaxWidth()) {
            DefaultWheelDatePicker(
                modifier = Modifier.weight(1f),
                items = monthOptions,
                selectedItem = month
            ) {
                selectedMonth = (it as Month)
            }
        }
    }
}