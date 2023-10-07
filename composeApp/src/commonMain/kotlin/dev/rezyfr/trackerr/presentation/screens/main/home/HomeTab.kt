package dev.rezyfr.trackerr.presentation.screens.main.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material.icons.rounded.ExpandCircleDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.aay.compose.baseComponents.model.LegendPosition
import com.aay.compose.lineChart.LineChart
import com.aay.compose.lineChart.model.LineParameters
import com.aay.compose.lineChart.model.LineType
import dev.rezyfr.trackerr.Res
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.Granularity
import dev.rezyfr.trackerr.domain.model.transaction.TransactionFrequencyModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionSummaryModel
import dev.rezyfr.trackerr.presentation.HSpacer
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.base.TrCapsuleButton
import dev.rezyfr.trackerr.presentation.component.base.TrOutlinedButton
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.ui.LoadingDots
import dev.rezyfr.trackerr.presentation.component.ui.TransactionItem
import dev.rezyfr.trackerr.presentation.component.util.format
import dev.rezyfr.trackerr.presentation.screens.main.home.store.HomeStore
import dev.rezyfr.trackerr.presentation.theme.Green100
import dev.rezyfr.trackerr.presentation.theme.HomeTopBackground
import dev.rezyfr.trackerr.presentation.theme.Red100
import dev.rezyfr.trackerr.presentation.theme.Yellow100
import dev.rezyfr.trackerr.presentation.theme.Yellow20
import io.github.aakira.napier.Napier
import io.github.skeptick.libres.compose.painterResource

@Composable
fun HomeTab(
    homeComponent: HomeComponent,
    selectedMonth: Month,
    onMonthClick: () -> Unit = {},
) {
    val state by homeComponent.state.collectAsState()

    LaunchedEffect(true) {
        homeComponent.onEvent(HomeStore.Intent.Init(selectedMonth, false))
    }

    LaunchedEffect(selectedMonth) {
        homeComponent.onEvent(HomeStore.Intent.GetTransactionSummary(selectedMonth.value))
    }

    HomeScreen(
        state = state,
        onEvent = homeComponent::onEvent,
        selectedMonth = selectedMonth.text,
        onMonthClick = onMonthClick,
        refresh = { homeComponent.onEvent(HomeStore.Intent.Init(selectedMonth, true)) },
    )
}

