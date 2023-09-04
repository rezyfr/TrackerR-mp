package dev.rezyfr.trackerr.preview.component

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import dev.rezyfr.trackerr.domain.model.TransactionModel
import dev.rezyfr.trackerr.presentation.component.ui.TransactionItem
import dev.rezyfr.trackerr.presentation.theme.AppTheme

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun TransactionItemPreview() {
    AppTheme {
        TransactionItem(
            TransactionModel(
                id = 1,
                category = "Food",
                categoryIcon = "https://img.icons8.com/ios/452/food.png",
                desc = "Bought some food",
                amount = 100000,
                date = "07.30 PM",
                type = "expense"
            )
        )
    }
}