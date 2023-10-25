package com.stevyson.checklit.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.stevyson.checklit.ui.theme.Behr
import com.stevyson.checklit.ui.theme.Shapes


@Composable
fun CustomButton(
    modifier: Modifier = Modifier,
    label: String
){

    Surface(
        shape = Shapes.large,
        color = Behr,
        shadowElevation = 6.dp
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp, vertical = 8.dp)
        ) {
            Text(text = label)
        }
    }
}