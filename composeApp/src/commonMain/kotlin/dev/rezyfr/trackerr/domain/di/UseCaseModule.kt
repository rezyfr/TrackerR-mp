package dev.rezyfr.trackerr.domain.di

import dev.rezyfr.trackerr.domain.usecase.category.CreateCategoryUseCase
import dev.rezyfr.trackerr.domain.usecase.category.GetCategoriesUseCase
import dev.rezyfr.trackerr.domain.usecase.category.SyncCategoryUseCase
import dev.rezyfr.trackerr.domain.usecase.user.CheckTokenUseCase
import dev.rezyfr.trackerr.domain.usecase.wallet.CreateWalletUseCase
import dev.rezyfr.trackerr.domain.usecase.icon.GetIconUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.CreateTransactionUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.GetRecentTransactionUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.GetTransactionFrequencyUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.GetTransactionReportUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.GetTransactionSummaryUseCase
import dev.rezyfr.trackerr.domain.usecase.transaction.GetTransactionWithDateUseCase
import dev.rezyfr.trackerr.domain.usecase.user.LoginUseCase
import dev.rezyfr.trackerr.domain.usecase.user.RegisterUseCase
import dev.rezyfr.trackerr.domain.usecase.wallet.GetWalletBalanceUseCase
import dev.rezyfr.trackerr.domain.usecase.wallet.GetWalletsUseCase
import org.koin.dsl.module

fun getUseCaseModule() = module {
    // Auth
    single { LoginUseCase(get()) }
    single { RegisterUseCase(get()) }
    single { CheckTokenUseCase(get()) }

    // Icon
    single { GetIconUseCase(get()) }

    // Wallet
    single { CreateWalletUseCase(get()) }
    single { GetWalletBalanceUseCase(get()) }
    single { GetWalletsUseCase(get(), get()) }

    // Transaction
    single { GetRecentTransactionUseCase(get(), get()) }
    single { GetTransactionSummaryUseCase(get(), get()) }
    single { CreateTransactionUseCase(get()) }
    single { GetTransactionFrequencyUseCase(get(), get())}
    single { GetTransactionWithDateUseCase(get(), get()) }
    single { GetTransactionReportUseCase(get(), get(), get(), get()) }

    // Category
    single { SyncCategoryUseCase(get(), get()) }
    single { GetCategoriesUseCase(get(), get()) }
    single { CreateCategoryUseCase(get(), get()) }
}