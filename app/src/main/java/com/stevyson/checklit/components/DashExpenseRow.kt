package com.stevyson.checklit.components


import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stevyson.checklit.model.Expense
import com.stevyson.checklit.ui.theme.LabelSecondary
import com.stevyson.checklit.ui.theme.Typography
import com.stevyson.checklit.utils.formatDayForRange
import java.text.DecimalFormat

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DashExpenseRow(expense: Expense, modifier: Modifier = Modifier ){
    Column(modifier = modifier) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                expense.note ?: expense.category!!.name,
                style = Typography.headlineMedium
            )
            Text(
                "USD ${DecimalFormat("0.#").format(expense.amount)}",
                style = Typography.headlineMedium
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 6.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            CategoryBadge(category = expense.category!!)
            Text(
                expense.date.formatDayForRange(),
                style = Typography.bodyMedium,
                color = LabelSecondary
            )
        }
    }
}