@Composable
fun HomeScreen(
    state: HomeStore.State,
    selectedMonth: String,
    onEvent: (HomeStore.Intent) -> Unit = {},
    onMonthClick: () -> Unit = {},
    refresh: () -> Unit = {}
) {
    Box(
        Modifier
            .fillMaxSize()
    ) {
        Scaffold(
            topBar = { HomeTopBar(selectedMonth, refresh, onMonthClick) }
        ) {
            HomeContent(
                state = state,
                modifier = Modifier.padding(it),
                onEvent = onEvent,
            )
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HomeTopBar(
    selectedMonth: String,
    onSyncClicked: () -> Unit = {},
    onMonthClick: () -> Unit = {}
) {
    CenterAlignedTopAppBar(
        title = {
            TrOutlinedButton(
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
        navigationIcon = {
            Box(
                Modifier.padding(16.dp)
                    .clip(CircleShape)
                    .border(1.dp, MaterialTheme.colorScheme.primary, CircleShape)
            ) {
                Icon(
                    Icons.Filled.Person,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(4.dp)
                        .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                        .align(Alignment.Center)
                        .size(32.dp)
                )
            }
        },
        actions = {
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
                    .padding(top = 16.dp, bottom = 16.dp, end = 16.dp)
                    .size(32.dp)
                    .clickable { onSyncClicked.invoke() }
            )
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = HomeTopBackground,
            actionIconContentColor = MaterialTheme.colorScheme.primary
        )
    )
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
        item {
            FrequencySection(
                state.transactionFrequency,
                state.selectedGranularity,
                Modifier.fillParentMaxWidth(1.2f),
                onEvent
            )
        }
        item() {
            RecentTransaction(state.recentTransaction)
        }
    }
}
@Composable
private fun FrequencySection(
    transactionFrequency: UiResult<TransactionFrequencyModel>,
    selectedGranularity: Granularity,
    modifier: Modifier,
    onEvent: (HomeStore.Intent) -> Unit
) {
    Napier.d("Recompose FrequencySection: $transactionFrequency, $selectedGranularity, $modifier, ")
    Column(Modifier.fillMaxWidth().padding(top = 12.dp)) {
        Text(
            "Spend Frequency",
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 16.dp)
        )
        VSpacer(12)
        if (transactionFrequency is UiResult.Success) {
            val freqData = transactionFrequency.data
            val expenseParam = LineParameters(
                label = "",
                data = freqData.expenseData,
                lineColor = MaterialTheme.colorScheme.error,
                lineType = LineType.CURVED_LINE,
                lineShadow = true,
            )
            LineChart(
                modifier.height(185.dp).fillMaxWidth(),
                animateChart = true,
                linesParameters = listOf(expenseParam),
                xAxisData = freqData.xAxisData,
                isGrid = false,
                showXAxis = false,
                showYAxis = false,
                legendPosition = LegendPosition.DISAPPEAR,
                barWidthPx = 6.dp,
            )
        } else if (transactionFrequency is UiResult.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(185.dp)
                    .background(MaterialTheme.colorScheme.background),
                contentAlignment = Alignment.Center
            ) {
                LoadingDots()
            }
        }
        VSpacer(8)
        GranularitySection(
            Modifier.fillMaxWidth(),
            selectedGranularity,
            onClick = {
                onEvent.invoke(HomeStore.Intent.OnChangeGranularity(it))
            }
        )
    }
}
@Composable
private fun GranularitySection(
    modifier: Modifier = Modifier,
    selectedGranularity: Granularity = Granularity.WEEK,
    onClick: (Granularity) -> Unit = {}
) {
    Row(
        modifier.padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GranularityButton(Granularity.WEEK, selectedGranularity, onClick)
        GranularityButton(Granularity.MONTH, selectedGranularity, onClick)
        GranularityButton(Granularity.YEAR, selectedGranularity, onClick)
    }
}
@Composable
fun GranularityButton(
    granularity: Granularity,
    selectedGranularity: Granularity,
    onClick: (Granularity) -> Unit,
) {
    val isActive = granularity == selectedGranularity
    Box(Modifier
        .background(if (isActive) Yellow20 else Color.Transparent, CircleShape)
        .padding(vertical = 8.dp, horizontal = 24.dp)
        .clickable { onClick.invoke(granularity) }
    ) {
        Text(
            granularity.label, style = MaterialTheme.typography.bodySmall.copy(
                color = if (isActive) Yellow100 else MaterialTheme.colorScheme.tertiary,
                fontWeight = if (isActive) FontWeight.Bold else FontWeight.Medium
            )
        )
    }
}
@Composable
private fun SummarySection(
    accBalance: UiResult<Long>,
    transactionSummary: UiResult<TransactionSummaryModel>,
    modifier: Modifier = Modifier
) {
    Napier.d("Recompose SummarySection: $accBalance, $transactionSummary")
    Column(
        modifier
            .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        HomeTopBackground,
                        HomeTopBackground.copy(alpha = 0.15f)
                    ),
                )
            )
    ) {
        AccountBalance(modifier, accBalance)
        VSpacer(16)
        TransactionSummary(modifier.padding(bottom = 24.dp), transactionSummary)
    }
}
@Composable
private fun TransactionSummary(
    modifier: Modifier = Modifier,
    summary: UiResult<TransactionSummaryModel>
) {
    Napier.d("Recompose TransactionSummary: $summary")
    Row(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier.padding(horizontal = 16.dp)
    ) {
        when (summary) {
            is UiResult.Success -> {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Green100)
                ) {
                    Row(
                        Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .size(36.dp)
                        ) {
                            Image(
                                Res.image.ic_income.painterResource(),
                                null,
                                Modifier.size(24.dp).align(Alignment.Center)
                            )
                        }
                        HSpacer(10)
                        Column(
                            Modifier.wrapContentHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Income",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                            )
                            val totalIncome = summary.data.totalIncome.format()
                            Text(
                                "Rp$totalIncome",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = if (totalIncome.length > 6) 14.sp else 16.sp
                                ),
                                maxLines = 1
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Red100)
                ) {
                    Row(
                        Modifier.fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .size(36.dp)
                        ) {
                            Image(
                                Res.image.ic_expense.painterResource(),
                                null,
                                Modifier.size(24.dp).align(Alignment.Center)
                            )
                        }
                        HSpacer(10)
                        Column(
                            Modifier.wrapContentHeight(),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Expenses",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary),
                            )
                            val totalExpense = summary.data.totalExpense.format()
                            Text(
                                "Rp$totalExpense",
                                style = MaterialTheme.typography.bodyLarge.copy(
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontSize = if (totalExpense.length > 6) 14.sp else 16.sp
                                ),
                                maxLines = 1
                            )
                        }
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
private fun AccountBalance(
    modifier: Modifier = Modifier,
    balance: UiResult<Long>
) {
    Napier.d("Recompose AccountBalance: $balance")
    Column(
        modifier.heightIn(min = 60.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        when (balance) {
            is UiResult.Success -> {
                Text(
                    "Account Balance", style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.colorScheme.tertiary
                    )
                )
                VSpacer(4)
                Text(
                    text = "Rp${balance.data.format()}",
                    style = MaterialTheme.typography.titleMedium.copy(fontSize = 40.sp)
                )
            }

            is UiResult.Error -> {
            }

            is UiResult.Loading -> {
                LoadingDots()
            }
            else -> {
            }
        }
    }
}
@Composable
private fun RecentTransaction(
    recent: UiResult<List<TransactionModel>>
) {
    Napier.d("Recompose RecentTransaction: $recent")
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