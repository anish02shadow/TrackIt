package com.example69.projectx.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example69.projectx.R
import com.example69.projectx.data.IncomeTransactionEditDestination
import com.example69.projectx.data.Transaction
import com.example69.projectx.data.TransactionEditDestination
import com.example69.projectx.ui.theme.AppViewModelProvider
import java.time.format.DateTimeFormatter


@Composable
fun transactionsscreen(
    viewModel: TransactionsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
    navController: NavController
){
    val transactionScreenUiState by viewModel.transactionsScreenUiState.collectAsState()
    val transactionsScreenUiStateToday by viewModel.transactionsScreenUiStateToday.collectAsState()
    val transactionsScreenUiStateWeek by viewModel.transactionsScreenUiStateWeek.collectAsState()
    val transactionsScreenUiStateMonth by viewModel.transactionsScreenUiStateMonth.collectAsState()
    val transactionsScreenUiStateYear by viewModel.transactionsScreenUiStateYear.collectAsState()
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Color(0xFFFFF7F2)
        )){
        Column() {
            var selectedCategory by remember { mutableStateOf("All") }
            Column {
                timedropdown(selectedCategory = selectedCategory) { category ->
                    selectedCategory = category
                }
            }
            
            Spacer(modifier = Modifier.height(1.dp))
            
            Text(text = selectedCategory, color = Color(0xFF0D0E0F), fontSize = 18.sp, fontWeight =FontWeight.SemiBold,
            modifier = Modifier.padding(20.dp))
            if (transactionScreenUiState.transactionList.isEmpty()) {
                Text(
                    text = "No Items",
                    style = MaterialTheme.typography.subtitle2,
                    modifier = Modifier.padding(15.dp)
                )
            } else {
                Cardlist(
                        transactionList =
                        if(selectedCategory.equals("Today")){
                            transactionsScreenUiStateToday.transactionList
                        }
                        else if(selectedCategory.equals("Week")){
                            transactionsScreenUiStateWeek.transactionList
                        }
                        else if(selectedCategory.equals("Month")){
                            transactionsScreenUiStateMonth.transactionListt
                        }
                        else if(selectedCategory.equals("Year")){
                            transactionsScreenUiStateYear.transactionList
                        }
                        else{
                            transactionScreenUiState.transactionList
                        },
                        navController = navController)
            }
            }
        }

    }

