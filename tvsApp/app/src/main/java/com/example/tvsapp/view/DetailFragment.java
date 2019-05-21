package com.example.tvsapp.view;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.tvsapp.R;
import com.example.tvsapp.model.Userdetail;
import com.example.tvsapp.viewModel.ViewModelTVS;

import java.util.ArrayList;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;

public class DetailFragment extends Fragment implements View.OnClickListener {

	private static final int CAMERA_REQUEST = 1888;
	private TextView name, role, location, id, date, salary;
	private ImageButton cameraButton;
	private ImageView imageView;
	private View view;
	private Context mContext;
	private ViewModelTVS viewmodel;

	@Override
	public void onAttach(Context context) {
		super.onAttach(context);
		mContext = context;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
	                         Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		view = inflater.inflate(R.layout.fragment_detail, container, false);

		initialise();

		populateView();

		return view;
	}

	public void initialise() {
		viewmodel = ViewModelProviders.of(Objects.requireNonNull(getActivity())).get(ViewModelTVS.class);

		name = view.findViewById(R.id.u_name);
		role = view.findViewById(R.id.u_role);
		location = view.findViewById(R.id.u_location);
		id = view.findViewById(R.id.u_id);
		date = view.findViewById(R.id.u_date);
		salary = view.findViewById(R.id.u_salary);
		cameraButton = view.findViewById(R.id.camera_button);
		imageView = view.findViewById(R.id.captured_photo);

	}

	public void populateView() {
		Userdetail userdetail = viewmodel.getCurrentUserdetail();
		name.setText(userdetail.getName());
		role.setText(userdetail.getRole());
		location.setText(userdetail.getLocation());
		id.setText(userdetail.getId());
		date.setText(userdetail.getdate());
		salary.setText(userdetail.getSalary());

		cameraButton.setOnClickListener(this);

		//save image bitmap in model to keep it for the existing session.
		if (userdetail.getBitmap() != null) {
			imageView.setImageBitmap(userdetail.getBitmap());
		}

	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.camera_button) {
			//send intent to launch system camera
			Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
			startActivityForResult(cameraIntent, CAMERA_REQUEST);
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (requestCode == CAMERA_REQUEST && resultCode == RESULT_OK) {
			Bitmap mphoto = (Bitmap) data.getExtras().get("data");
			imageView.setImageBitmap(mphoto);

			//save photo bitmap to model
			viewmodel.updateImage(mphoto);
		}
	}

}
