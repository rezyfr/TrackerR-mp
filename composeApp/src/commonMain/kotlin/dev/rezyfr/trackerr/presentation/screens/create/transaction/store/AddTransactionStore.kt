package dev.rezyfr.trackerr.presentation.screens.create.transaction.store

import com.arkivanov.mvikotlin.core.store.Store
import dev.rezyfr.trackerr.domain.UiResult
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.transaction.TransactionModel
import dev.rezyfr.trackerr.domain.model.WalletModel
import dev.rezyfr.trackerr.presentation.component.base.datepicker.DateProperty
import dev.rezyfr.trackerr.presentation.component.base.datepicker.calculateDayOfMonths
import dev.rezyfr.trackerr.presentation.component.base.datepicker.calculateMonths
import dev.rezyfr.trackerr.presentation.component.base.datepicker.calculateYears
import dev.rezyfr.trackerr.presentation.component.base.datepicker.toDayOfMonth
import dev.rezyfr.trackerr.presentation.component.base.datepicker.toMonth
import dev.rezyfr.trackerr.presentation.component.base.datepicker.toYear
import dev.rezyfr.trackerr.presentation.component.ui.BottomSheet
import dev.rezyfr.trackerr.presentation.component.util.getCurrentLdt
import kotlinx.datetime.LocalDateTime

interface AddTransactionStore : Store<AddTransactionStore.Intent, AddTransactionStore.State, Nothing> {

    sealed class Intent {
        data class OnAmountChange(val amount: String) : Intent()
        data class OnDescriptionChange(val description: String) : Intent()
        data class OnCategoryChange(val category: Int) : Intent()
        data class OnWalletChange(val wallet: Int) : Intent()
        data class OnDateChange(val date: DateProperty) : Intent()
        data class OnTypeChange(val type: CategoryType) : Intent()
        data class OnChangeDayOfMonth(val dom: Int) : Intent()
        data class OnChangeMonth(val month: Int) : Intent()
        data class OnChangeYear(val year: Int) : Intent()
        object CreateTransaction : Intent()
        object GetWallets : Intent()
        data class GetCategories(val type: CategoryType) : Intent()
    }

    sealed class Result {
        data class OnAmountChange(val amount: String) : Result()
        data class OnDescriptionChange(val description: String) : Result()
        data class OnCategoryChange(val category: CategoryModel) : Result()
        data class OnWalletChange(val wallet: WalletModel) : Result()
        data class OnDateChange(val date: DateProperty) : Result()
        data class OnTypeChange(val type: CategoryType) : Result()
        data class CreateTransaction(val result: UiResult<TransactionModel>) : Result()
        data class GetWallets(val result: UiResult<List<WalletModel>>) : Result()
        data class GetCategories(val result: UiResult<List<CategoryModel>>) : Result()
    }

    data class State(
        val type: CategoryType = CategoryType.EXPENSE,
        val amount: Double = 0.0,
        val categoryId: Int = 0,
        val createdDate: LocalDateTime = getCurrentLdt(),
        val selectedDay: DateProperty = createdDate.toDayOfMonth(),
        val selectedMonth: DateProperty = createdDate.toMonth(),
        val selectedYear: DateProperty = createdDate.toYear(IntRange(2000, 2100)),
        val description: String = "",
        val selectedWallet: WalletModel? = null,
        val selectedCategory: CategoryModel? = null,
        val transactionResult: UiResult<TransactionModel> = UiResult.Uninitialized,
        val walletResult: UiResult<List<WalletModel>> = UiResult.Uninitialized,
        val dateOptions: Triple<List<DateProperty>, List<DateProperty>, List<DateProperty>> = Triple(
            calculateDayOfMonths(),
            calculateMonths(),
            calculateYears()
        ),
        val categoryResult: UiResult<List<CategoryModel>> = UiResult.Uninitialized,
        val datePickerSheet: BottomSheet = BottomSheet(),
        val walletBottomSheet: BottomSheet = BottomSheet(),
        val categoryBottomSheet: BottomSheet = BottomSheet(),
    )
}