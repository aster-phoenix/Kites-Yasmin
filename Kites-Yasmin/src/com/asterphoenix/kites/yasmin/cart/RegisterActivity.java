package com.asterphoenix.kites.yasmin.cart;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RestAdapter.LogLevel;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
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

public class RegisterActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		getActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public void createAccount(View v) {
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
		EditText et = (EditText) findViewById(R.id.registerFirstName);
		fname = et.getText().toString().trim();
		et = (EditText) findViewById(R.id.registerLastName);
		lname = et.getText().toString().trim();
		et = (EditText) findViewById(R.id.registerUsername);
		username = et.getText().toString().trim();
		et = (EditText) findViewById(R.id.registerPassword);
		password = et.getText().toString().trim();
		et = (EditText) findViewById(R.id.registerRepeatPassword);
		repeatPassword = et.getText().toString().trim();
		et = (EditText) findViewById(R.id.registerEmail);
		email = et.getText().toString().trim();
		et = (EditText) findViewById(R.id.registerAddress);
		address = et.getText().toString().trim();
	}
	
	public Customer validateCustomerData() {
		if (fname.equals("")) {
			Toast.makeText(this, "Please enter a Firs tname", Toast.LENGTH_LONG).show();
			return null;
		}
		if (lname.equals("")) {
			Toast.makeText(this, "Please enter a Last name", Toast.LENGTH_LONG).show();
			return null;
		}
		if (username.length() < 4) {
			Toast.makeText(this, "Username should be more than 4 charachters", Toast.LENGTH_LONG).show();
			return null;
		}
		if (password.length() < 6) {
			Toast.makeText(this, "Username should be more than 6 charachters", Toast.LENGTH_LONG).show();
			return null;
		}
		if (!password.equals(repeatPassword)) {
			Toast.makeText(this, "Passwords do not match", Toast.LENGTH_LONG).show();
			return null;
		}
		if (email.equals("")) {
			Toast.makeText(this, "Please enter an E-mail tname", Toast.LENGTH_LONG).show();
			return null;
		}
		if (address.equals("")) {
			Toast.makeText(this, "Please enter an address name", Toast.LENGTH_LONG).show();
			return null;
		}
		
		Customer c = new Customer();
		c.setCustomerFName(fname);
		c.setCustomerLName(lname);
		c.setCustomerUsername(username);
		c.setCustomerPassword(hashPassword(password));
		c.setCustomerEmail(email);
		c.setCustomerAddress(address);
		c.setCustomerIsActive(true);
		return c;
	}
	
	public String hashPassword(String password) {
		RoxyDigest digest = new RoxyDigest();
		return new String(digest.digestWithSHA256(password.getBytes()));
	}
	
	@SuppressLint("NewApi")
	public void sendData(Customer customer) {
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatalogActivity.ENDPOINT).setLogLevel(LogLevel.FULL).build();
		CustomerAPI api = adapter.create(CustomerAPI.class);
		api.addCustomer(customer, new Callback<String>() {
			
			@Override
			public void success(String arg0, Response arg1) {
				if (!arg0.isEmpty()) {
					Toast.makeText(RegisterActivity.this, "Account created successfuly", Toast.LENGTH_LONG).show();
					RegisterActivity.this.navigateUpTo(RegisterActivity.this.getParentActivityIntent());
				} else {
					Toast.makeText(RegisterActivity.this, "Please try again", Toast.LENGTH_LONG).show();	
				}
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(RegisterActivity.this, arg0.getMessage(), Toast.LENGTH_LONG).show();
			}
		});
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
	
	private String fname;
	private String lname;
	private String username;
	private String password;
	private String repeatPassword;
	private String email;
	private String address;
}
