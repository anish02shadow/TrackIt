package com.example69.projectx.ui.theme


import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example69.projectx.TransactionApplicatiom
import com.example69.projectx.ui.theme.screens.EditTransactionViewModel
import com.example69.projectx.ui.theme.screens.TransactionsScreenViewModel
import com.example69.projectx.ui.theme.screens.TransactionEntryViewModel

object AppViewModelProvider{
        val Factory= viewModelFactory {
        initializer {
            TransactionEntryViewModel(
                transacrionApplicatiom().container.transactionRepository
            )
        }
            initializer {
                TransactionsScreenViewModel(
                    transacrionApplicatiom().container.transactionRepository
                )
            }
            initializer {
                EditTransactionViewModel(
                    this.createSavedStateHandle(),
                    transacrionApplicatiom().container.transactionRepository
                )
            }
    }
}

fun CreationExtras.transacrionApplicatiom():TransactionApplicatiom=
    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as TransactionApplicatiom)