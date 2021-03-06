package ir.cdesign.contactrate.homeScreen;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LegendEntry;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
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
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendar;
import ir.cdesign.contactrate.persianmaterialdatetimepicker.utils.PersianCalendarConstants;
import pl.bclogic.pulsator4droid.library.PulsatorLayout;

/**
 * Created by amin pc on 31/08/2016.
 */
public class GraphPage extends Fragment {

    private LineChart chart;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_graph, container, false);

        chart = (LineChart) view.findViewById(R.id.chart);
        chart.setTouchEnabled(false);

        setChartConfig();

        setChartAliases();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setChartData();
        chart.invalidate();
    }

    private void setChartAliases() {
        HashMap<Float, String> weekMap = new HashMap<>();
        weekMap.put(1f, "Sat");
        weekMap.put(2f, "Sun");
        weekMap.put(3f, "Mon");
        weekMap.put(4f, "Tue");
        weekMap.put(5f, "Wed");
        weekMap.put(6f, "Thu");
        weekMap.put(7f, "Fri");
        chart.setLabelAliases(weekMap);
    }

    private void setChartData() {
        List<Entry> entries = new ArrayList<>();

        List<HashMap> invites = DatabaseCommands.getInstance(getContext()).getInvite(0,0);

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

        chart.setData(lineData, entries);
    }

    private void setChartConfig() {
        Description desc = new Description();
        desc.setText("Tasks Done per day");
        desc.setTextColor(Color.WHITE);
        desc.setTextSize(10);
        desc.setYOffset(8);
        desc.setXOffset(8);
        chart.setDescription(desc);
        chart.setExtraLeftOffset(5);
        chart.setExtraRightOffset(5);
        chart.setDrawGridBackground(false);
        chart.getLegend().setCustom(new LegendEntry[]{});
        chart.getXAxis().setTextColor(Color.WHITE);
        chart.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        chart.getAxis(YAxis.AxisDependency.RIGHT).setTextColor(Color.WHITE);
        chart.getAxis(YAxis.AxisDependency.LEFT).setTextColor(Color.WHITE);
        chart.getAxis(YAxis.AxisDependency.LEFT).setAxisMinimum(0);
        chart.getAxis(YAxis.AxisDependency.RIGHT).setAxisMinimum(0);
        chart.getXAxis().setAxisMaximum(7.5f);
        chart.getXAxis().setAxisMinimum(0.5f);
    }
}
