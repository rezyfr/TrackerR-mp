package dev.rezyfr.trackerr.presentation.screens.transaction

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import cafe.adriel.voyager.navigator.tab.LocalTabNavigator
import cafe.adriel.voyager.navigator.tab.Tab
import cafe.adriel.voyager.navigator.tab.TabOptions
import dev.rezyfr.trackerr.Res

class TransactionTab : Tab {
    override val options: TabOptions
        @Composable get() {
            val navigator = LocalTabNavigator.current
            return TabOptions(
                0u,
                "Home",
                painterResource(if (navigator.current == this) Res.image.ic_menu_transaction_active else Res.image.ic_menu_transaction_inactive),
            )
        }

    @Composable
    override fun Content() {

    }
}