val timeitems= listOf("Today","Week","Month","Year","All")
@Composable
fun timedropdown(selectedCategory: String, onCategorySelected: (String) -> Unit){
    var expanded by remember { mutableStateOf(false) }
       Column() {
           Box(
               modifier = Modifier
                   .padding(10.dp)
                   .fillMaxWidth(0.35f)
                   .border(
                       width = 2.5.dp,
                       color = Color(0xFFF1F1FA),
                       shape = RoundedCornerShape(25.dp)
                   )
                   .clip(RoundedCornerShape(25.dp)),
               contentAlignment = Alignment.Center
           ) {
               IconButton(
                   onClick = { expanded = true },
                   modifier = Modifier.align(Alignment.CenterStart)
               ){
                   Icon(painter = painterResource(id = com.example69.projectx.R.drawable.img_9), contentDescription ="DropDown" ,
                       tint = Color(0xFF7F3DFF))
               }

               Text(text = selectedCategory, color = Color(0xFF212325))

               DropdownMenu(
                   expanded = expanded,
                   onDismissRequest = { expanded = false},
                   modifier=Modifier.background(Color(0xFFFCFCFCFC))
               ) {
                   timeitems.forEach { category ->
                       DropdownMenuItem(
                           onClick = {
                               onCategorySelected(category)
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
@Composable
fun Cardlist(
    transactionList: List<Transaction>,navController: NavController
){
    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)){
        items(items = transactionList, key = { it.id }) { item ->
            TransactionCardd(transaction=item,navController = navController)
        }
    }
}

@Composable
fun TransactionCard(
    transaction: Transaction
) {
    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFFFF6F0)),
    )
    {
        //if (transaction.category.equals("Shopping") && transaction.type.equals("Expense")) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .size(75.dp)
                            .background(Color(0xFFFCEED4))
                            .padding(5.dp)
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.img_5),
                            contentDescription = "CategoryIcon",
                            tint = Color(0xFFFCAC12),
                            modifier = Modifier.size(55.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = transaction.category,
                                fontSize = 18.sp,
                                color = Color(0xFF292B2D)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = transaction.description,
                                fontSize = 15.sp,
                                color = Color(0xFF91919F),
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.fillMaxWidth(0.5f)
                            )
                        }

                        Text(
                            text = transaction.amount.toString(),
                            fontSize = 18.sp,
                            color = Color(0xFFFD3C4A)
                        )
                    }
                }
            }


       /* if (transaction.category.equals("Food") && transaction.type.equals("Expense")) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {

                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .size(75.dp)
                            .background(Color(0xFFFDD5D7))
                            .padding(5.dp)
                    ) {

                        Icon(
                            painter = painterResource(id = R.drawable.img_11),
                            contentDescription = "CategoryIcon",
                            tint = Color(0xFFFD3C4A),
                            modifier = Modifier.size(55.dp)
                        )
                        Spacer(modifier = Modifier.width(5.dp))
                        Column(modifier = Modifier.padding(10.dp)) {
                            Text(
                                text = transaction.category,
                                fontSize = 18.sp,
                                color = Color(0xFF292B2D)
                            )
                            Spacer(modifier = Modifier.height(10.dp))
                            Text(
                                text = transaction.description,
                                fontSize = 15.sp,
                                color = Color(0xFF91919F)
                            )
                        }

                        Text(
                            text = transaction.amount.toString(),
                            fontSize = 18.sp,
                            color = Color(0xFFFD3C4A)
                        )
                    }
                }
            }
        }
            if (transaction.category.equals("Others") && transaction.type.equals("Expense")) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 10.dp, end = 10.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {

                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            contentAlignment = Alignment.Center, modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .size(75.dp)
                                .background(Color(0xFFEEE5FF))
                                .padding(5.dp)
                        ) {

                            Icon(
                                painter = painterResource(id = R.drawable.img_12),
                                contentDescription = "CategoryIcon",
                                tint = Color(0xFF7F3DFF),
                                modifier = Modifier.size(55.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Column(modifier = Modifier.padding(10.dp)) {
                                Text(
                                    text = transaction.category,
                                    fontSize = 18.sp,
                                    color = Color(0xFF292B2D)
                                )
                                Spacer(modifier = Modifier.height(10.dp))
                                Text(
                                    text = transaction.description,
                                    fontSize = 15.sp,
                                    color = Color(0xFF91919F)
                                )
                            }

                            Text(
                                text = transaction.amount.toString(),
                                fontSize = 18.sp,
                                color = Color(0xFFFD3C4A)
                            )
                        }
                    }
                }
            }
                if (transaction.category.equals("Entertainment") && transaction.type.equals("Expense")) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 10.dp, end = 10.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {

                        Row(
                            horizontalArrangement = Arrangement.Start,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                contentAlignment = Alignment.Center, modifier = Modifier
                                    .clip(RoundedCornerShape(15.dp))
                                    .size(75.dp)
                                    .background(Color(0xFFB6E7E7))
                                    .padding(5.dp)
                            ) {

                                Icon(
                                    painter = painterResource(id = R.drawable.video),
                                    contentDescription = "CategoryIcon",
                                    tint = Color(0xFF3DE8FF),
                                    modifier = Modifier.size(55.dp)
                                )
                                Spacer(modifier = Modifier.width(5.dp))
                                Column(modifier = Modifier.padding(10.dp)) {
                                    Text(
                                        text = transaction.category,
                                        fontSize = 18.sp,
                                        color = Color(0xFF292B2D)
                                    )
                                    Spacer(modifier = Modifier.height(10.dp))
                                    Text(
                                        text = transaction.description,
                                        fontSize = 15.sp,
                                        color = Color(0xFF91919F)
                                    )
                                }

                                Text(
                                    text = transaction.amount.toString(),
                                    fontSize = 18.sp,
                                    color = Color(0xFFFD3C4A)
                                )
                            }
                        }
                    }

*/
                }
            }
