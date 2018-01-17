package com.example.administrator.candlestickcharttest225;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

import java.text.DecimalFormat;

/**
 * @Author zhangxin
 * @date 2017/3/25 15:13
 * @description 自定义表格数据格式化
 **/
public class CustomYAxisValueFormatter2 implements YAxisValueFormatter {


    protected DecimalFormat mFormat;

    public CustomYAxisValueFormatter2() {
        mFormat = new DecimalFormat("0.000000");
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        return String.format("%s", mFormat.format(value));
    }
}
