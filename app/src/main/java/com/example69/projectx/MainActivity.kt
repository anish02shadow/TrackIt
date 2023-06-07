package com.example69.projectx

import android.annotation.SuppressLint
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example69.projectx.data.*
import com.example69.projectx.login.LoginViewModel
import com.example69.projectx.ui.theme.AppViewModelProvider
import com.example69.projectx.ui.theme.ProjectXTheme
import com.example69.projectx.ui.theme.screens.*

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProjectXTheme {
                val navController= rememberNavController()

                Navigation(navController = navController)
        }
    }
}
    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    @Composable
    fun Navigation(navController: NavHostController){
        NavHost(navController = navController, startDestination = "Home"){
            composable("loginpage"){
                LoginPage1(onNavToHomePage = {
                    navController.navigate("Home") {
                        launchSingleTop = true
                        popUpTo(route = "Home") {
                            inclusive = true
                        }
                    }
                },
                    //loginViewModel = loginViewModel

                ) {
                    navController.navigate("Home") {
                        launchSingleTop = true
                        popUpTo("Home") {
                            inclusive = true
                        }
                    }
                }
            }

            composable("Home"){
                var expanded by remember { mutableStateOf(false) }
                Scaffold(bottomBar = {
                    bottommenu(items = listOf(
                        BottomMenuContent("Home","Home",R.drawable.img_1),
                        BottomMenuContent("Transactions","Transactions",R.drawable.img_2),
                    ), navController = navController, onItemClick = {
                        navController.navigate(it.route)
                    }, bottomBarState = true)
                },
                    floatingActionButton = {
                        FloatingActionButton(
                            onClick = {
                                expanded = !expanded
                                //navController.navigate("ExpenseScreen")
                            },
                            modifier = Modifier.padding(bottom=5.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                contentDescription = "Add Transaction",
                                tint = Color.White
                            )
                        }
                        if (expanded){
                                Column {
                                    FloatingActionButton(
                                        onClick = {
                                            navController.navigate("IncomeScreen")
                                        },
                                        modifier = Modifier.padding(bottom=15.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.profile),
                                            contentDescription = "Add Income",
                                        )
                                    }
                                    FloatingActionButton(
                                        onClick = {
                                            navController.navigate("ExpenseScreen")
                                        },
                                        modifier = Modifier.padding(bottom=5.dp)
                                    ) {
                                        Image(
                                            painter = painterResource(id = R.drawable.img_6),
                                            contentDescription = "Add Expense",
                                        )
                                    }

                                }

                        }

                    },
                    content = {
                         homescreen(navController = navController)
                    }
                )

            }
            composable("Transactions"){
                var topbarcolor by  rememberSaveable{(mutableStateOf(0xFFFFF7F2))}

                var colortext by rememberSaveable{(mutableStateOf(0xFF212325))}
                Scaffold(topBar ={TransactionAppBar(
                    title="Transactions",
                    canNavigateBack =navController.previousBackStackEntry != null ,
                    navigateUp = { navController.navigateUp() },
                    topBarState = true,
                    colorAppBar = topbarcolor,
                    colorText = colortext)} ,
                bottomBar = {
                    bottommenu(items = listOf(
                        BottomMenuContent("Home","Home",R.drawable.img_1),
                        BottomMenuContent("Transactions","Transactions",R.drawable.img_2),
                    ), navController = navController, onItemClick = {
                        navController.navigate(it.route)
                    }, bottomBarState = true)
                }){
                    transactionsscreen(navController = navController)
                }

            }
            composable("ExpenseScreen"){
                var topbarcolor by  rememberSaveable{(mutableStateOf(0xFFFD3C4A))}

                var colortext by rememberSaveable{(mutableStateOf(0xFFFCFCFC))}
                Scaffold(topBar ={TransactionAppBar(
                    title="Expense",
                    canNavigateBack =navController.previousBackStackEntry != null ,
                    navigateUp = { navController.navigateUp() },
                    topBarState = true,
                    colorAppBar = topbarcolor,
                    colorText = colortext)}) {
                    screenexpense(navigateBack = { navController.popBackStack() })
                }
            }
            composable("IncomeScreen"){
                var topbarcolor by  rememberSaveable{(mutableStateOf(0xFF00A86B))}

                var colortext by rememberSaveable{(mutableStateOf(0xFFFCFCFC))}
                Scaffold(topBar ={TransactionAppBar(
                    title="Income",
                    canNavigateBack =navController.previousBackStackEntry != null ,
                    navigateUp = { navController.navigateUp() },
                    topBarState = true,
                    colorAppBar = topbarcolor,
                    colorText = colortext)}) {
                    screenincome(navigateBack = { navController.popBackStack() })
                }
            }
            composable(route = TransactionEditDestination.routeWithArgs,
                arguments = listOf(navArgument(TransactionEditDestination.transactionIdArg){
                    type= NavType.IntType
                })
            ){
                var topbarcolor by  rememberSaveable{(mutableStateOf(0xFFFD3C4A))}

                var colortext by rememberSaveable{(mutableStateOf(0xFFFCFCFC))}
                Scaffold(topBar ={TransactionAppBar(
                    title="Edit Expense",
                    canNavigateBack =navController.previousBackStackEntry != null ,
                    navigateUp = { navController.navigateUp() },
                    topBarState = true,
                    colorAppBar = topbarcolor,
                    colorText = colortext)}) {
                    editscreenexpense(navigateBack = { navController.popBackStack() })
                }
            }

            composable(route = IncomeTransactionEditDestination.routeWithArgs,
                arguments = listOf(navArgument(TransactionEditDestination.transactionIdArg){
                    type= NavType.IntType
                })
            ){
                var topbarcolor by  rememberSaveable{(mutableStateOf(0xFF00A86B))}

                var colortext by rememberSaveable{(mutableStateOf(0xFFFCFCFC))}
                Scaffold(topBar ={TransactionAppBar(
                    title="Edit Income",
                    canNavigateBack =navController.previousBackStackEntry != null ,
                    navigateUp = { navController.navigateUp() },
                    topBarState = true,
                    colorAppBar = topbarcolor,
                    colorText = colortext)}) {
                    editscreenincome(navigateBack = { navController.popBackStack() })
                }
            }
        }
    }


    @Composable
    fun TransactionAppBar(
        title: String,
        canNavigateBack: Boolean,
        navigateUp: () -> Unit,
        modifier: Modifier = Modifier,
        topBarState: Boolean,
        colorAppBar: Long,
        colorText: Long,
    )  {
        AnimatedVisibility(visible = topBarState) {
            TopAppBar(
                backgroundColor =Color(colorAppBar),
                title = { Text(title, fontSize = 18.sp, color=Color(colorText))},
                modifier = modifier,
                navigationIcon = {
                    if (canNavigateBack) {
                        IconButton(onClick = navigateUp) {
                            Icon(painter = painterResource(id = R.drawable.img_8), contentDescription = "Back", tint = Color(colorText))
                        }
                    }
                }
            )
        }
    }
