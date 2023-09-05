package dev.rezyfr.trackerr.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabNavigator
import dev.rezyfr.trackerr.presentation.screens.home.HomeTab
import dev.rezyfr.trackerr.presentation.screens.transaction.TransactionTab
import dev.rezyfr.trackerr.presentation.theme.Disabled


class RootScreen : Screen {

    private val tabs = listOf(HomeTab(), TransactionTab())
    @Composable
    override fun Content() {
        TabNavigator(tab = HomeTab()) { nav ->
            Scaffold(
                bottomBar = {
//                    var tabs by remember { listOf() }
                    RootNavBar(
                        tabs = tabs,
                        onTabSelected = { tab ->
                            nav.current = tab
                        },
                        selectedTab = nav.current
                    )
                }
            ) {
                Box(Modifier.padding(bottom = it.calculateBottomPadding())) {
                    nav.current.Content()
                }
            }
        }
    }

    @Composable
    fun RootNavBar(
        tabs: List<Tab>,
        onTabSelected: (Tab) -> Unit,
        selectedTab: Tab
    ) {
        NavigationBar {
            tabs.forEach {
                val isSelected = it == selectedTab
                NavigationBarItem(
                    selected = isSelected,
                    onClick = {
                        onTabSelected(it)
                    },
                    icon = {
                        Image(
                            painter = it.options.icon!!,
                            contentDescription = null,
                        )
                    },
                    label = {
                        Text(
                            it.options.title, style = MaterialTheme.typography.labelSmall.copy(
                                fontSize = 10.sp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else Disabled
                            )
                        )
                    }
                )
            }
        }
    }
}