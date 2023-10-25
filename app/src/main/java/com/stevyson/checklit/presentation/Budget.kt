package com.stevyson.checklit.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.stevyson.checklit.components.BudgetMeter
import com.stevyson.checklit.components.ExpensesList
import com.stevyson.checklit.components.MonthIndicator
import com.stevyson.checklit.mock.mockExpenses
import com.stevyson.checklit.model.DayExpenses
import com.stevyson.checklit.model.groupedByDay
import com.stevyson.checklit.ui.theme.BackgroundElevated
import com.stevyson.checklit.ui.theme.CheckLitTheme
import com.stevyson.checklit.ui.theme.Chrysler
import com.stevyson.checklit.ui.theme.DividerColor
import com.stevyson.checklit.ui.theme.Typography
import com.stevyson.checklit.viewmodels.BudgetViewModel
import java.time.LocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Budget(
    navController: NavController,
    vm: BudgetViewModel = viewModel(),
){



    val state by vm.uiState.collectAsState()


    Scaffold(
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "Manage Your Expenses") },
                colors = TopAppBarDefaults.mediumTopAppBarColors(
                    containerColor = Chrysler
                ),
                navigationIcon = {
                    Surface(
                        color = Color.Transparent
                    ) {
                        Column(
                            modifier = Modifier.padding(vertical = 10.dp),
                            verticalArrangement = Arrangement.SpaceAround,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {

                            Text("Budget", style = Typography.titleLarge)

                        }
                    }
                }
            )
        },
        content = { innerPadding ->
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .padding(innerPadding)
                    .background(BackgroundElevated)
                    .fillMaxHeight()
            ) {
                Row(horizontalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 20.dp))
                {
                    MonthIndicator()
                }
                Row(modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp))
                {

                    BudgetMeter(totalSpent = 78f , totalBudget = 100f, navController = navController)
                }
                Divider(thickness = 3.dp, color = DividerColor, modifier = Modifier.padding(bottom = 8.dp))
                Text(text = "Update Your Expenses")
                Column {
                    ExpensesList(
                        expenses = state.expenses,
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(
                                rememberScrollState()
                            )
                    )
                }
                


            }
        }
    )

}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun PreviewAd() {
//    CheckLitTheme {
//        val navController = rememberNavController()
//        Budget(navController = navController)
//    }
//}