@Composable
fun bottommenu(items: List<BottomMenuContent>, modifier: Modifier= Modifier,
               activeHighlightColor: Color = Color(0xFF7F3DFF),
               activeTextColor: Color = Color(0xFF7F3DFF),
               inactiveTextColor: Color = Color(0xFFC6C6C6),
               initialSelectedItemIndex: Int=0,
               navController: NavController,
               onItemClick: (BottomMenuContent)->Unit,
                bottomBarState:Boolean){
    val backstackentry=navController.currentBackStackEntryAsState()
    AnimatedVisibility(visible = bottomBarState) {
        BottomNavigation(modifier = modifier
            , backgroundColor = Color(0xFFFFF6F0),
            elevation = 5.dp){
            items.forEach{item->
                val selected= item.route==backstackentry.value?.destination?.route
                BottomNavigationItem(
                    selected=selected,
                    selectedContentColor = activeHighlightColor,
                    unselectedContentColor = inactiveTextColor,

                    onClick = {onItemClick(item)},
                    icon={
                        Column(horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center) {
                            if(item.badgecount>0){
                                BadgedBox(badge = {
                                    Text(text=item.badgecount.toString())
                                }) {
                                    Icon(painter = painterResource(id = item.iconId), contentDescription = item.title,
                                        tint=  if(selected) Color.White else inactiveTextColor,
                                        modifier = Modifier.size(30.dp))
                                }
                            }
                            else{
                                Icon(painter = painterResource(id = item.iconId), contentDescription = item.title,
                                    tint=  if(selected) activeTextColor else inactiveTextColor,
                                    modifier = Modifier.size(30.dp))
                            }
                            if(selected){
                                Text(
                                    text = item.title,
                                    color= if(selected) activeTextColor else inactiveTextColor
                                )
                            }
                        }
                    })
            }
        }
    }

}

