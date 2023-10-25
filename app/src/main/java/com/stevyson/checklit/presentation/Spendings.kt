package com.stevyson.checklit.presentation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.KeyboardArrowLeft
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
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
import com.stevyson.checklit.components.ExpensesList
import com.stevyson.checklit.components.PickerTrigger
import com.stevyson.checklit.model.Recurrence
import com.stevyson.checklit.ui.theme.Chrysler
import com.stevyson.checklit.ui.theme.LabelSecondary
import com.stevyson.checklit.ui.theme.Typography
import com.stevyson.checklit.viewmodels.SpendingViewModel
import java.text.DecimalFormat

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Spendings(
    navController: NavController,
    vm: SpendingViewModel = viewModel()
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
            title = { Text("Spendings") },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Chrysler
            ),
            navigationIcon = {
                Surface(
                    onClick = navController::popBackStack,
                    color = Chrysler,
                ) {
                    Row(modifier = Modifier.padding(vertical = 10.dp)) {
                        Icon(
                            Icons.Rounded.KeyboardArrowLeft, contentDescription = "dashboard"
                        )
                        Text("Dashboard")
                    }
                }
            })
    }, content = { innerPadding ->
        Column(
            modifier = Modifier.padding(innerPadding)
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    "Total for:",
                    style = Typography.bodyMedium,
                )
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
            }
            Row(modifier = Modifier.padding(vertical = 32.dp)) {
                Text(
                    "$",
                    style = Typography.bodyMedium,
                    color = LabelSecondary,
                    modifier = Modifier.padding(end = 4.dp, top = 4.dp)
                )
                Text(
                    DecimalFormat("0.#").format(state.sumTotal),
                    style = Typography.titleLarge
                )
            }
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
    )
}