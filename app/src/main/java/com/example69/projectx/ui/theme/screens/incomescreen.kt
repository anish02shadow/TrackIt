package com.example69.projectx.ui.theme.screens

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.ButtonDefaults.buttonColors
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.SnackbarDefaults.backgroundColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusRequester.Companion.createRefs
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example69.projectx.R
import com.example69.projectx.data.TransactionUiState
import com.example69.projectx.ui.theme.AppViewModelProvider
import com.vanpra.composematerialdialogs.MaterialDialog
import com.vanpra.composematerialdialogs.datetime.date.datepicker
import com.vanpra.composematerialdialogs.datetime.time.timepicker
import com.vanpra.composematerialdialogs.rememberMaterialDialogState
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.*



@Composable
fun screenexpense(viewModel: TransactionEntryViewModel= androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),navigateBack: () -> Unit){
    expensescreen(transactionUiState = viewModel.transactionUiState,navigateBack=navigateBack)
}
@Composable
fun expensescreen(transactionUiState: TransactionUiState,
                  viewModel: TransactionEntryViewModel= androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                  navigateBack: () -> Unit) {
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

                    EditableTextFieldWithPlaceholderAdd(
                        placeholder = textValue,
                        value = transactionUiState.amount,
                        transactionUiState = transactionUiState,
                        onValueChange=viewModel::updateUiState
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
                                horizontalArrangement = Arrangement.Center,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                OutlinedButton(onClick = { coroutineScope.launch {
                                    viewModel.saveTransactionExpense()
                                    navigateBack()
                                }},
                                    modifier = Modifier
                                        .fillMaxWidth(0.9f)
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
                            }
                        }

                    }
                }
                }





@Composable
fun screenincome(viewModel: TransactionEntryViewModel= androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),navigateBack: () -> Unit){
    incomescreen(transactionUiState = viewModel.transactionUiState,navigateBack=navigateBack)
}
@Composable
fun incomescreen(transactionUiState: TransactionUiState,viewModel: TransactionEntryViewModel= androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
                 navigateBack: () -> Unit){

    val coroutineScope = rememberCoroutineScope()
    Column(
        modifier = Modifier.background(Color(0xFF00A86B))
    ) {

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

        EditableTextFieldWithPlaceholderAdd(
            placeholder = textValue,
            value = transactionUiState.amount,
            transactionUiState = transactionUiState,
            onValueChange=viewModel::updateUiState
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
                var selectedCategory by remember { mutableStateOf("Category") }
                Column {
                    CategoryDropdownIncome(transactionUiState = transactionUiState,onValueChange=viewModel::updateUiState)
                }
                Spacer(modifier = Modifier.height(10.dp))
                var description by remember { mutableStateOf("") }
                Column {
                    DescriptionTextField(transactionUiState = transactionUiState,onValueChange=viewModel::updateUiState)
                }
                Spacer(modifier = Modifier.height(10.dp))
                datetimepicker(onValueChange=viewModel::updateUiState, transactionUiState = transactionUiState)
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    OutlinedButton(onClick = { coroutineScope.launch {
                        viewModel.saveTransactionIncome()
                        navigateBack()
                    }},
                        modifier = Modifier
                            .fillMaxWidth(0.9f)
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
                }
            }

        }
    }
        /*Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(25.dp)
                .fillMaxWidth()
        ) {
            IconButton(onClick = { *//*TODO*//* },
            ) {
                Icon(painter = painterResource(id = R.drawable.img_8), contentDescription = "Back", tint = Color(0xFFFCFCFC))

            }

            Text(text = "Expense", fontSize = 18.sp,color=Color(0xFFFCFCFC),
                modifier = Modifier.clickable(
                    onClick = {
                        navController.navigate("ExpenseScreen")
                    }))
            Text(text = "....", fontSize = 18.sp, modifier = Modifier, color= Color(0xFFFD3C4A))*/

        }


val categoryOptions = listOf("Shopping", "Food", "Entertainment", "Others")
@Composable
fun CategoryDropdown(transactionUiState: TransactionUiState,onValueChange: (TransactionUiState) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 1.5.dp,
                    color = Color(0xFFF1F1FA),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
        ) {
            Text(
                text = transactionUiState.category,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                color=Color(0xFF91919F)
            )
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand category dropdown",
                    tint=Color(0xFF91919F)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f).background(Color(0xFFFCFCFCFC))
            ) {
                categoryOptions.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(transactionUiState.copy(category=category))
                            //onCategorySelected(category)
                            expanded = false
                        }
                    ) {
                        Text(text = category, color = Color.Black)
                    }
                }
            }
        }
    }
}
@Composable
fun CategoryDropdownIncome(transactionUiState: TransactionUiState,onValueChange: (TransactionUiState) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Column {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(
                    width = 1.5.dp,
                    color = Color(0xFFF1F1FA),
                    shape = RoundedCornerShape(10.dp)
                )
                .clip(RoundedCornerShape(10.dp))
        ) {
            Text(
                text = transactionUiState.category,
                modifier = Modifier
                    .padding(12.dp)
                    .fillMaxWidth(),
                color=Color(0xFF91919F)
            )
            IconButton(
                onClick = { expanded = true },
                modifier = Modifier.align(Alignment.CenterEnd)
            ) {
                Icon(
                    imageVector = Icons.Default.ArrowDropDown,
                    contentDescription = "Expand category dropdown",
                    tint=Color(0xFF91919F)
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false },
                modifier = Modifier.fillMaxWidth(0.9f).background(Color(0xFFFCFCFCFC))
            ) {
                categoryOptionsIncome.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            onValueChange(transactionUiState.copy(category=category))
                            //onCategorySelected(category)
                            expanded = false
                        }
                    ) {
                        Text(text = category,color=Color.Black)
                    }
                }
            }
        }
    }
}

