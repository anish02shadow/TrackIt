package com.example69.projectx.ui.theme.screens

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example69.projectx.data.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class EditTransactionViewModel(
    savedStateHandle: SavedStateHandle,
    private val transactionRepository: TransactionRepository
) : ViewModel() {

    var transactionUiState by mutableStateOf(TransactionUiState())
        private set

    private val transactionId: Int = checkNotNull(savedStateHandle[TransactionEditDestination.transactionIdArg])

    init {
        viewModelScope.launch {
            transactionUiState = transactionRepository.getTransactionStream(transactionId)
                .filterNotNull()
                .first()
                .toTransactionUiState(actionEnabled = true)
        }
    }

    fun updateUiState(newItemUiState: TransactionUiState){
        transactionUiState=newItemUiState.copy(actionEnabled = newItemUiState.isValid())
    }
    suspend fun updateTransaction(){
        if(transactionUiState.isValid()){
            transactionRepository.updateTransaction(transactionUiState.toTransaction())
        }
    }
    suspend fun deleteTransaction() {
        transactionRepository.deleteTransaction(transactionUiState.toTransaction())
    }
}


/*class EditTransactionViewModel(savedStateHandle: SavedStateHandle,
private val transactionRepository: TransactionRepository): ViewModel() {
    companion object {
        private const val TIMEOUT_MILLIS = 5_000L
    }

    private val transactionId: Int =
        checkNotNull(savedStateHandle[TransactionEditDestination.transactionIdArg])

    val uiState: StateFlow<TransactionUiState> =
        transactionRepository.getTransactionStream(transactionId)
            .filterNotNull()
            .map {
                it.toTransactionUiState(actionEnabled = true)
            }.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(TIMEOUT_MILLIS),
                initialValue = TransactionUiState()
            )*/
    /*   var transactionUiState by mutableStateOf(TransactionUiState())
        private set

    private val transactionId: Int = checkNotNull(savedStateHandle[TransactionEditDestination.transactionIdArg])

    init {
        viewModelScope.launch {
            transactionUiState = transactionRepository.getTransactionStream(transactionId)
                .filterNotNull()
                .first()
                .toTransactionUiState(actionEnabled = true)
        }
    }

    fun updateUiState(newItemUiState: TransactionUiState){
        transactionUiState=newItemUiState.copy(actionEnabled = newItemUiState.isValid())
    }
    suspend fun updateTransaction(){
        if(transactionUiState.isValid()){
            transactionRepository.updateTransaction(transactionUiState.toTransaction())
        }
    }*/

    /*    fun updateDate(date: LocalDate){
        *//*val date = DateTimeFormatter
            .ofPattern("MMM dd yyyy")
            .format(date)*//*
        transactionUiState=transactionUiState.copy(date=date)
    }

    fun updateTime(time: LocalTime){
        *//*val time = DateTimeFormatter
            .ofPattern("hh:mm")
            .format(time)*//*
        transactionUiState = transactionUiState.copy(time = time)
    }
    fun updateCategory(category: String) {
        transactionUiState = transactionUiState.copy(category = category)
    }
    fun updateAmount(amount: Double) {
        transactionUiState = transactionUiState.copy(amount = amount.toString())
    }*/
