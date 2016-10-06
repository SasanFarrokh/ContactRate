package ir.cdesign.contactrate.homeScreen;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.CandleStickChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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
import com.github.mikephil.charting.formatter.LargeValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import ir.cdesign.contactrate.DatabaseCommands;
import ir.cdesign.contactrate.R;
import ir.cdesign.contactrate.models.TaskModel;
import ir.cdesign.contactrate.utilities.WallpaperBoy;

public class Statistics extends AppCompatActivity {

    PieChart pieChart;
    LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);

        pieChart = (PieChart) findViewById(R.id.pie_chart);
        lineChart = (LineChart) findViewById(R.id.line_chart);

        WallpaperBoy wallpaperBoy = new WallpaperBoy();
        pieChart.getRootView().setBackgroundResource(wallpaperBoy.manSitting(HomeScreen.manInTheMiddle,this));

        setPieChart();
        setLineChart();

    }

    private void setLineChart() {
        setLineChartAliases();
//        setLineChartConfig();
        setLineChartData();
        lineChart.invalidate();
    }


    private void setPieChart() {
        List<PieEntry> entries = new ArrayList<PieEntry>();

        int[] data = DatabaseCommands.getInstance(this).getTaskTypeCounts();

        for (int i = 0; i < 9 ; i++) {
            entries.add(i, new PieEntry(data[i] * 10, TaskModel.getTitles()[i]));
        }


        PieDataSet dataSet = new PieDataSet(entries, "Label");
        PieData pieData = new PieData(dataSet);

        dataSet.setColors(ColorTemplate.PASTEL_COLORS);

        pieChart.setRotationEnabled(false);
        pieChart.setData(pieData);
        pieChart.invalidate();
    }

    private void setLineChartAliases() {
        HashMap<Float, String> weekMap = new HashMap<>();
        weekMap.put(1f, "Sat");
        weekMap.put(2f, "Sun");
        weekMap.put(3f, "Mon");
        weekMap.put(4f, "Tue");
        weekMap.put(5f, "Wed");
        weekMap.put(6f, "Thu");
        weekMap.put(7f, "Fri");
//        lineChart.setLabelAliases(weekMap);
    }

    private void setLineChartData() {
        List<Entry> entries = new ArrayList<>();

        List<HashMap> invites = DatabaseCommands.getInstance(this).getInvite(0,0);

        DatabaseCommands.getInstance().getInvite(0,0);
        Calendar calendar = Calendar.getInstance();
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        List<Integer> weekActivity = new ArrayList<>();
        for (int i = 0 ; i <= 6; i++) {
            weekActivity.add(i,0);
            if (i > dayOfWeek) continue;
            calendar.set(Calendar.HOUR_OF_DAY,0);
            calendar.set(Calendar.MINUTE,0);
            calendar.set(Calendar.DAY_OF_WEEK,i);
            long time = calendar.getTimeInMillis();

            for (HashMap invite : invites) {
                long taskTime = (long) invite.get("done_time");
                if (taskTime !=0 && taskTime > time && taskTime < time + 86399000L) {
                    weekActivity.set(i,weekActivity.get(i) + 1);
                }
            }
        }


        entries.add(new Entry(1, weekActivity.get(0)));
        entries.add(new Entry(2, weekActivity.get(1)));
        entries.add(new Entry(3, weekActivity.get(2)));
        entries.add(new Entry(4, weekActivity.get(3)));
        entries.add(new Entry(5, weekActivity.get(4)));
        entries.add(new Entry(6, weekActivity.get(5)));
        entries.add(new Entry(7, weekActivity.get(6)));

        LineDataSet dataSet = new LineDataSet(entries, null);
        dataSet.setMode(LineDataSet.Mode.LINEAR);
        dataSet.setValueTextColor(Color.WHITE);

        float tempY = -1;
        int[] colors = new int[6];
        for (int i = 0; i < entries.size(); i++) {
            Entry e = entries.get(i);
            if (i == 0) {
                tempY = e.getY();
                continue;
            }
            int color;
            if (e.getY() >= tempY) {
                color = Color.rgb(207, 248, 246);
            } else {
                color = Color.rgb(118, 174, 175);
            }
            colors[i - 1] = color;
        }
        dataSet.setColors(colors);

        LineData lineData = new LineData(dataSet);
        lineData.setValueFormatter(new LargeValueFormatter());

//        lineChart.setData(lineData, entries);
    }

    /*private void setLineChartConfig() {
        Description desc = new Description();
        desc.setText("Tasks Done per day");
        desc.setTextColor(Color.WHITE);
        desc.setTextSize(10);
        desc.setYOffset(8);
        desc.setXOffset(8);
        lineChart.setDescription(desc);
        lineChart.setExtraLeftOffset(5);
        lineChart.setExtraRightOffset(5);
        lineChart.setDrawGridBackground(false);
        lineChart.getLegend().setCustom(new LegendEntry[]{});
        lineChart.getXAxis().setTextColor(Color.WHITE);
        lineChart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        lineChart.getAxis(YAxis.AxisDependency.RIGHT).setTextColor(Color.WHITE);
        lineChart.getAxis(YAxis.AxisDependency.LEFT).setTextColor(Color.WHITE);
        lineChart.getAxis(YAxis.AxisDependency.LEFT).setAxisMinimum(0);
        lineChart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMinimum(0);
        lineChart.getXAxis().setAxisMaximum(7.5f);
        lineChart.getXAxis().setAxisMinimum(0.5f);
    }*/
}
