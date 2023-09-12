package dev.rezyfr.trackerr.presentation.screens.root

import com.arkivanov.mvikotlin.core.store.Store

interface RootStore : Store<RootStore.Intent, Unit, Unit> {
    sealed class Intent {
        object CheckUserToken : Intent()
    }
}