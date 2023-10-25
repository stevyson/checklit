package com.stevyson.checklit.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stevyson.checklit.db
import com.stevyson.checklit.model.Expense
import com.stevyson.checklit.model.Recurrence
import com.stevyson.checklit.utils.calculateDateRange

import io.realm.kotlin.ext.query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class DashboardState(
    val recurrence: Recurrence = Recurrence.Daily,
    val sumTotalSpent: Double = 0.0,
    val expenses: List<Expense> = listOf()
)

class DashboardViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(DashboardState())
    val uiState: StateFlow<DashboardState> = _uiState.asStateFlow()

    init {
        _uiState.update { currentState ->
            currentState.copy(
                expenses = db.query<Expense>().find()
            )
        }
        viewModelScope.launch(Dispatchers.IO) {
            setRecurrence(Recurrence.Daily)
        }
    }

    fun setRecurrence(recurrence: Recurrence) {
        val (start, end) = calculateDateRange(recurrence, 0)

        val filteredExpenses = db.query<Expense>().find().filter { expense ->
            (expense.date.toLocalDate().isAfter(start) && expense.date.toLocalDate()
                .isBefore(end)) || expense.date.toLocalDate()
                .isEqual(start) || expense.date.toLocalDate().isEqual(end)
        }

        val sumTotal = filteredExpenses.sumOf { it.amount }

        _uiState.update { currentState ->
            currentState.copy(
                recurrence = recurrence,
                expenses = filteredExpenses,
                sumTotalSpent = sumTotal
            )
        }
    }
}