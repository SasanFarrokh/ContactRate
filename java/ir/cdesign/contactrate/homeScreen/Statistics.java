package ir.cdesign.contactrate.homeScreen;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.CandleData;
import com.github.mikephil.charting.data.CandleDataSet;
import com.github.mikephil.charting.data.CandleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.R;

public class Statistics extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        PieChart chart = (PieChart) findViewById(R.id.chart);

        List<PieEntry> entries = new ArrayList<PieEntry>();

        entries.add(0,new PieEntry(12,"mamad"));
        entries.add(1,new PieEntry(18,"maafgad"));
        entries.add(2,new PieEntry(16,"maad"));
        entries.add(3,new PieEntry(35,"mad"));
        entries.add(4,new PieEntry(25,"mad"));

        PieDataSet dataSet = new PieDataSet(entries, "Label");
        PieData pieData = new PieData(dataSet);

        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        chart.setData(pieData);
        chart.invalidate();
        //
        BarChart lineChart = (BarChart) findViewById(R.id.lineChart);

        List<BarEntry> barEntries = new ArrayList<BarEntry>();

        barEntries.add(0,new BarEntry(12,21));
        barEntries.add(1,new BarEntry(22,78));
        barEntries.add(2,new BarEntry(62,35));
        barEntries.add(3,new BarEntry(14,45));
        barEntries.add(4,new BarEntry(43,12));
        barEntries.add(5,new BarEntry(26,59));

        BarDataSet barDataSet = new BarDataSet(barEntries,"label");
        BarData lineData = new BarData(barDataSet);

        barDataSet.setColors(ColorTemplate.JOYFUL_COLORS);

        lineChart.setData(lineData);
        lineChart.invalidate();
        //
        LineChart candleStickChart = (LineChart) findViewById(R.id.candle);

        List<Entry> candleEntries  = new ArrayList<Entry>();

        candleEntries.add(0,new Entry(1,20));
        candleEntries.add(1,new Entry(5,26));
        candleEntries.add(2,new Entry(10,30));
        candleEntries.add(3,new Entry(12,50));
        candleEntries.add(4,new Entry(15,10));
        candleEntries.add(5,new Entry(18,20));

        LineDataSet candleDataSet = new LineDataSet(candleEntries,"label");
        LineData candleData = new LineData(candleDataSet);

        candleDataSet.setColors(ColorTemplate.MATERIAL_COLORS);

        candleStickChart.setData(candleData);
        candleStickChart.invalidate();

    }
}
