package com.asterphoenix.kites.yasmin.cart;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.asterphoenix.kites.model.Order;
import com.asterphoenix.kites.model.Order.OrderStatus;
import com.asterphoenix.kites.model.OrderItem;
import com.asterphoenix.kites.yasmin.R;

public class CartActivity extends ListActivity {
	
	Order order;
	TextView totalLabel;
//	List<Product> productList = new ArrayList<>();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cart);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		
		totalLabel = (TextView) findViewById(R.id.cartTotalPrice);
		
		if (isOnline()) {
			readOrderFile();
			updateDisplay();
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
	}
	
	public void checkout(View v) {
		Intent intent = new Intent(CartActivity.this, LoginActivity.class);
		startActivity(intent);
	}
	
	protected void updateDisplay() {
		CartAdapter adapter = new CartAdapter(this, R.layout.item_cart, order.getOrders());
		adapter.setOrder(order);
		adapter.setTotalLabel(totalLabel);
		setListAdapter(adapter);
	}
	
	@SuppressLint("NewApi")
	protected void readOrderFile() {
		try {
			FileInputStream fileIn = this.openFileInput("order_file");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			order = (Order) objectIn.readObject();
			objectIn.close();
			fileIn.close();
		} catch (Exception e) {
			Toast.makeText(this, "Cart is empty", Toast.LENGTH_LONG).show();
			navigateUpTo(getParentActivityIntent());
		}
		if (null == order || order.getOrderStatus() != OrderStatus.New ) {
			order = new Order();
			order.setOrders(new ArrayList<OrderItem>());
			order.setOrderStatus(OrderStatus.New);
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
