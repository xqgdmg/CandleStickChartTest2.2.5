package com.example.administrator.candlestickcharttest225;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * 蜡烛图 Y轴数据格式化
 **/
public class CandleYAxisValueFormatter implements YAxisValueFormatter {


    protected DecimalFormat mFormat;

    public CandleYAxisValueFormatter() {
        mFormat = new DecimalFormat("0.00");
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        return String.format("%s", mFormat.format(value));
    }
}
