package dev.rezyfr.trackerr.presentation.screens.create

sealed class CreateScreens(val header: String, val dialog: TrDialog) {
    data object AddAccount : CreateScreens("Add Account", TrDialog.AddAccount)
//    data object AddTransaction : CreateScreens("Add Transaction", TrDialog.AddTransaction)
//    data object AddCategory : CreateScreens("Add Category", TrDialog.AddCategory)
//    data object AddBudget : CreateScreens("Add Budget", TrDialog.AddBudget)
}

sealed interface TrDialog {
    data object AddAccount : TrDialog
//    data object AddTransaction : TrDialog
//    data object AddCategory : TrDialog
//    data object AddBudget : TrDialog
}