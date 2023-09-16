package dev.rezyfr.trackerr.presentation.screens.main.transaction

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.screens.main.transaction.store.TransactionStore

@Composable
fun TransactionTab(
    transactionComponent: TransactionComponent,
    selectedMonth: Month,
    onMonthClick: () -> Unit = {},
) {
    val state by transactionComponent.state.collectAsState()

    LaunchedEffect(selectedMonth) {
        transactionComponent.onEvent(TransactionStore.Intent.Init(selectedMonth))
    }

    TransactionScreen(
        state = state,
        onEvent = transactionComponent::onEvent,
        selectedMonth = selectedMonth.text,
        onMonthClick = onMonthClick,
    )
}

@Composable
fun TransactionScreen(
    state: TransactionStore.State,
    onEvent: (TransactionStore.Intent) -> Unit,
    selectedMonth: String,
    onMonthClick: () -> Unit
) {

}

