
package com.example.administrator.candlestickcharttest225;

import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.WindowManager;

import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.components.YAxis.AxisDependency;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;

import java.util.ArrayList;

public class CandleStickChartActivity extends DemoBase {

    private CandleStickChart mChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_candlechart);

        mChart = (CandleStickChart) findViewById(R.id.chart1);

        initChart();

        setData();

    }

    private void initChart() {

        initOld();

        mChart.setBackgroundColor(Color.WHITE);

        mChart.setDescription("");

        // 一张表如果超过60个数据，将会不会显示任何数据
        mChart.setMaxVisibleValueCount(60);

        // 缩放目前只支持X轴和Y轴，是否可以双指缩放
        mChart.setPinchZoom(true);

        mChart.setDrawGridBackground(false);

        XAxis xAxis = mChart.getXAxis();
        xAxis.setPosition(XAxisPosition.BOTTOM);
        xAxis.setSpaceBetweenLabels(2);
        xAxis.setDrawGridLines(true);

        YAxis leftAxis = mChart.getAxisLeft();
        leftAxis.setEnabled(false);
//        leftAxis.setLabelCount(7, false);
//        leftAxis.setDrawGridLines(false);
//        leftAxis.setDrawAxisLine(false);

        YAxis rightAxis = mChart.getAxisRight();
        rightAxis.setEnabled(true);
        rightAxis.setValueFormatter(new CandleYAxisValueFormatter());// 定义y轴的小数位
//        rightAxis.setLabelCount(5, true);// true 强制右边数据的数量
        rightAxis.setDrawGridLines(true);// 是否绘制表格的网格背景
        rightAxis.setDrawAxisLine(true);// 是否绘制y轴的线
        rightAxis.setStartAtZero(true);//y轴的起点是否为0，右边无效？

        // 不显示图例
        mChart.getLegend().setEnabled(false);

        //
//        mChart.zoom();
    }

    private void initOld() {
        // 没有数据的时候，显示“暂无数据”
        mChart.setNoDataText("no data");
        // 可拖曳
        mChart.setDragEnabled(true);
        // 不显示表格颜色
//        mChart.setDrawGridBackground(false);
//        mChart.setBackgroundColor(Color.parseColor("#ffffff"));
//        mChart.setDrawMarkerViews(true);
        mChart.setTouchEnabled(true); // 设置是否可以触摸，不可触摸没有辅助线，但是不可触摸就不可以缩放
        // 可以缩放
        mChart.setScaleEnabled(true);


        // 向左偏移15dp，抵消y轴向右偏移的30dp
//        mChart.setExtraLeftOffset(-10);
        XAxis xAxis = mChart.getXAxis();
        // true 第一个和最后一个不会绘制在表格边缘
        xAxis.setAvoidFirstLastClipping(true);
        // 设置x轴数据的位置
        xAxis.setTextColor(Color.parseColor("#ff0000"));
        xAxis.setTextSize(10);
        xAxis.setGridColor(Color.parseColor("#d8d8d8"));
        // 设置x轴数据偏移量
//        xAxis.setYOffset(-12);
    }

    private void setData() {

        mChart.resetTracking();

        ArrayList<CandleEntry> yVals1 = new ArrayList<CandleEntry>();

        for (int i = 0; i < 20; i++) {
            float val = (float) (Math.random() * 40) + 10;

            float high = (float) (Math.random() * 9) + 8f;
            float low = (float) (Math.random() * 9) + 8f;

            float open = (float) (Math.random() * 6) + 1f;
            float close = (float) (Math.random() * 6) + 1f;

            boolean even = i % 2 == 0;

            /**
             * @param x The value on the x-axis（值）30
             * @param shadowH The (shadow) high value（开盘成交最高价格）（蜡烛上面竖线的最低高的值）35
             * @param shadowL The (shadow) low value（开盘成交最低价格）（蜡烛下面竖线的最低点的值）20
             * @param open（开盘价格）（蜡烛顶点的值）32
             * @param close（收盘价格）（蜡烛底点的值）23
             * @param icon Icon image
             */
            yVals1.add(new CandleEntry(i, val + high, val - low, even ? val + open : val - open,
                    even ? val - close : val + close));
        }

        ArrayList<String> xVals = new ArrayList<String>();
        for (int i = 0; i < 20; i++) {
            xVals.add("haha" + i);
        }

        // TODO: 2018/1/13 这个缩放等级会影响到x轴显示的标签的数量
        if (xVals.size() > 5) {
            Matrix matrix = new Matrix();
            //x轴缩放1.5倍
            matrix.postScale(2.5f, 1f);
            //在图表动画显示之前进行缩放
            mChart.getViewPortHandler().refresh(matrix, mChart, false);
            //x轴执行动画
            mChart.animateX(1500);
            mChart.moveViewToX(xVals.size());
        }

        CandleDataSet set1 = new CandleDataSet(yVals1, "Data Set");
        set1.setHighlightEnabled(false);//不显示高亮线(点击的定位线)
        set1.setAxisDependency(AxisDependency.LEFT);
//        set1.setColor(Color.rgb(80, 80, 80));
        set1.setShadowColor(Color.parseColor("#ff0000")); // 蜡烛上下竖线的颜色
        set1.setShadowWidth(0.7f); // 蜡烛上下竖线的宽度
        set1.setDecreasingColor(Color.parseColor("#5500ff00")); // 下降的颜色 open >= close.
        set1.setDecreasingPaintStyle(Paint.Style.FILL); // 下降画实心的蜡烛
        set1.setIncreasingColor(Color.parseColor("#550000ff")); // 上升的颜色 open <= close.
        set1.setIncreasingPaintStyle(Paint.Style.FILL); // 上升画实心的蜡烛
        set1.setNeutralColor(Color.BLUE); // open == close. 的颜色
//        set1.setHighlightLineWidth(1f);  // 什么叫高亮线(点击的定位线)宽度

        CandleData data = new CandleData(xVals, set1);

        mChart.setData(data);
        mChart.invalidate();
    }


}
