package com.stevyson.checklit.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.stevyson.checklit.db
import com.stevyson.checklit.model.Budget
import com.stevyson.checklit.model.Expense
import io.realm.kotlin.ext.query
import io.realm.kotlin.query.RealmResults
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

data class BudgetScreenState(
    val expenseAmount: String = "",
    var totalBudget: String = "0.0",
    val totalLeft: String = "",
    val budget: RealmResults<Budget>? = null,
    val expenses: List<Expense> = listOf()
)


class BudgetViewModel(): ViewModel() {


    private val _uiState = MutableStateFlow(BudgetScreenState())
    val uiState: StateFlow<BudgetScreenState> = _uiState.asStateFlow()//update the total budget

     init {


         viewModelScope.launch {
             _uiState.update { currentState ->
                 currentState.copy(
                     budget = db.query<Budget>().find(),
                     expenses = db.query<Expense>().find()
                 )
             }
         }
     }

//add expense
    fun setBudget(totalBudget: String) {
    var parsed = totalBudget.toDoubleOrNull()

    if (totalBudget.isEmpty()) {
        parsed = 0.0
    }

    if (parsed != null) {
        _uiState.update { currentState ->
            currentState.copy(
                totalBudget = totalBudget.trim().ifEmpty { "0" },
            )
        }
    }

    }


    fun saveBudget() {
        viewModelScope.launch(Dispatchers.IO) {
            db.write {
                this.copyToRealm(
                    Budget(
                        _uiState.value.totalBudget.toDouble()
                    )
                )
            }
            _uiState.update { currentState ->
                currentState.copy(
                    totalBudget = ""
                )
            }
        }
    }



}

//work the meter and expense left

//update expenses