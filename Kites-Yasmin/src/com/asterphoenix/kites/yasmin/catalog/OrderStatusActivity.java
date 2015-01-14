package com.asterphoenix.kites.yasmin.catalog;

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
import android.widget.TextView;
import android.widget.Toast;

import com.asterphoenix.kites.yasmin.MainActivity;
import com.asterphoenix.kites.yasmin.R;
import com.asterphoenix.kites.yasmin.api.CartAPI;

public class OrderStatusActivity extends Activity {
	
	EditText et;
	TextView tv;
	TextView tvLabel;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_orderstatus);
		et = (EditText) findViewById(R.id.order_in);
		tv = (TextView) findViewById(R.id.order_out);
		tvLabel = (TextView) findViewById(R.id.order_out_label);
		tvLabel.setVisibility(View.INVISIBLE);
	}
	
	public void checkOrder(View view) {
		if (isOnline()) {
			RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatalogActivity.ENDPOINT).build();
			CartAPI api = adapter.create(CartAPI.class);
			api.checkOrder(Long.valueOf(et.getText().toString()), new Callback<String>() {
				
				@Override
				public void success(String arg0, Response arg1) {
					tvLabel.setVisibility(View.VISIBLE);
					tv.setText(arg0);
				}
				
				@Override
				public void failure(RetrofitError arg0) {
					Toast.makeText(OrderStatusActivity.this, "Network error", Toast.LENGTH_LONG).show();
				}
			});
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
	}
	
	public void goHome(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void goExit(View view) {
		finish();
		System.exit(0);
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
