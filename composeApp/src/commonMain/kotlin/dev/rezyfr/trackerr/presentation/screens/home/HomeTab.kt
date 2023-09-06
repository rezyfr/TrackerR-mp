package dev.rezyfr.trackerr.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import com.seiko.imageloader.rememberImagePainter
import dev.rezyfr.trackerr.Res
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.TransactionModel
import dev.rezyfr.trackerr.domain.model.TransactionSummaryModel
import dev.rezyfr.trackerr.presentation.HSpacer
import dev.rezyfr.trackerr.presentation.VSpacer
import dev.rezyfr.trackerr.presentation.component.base.TrCapsuleButton
import dev.rezyfr.trackerr.presentation.component.ui.TransactionItem
import dev.rezyfr.trackerr.presentation.component.util.format
import dev.rezyfr.trackerr.presentation.theme.Green100
import dev.rezyfr.trackerr.presentation.theme.HomeTopBackground
import dev.rezyfr.trackerr.presentation.theme.Red100
import io.github.skeptick.libres.compose.painterResource
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

internal class HomeTab : Tab, KoinComponent {
    override val options: TabOptions
        @Composable get() {
            val navigator = LocalTabNavigator.current
            return TabOptions(
                0u,
                "Home",
                painterResource(if (navigator.current == this) Res.image.ic_menu_home_active else Res.image.ic_menu_home_inactive),
            )
        }


    @Composable
    override fun Content() {
        val viewModel: HomeViewModel by inject()

        val state by viewModel.state.collectAsState()

        HomeScreen(
            state = state
        )
    }

    @Composable
    fun HomeScreen(
        state: HomeState
    ) {
        Scaffold(
            topBar = {
                HomeTopBar()
            }
        ) {
            HomeContent(
                state = state,
                modifier = Modifier.padding(it)
            )
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    private fun HomeTopBar() {
        CenterAlignedTopAppBar(
            title = {

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
                    modifier = Modifier.padding(16.dp).size(32.dp)
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
        state: HomeState
    ) {
        LazyColumn(modifier) {
            item {
                SummarySection(
                    state, Modifier.fillMaxWidth()
                )
            }
            item() {
                RecentTransaction(state.recentTransaction)
            }
        }
    }

    @Composable
    private fun SummarySection(
        state: HomeState,
        modifier: Modifier = Modifier
    ) {
        AccountBalance(
            modifier.background(HomeTopBackground).padding(bottom = 16.dp), state.accBalance
        )
        TransactionSummary(
            modifier.background(HomeTopBackground)
                .clip(RoundedCornerShape(bottomStart = 32.dp, bottomEnd = 32.dp))
                .padding(bottom = 24.dp), state.transactionSummary
        )
    }

    @Composable
    private fun TransactionSummary(
        modifier: Modifier = Modifier,
        summary: UiResult<TransactionSummaryModel>
    ) {
        Row(
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = modifier.padding(horizontal = 16.dp)
        ) {
            if (summary is UiResult.Success) {
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Green100)
                ) {
                    Row(Modifier.fillMaxWidth().padding(16.dp)) {
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .size(48.dp)
                        ) {
                            Image(
                                rememberImagePainter(Res.image.ic_income),
                                null,
                                Modifier.size(32.dp).align(Alignment.Center)
                            )
                        }
                        HSpacer(10)
                        Column(
                            Modifier.height(48.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Income",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                            )
                            Text(
                                "Rp${summary.data.totalIncome.format()}",
                                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onPrimary),
                            )
                        }
                    }
                }
                Card(
                    modifier = Modifier.weight(1f),
                    shape = RoundedCornerShape(28.dp),
                    colors = CardDefaults.cardColors(containerColor = Red100)
                ) {
                    Row(Modifier.fillMaxWidth().padding(16.dp)) {
                        Box(
                            modifier = Modifier.clip(RoundedCornerShape(16.dp))
                                .background(MaterialTheme.colorScheme.onPrimary)
                                .size(48.dp)
                        ) {
                            Image(
                                rememberImagePainter(Res.image.ic_expense),
                                null,
                                Modifier.size(32.dp).align(Alignment.Center)
                            )
                        }
                        HSpacer(10)
                        Column(
                            Modifier.height(48.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                "Expenses",
                                style = MaterialTheme.typography.bodySmall.copy(color = MaterialTheme.colorScheme.onPrimary),
                            )
                            Text(
                                "Rp${summary.data.totalExpense.format()}",
                                style = MaterialTheme.typography.titleSmall.copy(color = MaterialTheme.colorScheme.onPrimary)
                            )
                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun AccountBalance(
        modifier: Modifier = Modifier,
        balance: UiResult<Long>
    ) {
        Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                "Account Balance", style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.tertiary
                )
            )
            VSpacer(4)
            when (balance) {
                is UiResult.Success -> {
                    Text(
                        text = "Rp${balance.data.format()}",
                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 40.sp)
                    )
                }

                is UiResult.Error -> {

                }

                else -> {

                }
            }
        }
    }

    @Composable
    fun RecentTransaction(
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

            is UiResult.Error -> {

            }

            else -> {

            }
        }
    }
}