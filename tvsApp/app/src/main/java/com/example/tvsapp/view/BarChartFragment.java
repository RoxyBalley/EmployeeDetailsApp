package com.example.tvsapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.tvsapp.R;
import com.example.tvsapp.model.Userdetail;
import com.example.tvsapp.viewModel.ViewModelTVS;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;

import java.util.ArrayList;
import java.util.Objects;

public class BarChartFragment extends Fragment {


	public View view;
	ViewModelTVS viewModel;

	public BarChartFragment() {
		// Required empty public constructor
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_bar_chart, container, false);

		viewModel =
				ViewModelProviders.of(Objects.requireNonNull(getActivity()))
						.get(ViewModelTVS.class);
		setupBarChart();

		return view;
	}

	public void setupBarChart() {
		BarChart chart = view.findViewById(R.id.barchart);

		XAxis xAxis = chart.getXAxis();
		xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
		xAxis.setDrawGridLines(false);
		xAxis.setGranularity(1f);
		xAxis.setLabelCount(7);

		YAxis leftAxis = chart.getAxisLeft();
		leftAxis.setLabelCount(8, false);
		leftAxis.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);
		leftAxis.setSpaceTop(15f);
		leftAxis.setAxisMinimum(0f); // this replaces setStartAtZero(true)

		YAxis rightAxis = chart.getAxisRight();
		rightAxis.setEnabled(false);

		Legend l = chart.getLegend();
		l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
		l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
		l.setOrientation(Legend.LegendOrientation.HORIZONTAL);
		l.setDrawInside(false);
		l.setForm(Legend.LegendForm.SQUARE);
		l.setFormSize(9f);
		l.setTextSize(11f);
		l.setXEntrySpace(4f);

		ArrayList<BarEntry> values = new ArrayList<>();
		ArrayList<Userdetail> userDetail = viewModel.getUserList();

		for (int i = 0; i < 10; i++) {

			values.add(new BarEntry(i + 1, Integer.valueOf(userDetail.get(i).getSalary()
					.replace("$", "")
					.replace(",", "")
					.replace("\"", ""))));

		}

		BarDataSet set1;
		set1 = new BarDataSet(values, "Salary");

		ArrayList<IBarDataSet> dataSets = new ArrayList<>();
		dataSets.add(set1);

		BarData data = new BarData(dataSets);
		data.setValueTextSize(10f);
		data.setBarWidth(0.9f);

		chart.setData(data);
	}

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}
