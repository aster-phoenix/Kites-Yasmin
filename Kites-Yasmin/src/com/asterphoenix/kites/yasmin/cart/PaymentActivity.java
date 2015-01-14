package com.asterphoenix.kites.yasmin.cart;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.asterphoenix.kites.yasmin.R;

public class PaymentActivity extends Activity {
	
	long customerID;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_payment);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		customerID = getIntent().getExtras().getLong("customerID");
	}
	
	public void onDelivery(View view) {
		if (isOnline()) {
			Intent intent = new Intent(this, AddressConfirmationActivity.class);
			intent.putExtra("customerID", customerID);
			startActivity(intent);
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
	}
	
	public void onPickup(View view) {
		if (isOnline()) {
			Intent intent = new Intent(this, ProcessOrderActivity.class);
			intent.putExtra("customerID", customerID);
			intent.putExtra("isDelivery", false);
			startActivity(intent);
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
	}
	
	public void onSoon(View view) {
		Toast.makeText(this, "Method not working yet", Toast.LENGTH_LONG).show();
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
