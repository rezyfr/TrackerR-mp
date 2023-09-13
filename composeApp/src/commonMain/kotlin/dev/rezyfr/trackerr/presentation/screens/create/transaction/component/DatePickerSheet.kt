package dev.rezyfr.trackerr.presentation.screens.create.transaction.component

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DefaultWheelDatePicker
import dev.rezyfr.trackerr.presentation.screens.create.transaction.store.AddTransactionStore

@Composable
fun DatePickerSheet(
    modifier: Modifier,
    state: AddTransactionStore.State,
    onChangeDay: (Int) -> Unit = {},
    onChangeMonth: (Int) -> Unit = {},
    onChangeYear: (Int) -> Unit = {},
) {

    var selectedDate by remember { mutableStateOf(state.selectedDay) }
    var selectedMonth by remember { mutableStateOf(state.selectedMonth) }
    var selectedYear by remember { mutableStateOf(state.selectedYear) }

    FormTransactionSheet(
        modifier = modifier,
        label = "Date",
        onContinue = {
            onChangeDay(selectedDate.value)
            onChangeMonth(selectedMonth.value)
            onChangeYear(selectedYear.value)
            state.datePickerSheet.collapse()
        },
    ) {
        Row(Modifier.fillMaxWidth()) {
            DefaultWheelDatePicker(
                modifier = Modifier.weight(1f),
                items = state.dateOptions.first,
                selectedItem = state.selectedDay
            ) {
                selectedDate = it
            }
            DefaultWheelDatePicker(
                modifier = Modifier.weight(1f),
                items = state.dateOptions.second,
                selectedItem = state.selectedMonth
            ) {
                selectedMonth = it
            }
            DefaultWheelDatePicker(
                modifier = Modifier.weight(1f),
                items = state.dateOptions.third,
                selectedItem = state.selectedYear
            ) {
                selectedYear = it
            }
        }
    }
}