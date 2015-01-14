package com.asterphoenix.kites.yasmin.cart;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asterphoenix.kites.model.Customer;
import com.asterphoenix.kites.yasmin.R;
import com.asterphoenix.kites.yasmin.api.CustomerAPI;
import com.asterphoenix.kites.yasmin.catalog.CatalogActivity;

public class AddressConfirmationActivity extends Activity {

	long customerID;
	TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addressconfirmation);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		customerID = getIntent().getExtras().getLong("customerID");
		Log.i("xxxxxxxxxxx", ""+customerID);
		tv = (TextView) findViewById(R.id.confirmAddress);
		if (isOnline()) {
			getCustomerData();
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
	}

	public void getCustomerData() {
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatalogActivity.ENDPOINT).build();
		CustomerAPI api = adapter.create(CustomerAPI.class);
		api.getCustomerByID(customerID, new Callback<Customer>() {

			@Override
			public void success(Customer arg0, Response arg1) {
				Log.i("xxxxxxxxxxx", "success");
				tv.setText(arg0.getCustomerAddress());
			}

			@Override
			public void failure(RetrofitError arg0) {
				Log.i("xxxxxxxxxxx", "fail");
			}
		});
	}

	public void onConfirm(View view) {
		if (!tv.getText().toString().trim().equals("")) {
			Intent intent = new Intent(this, ProcessOrderActivity.class);
			intent.putExtra("customerID", customerID);
			intent.putExtra("isDelivery", true);
			intent.putExtra("shippingAddress", tv.getText().toString());
			startActivity(intent);
		} else {
			Toast.makeText(this, "provide an address", Toast.LENGTH_LONG).show();
		}
	}

	protected boolean isOnline() {
		ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnectedOrConnecting()) {
			return true;
		} else {
			return false;
		}
	}

}
