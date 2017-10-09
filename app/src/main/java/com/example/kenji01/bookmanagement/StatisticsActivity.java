package com.example.kenji01.bookmanagement;



import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;


import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class StatisticsActivity extends Activity {

    PieChart mPieChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        mPieChart = (PieChart) findViewById(R.id.pie_chart);
        setupPieChartView();


        LineChart lineChart = (LineChart)findViewById(R.id.chart1);

        //グラフ用データ
        List<Float> values = Arrays.asList(500f,1000f,875f,2098f,
                6985f,1980f,10000f,7863f,873f,420f,100f,905f);
        List<Entry> entries = new ArrayList<Entry>();
        for (int i = 0; i < 11; i++) {
            entries.add(new Entry(values.get(i), i));
        }


        //データをセット
        LineDataSet dataSet = new LineDataSet(entries,"money");

        //ラベル
        String[] labels = {"1","2","3","4","5","6","7","8","9","10","11","12"};

        //LineDataインスタンス生成
        LineData data = new LineData(labels,dataSet);

        //LineDataをLineChartにセット
        lineChart.setData(data);

        //説明分
        lineChart.setDescription("使用金額");

        //背景色
        lineChart.setBackgroundColor(Color.WHITE);

        //アニメーション
        lineChart.animateY(2000);
    }

    private void setupPieChartView() {
        mPieChart.setUsePercentValues(true);    //円グラフの穴
        mPieChart.setDescription("カテゴリ別");

        // 更新
        mPieChart.invalidate();
        // アニメーション
        mPieChart.animateXY(2000, 2000); // 表示アニメーション

        Legend legend = mPieChart.getLegend();
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);

        List<Float> values = Arrays.asList(40f, 30f, 20f, 10f);
        List<Entry> entries = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            entries.add(new Entry(values.get(i), i));
        }

        PieDataSet dataSet = new PieDataSet(entries, "チャートのラベル");
        dataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        dataSet.setDrawValues(true);

        List<String> labels = Arrays.asList("ジャンプ", "サンデー", "マガジン", "チャンピオン");
        PieData pieData = new PieData(labels, dataSet);
        pieData.setValueFormatter(new PercentFormatter());
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.WHITE);

        mPieChart.setData(pieData);
    }
}

