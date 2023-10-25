package com.stevyson.checklit.components

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.stevyson.checklit.ui.theme.CheckLitTheme
import com.stevyson.checklit.ui.theme.FillTertiary
import com.stevyson.checklit.ui.theme.PurpleGrey80
import com.stevyson.checklit.ui.theme.Shapes
import com.stevyson.checklit.ui.theme.Typography
import com.stevyson.checklit.ui.theme.Zoffany
import com.stevyson.checklit.viewmodels.DashboardViewModel

@Composable
fun DashBoardBudgetMeter(
    vm: DashboardViewModel = viewModel(),
    modifier: Modifier = Modifier,
    totalSpent: Float,
    totalBudget: Float,
//percentage: Float,
    number: Int = 100,
    fontSize: TextUnit = 28.sp,
    radius: Dp = 50.dp,
    color: Color = PurpleGrey80,
    strokeWidth: Dp = 8.dp,
    animDuration: Int = 1000,
    animDelay: Int = 0
){
    val state by vm.uiState.collectAsState()

    var animationPlayed by remember {
        mutableStateOf(false)
    }
    val percentage = totalSpent/totalBudget
    //val percentage = ans
    val curPercentage = animateFloatAsState(
        targetValue = if (animationPlayed) percentage else 0.0f,
        animationSpec = tween(
            durationMillis = animDuration,
            delayMillis = animDelay
        )
    )
    LaunchedEffect(key1 = true ){
        animationPlayed = true
    }

    Surface(
        shape = Shapes.large,
        color = Color.Transparent,
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
            ,
            shape = CardDefaults.elevatedShape,
            colors = CardDefaults.cardColors(Zoffany),
            elevation = CardDefaults.cardElevation(6.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceAround,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(radius * 3f)
                        .padding(end = 4.dp)
                ) {

                    Canvas(modifier = Modifier.size(radius * 2f)) {
                        drawCircle(
                            color = FillTertiary,
                            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = color,
                            -90f,
                            360 * curPercentage.value,
                            useCenter = false,
                            style = Stroke(strokeWidth.toPx(), cap = StrokeCap.Round)
                        )
                    }
                    Text(
                        text = "${(curPercentage.value * number).toInt()}%",
                        color = Color.White,
                        fontSize = fontSize,
                        fontWeight = FontWeight.Bold
                    )
                    Text(modifier = Modifier.padding( top = 42.dp),
                        text = ("Spent"),
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold

                    )
                }

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(radius * 2f)
                ) {
                    Column(

                    ) {

                        Text(modifier = Modifier.padding(bottom = 12.dp),
                            text = ("Total budget: ${15600}"),
                            color = Color.White,
                            style = Typography.headlineMedium
                        )
                        Text(
                            text = ("${state.sumTotalSpent} left"),
                            color = PurpleGrey80,
                            style = Typography.headlineMedium
                        )
                    }
                }

            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewAd() {
    CheckLitTheme {
        DashBoardBudgetMeter(totalSpent = 100f, totalBudget = 50f)
    }
}