package com.stevyson.checklit.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.stevyson.checklit.ui.theme.FillTertiary
import com.stevyson.checklit.ui.theme.PurpleGrey80
import com.stevyson.checklit.ui.theme.Shapes
import com.stevyson.checklit.ui.theme.Typography
import com.stevyson.checklit.ui.theme.Zoffany
import com.stevyson.checklit.viewmodels.BudgetViewModel
import com.stevyson.checklit.viewmodels.DashboardViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BudgetMeter(
    vm2: DashboardViewModel = viewModel(),
    vm: BudgetViewModel = viewModel(),
    navController: NavController,
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
    val state2 by vm2.uiState.collectAsState()
    val state by vm.uiState.collectAsState()

    val left = state.budget?.last()?.totalBudget?.minus(state2.sumTotalSpent)





    var showDialog by rememberSaveable { mutableStateOf(false) }

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
                horizontalArrangement = Arrangement.SpaceEvenly,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .size(radius * 3f)
                        .padding(end = 14.dp)
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
                }
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.size(radius * 2f)
                ) {
                    Column(

                    ) {

                        Text(
                            modifier = Modifier
                                .padding(bottom = 12.dp)
                                .clickable {
                                    showDialog = true
                                }
                            ,
                            text = ("Total budget: ${state.budget?.last()?.totalBudget}"),
                            color = Color.White,
                            style = Typography.labelLarge
                        )
                        Text(
                            text = ("$left left"),
                            color = PurpleGrey80,
                            style = Typography.labelLarge
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(radius * 2f)
                        .padding(start = 15.dp)
                        .clickable { navController.navigate("budget/add") },
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Box(contentAlignment = Alignment.Center, modifier = Modifier.padding(bottom = 5.dp)) {
                            Canvas(modifier = modifier.size(radius)) {
                                drawCircle(
                                    color = PurpleGrey80,
                                    style = Stroke(4.dp.toPx()),

                                    )
                            }
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "Add",
                                tint = PurpleGrey80
                            )
                        }
                        Text(text = "Add", color = PurpleGrey80, style = Typography.labelLarge)
                        Text(text = "expenses", color = PurpleGrey80, style = Typography.labelLarge)
                    }
                }
            }
        }
        if (showDialog) {
            Dialog(
                onDismissRequest = { showDialog = false },
                content = {
                    Surface(
                        shape = Shapes.large,
                        shadowElevation = 6.dp,
                        border = BorderStroke(3.dp, Color.Transparent)
                    ){
                        Column(
                            modifier = Modifier.padding(40.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text(
                                text = "Edit Your Budget",
                                style = Typography.titleMedium,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                            UnstyledTextField(
                                value = state.totalBudget,
                                onValueChange = vm::setBudget,
                                modifier = Modifier.padding(vertical = 10.dp)
                            )
                            Button(
                                onClick = vm::saveBudget,
                                modifier = Modifier.padding(16.dp),
                                shape = Shapes.large,
                                enabled = true
                            ) {
                                Text("Save")
                            }

                        }
                    }
                }




            )
        }
    }
}

//@RequiresApi(Build.VERSION_CODES.O)
//@Preview(showBackground = true)
//@Composable
//fun PreviewAd() {
//    CheckLitTheme {
//        BudgetMeter(totalSpent = 100f, totalBudget = 50f)
//    }
//}