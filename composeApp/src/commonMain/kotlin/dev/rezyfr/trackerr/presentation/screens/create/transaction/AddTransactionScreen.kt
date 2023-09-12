package dev.rezyfr.trackerr.presentation.screens.create.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.presentation.component.AmountTextField
import dev.rezyfr.trackerr.presentation.component.base.ButtonText
import dev.rezyfr.trackerr.presentation.component.base.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.component.base.TrTextField
import dev.rezyfr.trackerr.presentation.component.ui.TypeSelector
import dev.rezyfr.trackerr.presentation.component.util.format
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.CategoryPickerSheet
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.DatePickerSheet
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.RevealingSheet
import dev.rezyfr.trackerr.presentation.screens.create.transaction.component.WalletPickerSheet
import dev.rezyfr.trackerr.presentation.theme.typeIndicatorColor
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddTransactionScreen() : Screen, KoinComponent {
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val viewModel by inject<AddTransactionViewModel>()
        val state by viewModel.state.collectAsState()

        AddTransactionScreen(
            state,
            onChangeType = { viewModel.onChangeType(it) },
            onChangeAmount = { viewModel.onChangeAmount(it.text) },
            onChangeDate = { viewModel.onChangeDayOfMonth(it) },
            onChangeMonth = { viewModel.onChangeMonth(it) },
            onChangeYear = { viewModel.onChangeYear(it) },
            onChangeWallet = { viewModel.onChangeWallet(it) },
            onChangeDescription = { viewModel.onChangeDescription(it) },
            onChangeCategory = { viewModel.onChangeCategory(it) },
            onContinue = { viewModel.onContinue() },
            onBack = { navigator.pop() }
        )

        LaunchedEffect(state.transactionResult) {
            if (state.transactionResult.isSuccess()) {
                navigator.pop()
            }
        }
    }

    @Composable
    private fun AddTransactionScreen(
        state: AddTransactionState,
        onContinue: () -> Unit = {},
        onBack: () -> Unit = {},
        onChangeWallet: (Int) -> Unit = {},
        onChangeDate: (Int) -> Unit = {},
        onChangeMonth: (Int) -> Unit = {},
        onChangeYear: (Int) -> Unit = {},
        onChangeAmount: (TextFieldValue) -> Unit = {},
        onChangeDescription: (String) -> Unit = {},
        onChangeType: (CategoryType) -> Unit = {},
        onChangeCategory: (Int) -> Unit = {},
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
                Box(
                    Modifier.fillMaxSize()
                        .background(state.type.typeIndicatorColor()),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    AddTransactionDialog(
                        state = state,
                        onContinue = onContinue,
                        onChangeAmount = onChangeAmount,
                        onChangeDescription = onChangeDescription,
                    )
                }
            }
            RevealingSheet(state.datePickerSheet, modifier = Modifier.align(Alignment.BottomCenter)) {
                DatePickerSheet(
                    Modifier.fillMaxWidth(),
                    state = state,
                    onChangeDay = { onChangeDate(it) },
                    onChangeMonth = { onChangeMonth(it) },
                    onChangeYear = { onChangeYear(it) },
                )
            }
            RevealingSheet(state.walletBottomSheet, modifier = Modifier.align(Alignment.BottomCenter)) {
                WalletPickerSheet(
                    Modifier.fillMaxWidth(),
                    wallets = state.walletResult,
                    onContinue = {
                        onChangeWallet(it)
                        state.walletBottomSheet.collapse()
                    },
                    currentWallet = state.selectedWallet
                )
            }
            RevealingSheet(state.categoryBottomSheet, modifier = Modifier.align(Alignment.BottomCenter)) {
                CategoryPickerSheet(
                    Modifier.fillMaxWidth(),
                    categories = state.categoryResult,
                    onContinue = {
                        onChangeCategory(it)
                        state.categoryBottomSheet.collapse()
                    },
                    currentCategory = state.selectedCategory
                )
            }
        }
    }

    @Composable
    fun AddTransactionDialog(
        modifier: Modifier = Modifier,
        state: AddTransactionState,
        onContinue: () -> Unit,
        onChangeAmount: (TextFieldValue) -> Unit,
        onChangeDescription: (String) -> Unit,
    ) {
        Column(modifier = modifier.fillMaxWidth()) {
            val formattedAmount = state.amount.format()
            AmountTextField(
                modifier = Modifier.padding(16.dp),
                label = "How much?",
                onValueChange = onChangeAmount,
                value = TextFieldValue(formattedAmount, TextRange(formattedAmount.length)),
            )
            Column(
                Modifier
                    .fillMaxWidth()
                    .background(
                        color = MaterialTheme.colorScheme.background,
                        shape = RoundedCornerShape(
                            topStart = 32.dp, topEnd = 32.dp
                        )
                    )
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TrTextField(
                    placeholder = "Wallet",
                    value = state.selectedWallet?.name.orEmpty(),
                    onClick = { state.walletBottomSheet.expand() }
                )
                TrTextField(
                    placeholder = "Description",
                    value = state.description,
                    onValueChange = onChangeDescription
                )
                TrTextField(
                    placeholder = "Date",
                    value = "${state.selectedDay.value} ${state.selectedMonth.text} ${state.selectedYear.value}",
                    onClick = { state.datePickerSheet.expand() }
                )
                TrTextField(
                    placeholder = "Category",
                    value = state.selectedCategory?.name.orEmpty(),
                    onClick = { state.categoryBottomSheet.expand() }
                )
                TrPrimaryButton(
                    Modifier.fillMaxWidth(),
                    text = { ButtonText("Continue") },
                    onClick = onContinue
                )
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun TransactionAppBar(
        modifier: Modifier = Modifier,
        onBack: () -> Unit = {},
        type: CategoryType,
        onSelectType: (CategoryType) -> Unit = {},
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