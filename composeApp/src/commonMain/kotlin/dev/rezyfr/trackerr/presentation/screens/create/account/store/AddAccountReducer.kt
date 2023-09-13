package dev.rezyfr.trackerr.presentation.screens.create.account.store

import com.arkivanov.mvikotlin.core.store.Reducer

class AddAccountReducer : Reducer<AddAccountStore.State, AddAccountStore.Result> {
    override fun AddAccountStore.State.reduce(msg: AddAccountStore.Result): AddAccountStore.State {
        return when (msg) {
            is AddAccountStore.Result.CreateWallet -> copy(result = msg.result)
            is AddAccountStore.Result.GetIcon -> copy(iconList = msg.result)
            is AddAccountStore.Result.OnBalanceChange -> copy(balance = msg.balance)
            is AddAccountStore.Result.OnIconChange -> copy(icon = msg.icon)
            is AddAccountStore.Result.OnNameChange -> copy(name = msg.name)
        }
    }

}