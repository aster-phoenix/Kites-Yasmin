package com.asterphoenix.kites.yasmin.cart;

import java.io.FileInputStream;
import java.io.ObjectInputStream;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ListActivity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.asterphoenix.kites.model.Order;
import com.asterphoenix.kites.yasmin.R;
import com.asterphoenix.kites.yasmin.api.CartAPI;
import com.asterphoenix.kites.yasmin.catalog.CatalogActivity;

public class CartActivity extends ListActivity {
	
	Order order;
//	List<Product> productList = new ArrayList<>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		
		if (isOnline()) {
			readOrderFile();
			updateDisplay();
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_exit) {
			return true;
		}
		return false;
	}
	
	protected void checkout(View view) {
		//TODO
	}
	
	protected void sendData() {

		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatalogActivity.ENDPOINT).build();
		CartAPI api = adapter.create(CartAPI.class);
		api.addOrder(order, new Callback<String>() {
			
			@Override
			public void success(String arg0, Response arg1) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void failure(RetrofitError arg0) {
			}
		});

	}
	
	protected void updateDisplay() {
		CartAdapter adapter = new CartAdapter(this, R.layout.item_cart, order.getOrders());
		adapter.setOrder(order);
		setListAdapter(adapter);
	}
	
	protected void readOrderFile() {
		try {
			FileInputStream fileIn = this.openFileInput("order_file");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			order = (Order) objectIn.readObject();
			objectIn.close();
			fileIn.close();
		} catch (Exception e) {
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
