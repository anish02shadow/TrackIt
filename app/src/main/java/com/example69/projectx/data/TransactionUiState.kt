package com.example69.projectx.data

import android.accounts.AuthenticatorDescription
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter


data class TransactionUiState(
    val id: Int=0,
    val amount: String="",
    val category: String="",
    val type: String="",
    val date: LocalDate=LocalDate.now(),
    val time: LocalTime=LocalTime.now(),
    val description: String="",
    val actionEnabled: Boolean = false
)
val dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
fun TransactionUiState.toTransaction():Transaction= Transaction(
    id=id,
    amount=amount.toDoubleOrNull()?:0.0,
    category=category,
    type=type,
    date= date,
    time= time,
    description=description
)

fun Transaction.toTransactionUiState(actionEnabled: Boolean=false): TransactionUiState= TransactionUiState(
    id = id,
    amount = amount.toString(),
    category=category,
    type=type,
    date=date,
    time=time,
    description=description,
    actionEnabled=actionEnabled
)

fun TransactionUiState.isValid(): Boolean {
    return category.isNotBlank()
}