@Composable
fun homescreen(viewModel: TransactionsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
               navController: NavController){
    val transactionScreenUiState by viewModel.transactionsScreenUiState.collectAsState()
    val transactionsScreenUiStateToday by viewModel.transactionsScreenUiStateToday.collectAsState()
    val transactionsScreenUiStateWeek by viewModel.transactionsScreenUiStateWeek.collectAsState()
    val transactionsScreenUiStateMonth by viewModel.transactionsScreenUiStateMonth.collectAsState()
    val transactionsScreenUiStateYear by viewModel.transactionsScreenUiStateYear.collectAsState()

    var totalIncome = 0.0
    var totalExpense = 0.0
    var time by remember {mutableStateOf("All")}
    if(time.equals("All")){
        transactionScreenUiState.transactionList.forEach { transaction ->
            if (transaction.type.equals("Expense")) {
                totalExpense += transaction.amount
            } else {
                totalIncome += transaction.amount
            }
        }
    }
    else if (time.equals("Today")){
        transactionsScreenUiStateToday.transactionList.forEach { transaction ->
            if (transaction.type.equals("Expense")) {
                totalExpense += transaction.amount
            } else {
                totalIncome += transaction.amount
            }
        }
    }
    else if (time.equals("Week")){
        transactionsScreenUiStateWeek.transactionList.forEach { transaction ->
            if (transaction.type.equals("Expense")) {
                totalExpense += transaction.amount
            } else {
                totalIncome += transaction.amount
            }
        }
    }
    else if (time.equals("Month")){
        transactionsScreenUiStateMonth.transactionListt.forEach { transaction ->
            if (transaction.type.equals("Expense")) {
                totalExpense += transaction.amount
            } else {
                totalIncome += transaction.amount
            }
        }
    }
    else if (time.equals("Year")){
        transactionsScreenUiStateYear.transactionList.forEach { transaction ->
            if (transaction.type.equals("Expense")) {
                totalExpense += transaction.amount
            } else {
                totalIncome += transaction.amount
            }
        }
    }
    Box(modifier = Modifier
        .fillMaxSize()
        .background(
            Color(0xFFFFF7F2)
        )){
        Column() {
            var timee=time
            var transactionListt by remember { mutableStateOf(transactionScreenUiState.transactionList) }
            time=topbox(totalExpense=totalExpense, totalIncome = totalIncome, time=time,navController=navController)
            Spacer(modifier = Modifier.height(27.dp))
            var x:Int
            x=selecttime(
                items = listOf(
                    "Today",
                    "Week",
                    "Month",
                    "Year"
                )
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier
                    .padding(start = 20.dp, end = 15.dp)
                    .fillMaxWidth()
            ) {
                Text(text = "Recent Transaction", fontSize = 18.sp, color = Color(0xff292B2D),fontWeight =FontWeight.SemiBold)
            }
            Spacer(modifier = Modifier.height(15.dp))
            Cardlistt(
                transactionList =
                if(x==0){
                    transactionsScreenUiStateToday.transactionList
                }
                else if(x==1){
                    transactionsScreenUiStateWeek.transactionList
                }
                else if(x==2){
                    transactionsScreenUiStateMonth.transactionListt
                }
                else{
                    transactionsScreenUiStateYear.transactionList
                },
                navController = navController)
        }
    }


}
    @Composable
    fun ShareButton(
        totalExpense: Double,
        totalIncome: Double,
        viewModel: TransactionsScreenViewModel = androidx.lifecycle.viewmodel.compose.viewModel(factory= AppViewModelProvider.Factory),
        timee: String
    ) {
        val transactionScreenUiState by viewModel.transactionsScreenUiState.collectAsState()
        val transactionsScreenUiStateToday by viewModel.transactionsScreenUiStateToday.collectAsState()
        val transactionsScreenUiStateWeek by viewModel.transactionsScreenUiStateWeek.collectAsState()
        val transactionsScreenUiStateMonth by viewModel.transactionsScreenUiStateMonth.collectAsState()
        val transactionsScreenUiStateYear by viewModel.transactionsScreenUiStateYear.collectAsState()

        val context = LocalContext.current
        val launcher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.CreateDocument(),
            onResult = { uri ->
                uri?.let { documentUri ->
                    if(timee.equals("Today"))
                        writeToFile(context, documentUri, totalExpense, totalIncome, transactionsScreenUiStateToday.transactionList)
                    else if(timee.equals("Week"))
                        writeToFile(context, documentUri, totalExpense, totalIncome, transactionsScreenUiStateWeek.transactionList)
                    else if(timee.equals("Month"))
                        writeToFile(context, documentUri, totalExpense, totalIncome, transactionsScreenUiStateMonth.transactionListt)
                    else if(timee.equals("Year"))
                        writeToFile(context, documentUri, totalExpense, totalIncome, transactionsScreenUiStateYear.transactionList)
                    else
                        writeToFile(context, documentUri, totalExpense, totalIncome, transactionScreenUiState.transactionList)

                }
            }
        )

        IconButton(
            onClick = { launcher.launch("transaction_list.xls") }
        ) {
            Icon(
                painter = painterResource(id = com.example69.projectx.R.drawable.share),
                contentDescription = "Notification",
                tint = Color(0xFF7B61FF),
                modifier = Modifier.size(32.dp)
            )
        }
    }
    /*fun writeToFile(
        context: Context,
        uri: Uri,
        totalExpense: Double,
        totalIncome: Double,
        transactionList: List<Transaction>
    ) {
        val outputStream = context.contentResolver.openOutputStream(uri)
        val writer = outputStream?.bufferedWriter()

        writer?.use {
            val workbook = XSSFWorkbook()
            val sheet = workbook.createSheet("Transactions")

            // Create header row
            val headerRow = sheet.createRow(0)
            val headerCells = arrayOf(
                "Amount",
                "Type",
                "Category",
                "Description",
                "Date",
                "Time"
            )
            for (i in headerCells.indices) {
                headerRow.createCell(i).setCellValue(headerCells[i])
            }

            // Write data rows
            for (i in transactionList.indices) {
                val transaction = transactionList[i]
                val row = sheet.createRow(i + 1)
                val cells = arrayOf(
                    transaction.amount.toString(),
                    transaction.type,
                    transaction.category,
                    transaction.description,
                    transaction.date.toString(),
                    transaction.time.toString()
                )
                for (j in cells.indices) {
                    row.createCell(j).setCellValue(cells[j])
                }
            }

            // Write total expense and total income
            val totalRow = sheet.createRow(transactionList.size + 2)
            totalRow.createCell(0).setCellValue("Total Expense:")
            totalRow.createCell(1).setCellValue(totalExpense)
            val incomeRow = sheet.createRow(transactionList.size + 3)
            incomeRow.createCell(0).setCellValue("Total Income:")
            incomeRow.createCell(1).setCellValue(totalIncome)

            // Autosize columns
            for (i in 0 until headerCells.size) {
                sheet.autoSizeColumn(i)
            }

            // Write workbook to output stream
            workbook.write(outputStream)
        }
    }*/
    fun writeToFile(
        context: Context,
        uri: Uri,
        totalExpense: Double,
        totalIncome: Double,
        transactionList: List<Transaction>
    ) {
        // Create a new Excel workbook and sheet
        val workbook = XSSFWorkbook()
        val sheet = workbook.createSheet("Transaction List")

        // Create a header row
        val headerRow = sheet.createRow(0)
        headerRow.createCell(0).setCellValue("Amount")
        headerRow.createCell(1).setCellValue("Type")
        headerRow.createCell(2).setCellValue("Category")
        headerRow.createCell(3).setCellValue("Description")
        headerRow.createCell(4).setCellValue("Date")
        headerRow.createCell(5).setCellValue("Time")

        // Add the transaction data to the sheet
        var rowNum = 1
        for (transaction in transactionList) {
            val row = sheet.createRow(rowNum++)
            row.createCell(0).setCellValue(transaction.amount)
            row.createCell(1).setCellValue(transaction.type)
            row.createCell(2).setCellValue(transaction.category)
            row.createCell(3).setCellValue(transaction.description)
            row.createCell(4).setCellValue(transaction.date.toString())
            row.createCell(5).setCellValue(transaction.time.toString())
        }
        // Write total expense and total income
        val totalRow = sheet.createRow(transactionList.size + 2)
        totalRow.createCell(0).setCellValue("Total Expense:")
        totalRow.createCell(1).setCellValue(totalExpense)
        val incomeRow = sheet.createRow(transactionList.size + 3)
        incomeRow.createCell(0).setCellValue("Total Income:")
        incomeRow.createCell(1).setCellValue(totalIncome)



        // Write the workbook to the output stream
        val outputStream = context.contentResolver.openOutputStream(uri)
        workbook.write(outputStream)
        outputStream?.close()
    }




    @Composable
    fun Cardlistt(
        transactionList: List<Transaction>,
        navController: NavController
    ){
        LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)){
            items(items = transactionList, key = { it.id }) { item ->
                TransactionCardd(transaction=item, navController = navController)
            }
        }
    }
