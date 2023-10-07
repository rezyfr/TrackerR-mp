package dev.rezyfr.trackerr.presentation.screens.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.unit.dp
import dev.rezyfr.trackerr.Res
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.transaction.TransactionModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionSummaryModel
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.base.TrCapsuleButton
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.ui.LoadingDots
import dev.rezyfr.trackerr.presentation.component.ui.TransactionItem
import dev.rezyfr.trackerr.presentation.component.util.format
import dev.rezyfr.trackerr.presentation.screens.main.home.store.HomeStore
import dev.rezyfr.trackerr.presentation.theme.Green100
import dev.rezyfr.trackerr.presentation.theme.Light80
import dev.rezyfr.trackerr.presentation.theme.Red100
import io.github.skeptick.libres.compose.painterResource
import io.github.skeptick.libres.images.Image

@Composable
fun HomeTabLarge(
    homeComponent: HomeComponent,
    selectedMonth: Month,
) {
    val state by homeComponent.state.collectAsState()

    LaunchedEffect(true) {
        homeComponent.onEvent(HomeStore.Intent.Init(selectedMonth, false))
    }

    LaunchedEffect(selectedMonth) {
        homeComponent.onEvent(HomeStore.Intent.GetTransactionSummary(selectedMonth.value))
    }

    HomeScreenLarge(
        state = state,
        onEvent = homeComponent::onEvent,
        refresh = { homeComponent.onEvent(HomeStore.Intent.Init(selectedMonth, true)) },
    )
}
@Composable
fun HomeScreenLarge(
    state: HomeStore.State,
    onEvent: (HomeStore.Intent) -> Unit = {},
    refresh: () -> Unit = {}
) {
    Column(Modifier.fillMaxSize().background(Light80)) {
        HomeTopBar(onSyncClicked = refresh)
        HomeContent(
            state = state,
            modifier = Modifier.padding(16.dp),
            onEvent = onEvent,
        )
    }
}
@Composable
private fun HomeTopBar(
    onSyncClicked: () -> Unit = {},
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(start = 24.dp)
    ) {
        Text(
            "Home",
            style = MaterialTheme.typography.titleLarge,
            modifier = Modifier.weight(1f),
        )
        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                Icons.Filled.Notifications,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                    .size(32.dp)
            )
            Icon(
                Icons.Filled.Refresh,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(top = 16.dp, bottom = 16.dp, end = 24.dp)
                    .size(32.dp)
                    .clickable { onSyncClicked.invoke() }
            )
        }
    }
}
@Composable
private fun HomeContent(
    modifier: Modifier = Modifier,
    state: HomeStore.State,
    onEvent: (HomeStore.Intent) -> Unit
) {
    LazyColumn(modifier) {
        item {
            SummarySection(
                state.accBalance,
                state.transactionSummary,
                Modifier.fillMaxWidth()
            )
        }
        item() {
            RecentTransaction(state.recentTransaction)
        }
    }
}
@Composable
private fun SummarySection(
    accBalance: UiResult<Long>,
    transactionSummary: UiResult<TransactionSummaryModel>,
    modifier: Modifier = Modifier
) {
    VSpacer(16)
    TransactionSummary(modifier.padding(bottom = 24.dp), transactionSummary, accBalance)
}
@Composable
private fun TransactionSummary(
    modifier: Modifier = Modifier,
    summary: UiResult<TransactionSummaryModel>,
    accBalance: UiResult<Long>
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        when (summary) {
            is UiResult.Success -> {
                HomeSummaryCard(
                    Modifier.weight(1f),
                    label = "Income",
                    value = summary.data.totalIncome,
                    image = Res.image.ic_income,
                    color = Green100
                )
                HomeSummaryCard(
                    Modifier.weight(1f),
                    label = "Expense",
                    value = summary.data.totalExpense,
                    image = Res.image.ic_expense,
                    color = Red100
                )
            }

            is UiResult.Loading -> {
                LoadingDots()
            }

            else -> {}
        }

        when (accBalance) {
            is UiResult.Success -> {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(16.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
                ) {
                    Column(
                        Modifier.fillMaxWidth()
                            .padding(16.dp),
                    ) {
                        Text(
                            "Account Balance",
                            style = MaterialTheme.typography.bodyMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                        VSpacer(12)
                        Text(
                            "Rp${accBalance.data.format()}",
                            style = MaterialTheme.typography.titleLarge.copy(
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        )
                    }
                }
            }

            is UiResult.Loading -> {
                LoadingDots()
            }

            else -> {}
        }
    }
}

@Composable
private fun HomeSummaryCard(
    modifier: Modifier = Modifier,
    label: String,
    value: Number,
    image: Image,
    color: Color,
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background)
    ) {
        Column(
            Modifier.fillMaxWidth()
                .padding(16.dp),
        ) {
            Image(
                painter = image.painterResource(),
                null,
                modifier = Modifier.size(48.dp)
                    .clip(RoundedCornerShape(16.dp))
                    .background(color.copy(alpha = 0.2f))
                    .padding(8.dp),
                colorFilter = ColorFilter.tint(color)
            )
            VSpacer(8)
            Text(
                label,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.tertiary
                )
            )
            VSpacer(12)
            Text(
                "Rp${value.format()}",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground
                )
            )
        }
    }
}

@Composable
private fun RecentTransaction(
    recent: UiResult<List<TransactionModel>>
) {
    Row(
        Modifier.fillMaxWidth().padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text("Recent Transaction", style = MaterialTheme.typography.titleSmall)
        TrCapsuleButton(
            onClick = { /*TODO*/ },
            text = "See All"
        )
    }
    when (recent) {
        is UiResult.Success -> {
            recent.data.let {
                it.forEach { transaction ->
                    TransactionItem(
                        transaction = transaction
                    )
                }
            }
        }

        is UiResult.Loading -> {
            Box(Modifier.fillMaxWidth().height(100.dp), contentAlignment = Alignment.Center) {
                LoadingDots()
            }
        }

        is UiResult.Error -> {
        }

        else -> {
        }
    }
}