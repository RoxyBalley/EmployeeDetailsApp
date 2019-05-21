package com.example.tvsapp.view;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.tvsapp.ConstantClass;
import com.example.tvsapp.R;
import com.example.tvsapp.model.LoginObject;
import com.example.tvsapp.model.Userdetail;
import com.example.tvsapp.retrofit.RetrofitManager;
import com.example.tvsapp.viewModel.ViewModelTVS;

import org.json.JSONObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginFragment extends Fragment implements View.OnClickListener {

	private View view;
	private ViewModelTVS viewModel;
	private EditText loginIdText, passwordText;
	private Button loginButton;
	private String uloginId, upassword;
	private LoginObject loginObject;
	private Context mContext;

	public LoginFragment() {
		// Required empty public constructor
		loginObject = new LoginObject();
	}

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
		view = inflater.inflate(R.layout.fragment_login, container, false);

		initialiseViews();

		signIn();

		return view;
	}

	public void initialiseViews() {
		viewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()))
				.get(ViewModelTVS.class);
		loginIdText = view.findViewById(R.id.loginId);
		passwordText = view.findViewById(R.id.password);
		loginButton = view.findViewById(R.id.signIn);
		loginButton.setOnClickListener(this);
	}

	//sets login id and password credentials to pass in api request
	public void signIn() {
		uloginId = loginIdText.getText().toString();
		upassword = passwordText.getText().toString();

		loginObject.setLoginId(uloginId);
		loginObject.setPassword(upassword);

		loginIdText.setText("");
		passwordText.setText("");

	}

	@Override
	public void onClick(View v) {

		if (v.getId() == R.id.signIn) {

			signIn();

			RetrofitManager retrofitManager = new RetrofitManager();

			//passing login object inside the body
			Call<ResponseBody> responseBodyCall =
					retrofitManager.getUserDetails().postAPI(loginObject);

			responseBodyCall.enqueue(new Callback<ResponseBody>() {

				@Override
				public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
					try {

						//get json response in a string to view
						String json = null;
						if (response.body() != null) {
							json = response.body().string();
							Log.v("response :", json);

							try {
								//make string a json obj to format
								JSONObject obj = new JSONObject(json);
								Log.v("JSON_OBJ ", obj.toString());

								//formatting the json
								String output = obj.toString();
								output = output.substring(output.indexOf("\\\"data\\\":") + 8);
								output = output.replace("\\", "").trim();
								output = output.replace("[", "");
								output = output.replace("]", "}");
								output = output.substring(1, output.lastIndexOf("}"));
								Log.v("output ", output);

								// get individual item from the list of users
								String[] parts = output.split("\\},");

								//to get arrayList of user list
								ArrayList<Userdetail> userList = new ArrayList<>();

								for (int i = 0; i < parts.length; i++) {

									//get all details of individual item
									String temp = parts[i].substring(1, parts[i].lastIndexOf("\""));

									String[] newPart = temp.split("\",\"");

									//to store details in an object
									Userdetail userdetail = new Userdetail();

									for (int j = 0; j < newPart.length; j++) {

										switch (j) {

											case 0:
												userdetail.setName(newPart[j]);
												break;

											case 1:
												userdetail.setRole(newPart[j]);
												break;

											case 2:
												userdetail.setLocation(newPart[j]);
												break;

											case 3:
												userdetail.setId(newPart[j]);
												break;

											case 4:
												userdetail.setdate(newPart[j]);
												break;

											case 5:
												userdetail.setSalary(newPart[j]);
												break;

										}
									}

									//add objects for each item to list
									userList.add(userdetail);
								}

								//set the list in viewmodel to fetch in list fragment
								viewModel.setUserList(userList);

								startListFragment();

							} catch (Exception e) {
								e.printStackTrace();
							}
						}else{

							Toast.makeText(mContext,"Incorrect login credentials",
									Toast.LENGTH_LONG).show();

						}
					} catch (IOException e) {
						e.printStackTrace();
					}
				}

				@Override
				public void onFailure(Call<ResponseBody> call, Throwable t) {
				}
			});

		}
	}

	//send constant tag to viewmodel to identify which fragment to show
	public void startListFragment() {
		viewModel.gotoFragment(ConstantClass.LIST_SCREEN);
	}

	@Override
	public void onDetach() {
		super.onDetach();
	}

}
