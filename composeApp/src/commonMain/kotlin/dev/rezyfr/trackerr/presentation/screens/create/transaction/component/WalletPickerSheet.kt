package dev.rezyfr.trackerr.presentation.screens.create.transaction.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import com.seiko.imageloader.rememberImagePainter
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.WalletModel

@Composable
fun WalletPickerSheet(
    modifier: Modifier = Modifier,
    currentWallet: WalletModel? = null,
    wallets: UiResult<List<WalletModel>>,
    onContinue: (Int) -> Unit = {},
) {
    var selectedWallet by remember { mutableStateOf(currentWallet) }

    FormTransactionSheet(
        modifier = modifier,
        label = "Wallet",
        onContinue = {
            selectedWallet?.let {
                onContinue(it.id)
            }
        },
    ) {
        if (wallets is UiResult.Success) {
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(wallets.data) { wallet ->
                    val isChosen = selectedWallet?.id == wallet.id
                    IconButton(
                        onClick = { selectedWallet = wallet },
                        modifier = Modifier
                            .clip(RoundedCornerShape(8.dp))
                            .run {
                                if (isChosen) {
                                    background(
                                        color = MaterialTheme.colorScheme.primaryContainer,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.primary,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                } else {
                                    background(
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                    border(
                                        width = 1.dp,
                                        color = MaterialTheme.colorScheme.outline,
                                        shape = RoundedCornerShape(8.dp)
                                    )
                                }
                            }
                            .padding(4.dp)
                    ) {
                        Image(
                            painter = rememberImagePainter(wallet.icon),
                            contentDescription = null,
                            modifier = Modifier
                                .height(48.dp)
                                .wrapContentWidth(),
                            contentScale = ContentScale.FillWidth
                        )
                    }
                }
            }
        }
    }
}