@Composable
fun TransactionCardd(
    transaction: Transaction,
    navController: NavController
) {
    val formattedDate by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("MMM dd, yyyy")
                .format(transaction.date)
        }
    }
    val formattedTime by remember {
        derivedStateOf {
            DateTimeFormatter
                .ofPattern("HH:mm")
                .format(transaction.time)
        }
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFFFFF6F0))
            .clickable {
                if (transaction.type.equals("Expense")) {
                    navController.navigate("${TransactionEditDestination.route}/${transaction.id}")
                } else {
                    navController.navigate("${IncomeTransactionEditDestination.route}/${transaction.id}")
                }
            }
    )
    {
        if (transaction.type.equals("Expense")) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .size(75.dp)
                            .background(
                                if (transaction.category.equals("Shopping")) {
                                    Color(0xFFFCEED4)
                                } else if (transaction.category.equals("Food")) {
                                    Color(0xFFFDD5D7)
                                } else if (transaction.category.equals("Entertainment")) {
                                    Color(0xFFB6E7E7)
                                } else {
                                    Color(0xFFEEE5FF)
                                }
                            )
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id =
                                if (transaction.category.equals("Shopping")) {
                                    R.drawable.shopping
                                } else if (transaction.category.equals("Food")) {
                                    R.drawable.img_11
                                } else if (transaction.category.equals("Entertainment")) {
                                    R.drawable.entertain
                                } else {
                                    R.drawable.otherss
                                }
                            ), contentDescription = "Grocery",
                            modifier = Modifier.size(40.dp),
                            tint =
                            if (transaction.category.equals("Shopping")) {
                                Color(0xFFFCAC12)
                            } else if (transaction.category.equals("Food")) {
                                Color(0xFFFD3C4A)
                            } else if (transaction.category.equals("Entertainment")) {
                                Color(0xFF11808F)
                            } else {
                                Color(0xFF7F3DFF)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = transaction.category,
                            fontSize = 18.sp,
                            color = Color(0xFF292B2D)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = transaction.description,
                            fontSize = 15.sp,
                            color = Color(0xFF91919F),
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            modifier=Modifier.fillMaxWidth(0.75f)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = transaction.amount.toString(), fontSize = 18.sp,
                        color =
                            Color(0xFFFD3C4A)

                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Row() {
                        Text(text = formattedTime, fontSize = 13.sp, color = Color(0xFF91919F))
                    }
                }
            }
        } else {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        contentAlignment = Alignment.Center, modifier = Modifier
                            .clip(RoundedCornerShape(15.dp))
                            .size(75.dp)
                            .background(
                                if (transaction.category.equals("Salary")) {
                                    Color(0xFFCFFAEA)
                                } else if (transaction.category.equals("Gifts")) {
                                    Color(0xFFBDDCFF)
                                } else {
                                    Color(0xFFEEE5FF)
                                }
                            )
                            .padding(5.dp)
                    ) {
                        Icon(
                            painter = painterResource(
                                id =
                                if (transaction.category.equals("Salary")) {
                                    R.drawable.salary
                                } else if (transaction.category.equals("Gifts")) {
                                    R.drawable.gifts
                                } else {
                                    R.drawable.otherss
                                }
                            ), contentDescription = "Grocery",
                            modifier = Modifier.size(40.dp),
                            tint =
                            if (transaction.category.equals("Salary")) {
                                Color(0xFF00A86B)
                            } else if (transaction.category.equals("Gifts")) {
                                Color(0xFF0077FF)
                            } else {
                                Color(0xFF7F3DFF)
                            }
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Column(modifier = Modifier.padding(10.dp)) {
                        Text(
                            text = transaction.category,
                            fontSize = 18.sp,
                            color = Color(0xFF292B2D)
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Text(
                            text = transaction.description,
                            fontSize = 15.sp,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1,
                            color = Color(0xFF91919F),
                            modifier=Modifier.fillMaxWidth(0.75f)
                        )
                    }
                }
                Column(
                    verticalArrangement = Arrangement.SpaceEvenly,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = transaction.amount.toString(), fontSize = 18.sp,
                        color =
                            Color(0xFF00A86B)

                    )
                    Spacer(modifier = Modifier.height(3.dp))
                    Row() {
                        Text(text = formattedTime, fontSize = 13.sp, color = Color(0xFF91919F))
                    }
                }

            }

        }
    }
}



