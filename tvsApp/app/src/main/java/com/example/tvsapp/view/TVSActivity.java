package com.example.tvsapp.view;

import androidx.fragment.app.FragmentTransaction;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

import android.os.Bundle;

import com.example.tvsapp.ConstantClass;
import com.example.tvsapp.R;
import com.example.tvsapp.viewModel.ViewModelTVS;

public class TVSActivity extends AppCompatActivity {

	private LoginFragment loginFragment;
	private ListFragment listFragment;
	private DetailFragment detailFragment;
	private BarChartFragment barChartFragment;
	private ViewModelTVS viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tvs);
		loginFragment = new LoginFragment();
		listFragment = new ListFragment();
		barChartFragment = new BarChartFragment();
		detailFragment = new DetailFragment();

		instantiateViewModel();

		FragmentTransaction transaction =
				getSupportFragmentManager().beginTransaction();
		transaction.replace(R.id.login_fragment, loginFragment);
		transaction.commit();

	}

	private void instantiateViewModel() {
		viewModel = ViewModelProviders.of(this).get(ViewModelTVS.class);

		viewModel.getFragmentSwitcherLiveData().observe(this, fragmentName -> {
			performFragmentTransaction(fragmentName);
		});
	}

	private void performFragmentTransaction(String fragName) {

		switch (fragName) {

			case ConstantClass.LIST_SCREEN:
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.login_fragment, listFragment, ConstantClass.LIST_SCREEN)
						.addToBackStack(null)
						.commit();
				break;


			case ConstantClass.DETAIL_SCREEN:
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.login_fragment, detailFragment, ConstantClass.DETAIL_SCREEN)
						.addToBackStack(null)
						.commit();
				break;

			case ConstantClass.BARCHART_SCREEN:
				getSupportFragmentManager().beginTransaction()
						.replace(R.id.login_fragment, barChartFragment, ConstantClass.BARCHART_SCREEN)
						.addToBackStack(null)
						.commit();
				break;
		}
	}
}
