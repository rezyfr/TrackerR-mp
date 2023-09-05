package dev.rezyfr.trackerr.presentation.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.rezyfr.trackerr.Res
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.TransactionModel
import dev.rezyfr.trackerr.presentation.component.ui.TransactionItem
import org.koin.compose.koinInject
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class HomeTab : Tab, KoinComponent {
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
        val navigator = LocalNavigator.currentOrThrow
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
        Scaffold {
            HomeContent(
                state = state
            )
        }
    }

    @Composable
    private fun HomeContent(
        state: HomeState
    ) {
        LazyColumn {
            item() {
                RecentTransaction(state.recentTransaction)
            }
        }
    }

    @Composable
    fun RecentTransaction(
        recent: UiResult<List<TransactionModel>>
    ) {
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