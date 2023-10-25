package com.stevyson.checklit.presentation

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.DateRange
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.stevyson.checklit.components.ReportPage
import com.stevyson.checklit.model.Recurrence
import com.stevyson.checklit.ui.theme.Chrysler
import com.stevyson.checklit.viewmodels.ReportsViewModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalPagerApi::class)
@Composable
fun Report(
    vm: ReportsViewModel = viewModel()
){
    val recurrences = listOf(
        Recurrence.Weekly,
        Recurrence.Monthly,
        Recurrence.Yearly
    )

    val uiState by vm.uiState.collectAsState()

    Scaffold(topBar = {
        MediumTopAppBar(
            title = { Text("Reports") },
            colors = TopAppBarDefaults.mediumTopAppBarColors(
                containerColor = Chrysler
            ),
            actions = {
                IconButton(onClick = vm::openRecurrenceMenu ) {
                    Icon(
                        imageVector = Icons.Rounded.DateRange,
                        contentDescription = "Date Range"
                    )
                }
                DropdownMenu(
                    expanded = uiState.recurrenceMenuOpened,
                    onDismissRequest = vm::closeRecurrenceMenu
                ) {
                    recurrences.forEach { recurrence ->
                        DropdownMenuItem(text = { Text(recurrence.name) }, onClick = {
                            vm.setRecurrence(recurrence)
                            vm.closeRecurrenceMenu()
                        })
                    }
                }
            }
        )
    }, content = { innerPadding ->
        val numOfPages = when (uiState.recurrence){
            Recurrence.Weekly -> 53
            Recurrence.Monthly -> 12
            Recurrence.Yearly -> 1
            else -> 53
        }
        HorizontalPager(count = numOfPages, reverseLayout = true) { page ->
           ReportPage(innerPadding, page ,uiState.recurrence )
        }
    }
    )
}