@Composable
fun topbox(totalExpense:Double, totalIncome:Double,time: String,navController: NavController): String {
    var timee=""
    Box(
        modifier = Modifier
            .clip(
                RoundedCornerShape(
                    bottomStart = 32.dp,
                    bottomEnd = 32.dp
                )
            )
            .height(312.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFECD8B2),
                        Color(0x73F8EDD8)
                    )
                )
            )

    ) {
        Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally) {
            timee=topnavigation(totalExpense=totalExpense, totalIncome=totalIncome, time=time)

            Text(
                text = "Account Balance",
                fontSize = 17.sp,
                color = Color(0xFF91919F)
            )
            Text(
                text = (totalIncome-totalExpense).toInt().toString(),
                fontSize = 48.sp,
                modifier = Modifier.padding(bottom = 5.dp),
                fontWeight = FontWeight.Bold,
                color = Color(0xFF161719)

            )


            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly,
                modifier = Modifier
                    .padding(15.dp)
                    .fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(155.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFF00A86B))
                        .padding(6.dp)
                        .clickable {
                            navController.navigate("IncomeScreen")
                        }


                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        /*      Box(contentAlignment=Alignment.Center,
                    modifier = Modifier
                        .background(Color(0xFfFCFCFC))
                        .clip(
                            RoundedCornerShape(20.dp)
                        )
                        .height(48.dp)
                        .width(48.dp)
                        .padding(start=10.dp, top = 5.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.img_3),
                        contentDescription = "Profit",
                        tint = Color(0xFF00A86B),
                        modifier = Modifier.size(40.dp)
                    )
                }*/
                        Image(
                            painter = painterResource(id = R.drawable.profile),
                            contentDescription = "Profit Icon",
                            modifier = Modifier
                                .padding(10.dp)
                                .size(46.dp)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Income",
                                color = Color(0xFfFCFCFC),
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                            Text(
                                text = totalIncome.toInt().toString(),
                                color = Color(0xFfFCFCFC),
                                //fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }


                    }
                }
                Box(
                    modifier = Modifier
                        .height(80.dp)
                        .width(155.dp)
                        .clip(RoundedCornerShape(25.dp))
                        .background(Color(0xFFFD3C4A))
                        .padding(6.dp)
                        .clickable {
                            navController.navigate("ExpenseScreen")
                        }


                ) {
                    Row(
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_6),
                            contentDescription = "Loss Icon",
                            modifier = Modifier
                                .padding(10.dp)
                                .size(46.dp)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "Expense",
                                color = Color(0xFfFCFCFC),
                                fontSize = 18.sp,
                                modifier = Modifier.padding(top = 10.dp)
                            )
                            Text(
                                text = totalExpense.toInt().toString(),
                                color = Color(0xFfFCFCFC),
                                //fontSize = 22.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                    }
                }
            }
        }
    }
    return timee
}

