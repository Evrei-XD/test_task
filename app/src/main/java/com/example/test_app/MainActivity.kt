package com.example.test_app

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.persistence.sqlite.SqliteManager
import com.example.utils.DateUtils
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.ViewPortHandler
import kotlinx.android.synthetic.main.activity_main.*
import java.util.ArrayList
import javax.inject.Inject
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var sqliteManager: SqliteManager


    private var secondsCount = 0
    private var dotCount = 60
    private var shiftGraphFromRightSide = 0
    private val myArray = arrayOf(1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1, 2, 4, 8, 16, 32, 64, 128, 256, 512)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        secondsCount = -dotCount+((DateUtils.getTime()?.toInt()?.plus(shiftGraphFromRightSide))?.rem(dotCount)!!)
        DateUtils.getTime()?.toInt()?.let { initializeChart(59, it + shiftGraphFromRightSide) }
    }


    private fun initializeChart(dayCount: Int, newLabels: Int) {
        val entries = ArrayList<Entry>()
        for (i in 0..dayCount) {
            entries.add(Entry(myArray[i].toFloat() / 100, i))
        }

        val labels = ArrayList<String>()
        for(s in 0+newLabels..59+newLabels){
            labels.add("")
        }
        val dataset = LineDataSet(entries, "")
        val data = LineData(labels, dataset)


        chart_mainchart?.data = data

        val computed = intArrayOf(Color.TRANSPARENT)
        val label = arrayOf("")
        chart_mainchart.setDescription("")
        chart_mainchart.setDescriptionTextSize(16f)
        chart_mainchart.setDescriptionColor(Color.TRANSPARENT)
        chart_mainchart.legend.isEnabled = false
        chart_mainchart.legend.isWordWrapEnabled = false
        chart_mainchart.legend.textColor = Color.TRANSPARENT
        chart_mainchart.legend.setCustom(computed, label)

        chart_mainchart.setDrawGridBackground(false)
        chart_mainchart.axisLeft.setDrawGridLines(true)
        chart_mainchart.axisLeft.setDrawAxisLine(true)
        chart_mainchart.axisLeft.gridColor = Color.WHITE
        chart_mainchart.axisRight.setDrawGridLines(false)
        chart_mainchart.axisRight.textColor = Color.TRANSPARENT
        chart_mainchart.xAxis.setDrawGridLines(false)

        chart_mainchart.setPinchZoom(false)
        chart_mainchart.isDragEnabled = true //здесь можно сделать изменение масштаба только по оси х
        chart_mainchart.isScaleXEnabled = true//и перетаскивание по ней же если поставить в обоих этих строчках true
        chart_mainchart.isScaleYEnabled = false
        chart_mainchart.setScaleMinima(60f, 0f)//здесь можно увеличить начальный масштаб 2f = 2x
        chart_mainchart.setVisibleXRange(20f, 60f)//здесь можно настроить минимальный и максимальный диапазон увеличения (настраивается количеством отображаемых по оси х точек)
        chart_mainchart.moveViewToX(40f)//здесь можно задать начальное смещение по оси Х. Вычисляется как количество отрезков от начала графика, на которое сдвигается график с лэйблами

        // X - axis settings
        val xAxis = chart_mainchart.xAxis
        xAxis.textSize = 12f
        xAxis.setLabelsToSkip(0)//здесь можно пропустить несколько лэйблов из общего числа
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.textColor = Color.rgb(255, 255, 255)
        xAxis.axisLineColor = Color.WHITE


        // Y - axis settings
        val leftAxis = chart_mainchart.axisLeft
        leftAxis.textColor = Color.rgb(255, 255, 255)
        leftAxis.textSize = 12f
        leftAxis.axisLineColor = Color.TRANSPARENT
        leftAxis.setStartAtZero(true)
        leftAxis.spaceTop = 0f
        leftAxis.valueFormatter = YAxisValueFormatter()

        // Y2 - axis settings
        val rightAxis = chart_mainchart.axisRight
        rightAxis.axisLineColor = Color.TRANSPARENT

        // dataSet settings
        dataset.setDrawFilled(true)
        dataset.circleRadius = 3f
        dataset.valueTextSize = 13f
        dataset.valueTextColor = Color.TRANSPARENT
        dataset.enableDashedHighlightLine(10f, 1f, 0f)
        dataset.valueFormatter = DataSetValueFormatter()
    }
    /**
     * YAxis : Water Y-Value Formatter
     */
    private inner class YAxisValueFormatter : com.github.mikephil.charting.formatter.YAxisValueFormatter {
        override fun getFormattedValue(value: Float, yAxis: YAxis): String {
            return value.roundToInt().toString() + " ед"
        }
    }
    /**
     * Water DataSet-Value Formatter
     */
    private inner class DataSetValueFormatter : ValueFormatter {
        override fun getFormattedValue(
            value: Float,
            entry: Entry,
            dataSetIndex: Int,
            viewPortHandler: ViewPortHandler
        ): String {
            return value.roundToInt().toString() + ""
        }
    }
}