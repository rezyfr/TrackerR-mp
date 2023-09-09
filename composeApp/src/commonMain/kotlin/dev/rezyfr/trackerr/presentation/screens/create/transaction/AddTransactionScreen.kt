package dev.rezyfr.trackerr.presentation.screens.create.transaction

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.presentation.component.AmountTextField
import dev.rezyfr.trackerr.presentation.component.base.ButtonText
import dev.rezyfr.trackerr.presentation.component.base.TrPrimaryButton
import dev.rezyfr.trackerr.presentation.component.base.TrTextField
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DayOfMonth
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DefaultWheelDatePicker
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Year
import dev.rezyfr.trackerr.presentation.component.ui.BottomSheet
import dev.rezyfr.trackerr.presentation.component.ui.TypeSelector
import dev.rezyfr.trackerr.presentation.component.util.format
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
            onChangeType = { viewModel.onChangeType(it) },
            onChangeAmount = { viewModel.onChangeAmount(it.text) },
            onChangeDate = { viewModel.onChangeDayOfMonth(it) },
            onChangeMonth = { viewModel.onChangeMonth(it) },
            onChangeYear = { viewModel.onChangeYear(it) },
            onChangeDescription = { viewModel.onChangeDescription(it) },
            onContinue = { viewModel.onContinue() },
        )
    }

    @Composable
    private fun AddTransactionScreen(
        state: AddTransactionState,
        onContinue: () -> Unit = {},
        onBack: () -> Unit = {},
        onChangeCategory: (Int) -> Unit = {},
        onChangeDate: (Int) -> Unit = {},
        onChangeMonth: (Int) -> Unit = {},
        onChangeYear: (Int) -> Unit = {},
        onChangeAmount: (TextFieldValue) -> Unit = {},
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
                DatePickerSection(
                    Modifier.fillMaxWidth()
                        .background(
                            color = MaterialTheme.colorScheme.background,
                            shape = RoundedCornerShape(
                                topStart = 32.dp, topEnd = 32.dp
                            )
                        )
                        .padding(horizontal = 16.dp, vertical = 24.dp),
                    state,
                    onChangeDay = { onChangeDate(it) },
                    onChangeMonth = { onChangeMonth(it) },
                    onChangeYear = { onChangeYear(it) },
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
        Column(
            modifier = modifier.fillMaxWidth()
        ) {
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
                    placeholder = "Description",
                    value = state.description,
                    onValueChange = onChangeDescription
                )
                TrTextField(
                    placeholder = "Date",
                    value = "${state.selectedDay.value} ${state.selectedMonth.text} ${state.selectedYear.value}",
                    onClick = { state.datePickerSheet.expand() }
                )
                TrPrimaryButton(
                    Modifier.fillMaxWidth(),
                    text = { ButtonText("Continue") },
                    onClick = onContinue
                )
            }
        }
    }

    @Composable
    fun DatePickerSection(
        modifier: Modifier = Modifier,
        state: AddTransactionState,
        onChangeDay: (Int) -> Unit = {},
        onChangeMonth: (Int) -> Unit = {},
        onChangeYear: (Int) -> Unit = {},
    ) {
        var selectedDate by remember { mutableStateOf(state.selectedDay) }
        var selectedMonth by remember { mutableStateOf(state.selectedMonth) }
        var selectedYear by remember { mutableStateOf(state.selectedYear) }

        Column(modifier) {
            Text(
                "Date",
                style = MaterialTheme.typography.bodyMedium.copy(color = MaterialTheme.colorScheme.onBackground)
            )
            Row(Modifier.fillMaxWidth()) {
                DefaultWheelDatePicker(
                    modifier = Modifier.weight(1f),
                    items = state.dateOptions.first,
                    selectedItem = state.selectedDay
                ) {
                    selectedDate = it
                }
                DefaultWheelDatePicker(
                    modifier = Modifier.weight(1f),
                    items = state.dateOptions.second,
                    selectedItem = state.selectedMonth
                ) {
                    selectedMonth = it
                }
                DefaultWheelDatePicker(
                    modifier = Modifier.weight(1f),
                    items = state.dateOptions.third,
                    selectedItem = state.selectedYear
                ) {
                    selectedYear = it
                }
            }
            TrPrimaryButton(
                Modifier.fillMaxWidth(),
                text = { ButtonText("Continue") },
                onClick = {
                    onChangeDay(selectedDate.value)
                    onChangeMonth(selectedMonth.value)
                    onChangeYear(selectedYear.value)
                    state.datePickerSheet.collapse()
                }
            )
        }
    }

    @OptIn(ExperimentalComposeUiApi::class)
    @Composable
    fun RevealingSheet(
        bottomSheet: BottomSheet,
        modifier: Modifier = Modifier,
        content: @Composable () -> Unit
    ) {
        val visible by bottomSheet.visibilityState
        AnimatedVisibility(
            modifier = Modifier
                .fillMaxSize()
                .zIndex(1_000f),
            visible = visible,
            enter = fadeIn(),
            exit = fadeOut()
        ) {
            val keyboardController = LocalSoftwareKeyboardController.current
            Spacer(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.4f))
                    .testTag("modal_outside_blur")
                    .clickable(
                        onClick = {
                            keyboardController?.hide()
                            bottomSheet.collapse()
                        },
                        enabled = visible
                    )
            )
        }
        AnimatedVisibility(
            modifier = modifier.zIndex(1_100f),
            visible = visible,
            enter = slideInVertically(
                animationSpec = tween(500),
                initialOffsetY = { fullHeight -> fullHeight }
            ),
        ) {
            content()
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