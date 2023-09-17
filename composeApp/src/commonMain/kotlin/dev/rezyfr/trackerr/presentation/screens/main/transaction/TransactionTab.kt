package dev.rezyfr.trackerr.presentation.screens.main.transaction

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandCircleDown
import androidx.compose.material.icons.rounded.Filter
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionWithDateResponse
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.transaction.TransactionWithDateModel
import dev.rezyfr.trackerr.presentation.component.base.TrOutlinedButton
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.ui.TransactionItem
import dev.rezyfr.trackerr.presentation.component.util.toDateFormat
import dev.rezyfr.trackerr.presentation.screens.main.transaction.store.TransactionStore
import dev.rezyfr.trackerr.utils.toDateRelatives
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDate

@Composable
fun TransactionTab(
    transactionComponent: TransactionComponent,
    selectedMonth: Month,
    selectedSort: String? = null,
    selectedType: CategoryType? = null,
    categoryIds: List<Int>? = null,
    appliedFilter: Boolean = false,
    onMonthClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onAppliedFilter: () -> Unit = {}
) {
    val state by transactionComponent.state.collectAsState()

    LaunchedEffect(true) {
        transactionComponent.onEvent(TransactionStore.Intent.Init(selectedMonth))
    }

    LaunchedEffect(appliedFilter) {
        if (!appliedFilter) return@LaunchedEffect
        transactionComponent.onEvent(
            TransactionStore.Intent.ApplyFilter(
                month = selectedMonth,
                categoryId = categoryIds,
                type = selectedType,
                sort = selectedSort
            )
        )
        onAppliedFilter.invoke()
    }

    TransactionScreen(
        state = state,
        onEvent = transactionComponent::onEvent,
        selectedMonth = selectedMonth.text,
        onMonthClick = onMonthClick,
        onFilterClick = onFilterClick
    )
}

@Composable
fun TransactionScreen(
    state: TransactionStore.State,
    onEvent: (TransactionStore.Intent) -> Unit,
    selectedMonth: String,
    onMonthClick: () -> Unit,
    onFilterClick: () -> Unit = {},
) {
    Scaffold(
        topBar = {
            TransactionTopBar(
                selectedMonth = selectedMonth,
                onMonthClick = onMonthClick,
                onFilterClick = onFilterClick
            )
        },
    ) {
        TransactionContent(
            Modifier.padding(it),
            transactions = state.transaction
        )
    }
}

@Composable
fun TransactionContent(
    modifier: Modifier = Modifier,
    transactions: UiResult<List<TransactionWithDateModel>>
) {
    when (transactions) {
        is UiResult.Success -> {
            TransactionList(
                modifier = modifier,
                transactions = transactions.data
            )
        }

        is UiResult.Error -> {
//            Text(text = transactions.message)
        }

        else -> {
            Text(text = "Loading")
        }
    }
}

@Composable
fun TransactionList(
    modifier: Modifier = Modifier,
    transactions: List<TransactionWithDateModel>
) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        transactions.forEach {
            item {
                Text(
                    text = it.date.toDateRelatives(newFormat = "dd MMMM yyyy"),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            items(it.transactions.size) { index ->
                TransactionItem(
                    transaction = it.transactions[index]
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionTopBar(
    selectedMonth: String,
    onMonthClick: () -> Unit,
    onFilterClick: () -> Unit = {},
) {
    CenterAlignedTopAppBar(
        title = {},
        navigationIcon = {
            TrOutlinedButton(
                modifier = Modifier.padding(start = 16.dp, top = 8.dp),
                text = {
                    Text(
                        selectedMonth,
                        style = MaterialTheme.typography.bodySmall
                    )
                },
                onClick = onMonthClick,
                leadingIcon = { Icon(Icons.Rounded.ExpandCircleDown, null) },
                contentPadding = PaddingValues(horizontal = 16.dp)
            )
        },
        actions = {
            IconButton(
                modifier = Modifier
                    .padding(end = 16.dp, top = 8.dp)
                    .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp)),
                onClick = onFilterClick
            ) {
                Icon(Icons.Rounded.FilterList, null)
            }
        }
    )
}

