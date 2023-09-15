package dev.rezyfr.trackerr.presentation.screens.create.transaction.store

import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.handleResult
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.usecase.category.GetCategoriesUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.CreateTransactionUseCase
import dev.rezyfr.trackerr.domain.usecase.wallet.GetWalletsUseCase
import dev.rezyfr.trackerr.mainDispatcher
import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AddTransactionExecutor :
    CoroutineExecutor<AddTransactionStore.Intent, Unit, AddTransactionStore.State, AddTransactionStore.Result, Nothing>(
        mainDispatcher
    ), KoinComponent {

    private val createTransactionUseCase by inject<CreateTransactionUseCase>()
    private val getWalletsUseCase by inject<GetWalletsUseCase>()
    private val getCategoriesUseCase by inject<GetCategoriesUseCase>()

    init {
        getCategories(CategoryType.EXPENSE)
        getWallet()
    }

    private fun getCategories(type: CategoryType) {
        scope.launch {
            getCategoriesUseCase.executeFlow(type)
                .collectLatest {
                    it.handleResult(
                        ifError = {
                            Napier.e("getCategories: ${it.message}")
                        },
                        ifSuccess = { cat ->
                            dispatch(AddTransactionStore.Result.GetCategories(UiResult.Success(cat)))
                        }
                    )
                }
        }
    }

    private fun getWallet() {
        scope.launch {
            getWalletsUseCase.executeFlow(null)
                .collectLatest {
                    it.handleResult(
                        ifError = {
                            Napier.e("getWallet: ${it.message}")
                        },
                        ifSuccess = { wallet ->
                            dispatch(AddTransactionStore.Result.GetWallets(UiResult.Success(wallet)))
                        }
                    )
                }
        }
    }

    private fun createTransaction(state: AddTransactionStore.State) {
        scope.launch {
            createTransactionUseCase.executeFlow(
                CreateTransactionUseCase.Param(
                    state.amount,
                    state.selectedCategory!!.id,
                    state.selectedMonth.value,
                    state.selectedDay.value,
                    state.selectedYear.value,
                    state.description,
                    state.selectedWallet!!.id,
                )
            ).collectLatest {
                it.handleResult(
                    ifError = {
                        dispatch(AddTransactionStore.Result.CreateTransaction(UiResult.Error(it)))
                    },
                    ifSuccess = { trx ->
                        dispatch(AddTransactionStore.Result.CreateTransaction(UiResult.Success(trx)))
                    }
                )
            }
        }
    }

    override fun executeIntent(
        intent: AddTransactionStore.Intent,
        getState: () -> AddTransactionStore.State
    ) {
        when (intent) {
            AddTransactionStore.Intent.CreateTransaction -> createTransaction(getState())
            is AddTransactionStore.Intent.GetCategories -> getCategories(intent.type)
            AddTransactionStore.Intent.GetWallets -> getWallet()
            is AddTransactionStore.Intent.OnAmountChange -> {
                dispatch(AddTransactionStore.Result.OnAmountChange(intent.amount))
            }

            is AddTransactionStore.Intent.OnCategoryChange -> {
                val selected =
                    getState().categoryResult.asSuccess()?.find { it.id == intent.category }
                dispatch(AddTransactionStore.Result.OnCategoryChange(selected!!))
            }

            is AddTransactionStore.Intent.OnDateChange -> {
                dispatch(AddTransactionStore.Result.OnDateChange(intent.date))
            }

            is AddTransactionStore.Intent.OnDescriptionChange -> {
                dispatch(AddTransactionStore.Result.OnDescriptionChange(intent.description))
            }

            is AddTransactionStore.Intent.OnTypeChange -> {
                dispatch(AddTransactionStore.Result.OnTypeChange(intent.type))
            }

            is AddTransactionStore.Intent.OnWalletChange -> {
                val selected = getState().walletResult.asSuccess()?.find { it.id == intent.wallet }
                dispatch(AddTransactionStore.Result.OnWalletChange(selected!!))
            }

            is AddTransactionStore.Intent.OnChangeDayOfMonth -> {
                val selected = getState().dateOptions.first.find { it.value == intent.dom }
                dispatch(AddTransactionStore.Result.OnDateChange(selected!!))
            }

            is AddTransactionStore.Intent.OnChangeMonth -> {
                val selected = getState().dateOptions.second.find { it.value == intent.month }
                dispatch(AddTransactionStore.Result.OnDateChange(selected!!))
            }

            is AddTransactionStore.Intent.OnChangeYear -> {
                val selected = getState().dateOptions.third.find { it.value == intent.year }
                dispatch(AddTransactionStore.Result.OnDateChange(selected!!))
            }
        }
    }
}
