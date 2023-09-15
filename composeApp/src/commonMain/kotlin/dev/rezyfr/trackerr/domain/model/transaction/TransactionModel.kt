package dev.rezyfr.trackerr.domain.model.transaction

import androidx.compose.ui.graphics.Color
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.presentation.component.util.format
import dev.rezyfr.trackerr.presentation.theme.GreenIcon
import dev.rezyfr.trackerr.presentation.theme.RedIcon

data class TransactionModel(
    val id: Int,
    val desc: String,
    val amount: Long,
    val type: CategoryType,
    val date: String,
    val categoryName: String,
    val categoryIcon: String,
    val categoryColor: Long
) {
    private val isIncome: Boolean
        get() = type == CategoryType.INCOME

    val amountLabel: String
        get() = "${if (isIncome) '+' else '-' } Rp${amount.format()}"

    val labelColor: Color
        get() = if (isIncome) GreenIcon else RedIcon
}
