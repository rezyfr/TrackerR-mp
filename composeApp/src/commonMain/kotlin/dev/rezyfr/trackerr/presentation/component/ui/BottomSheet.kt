package dev.rezyfr.trackerr.presentation.component.ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

const val EXPAND = 1
const val COLLAPSE = 0

var showBottomSheet = COLLAPSE

data class BottomSheet(
    val visibilityState: MutableState<Boolean> = mutableStateOf(false)
) {
    fun collapse() {
        visibilityState.value = false
        showBottomSheet = COLLAPSE
    }

    fun expand() {
        visibilityState.value = true
        showBottomSheet = EXPAND
    }
}