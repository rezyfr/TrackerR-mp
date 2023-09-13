package dev.rezyfr.trackerr.presentation.screens.create.transaction.store

import com.arkivanov.mvikotlin.core.store.Reducer
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DayOfMonth
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Month
import dev.rezyfr.trackerr.presentation.component.base.datepicker.Year
import dev.rezyfr.trackerr.utils.NumberUtils

class AddTransactionReducer: Reducer<AddTransactionStore.State, AddTransactionStore.Result> {
    override fun AddTransactionStore.State.reduce(msg: AddTransactionStore.Result): AddTransactionStore.State {
        return when (msg) {
            is AddTransactionStore.Result.CreateTransaction -> copy(transactionResult = msg.result)
            is AddTransactionStore.Result.GetCategories -> copy(categoryResult = msg.result)
            is AddTransactionStore.Result.GetWallets -> copy(walletResult = msg.result)
            is AddTransactionStore.Result.OnAmountChange -> copy(amount = NumberUtils.getCleanString(msg.amount))
            is AddTransactionStore.Result.OnCategoryChange -> copy(selectedCategory = msg.category)
            is AddTransactionStore.Result.OnDateChange -> {
                when (msg.date) {
                    is DayOfMonth -> copy(selectedDay = msg.date)
                    is Month -> copy(selectedMonth = msg.date)
                    is Year -> copy(selectedYear = msg.date)
                    else -> this
                }
            }
            is AddTransactionStore.Result.OnDescriptionChange -> copy(description = msg.description)
            is AddTransactionStore.Result.OnTypeChange -> copy(type = msg.type)
            is AddTransactionStore.Result.OnWalletChange -> copy(selectedWallet = msg.wallet)
        }
    }
}