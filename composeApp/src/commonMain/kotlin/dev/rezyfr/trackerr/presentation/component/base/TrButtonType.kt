package dev.rezyfr.trackerr.presentation.component.base

sealed interface TrButtonType {
    object Primary : TrButtonType
    object Danger: TrButtonType
}