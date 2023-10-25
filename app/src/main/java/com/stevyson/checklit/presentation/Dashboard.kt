package com.stevyson.checklit.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.stevyson.checklit.components.CustomButton
import com.stevyson.checklit.components.DashBoardBudgetMeter
import com.stevyson.checklit.components.DashExpenseRow
import com.stevyson.checklit.components.ExpensesList
import com.stevyson.checklit.components.PickerTrigger
import com.stevyson.checklit.mock.mockExpenses
import com.stevyson.checklit.model.Expense
import com.stevyson.checklit.model.Recurrence
import com.stevyson.checklit.ui.theme.BackgroundElevated
import com.stevyson.checklit.ui.theme.Chrysler
import com.stevyson.checklit.ui.theme.DividerColor
import com.stevyson.checklit.viewmodels.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DashBoard(
    navController: NavController,
    vm: DashboardViewModel = viewModel()
){
    val recurrences = listOf(
        Recurrence.Daily,
        Recurrence.Weekly,
        Recurrence.Monthly,
        Recurrence.Yearly
    )

    val state by vm.uiState.collectAsState()
    var recurrenceMenuOpened by remember {
        mutableStateOf(false)
    }

    Scaffold(topBar = {
    MediumTopAppBar(
        title = { Text("Dashboard") },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = Chrysler
        )
    )
}, content = { innerPadding ->
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .background(BackgroundElevated)
            .fillMaxHeight()
            ,
        horizontalAlignment = Alignment.CenterHorizontally
    )
    {
        PickerTrigger(
            state.recurrence.target ?: Recurrence.None.target,
            onClick = { recurrenceMenuOpened = !recurrenceMenuOpened },
            modifier = Modifier.padding(start = 16.dp)
        )
        DropdownMenu(expanded = recurrenceMenuOpened,
            onDismissRequest = { recurrenceMenuOpened = false }) {
            recurrences.forEach { recurrence ->
                DropdownMenuItem(text = { Text(recurrence.target) }, onClick = {
                    vm.setRecurrence(recurrence)
                    recurrenceMenuOpened = false
                })
            }
        }
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp))
        {
            DashBoardBudgetMeter(totalSpent = 49f, totalBudget = 100f)
        }
        Divider(thickness = 3.dp, color = DividerColor)
        Text(text = "Largest Expense")
        Row(horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp, horizontal = 10.dp))
        {
           val largestExpense = mockExpenses.maxBy { expense: Expense ->
               expense.amount
           }
            DashExpenseRow(expense = largestExpense )
        }
        Divider(thickness = 3.dp,
            color = DividerColor,
            modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 8.dp)
        )
        CustomButton(label = "View Your Spending", modifier = Modifier.clickable {
            navController.navigate("dashboard/spending")
        })
        Divider(
            thickness = 3.dp,
            color = DividerColor,
            modifier = Modifier
            .padding(horizontal = 30.dp, vertical = 8.dp))
        CustomButton(label = "Tips On Budgeting")
        Divider(
            thickness = 3.dp,
            color = DividerColor,
            modifier = Modifier
                .padding(horizontal = 30.dp, vertical = 8.dp))
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

//    buget metter

//    Largest expense

//    View your spending

//    Tips option Tab

    }
    )
}