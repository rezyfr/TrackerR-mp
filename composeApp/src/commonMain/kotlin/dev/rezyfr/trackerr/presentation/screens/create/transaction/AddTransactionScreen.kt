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
import dev.rezyfr.trackerr.presentation.screens.create.transaction.store.AddTransactionStore
import dev.rezyfr.trackerr.presentation.theme.typeIndicatorColor

@Composable
fun AddTransactionScreen(
    addTransactionComponent: AddTransactionComponent,
) {
    val state by addTransactionComponent.state.collectAsState()

    AddTransactionScreen(
        state,
        onEvent = addTransactionComponent::onEvent,
        onAction = addTransactionComponent::onAction,
    )

    LaunchedEffect(state.transactionResult) {
        if (state.transactionResult.isSuccess()) {
            addTransactionComponent.onAction(AddTransactionComponent.Action.Finish)
        }
    }
}

@Composable
private fun AddTransactionScreen(
    state: AddTransactionStore.State,
    onEvent: (AddTransactionStore.Intent) -> Unit,
    onAction: (AddTransactionComponent.Action) -> Unit,
) {
    Box(Modifier.fillMaxSize()) {
        Scaffold(
            topBar = {
                TransactionAppBar(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.TopCenter),
                    type = state.type,
                    onBack = { onAction(AddTransactionComponent.Action.NavigateBack) },
                    onEvent = onEvent,
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
                    onEvent = onEvent,
                )
            }
        }
        RevealingSheet(state.datePickerSheet, modifier = Modifier.align(Alignment.BottomCenter)) {
            DatePickerSheet(
                Modifier.fillMaxWidth(),
                state = state,
                onChangeDay = { onEvent(AddTransactionStore.Intent.OnChangeDayOfMonth(it)) },
                onChangeMonth = { onEvent(AddTransactionStore.Intent.OnChangeMonth(it)) },
                onChangeYear = { onEvent(AddTransactionStore.Intent.OnChangeYear(it)) },
            )
        }
        RevealingSheet(state.walletBottomSheet, modifier = Modifier.align(Alignment.BottomCenter)) {
            WalletPickerSheet(
                Modifier.fillMaxWidth(),
                wallets = state.walletResult,
                onContinue = {
                    onEvent(AddTransactionStore.Intent.OnWalletChange(it))
                    state.walletBottomSheet.collapse()
                },
                currentWallet = state.selectedWallet
            )
        }
        RevealingSheet(
            state.categoryBottomSheet,
            modifier = Modifier.align(Alignment.BottomCenter)
        ) {
            CategoryPickerSheet(
                Modifier.fillMaxWidth(),
                categories = state.categoryResult,
                onContinue = {
                    onEvent(AddTransactionStore.Intent.OnCategoryChange(it))
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
    state: AddTransactionStore.State,
    onEvent: (AddTransactionStore.Intent) -> Unit
) {
    Column(modifier = modifier.fillMaxWidth()) {
        val formattedAmount = state.amount.format()
        AmountTextField(
            modifier = Modifier.padding(16.dp),
            label = "How much?",
            onValueChange = { onEvent(AddTransactionStore.Intent.OnAmountChange(it.text)) },
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
                onValueChange = { onEvent(AddTransactionStore.Intent.OnDescriptionChange(it))}
            )
            TrTextField(
                placeholder = "Date",
                value = "${state.selectedDay.value} ${state.selectedMonth.text} ${state.selectedYear.value}",
                onClick = { state.datePickerSheet.expand() }
            )
            TrTextField(
                placeholder = "Category",
                value = state.selectedCategory?.name.orEmpty(),
                onClick = {
                    state.categoryBottomSheet.expand()
                }
            )
            TrPrimaryButton(
                Modifier.fillMaxWidth(),
                text = { ButtonText("Continue") },
                onClick = { onEvent(AddTransactionStore.Intent.CreateTransaction) }
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
    onEvent: (AddTransactionStore.Intent) -> Unit,
) {
    CenterAlignedTopAppBar(
        title = {
            TypeSelector(
                type = type,
                onSelectType = { onEvent(AddTransactionStore.Intent.OnTypeChange(it)) },
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