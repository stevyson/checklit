package com.stevyson.checklit.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stevyson.checklit.ui.theme.Behr
import com.stevyson.checklit.ui.theme.Shapes
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MonthIndicator(
    modifier: Modifier = Modifier,
){
        val date = LocalDate.now()
        val thisMonth = date.month

   Surface(
       shape = Shapes.large,
       color = Behr,
       shadowElevation = 6.dp
   ) {
       Row(
           horizontalArrangement = Arrangement.Center,
           modifier = modifier

               .padding(horizontal = 30.dp, vertical = 8.dp)
       ) {
           Text(text = thisMonth.toString() + "  " + date.year.toString())
       }
   }
}