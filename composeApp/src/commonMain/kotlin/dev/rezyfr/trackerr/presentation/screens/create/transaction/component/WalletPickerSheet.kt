package dev.rezyfr.trackerr.presentation.screens.create.transaction.component

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.WalletModel
import dev.rezyfr.trackerr.presentation.component.ui.IconPicker

@Composable
fun WalletPickerSheet(
    modifier: Modifier = Modifier,
    currentWallet: WalletModel? = null,
    wallets: UiResult<List<WalletModel>>,
    onContinue: (Int) -> Unit = {},
) {
    var selectedWalletId by remember { mutableStateOf(currentWallet?.id) }

    FormTransactionSheet(
        modifier = modifier,
        label = "Wallet",
        onContinue = {
            selectedWalletId?.let {
                onContinue(it)
            }
        },
    ) {
        if (wallets is UiResult.Success) {
            IconPicker(
                selectedWalletId = selectedWalletId,
                idAndIcon = wallets.data.map { it.id to it.icon },
                onIconChoose = { selectedWalletId = it },
            )
        }
    }
}