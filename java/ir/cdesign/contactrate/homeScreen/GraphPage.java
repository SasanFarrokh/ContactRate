package ir.cdesign.contactrate.homeScreen;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

import ir.cdesign.contactrate.R;

/**
 * Created by amin pc on 31/08/2016.
 */
public class GraphPage extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_graph,container,false);

        PieChart chart = (PieChart) view.findViewById(R.id.chart);
        chart.setTouchEnabled(false);

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

        return view;
    }
}
