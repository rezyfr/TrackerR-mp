package dev.rezyfr.trackerr.presentation.screens.main.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material.icons.rounded.ExpandCircleDown
import androidx.compose.material.icons.rounded.FilterList
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.transaction.TransactionWithDateModel
import dev.rezyfr.trackerr.presentation.component.base.TrOutlinedButton
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.ui.TransactionItem
import dev.rezyfr.trackerr.presentation.component.util.noRippleClick
import dev.rezyfr.trackerr.presentation.screens.main.transaction.store.TransactionStore
import dev.rezyfr.trackerr.utils.toDateRelatives

@Composable
fun TransactionTab(
    transactionComponent: TransactionComponent,
    selectedMonth: Month,
    selectedSort: String? = null,
    selectedType: CategoryType? = null,
    categoryIds: Sequence<Int>? = null,
    appliedFilter: Boolean = false,
    onMonthClick: () -> Unit = {},
    onFilterClick: () -> Unit = {},
    onAppliedFilter: () -> Unit = {},
    onReportWrapClick: () -> Unit = {}
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
        onReportClick = onReportWrapClick,
        selectedMonth = selectedMonth.text,
        onMonthClick = onMonthClick,
        onFilterClick = onFilterClick
    )
}
@Composable
fun TransactionScreen(
    state: TransactionStore.State,
    selectedMonth: String,
    onMonthClick: () -> Unit,
    onFilterClick: () -> Unit = {},
    onReportClick: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TransactionTopBar(
                selectedMonth = selectedMonth,
                onMonthClick = onMonthClick,
                onFilterClick = onFilterClick,
                state = state
            )
        },
    ) {
        TransactionContent(
            Modifier.padding(it),
            onReportClick,
            transactions = state.transaction
        )
    }
}
@Composable
fun TransactionContent(
    modifier: Modifier = Modifier,
    onReportClick: () -> Unit = {},
    transactions: UiResult<List<TransactionWithDateModel>>
) {
    LazyColumn(modifier, verticalArrangement = Arrangement.spacedBy(8.dp)) {
        getFinancialReportButton(onReportClick)
        getTransactionList(modifier, transactions)
    }
}

fun LazyListScope.getFinancialReportButton(
    onReportClick: () -> Unit = { }
) {
    item {
        CompositionLocalProvider(LocalContentColor provides MaterialTheme.colorScheme.primary) {
            Row(
                Modifier
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .background(MaterialTheme.colorScheme.primaryContainer, RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 12.dp)
                    .noRippleClick(onReportClick),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "See your financial report",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    modifier = Modifier.align(Alignment.CenterVertically)
                )
                Icon(Icons.Rounded.ChevronRight, contentDescription = null)
            }
        }
    }
}

fun LazyListScope.getTransactionList(
    modifier: Modifier = Modifier,
    transactions: UiResult<List<TransactionWithDateModel>>
) {
    when (transactions) {
        is UiResult.Success -> {
            transactions.data.forEach {
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

        is UiResult.Error -> {
        }

        else -> {
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionTopBar(
    selectedMonth: String,
    onMonthClick: () -> Unit,
    onFilterClick: () -> Unit = {},
    state: TransactionStore.State
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
            var filterCount = 0
            if (state.isCategoryActive) filterCount++
            if (state.isSortActive) filterCount++
            if (state.isTypeActive) filterCount++
            Box(
                Modifier
            ) {
                IconButton(
                    modifier = Modifier
                        .padding(end = 16.dp, top = 8.dp)
                        .border(1.dp, MaterialTheme.colorScheme.outline, RoundedCornerShape(8.dp))
                        .align(Alignment.Center),
                    onClick = onFilterClick
                ) {
                    Icon(
                        Icons.Rounded.FilterList,
                        null,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                Box(
                    Modifier
                        .padding(end = 16.dp, top = 8.dp)
                        .size(24.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .align(Alignment.TopEnd)
                ) {
                    Text(
                        filterCount.toString(),
                        style = MaterialTheme.typography.labelSmall.copy(color = MaterialTheme.colorScheme.onPrimary),
                        textAlign = TextAlign.Center,
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
            }
        }
    )
}

