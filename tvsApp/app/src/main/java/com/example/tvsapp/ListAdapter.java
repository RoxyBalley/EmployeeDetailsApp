package com.example.tvsapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.tvsapp.model.Userdetail;
import java.util.ArrayList;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListViewHolder> {

	ArrayList<Userdetail> dataSet;
	public ListAdapter(ArrayList<Userdetail> userdetailArrayList ) {
		this.dataSet = userdetailArrayList;
	}

	public static class ListViewHolder extends RecyclerView.ViewHolder {
		TextView name, role, location, id, date, salary;

		public ListViewHolder(@NonNull View itemView) {
			super(itemView);
			name = itemView.findViewById(R.id.u_name);
			role = itemView.findViewById(R.id.u_role);
			location = itemView.findViewById(R.id.u_location);
			id = itemView.findViewById(R.id.u_id);
			date = itemView.findViewById(R.id.u_date);
			salary = itemView.findViewById(R.id.u_salary);
		}
	}

	@NonNull
	@Override
	public ListAdapter.ListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		// create a new view
		LinearLayout layout = (LinearLayout) LayoutInflater.from(parent.getContext())
				.inflate(R.layout.list_item, parent, false);

		ListViewHolder vh = new ListViewHolder(layout);
		return vh;
	}

	@Override
	public void onBindViewHolder(@NonNull ListAdapter.ListViewHolder holder, int position) {
		Userdetail userdetail = dataSet.get(position);
		holder.name.setText(userdetail.getName());
		holder.role.setText(userdetail.getRole());
		holder.location.setText(userdetail.getLocation());
		holder.id.setText(userdetail.getId());
		holder.date.setText(userdetail.getdate());
		holder.salary.setText(userdetail.getSalary());
	}

	@Override
	public int getItemCount() {
		return dataSet.size();
	}

	//show the list items which contain the search keyword
	public void updateList(ArrayList<Userdetail> updatedList){
		dataSet=updatedList;
		notifyDataSetChanged();
	}

}