@Composable
fun timetopboxdropdown(selectedCategory: String,onCategorySelected: (String) -> Unit): String{
    var expanded by remember { mutableStateOf(false) }
    TextButton(
        onClick = {  expanded = true}) {
        Icon(
            painter = painterResource(id = com.example69.projectx.R.drawable.loss),
            contentDescription = "Down Arrow",
            tint = Color(0xFF7B61FF)
        )
        Text(text = selectedCategory, fontSize = 18.sp, color = Color(0xFF212325))
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
            modifier=Modifier.background(Color(0xFCFCFCFC))
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
    return selectedCategory
}
@Composable
fun topnavigation(totalExpense: Double,totalIncome: Double,time:String): String {
    var select_time: String=""
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.padding(top=16.dp)
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(25.dp)
                .fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = com.example69.projectx.R.drawable.niniiii),
                contentDescription = "Profile Photo",
                modifier = Modifier
                    .clip(CircleShape)
                    .size(35.dp)
            )

            var selectedCategory by remember { mutableStateOf("All") }

            Column {
                var selected_time= timetopboxdropdown(selectedCategory = selectedCategory) { category ->
                    selectedCategory = category
                }
                select_time=selected_time
            }
            ShareButton(totalExpense =totalExpense , totalIncome = totalIncome , timee = time )
            }

        }
    return select_time
    }
}
@Composable
fun selecttime(items: List<String>, modifier: Modifier= Modifier,
               activeHighlightColor: Color = Color(0xFFFCEED4),
               activeTextColor: Color = Color(0xFFFCAC12),
               inactiveTextColor: Color = Color(0xFF91919F),
               initialSelectedItemIndex: Int=0): Int{
    var selecteditemindex by remember {
        mutableStateOf(initialSelectedItemIndex)
    }
    Row(horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
    ) {
        items.forEachIndexed{index,item->
            rangeselectoritem(item= item,
                isSelected = index==selecteditemindex,
                activeHighlightColor=activeHighlightColor,
                activeTextColor=activeTextColor,
                inactiveTextColor=inactiveTextColor){
                selecteditemindex=index
            }
        }

    }
    return selecteditemindex
}

