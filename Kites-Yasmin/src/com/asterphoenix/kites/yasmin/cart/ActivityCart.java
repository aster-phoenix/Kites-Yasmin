package com.asterphoenix.kites.yasmin.cart;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ListActivity;
import android.content.Context;
import android.database.DataSetObserver;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.asterphoenix.kites.model.Order;
import com.asterphoenix.kites.model.OrderItem;
import com.asterphoenix.kites.model.Order.OrderStatus;
import com.asterphoenix.kites.model.Product;
import com.asterphoenix.kites.yasmin.R;
import com.asterphoenix.kites.yasmin.api.CartAPI;
import com.asterphoenix.kites.yasmin.api.CatalogAPI;
import com.asterphoenix.kites.yasmin.catalog.CatalogActivity;

public class ActivityCart extends ListActivity {
	
	Order order;
	List<Product> productList = new ArrayList<>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		if (isOnline()) {
			readOrderFile();
			requestData();
			updateDisplay();
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
	}
	
	protected void readOrderFile() {
		try {
			FileInputStream fileIn = this.openFileInput("order_file");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			order = (Order) objectIn.readObject();
			objectIn.close();
			fileIn.close();
			if (order.getOrderStatus().equals(OrderStatus.Completed)) {
				this.deleteFile("order_file");
				order =  new Order();
			} 
		} catch (Exception e) {
		}
		
		
	}
	
	protected void requestData() {
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatalogActivity.ENDPOINT).build();
		CatalogAPI api = adapter.create(CatalogAPI.class);
		
		for (OrderItem i : order.getOrders()) {
			api.getProductsByID(String.valueOf(i.getProductID()), new Callback<Product>() {
				
				@Override
				public void success(Product arg0, Response arg1) {
					productList.add(arg0);
				}
				
				@Override
				public void failure(RetrofitError arg0) {
					// TODO Auto-generated method stub
					
				}
			});
		}
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
				// TODO Auto-generated method stub
				
			}
		});

	}
	
	protected void updateDisplay() {
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
