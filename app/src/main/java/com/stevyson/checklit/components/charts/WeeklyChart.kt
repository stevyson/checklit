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
import com.stevyson.checklit.model.groupedByDayOfWeek
import com.stevyson.checklit.ui.theme.Behr
import com.stevyson.checklit.ui.theme.PurpleGrey80
import com.stevyson.checklit.ui.theme.Zoffany
import com.stevyson.checklit.utils.simplifyNumber
import java.time.DayOfWeek

@Composable
fun WeeklyChart(expenses: List<Expense>){
    val groupedExpenses = expenses.groupedByDayOfWeek()

    LineChart(
        linesChartData = listOf(
            LineChartData(
                startAtZero = true,
            points = listOf(
                LineChartData.Point(
                    label = DayOfWeek.MONDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.MONDAY.name]?.total?.toFloat()
                        ?: 0f
                ),
                LineChartData.Point(
                    label = DayOfWeek.TUESDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.TUESDAY.name]?.total?.toFloat()
                        ?: 0f
                ),
                LineChartData.Point(
                    label = DayOfWeek.WEDNESDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.WEDNESDAY.name]?.total?.toFloat()
                        ?: 0f
                ),
                LineChartData.Point(
                    label = DayOfWeek.THURSDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.THURSDAY.name]?.total?.toFloat()
                        ?: 0f
                ),
                LineChartData.Point(
                    label = DayOfWeek.FRIDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.FRIDAY.name]?.total?.toFloat()
                        ?: 0f
                ),
                LineChartData.Point(
                    label = DayOfWeek.SATURDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.SATURDAY.name]?.total?.toFloat()
                        ?: 0f
                ),
                LineChartData.Point(
                    label = DayOfWeek.SUNDAY.name.substring(0, 1),
                    value = groupedExpenses[DayOfWeek.SUNDAY.name]?.total?.toFloat()
                        ?: 0f
                )
            ),
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
            labelTextColor = PurpleGrey80
        ),
        yAxisDrawer = SimpleYAxisDrawer(
            axisLineColor = PurpleGrey80,
            labelTextColor = PurpleGrey80,
            labelRatio = 7,
            labelValueFormatter = ::simplifyNumber,

        )

    )

}