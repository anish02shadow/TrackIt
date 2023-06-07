package com.example69.projectx.ui.theme.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.example69.projectx.data.TransactionRepository
import com.example69.projectx.data.TransactionUiState
import com.example69.projectx.data.isValid
import com.example69.projectx.data.toTransaction
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class TransactionEntryViewModel(private val transactionRepository: TransactionRepository): ViewModel() {

    var transactionUiState by mutableStateOf(TransactionUiState())
    private set

    fun updateUiState(newTransactionUiState: TransactionUiState){
        transactionUiState= newTransactionUiState.copy(actionEnabled = newTransactionUiState.isValid())
    }
    suspend fun saveTransactionExpense(){
        if(transactionUiState.isValid()){
            transactionUiState=transactionUiState.copy(type = "Expense")
            transactionRepository.addTransaction(transactionUiState.toTransaction())
        }
    }
    suspend fun saveTransactionIncome(){
        if(transactionUiState.isValid()){
            transactionUiState=transactionUiState.copy(type = "Income")
            transactionRepository.addTransaction(transactionUiState.toTransaction())
        }
    }
    fun updateDate(date: LocalDate){
        /*val date = DateTimeFormatter
            .ofPattern("MMM dd yyyy")
            .format(date)*/
        transactionUiState=transactionUiState.copy(date=date)
    }

    fun updateTime(time: LocalTime){
        /*val time = DateTimeFormatter
            .ofPattern("hh:mm")
            .format(time)*/
        transactionUiState = transactionUiState.copy(time = time)
    }
    fun updateCategory(category: String) {
        transactionUiState = transactionUiState.copy(category = category)
    }
    fun updateAmount(amount: Double) {
        transactionUiState = transactionUiState.copy(amount = amount.toString())
    }


}