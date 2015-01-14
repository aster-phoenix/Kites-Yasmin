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
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.asterphoenix.kites.model.Customer;
import com.asterphoenix.kites.yasmin.R;
import com.asterphoenix.kites.yasmin.api.CustomerAPI;
import com.asterphoenix.kites.yasmin.catalog.CatalogActivity;
import com.asterphoenix.roxy.RoxyDigest;

public class LoginActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void login(View v) {
		if (isOnline()) {
			extractCustomerData();
			Customer customer = validateCustomerData();
			if (null != customer) {
				sendData(customer);
			}
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
	}
	
	public void extractCustomerData() {
		EditText et = (EditText) findViewById(R.id.loginUsername);
		username = et.getText().toString().trim();
		et = (EditText) findViewById(R.id.loginPassword);
		password = et.getText().toString().trim();
	}
	
	public Customer validateCustomerData() {
		if (username.isEmpty()) {
			Toast.makeText(this, "Please enter a username", Toast.LENGTH_LONG).show();
			return null;
		}
		if (password.isEmpty()) {
			Toast.makeText(this, "Please enter a password", Toast.LENGTH_LONG).show();
			return null;
		}
		Customer customer = new Customer();
		customer.setCustomerUsername(username);
		customer.setCustomerPassword(hashPassword(password));
		return customer;
	}
	
	public String hashPassword(String password) {
		RoxyDigest digest = new RoxyDigest();
		return new String(digest.digestWithSHA256(password.getBytes()));
	}
	
	public void sendData(Customer customer) {
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatalogActivity.ENDPOINT).build();
		CustomerAPI api = adapter.create(CustomerAPI.class);
		api.logCustomerIn(customer, new Callback<Customer>() {
			
			@Override
			public void success(Customer arg0, Response arg1) {
				Toast.makeText(LoginActivity.this, "Welcome " + arg0.getCustomerFName(), Toast.LENGTH_LONG).show();
				Intent intent = new Intent(LoginActivity.this, PaymentActivity.class);
				intent.putExtra("customerID", arg0.getCustomerID());
				
				startActivity(intent);
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(LoginActivity.this, "Wrong user name or password, please try again", Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public void createAccount(View v) {
		Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
		startActivity(intent);
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
	
	private String username;
	private String password;

}
