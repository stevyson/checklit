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
import com.stevyson.checklit.model.groupedByMonth
import com.stevyson.checklit.ui.theme.Behr
import com.stevyson.checklit.ui.theme.PurpleGrey80
import com.stevyson.checklit.ui.theme.Zoffany
import com.stevyson.checklit.utils.simplifyNumber
import java.time.Month

@Composable
fun YearlyChart(expenses: List<Expense>){
    val groupedExpenses = expenses.groupedByMonth()

    LineChart(
        linesChartData = listOf(
            LineChartData(
                startAtZero = true,
                points = listOf(
                    LineChartData.Point(
                        label = Month.JANUARY.name.substring(0, 1),
                        value = groupedExpenses[Month.JANUARY.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.FEBRUARY.name.substring(0, 1),
                        value = groupedExpenses[Month.FEBRUARY.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.MARCH.name.substring(0, 1),
                        value = groupedExpenses[Month.MARCH.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.APRIL.name.substring(0, 1),
                        value = groupedExpenses[Month.APRIL.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.MAY.name.substring(0, 1),
                        value = groupedExpenses[Month.MAY.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.JUNE.name.substring(0, 1),
                        value = groupedExpenses[Month.JUNE.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.JULY.name.substring(0, 1),
                        value = groupedExpenses[Month.JULY.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.AUGUST.name.substring(0, 1),
                        value = groupedExpenses[Month.AUGUST.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.SEPTEMBER.name.substring(0, 1),
                        value = groupedExpenses[Month.SEPTEMBER.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.OCTOBER.name.substring(0, 1),
                        value = groupedExpenses[Month.OCTOBER.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.NOVEMBER.name.substring(0, 1),
                        value = groupedExpenses[Month.NOVEMBER.name]?.total?.toFloat()
                            ?: 0f
                    ),
                    LineChartData.Point(
                        label = Month.DECEMBER.name.substring(0, 1),
                        value = groupedExpenses[Month.DECEMBER.name]?.total?.toFloat()
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