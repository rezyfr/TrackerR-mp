package dev.rezyfr.trackerr.presentation.screens.create.account.store

import androidx.compose.ui.text.input.TextFieldValue
import com.arkivanov.mvikotlin.core.store.Store
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.IconModel

interface AddAccountStore : Store<AddAccountStore.Intent, AddAccountStore.State, Nothing> {

    sealed class Intent {
        data class OnNameChange(val name: String) : Intent()
        data class OnBalanceChange(val balance: TextFieldValue) : Intent()
        data class OnIconChange(val icon: Int) : Intent()
        object CreateWallet : Intent()
    }

    sealed class Result {
        data class OnNameChange(val name: String) : Result()
        data class OnBalanceChange(val balance: TextFieldValue) : Result()
        data class OnIconChange(val icon: Int) : Result()
        data class CreateWallet(val result: UiResult<Unit>) : Result()
        data class GetIcon(val result: List<IconModel>) : Result()
    }

    data class State(
        val name: String = "",
        val balance: TextFieldValue = TextFieldValue("0"),
        val icon: Int = -1,
        val result: UiResult<Unit> = UiResult.Uninitialized,
        val iconList: List<IconModel> = listOf()
    )
}