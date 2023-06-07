package com.example69.projectx.data

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example69.projectx.NavigationDestination
import com.example69.projectx.ui.theme.AppViewModelProvider
import com.example69.projectx.ui.theme.screens.*
import kotlinx.coroutines.launch

object TransactionEditDestination : NavigationDestination {
    override val route = "transaction_edit"
    override val titleRes = "Edit Transaction"
    const val transactionIdArg = "transactionId"
    val routeWithArgs = "$route/{$transactionIdArg}"
}
object IncomeTransactionEditDestination : NavigationDestination {
    override val route = "transaction_editincome"
    override val titleRes = "Edit Transaction_Income"
    const val transactionIdArg = "transactionId"
    val routeWithArgs = "$route/{$transactionIdArg}"
}

@Composable
fun editscreenexpense(viewModel: EditTransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      navigateBack: () -> Unit){
    editexpensescreen(navigateBack = navigateBack, transactionUiState = viewModel.transactionUiState)
}

@Composable
fun editexpensescreen(viewModel: EditTransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                      navigateBack: () -> Unit, transactionUiState: TransactionUiState){
    Column(
        modifier = Modifier.background(Color(0xFFFD3C4A))
    ) {
        val coroutineScope = rememberCoroutineScope()
        var textFieldstate by remember {
            mutableStateOf("")
        }

        Spacer(modifier = Modifier.height(125.dp))

        Text(
            text = "How Much?",
            fontSize = 18.sp,
            color = Color(0xA3FCFCFC),
            modifier = Modifier.padding(start = 32.dp)
        )
        var textValue by remember { mutableStateOf("0") }

        EditableTextFieldWithPlaceholder(
            value = transactionUiState.amount,
            transactionUiState=transactionUiState,
            onValueChange = viewModel::updateUiState
        )

        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp
                    )
                )
                .fillMaxSize()
                .background(Color(0xFFFFF7F2))
        ) {
            Column(modifier = Modifier.padding(top = 20.dp)) {
                //var selectedCategory by remember { mutableStateOf("Category") }
                Column {
                    CategoryDropdown(transactionUiState=transactionUiState,onValueChange=viewModel::updateUiState)
                }
                Spacer(modifier = Modifier.height(10.dp))
                //var description by remember { mutableStateOf("") }
                Column {
                    DescriptionTextField(transactionUiState=transactionUiState,onValueChange=viewModel::updateUiState)
                }
                Spacer(modifier = Modifier.height(10.dp))
                datetimepicker(onValueChange=viewModel::updateUiState, transactionUiState = transactionUiState)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = {coroutineScope.launch {
                            viewModel.updateTransaction()
                            navigateBack()
                        }},
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        //enabled=viewModel.transactionUiState.actionEnabled,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF5F1FDB),
                            contentColor = Color(0xFFFFF7F2)
                        ),
                        border = BorderStroke(2.dp, Color(0xFF5F1FDB)),
                        shape = RoundedCornerShape(15.dp)

                    ) {
                        Text(text = "Save", color = Color.White)
                    }
                    OutlinedButton(
                        onClick = {coroutineScope.launch {
                            viewModel.deleteTransaction()
                            navigateBack()
                        }},
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFFF7F2),
                            contentColor = Color(0xFF5F1FDB)
                        ),
                        border = BorderStroke(2.dp, Color(0xFF5F1FDB)),
                        shape = RoundedCornerShape(15.dp)
                        //enabled=viewModel.transactionUiState.actionEnabled,
                    ) {
                        Text(text = "Delete")
                    }
                }
            }

        }
    }

}

@Composable
fun editscreenincome(viewModel: EditTransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                     navigateBack: () -> Unit){
    editincomescreen(navigateBack = navigateBack, transactionUiState = viewModel.transactionUiState)
}

@Composable
fun editincomescreen(viewModel: EditTransactionViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                     navigateBack: () -> Unit, transactionUiState: TransactionUiState){
    Column(
        modifier = Modifier.background(Color(0xFF00A86B))
    ) {
        val coroutineScope = rememberCoroutineScope()
        var textFieldstate by remember {
            mutableStateOf("")
        }

        Spacer(modifier = Modifier.height(125.dp))

        Text(
            text = "How Much?",
            fontSize = 18.sp,
            color = Color(0xA3FCFCFC),
            modifier = Modifier.padding(start = 32.dp)
        )
        var textValue by remember { mutableStateOf("0") }

        EditableTextFieldWithPlaceholder(
            value = transactionUiState.amount,
            transactionUiState=transactionUiState,
            onValueChange = viewModel::updateUiState
        )

        Box(
            modifier = Modifier
                .padding(top = 10.dp)
                .clip(
                    RoundedCornerShape(
                        topStart = 32.dp,
                        topEnd = 32.dp
                    )
                )
                .fillMaxSize()
                .background(Color(0xFFFFF7F2))
        ) {
            Column(modifier = Modifier.padding(top = 20.dp)) {
                //var selectedCategory by remember { mutableStateOf("Category") }
                Column {
                    CategoryDropdownIncome(transactionUiState=transactionUiState,onValueChange=viewModel::updateUiState)
                }
                Spacer(modifier = Modifier.height(10.dp))
                //var description by remember { mutableStateOf("") }
                Column {
                    DescriptionTextField(transactionUiState=transactionUiState,onValueChange=viewModel::updateUiState)
                }
                Spacer(modifier = Modifier.height(10.dp))
                datetimepicker(onValueChange=viewModel::updateUiState, transactionUiState = transactionUiState)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(
                        onClick = {coroutineScope.launch {
                            viewModel.updateTransaction()
                            navigateBack()
                        }},
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        //enabled=viewModel.transactionUiState.actionEnabled,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFF5F1FDB),
                            contentColor = Color(0xFFFFF7F2)
                        ),
                        border = BorderStroke(2.dp, Color(0xFF5F1FDB)),
                        shape = RoundedCornerShape(15.dp)

                    ) {
                        Text(text = "Save", color = Color.White)
                    }
                    OutlinedButton(
                        onClick = {coroutineScope.launch {
                            viewModel.deleteTransaction()
                            navigateBack()
                        }},
                        modifier = Modifier
                            .weight(1f)
                            .padding(10.dp)
                            .clip(RoundedCornerShape(15.dp)),
                        //enabled=viewModel.transactionUiState.actionEnabled,
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color(0xFFFFF7F2),
                            contentColor = Color(0xFF5F1FDB)
                        ),
                        border = BorderStroke(2.dp, Color(0xFF5F1FDB)),
                        shape = RoundedCornerShape(15.dp)
                    ) {
                        Text(text = "Delete")
                    }
                }
            }

        }
    }

}