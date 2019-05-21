package com.example.tvsapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.tvsapp.ConstantClass;
import com.example.tvsapp.ListAdapter;
import com.example.tvsapp.R;
import com.example.tvsapp.RecyclerItemClickListener;
import com.example.tvsapp.model.Userdetail;
import com.example.tvsapp.viewModel.ViewModelTVS;

import java.util.ArrayList;
import java.util.Objects;

public class ListFragment extends Fragment implements View.OnClickListener {

	private View view;
	private ViewModelTVS viewModel;
	private RecyclerView recyclerView;
	private ListAdapter listAdapter;
	private RecyclerView.LayoutManager listLayoutManager;
	private ArrayList<Userdetail> userdetailArrayList;
	ArrayList<Userdetail> temp = new ArrayList<>();
	private EditText searchField;
	private ImageButton chartButton;

	private Context mContext;
	private RecyclerItemClickListener recyclerItemClickListener =
			new RecyclerItemClickListener(mContext, recyclerView,
					new RecyclerItemClickListener.OnItemClickListener() {
						@Override
						public void onItemClick(View view, int position) {
							//save index of current item
							viewModel.setCurrentPos(position);

							//save object of current item
							viewModel.setCurrentUserdetail(userdetailArrayList.get(position));

							//show detail fragment
							viewModel.gotoFragment(ConstantClass.DETAIL_SCREEN);

						}

						@Override
						public void onLongItemClick(View view, int position) {

						}
					});

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public void onStart() {
		super.onStart();
		Log.v("List ","Onstart Size of Temp "+ temp.size());
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_list, container, false);
		viewModel =
				ViewModelProviders.of(Objects.requireNonNull(getActivity()))
						.get(ViewModelTVS.class);

		chartButton = view.findViewById(R.id.btnBarChart);
		chartButton.setOnClickListener(this);

		setupRecyclerView();

		setupSeachView();

		return view;
	}

	public void setupRecyclerView() {

		userdetailArrayList = viewModel.getUserList();

		recyclerView = view.findViewById(R.id.list_view);

		//set layout manager
		listLayoutManager = new LinearLayoutManager(getActivity());
		recyclerView.setLayoutManager(listLayoutManager);

		//set adapter
		listAdapter = new ListAdapter(userdetailArrayList);
		recyclerView.setAdapter(listAdapter);
		recyclerView.addOnItemTouchListener(recyclerItemClickListener);

	}

	private void setupSeachView() {
		searchField = view.findViewById(R.id.search_field);

		searchField.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) {

			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {

			}

			@Override
			public void afterTextChanged(Editable s) {
				filter(s.toString().trim());
			}
		});
	}

	private void filter(String text) {

		temp.clear();

		temp = viewModel.filter(text, userdetailArrayList);

		listAdapter.updateList(temp);

		Log.v(" List ","filter Size of Temp "+ temp.size());
	}

	@Override
	public void onDetach() {
		super.onDetach();

	}


	@Override
	public void onClick(View v) {
		if(v.getId() == R.id.btnBarChart){
			viewModel.gotoFragment(ConstantClass.BARCHART_SCREEN);

		}
	}
}
