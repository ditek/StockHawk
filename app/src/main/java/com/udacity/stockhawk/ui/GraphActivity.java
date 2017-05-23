package com.udacity.stockhawk.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.utils.EntryXComparator;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.udacity.stockhawk.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import timber.log.Timber;

public class GraphActivity extends AppCompatActivity {
    String mSymbol;
    String mHistory;
    LineChart mGraph;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_graph);
        mGraph = (LineChart) findViewById(R.id.stock_graph);
        mGraph.setTouchEnabled(false);

        Intent intent = getIntent();
        if (intent != null && intent.hasExtra(MainActivity.EXTRA_SYMBOL)) {
            mSymbol = intent.getStringExtra(MainActivity.EXTRA_SYMBOL);
            mHistory = intent.getStringExtra(MainActivity.EXTRA_HISTORY);
        }
        Timber.i("Received" + mSymbol);

        List<Entry> entries = new ArrayList<>();
        List<String> lines = Lists.newArrayList(Splitter.on('\n')
                .omitEmptyStrings()
                .split(mHistory));
        for (String line : lines) {
            List<String> pair = Lists.newArrayList(Splitter.on(", ").split(line));
            entries.add(new Entry(Float.valueOf(pair.get(0)), Float.valueOf(pair.get(1))));
        }

        Collections.sort(entries, new EntryXComparator());
        LineDataSet dataSet = new LineDataSet(entries, "Test Data");
        dataSet.setDrawCircles(false);
        LineData lineData = new LineData(dataSet);
        mGraph.setData(lineData);
        mGraph.invalidate(); // refresh
    }
}
