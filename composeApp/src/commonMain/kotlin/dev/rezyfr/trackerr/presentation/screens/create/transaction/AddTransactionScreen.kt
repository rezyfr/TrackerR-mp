package dev.rezyfr.trackerr.presentation.screens.create.transaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rezyfr.trackerr.presentation.component.ui.TypeSelector
import dev.rezyfr.trackerr.presentation.theme.typeIndicatorColor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddTransactionScreen : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel by inject<AddTransactionViewModel>()
        val state by viewModel.state.collectAsState()

        AddTransactionScreen(
            state,
            onChangeType = { viewModel.onChangeType(it)}
        )
    }

    @Composable
    private fun AddTransactionScreen(
        state: AddTransactionState,
        onContinue: () -> Unit = {},
        onBack: () -> Unit = {},
        onChangeCategory: (Int) -> Unit = {},
        onChangeDate: (String) -> Unit = {},
        onChangeAmount: (String) -> Unit = {},
        onChangeDescription: (String) -> Unit = {},
        onChangeType: (String) -> Unit = {}
    ) {
        Box(Modifier.fillMaxSize()) {
            Scaffold(
                topBar = {
                    TransactionAppBar(
                        modifier = Modifier
                            .fillMaxWidth()
                            .align(Alignment.TopCenter),
                        type = state.type,
                        onBack = onBack,
                        onSelectType = onChangeType,
                    )
                },
                containerColor = state.type.typeIndicatorColor()
            ) {

            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TransactionAppBar(
        modifier: Modifier = Modifier,
        onBack: () -> Unit = {},
        type: String,
        onSelectType: (String) -> Unit = {},
    ) {
        CenterAlignedTopAppBar(
            title = {
                TypeSelector(
                    type = type,
                    onSelectType = onSelectType,
                )
            },
            colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Transparent),
            navigationIcon = {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        Icons.Default.ArrowBack,
                        null,
                        modifier = Modifier.padding(start = 16.dp),
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            },
            modifier = modifier
        )
    }
}