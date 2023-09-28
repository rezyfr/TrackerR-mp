package dev.rezyfr.trackerr.data.mapper

import dev.rezyfr.trackerr.data.remote.dto.response.TransactionFrequencyResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionReportResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionSummaryResponse
import dev.rezyfr.trackerr.data.remote.dto.response.TransactionWithDateResponse
import dev.rezyfr.trackerr.domain.model.CategoryModel
import dev.rezyfr.trackerr.domain.model.CategoryType
import dev.rezyfr.trackerr.domain.model.Granularity
import dev.rezyfr.trackerr.domain.model.transaction.TransactionFrequencyModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionReportModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionSummaryModel
import dev.rezyfr.trackerr.domain.model.transaction.TransactionWithDateModel
import dev.rezyfr.trackerr.utils.orZero

class TransactionMapper {
    fun mapResponseToDomain(response: TransactionResponse): TransactionModel {
        return TransactionModel(
            id = response.id,
            amount = response.amount.toLong(),
            date = response.createdDate,
            desc = response.description,
            type = CategoryType.valueOf(response.category!!.type),
            categoryIcon = response.category.icon,
            categoryName = response.categoryName,
            categoryColor = response.category.color,
        )
    }

    fun mapSummaryResponseToDomain(response: TransactionSummaryResponse): TransactionSummaryModel {
        return TransactionSummaryModel(
            totalIncome = response.totalIncome,
            totalExpense = response.totalExpense,
        )
    }

    fun mapFrequencyResponseToDomain(
        response: List<TransactionFrequencyResponse>,
        params: Granularity
    ): TransactionFrequencyModel {
        val freqList = mutableListOf<Triple<String, Double, Double>>()
        val intRange = when (params) {
            Granularity.WEEK -> 1..7
            Granularity.MONTH -> 1..30
            Granularity.YEAR -> 1..12
        }

        intRange.forEach { index ->
            response.find { it.index == index }?.let {
                freqList.add(
                    Triple(
                        it.index.toString(),
                        it.expenseAmount.toDouble(),
                        it.incomeAmount.toDouble()
                    )
                )
            } ?: run {
                freqList.add(Triple(index.toString(), 0.0, 0.0))
            }
        }

        return TransactionFrequencyModel(
            xAxisData = freqList.map { it.first },
            expenseData = freqList.map { it.second },
            incomeData = freqList.map { it.third },
        )
    }

    fun mapTrxWithDateResponseToDomain(response: TransactionWithDateResponse): TransactionWithDateModel {
        return TransactionWithDateModel(
            response.date,
            response.transactions.map { mapResponseToDomain(it) }
        )
    }

    suspend fun mapReportResponseToDomain(response: TransactionReportResponse, mapper: suspend (Int?) -> CategoryModel?): TransactionReportModel {
        return TransactionReportModel(
            TransactionReportModel.ReportItem(
                response.expense.categoryAmount,
                mapper(response.expense.categoryId),
                response.expense.totalAmount,
            ),
            TransactionReportModel.ReportItem(
                response.income.categoryAmount,
                mapper(response.income.categoryId),
                response.income.totalAmount,
            ),
            response.totalAmount.orZero(),
        )
    }
}