@Composable
fun rangeselectoritem(
    item: String,
    isSelected: Boolean = false,
    activeHighlightColor: Color = Color(0xFFFCEED4),
    activeTextColor: Color = Color(0xFFFCAC12),
    inactiveTextColor: Color = Color(0xFFE3E5E5),
    onItemClick: () ->Unit
){
    Row(horizontalArrangement = Arrangement.SpaceAround,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.clickable {
            onItemClick()
        }) {
        Box(contentAlignment = Alignment.Center,
            modifier = Modifier
                .clip(RoundedCornerShape(15.dp))
                .background(
                    if (isSelected) activeHighlightColor
                    else Color.Transparent
                )
                .padding(10.dp)) {
            Text(
                text = item,
                color= if(isSelected) activeTextColor else inactiveTextColor,
                fontSize = 14.sp
            )
        }
    }
}

@Composable
fun Cardlist(){
    LazyColumn(contentPadding = PaddingValues(bottom = 80.dp)){
        items((1..5).toList()){
            TransactionCard()
        }
    }
}

@Composable
fun TransactionCard(
){
    Box(modifier= Modifier
        .padding(10.dp)
        .clip(RoundedCornerShape(10.dp))
        .background(Color(0xFFFFF6F0)),
    )
    {
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(start = 10.dp, end = 10.dp), horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,){
            Row(horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .size(75.dp)
                    .background(Color(0xFFFCEED4))
                    .padding(5.dp)) {
                    Icon(painter = painterResource(id = R.drawable.img_5), contentDescription ="Grocery",
                        tint = Color(0xFFFCAC12),modifier = Modifier.size(55.dp))
                }
                Spacer(modifier = Modifier.width(5.dp))
                Column(modifier = Modifier.padding(10.dp)) {
                    Text(text = "Shopping", fontSize = 18.sp,color= Color(0xFF292B2D))
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(text="Buy some grocery", fontSize = 15.sp,color= Color(0xFF91919F))
                }
            }
            Text(text = "Rs.120", fontSize = 18.sp, color = Color(0xFFFD3C4A))

        }
    }
}