val categoryOptionsIncome = listOf("Salary","Gifts","Others")

@Composable
fun DescriptionTextField(transactionUiState: TransactionUiState,
                         onValueChange: (TransactionUiState) -> Unit = {}) {
    OutlinedTextField(
        textStyle = TextStyle(color = Color.Black),
        value = transactionUiState.description,
        onValueChange = {onValueChange(transactionUiState.copy(description=it))},
        placeholder = { Text(text = "Description", style = TextStyle(color = Color(0xFF91919F)))},
        modifier = Modifier
            .fillMaxWidth()
            .height(80.dp)
            .padding(12.dp)
            .border(
                width = 1.5.dp,
                color = Color(0xFFF1F1FA),
                shape = RoundedCornerShape(10.dp)
            ),
        maxLines = 5,
        singleLine = false
    )
}

@Composable
fun datetimepicker(transactionUiState: TransactionUiState,onValueChange: (TransactionUiState) -> Unit){
    var pickedDate by remember {
        mutableStateOf(transactionUiState.date)
    }
    var pickedTime by remember {
        mutableStateOf(transactionUiState.time)
    }
    val formattedDate by remember {
        derivedStateOf {
            /*DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(LocalDate.parse(viewModel.transactionUiState.date))
        }*/
            val date = transactionUiState.date
           /* if (date.isNotEmpty()) {
                //LocalDate.parse(date)
                    .format(DateTimeFormatter.ofPattern("MMM dd yyyy"))
            } else {
                "Pick a date"
            }*/
            DateTimeFormatter
                .ofPattern("MMM dd yyyy")
                .format(pickedDate)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("hh:mm")
                .format(pickedTime)
        }
    }
    val dateDialogState = rememberMaterialDialogState()
    val timeDialogState = rememberMaterialDialogState()
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Button(colors = buttonColors(Color(0xFFFFF7F2)), modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp)
            ),

            onClick = {
            dateDialogState.show()
        }) {
            Text(text = formattedDate, style = TextStyle(color = Color.Black))
        }
            Spacer(modifier = Modifier.width(10.dp))
        Button(colors = buttonColors(Color(0xFFFFF7F2)),modifier = Modifier
            .weight(1f)
            .clip(RoundedCornerShape(10.dp)),onClick = {
            timeDialogState.show()
        }) {
            Text(text = formattedTime,style = TextStyle(color = Color.Black))
        }
    }
    MaterialDialog(
        dialogState = dateDialogState,
        buttons = {
            positiveButton(text = "Ok") {
            }
            negativeButton(text = "Cancel")
        }
    ) {
        var selectedDate by rememberSaveable { mutableStateOf(LocalDate.now()) }
        datepicker(
            initialDate = transactionUiState.date,
            //LocalDate.now(),
            title = "Pick a date",
        ){
            pickedDate=it
            onValueChange(transactionUiState.copy(date=pickedDate))
            //viewModel.transactionUiState.copy(date=pickedDate.toString())
        }
    }
    MaterialDialog(
        dialogState = timeDialogState,
        buttons = {
            positiveButton(text = "Ok") {
            }
            negativeButton(text = "Cancel")
        }
    ) {
        var selectedTime by rememberSaveable { mutableStateOf(LocalTime.now()) }
        timepicker(
            initialTime = transactionUiState.time,
            // LocalTime.now(),
            title = "Pick a time",
        ){
            pickedTime=it
            onValueChange(transactionUiState.copy(time=pickedTime))
            //viewModel.transactionUiState.copy(time=pickedTime.toString())
        }
    }
}
@Composable
fun EditableTextFieldWithPlaceholder(
    value: String,
    transactionUiState: TransactionUiState,
    onValueChange: (TransactionUiState) -> Unit
) {
    //var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }

    TextField(
        value = value,
        onValueChange = { onValueChange(transactionUiState.copy(amount = it)) },
        textStyle = TextStyle(fontSize = 64.sp, color = Color(0xFFFCFCFC)),
        //placeholder = { Text(text = placeholder, style = TextStyle(color = Color(0xFFFCFCFC)), fontSize = 64.sp) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color(0xFFFCFCFC),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = Color(0xFFFCFCFC)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp, start = 15.dp),
        maxLines = 1,
    )
}
@Composable
fun EditableTextFieldWithPlaceholderAdd(
    value: String,
    transactionUiState: TransactionUiState,
    onValueChange: (TransactionUiState) -> Unit,
    placeholder:String
) {
    //var textFieldValue by remember { mutableStateOf(TextFieldValue(value)) }

    TextField(
        value = value,
        onValueChange = { onValueChange(transactionUiState.copy(amount = it)) },
        textStyle = TextStyle(fontSize = 64.sp, color = Color(0xFFFCFCFC)),
        placeholder = { Text(text = placeholder, style = TextStyle(color = Color(0xFFFCFCFC)), fontSize = 64.sp) },
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = Color.Transparent,
            cursorColor = Color(0xFFFCFCFC),
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            textColor = Color(0xFFFCFCFC)
        ),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 5.dp, start = 15.dp),
        maxLines = 1,
    )
}

