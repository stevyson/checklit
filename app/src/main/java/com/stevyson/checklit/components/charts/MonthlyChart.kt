package com.stevyson.checklit.components.charts

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.github.tehras.charts.line.LineChart
import com.github.tehras.charts.line.LineChartData
import com.github.tehras.charts.line.renderer.line.GradientLineShader
import com.github.tehras.charts.line.renderer.line.SolidLineDrawer
import com.github.tehras.charts.line.renderer.point.FilledCircularPointDrawer
import com.github.tehras.charts.line.renderer.xaxis.SimpleXAxisDrawer
import com.github.tehras.charts.line.renderer.yaxis.SimpleYAxisDrawer
import com.github.tehras.charts.piechart.animation.simpleChartAnimation
import com.stevyson.checklit.model.Expense
import com.stevyson.checklit.model.groupedByDayOfMonth
import com.stevyson.checklit.ui.theme.Behr
import com.stevyson.checklit.ui.theme.PurpleGrey80
import com.stevyson.checklit.ui.theme.Zoffany
import com.stevyson.checklit.utils.simplifyNumber
import java.time.LocalDate
import java.time.YearMonth

@Composable
fun MonthlyChart(expenses: List<Expense>, month: LocalDate){
    val groupedExpenses = expenses.groupedByDayOfMonth()
    val numberOfDays = YearMonth.of(month.year, month.month).lengthOfMonth()

    LineChart(
        linesChartData = listOf(
            LineChartData(
                startAtZero = true,
                points = buildList() {
                    for (i in 1..numberOfDays) {
                        add(LineChartData.Point(
                            label = "$i",
                            value = groupedExpenses[i]?.total?.toFloat() ?: 0f
                        ))

                    }
                },
                lineDrawer = SolidLineDrawer(color = Behr)
            )

        ),
        animation = simpleChartAnimation(),
        pointDrawer = FilledCircularPointDrawer(color = PurpleGrey80),
        lineShader = GradientLineShader(colors = listOf(Zoffany, PurpleGrey80)),
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxSize(),
        xAxisDrawer = SimpleXAxisDrawer(
            axisLineColor = PurpleGrey80,
            labelTextColor = PurpleGrey80,
            labelRatio = 4
        ),
        yAxisDrawer = SimpleYAxisDrawer(
            axisLineColor = PurpleGrey80,
            labelTextColor = PurpleGrey80,
            labelRatio = 4,
            labelValueFormatter = ::simplifyNumber,
            )


    )

}