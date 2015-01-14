package com.asterphoenix.kites.yasmin.catalog;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.asterphoenix.kites.model.Order;
import com.asterphoenix.kites.model.Order.OrderStatus;
import com.asterphoenix.kites.model.OrderItem;
import com.asterphoenix.kites.model.Product;
import com.asterphoenix.kites.yasmin.R;
import com.asterphoenix.kites.yasmin.api.CatalogAPI;
import com.asterphoenix.kites.yasmin.cart.CartActivity;

public class ProductDetailActivity extends Activity {
	
	private long productID;
	private Product product;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_productdetail);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		productID = getIntent().getExtras().getLong("productID");
		
		if (isOnline()) {
			requestData();
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
		SearchView searchView = (SearchView) menu.findItem(R.id.search).getActionView();
		searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getItemId() == R.id.action_cart) {
		}
		if (item.getItemId() == R.id.action_cart) {
			Intent intent = new Intent(ProductDetailActivity.this, CartActivity.class);
			startActivity(intent);
			return true;
		}
		return false;
	}
	
	protected void requestData() {

		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatalogActivity.ENDPOINT).build();
		CatalogAPI api = adapter.create(CatalogAPI.class);
		api.getProductsByID(String.valueOf(productID), new Callback<Product>() {
			
			@Override
			public void success(Product arg0, Response arg1) {
				ProductDetailActivity.this.product = arg0;
				updateDisplay();
			}

			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(ProductDetailActivity.this, "Network error" + arg0.getMessage(), Toast.LENGTH_LONG).show();				
			}
		});

	}
	
	private void updateDisplay() {
		TextView tv = (TextView) findViewById(R.id.detailProductName);
		tv.setText(product.getProductName());
		tv = (TextView) findViewById(R.id.detailProductDesc);
		tv.setText(product.getProductDescription());
		tv = (TextView) findViewById(R.id.detailProductPrice);
		tv.setText(String.valueOf(product.getProductPrice()) + " $");
		tv = (TextView) findViewById(R.id.detailProductBrand);
		tv.setText(product.getProductBrand());
		tv = (TextView) findViewById(R.id.detailTotalPrice);
		tv.setText(String.valueOf(product.getProductPrice()) + " $");
		if (product.getImageBytes() != null) {
			try {
				ImageView iv = (ImageView) findViewById(R.id.detailProductImage);
				byte[] bytes = Base64.decode(product.getImageBytes(), Base64.DEFAULT);
				iv.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0, bytes.length));
			} catch (Exception e) {
				Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
			}
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
	
	@SuppressLint("NewApi")
	public void addToCart(View v) {
		Order order = readOrder();
		OrderItem orderItem = new OrderItem();
		TextView tv = (TextView) findViewById(R.id.detailProductQTY);
		orderItem.setProductID(product.getProductID());
		orderItem.setQty(Float.parseFloat(tv.getText().toString()));
		orderItem.setProductName(product.getProductName());
		orderItem.setProductPrice(product.getProductPrice());
		orderItem.setImageBytes(product.getImageBytes());
		order.setOrderStatus(OrderStatus.New);
		if (null == order.getOrders()) {
			List<OrderItem> list = new ArrayList<>();
			list.add(orderItem);
			order.setOrders(list);
		} else {
			boolean exist = false;
			for (OrderItem i : order.getOrders()) {
				if (i.getProductID() == orderItem.getProductID()) {
					i.setQty(i.getQty() + orderItem.getQty());
					exist = true;
				}
			}
			if (!exist) {
				order.getOrders().add(orderItem);
			}
		}
		float totalPrice = order.getTotalPrice() + (orderItem.getQty() * product.getProductPrice());
		order.setTotalPrice(totalPrice);
		if (writeOrder(order)) {
			Toast.makeText(this, "Item added to cart", Toast.LENGTH_LONG).show();
			this.navigateUpTo(this.getParentActivityIntent());
		}
	}
	
	public Order readOrder() {
		Order order;
		try {
			FileInputStream fileIn = this.openFileInput("order_file");
			ObjectInputStream objectIn = new ObjectInputStream(fileIn);
			order = (Order) objectIn.readObject();
			objectIn.close();
			fileIn.close();
			if (!order.getOrderStatus().equals(OrderStatus.New)) {
				this.deleteFile("order_file");
				order = new Order();
			}
			return order;				
		} catch (Exception e) {
			return new Order();
		}
	}
	
	public Boolean writeOrder(Order order) {
		
		try {
			this.deleteFile("order_file");
			FileOutputStream fos = this.openFileOutput("order_file", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(order);
			oos.close();
			fos.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public void QTYMinus(View v) {
		TextView tv = (TextView) findViewById(R.id.detailProductQTY);
		float qty = Float.parseFloat(tv.getText().toString());
		if (qty != 1) {
			tv.setText("" + --qty);			
			float total = product.getProductPrice() * qty;
			tv = (TextView) findViewById(R.id.detailTotalPrice);
			tv.setText("" + total + " $");
		}
	}
	
	public void QTYPlus(View v) {
		TextView tv = (TextView) findViewById(R.id.detailProductQTY);
		float qty = Float.parseFloat(tv.getText().toString());
		if (qty < (product.getProductQTY() * 0.9)) {
			tv.setText("" + ++qty);
			float total = product.getProductPrice() * qty;
			tv = (TextView) findViewById(R.id.detailTotalPrice);
			tv.setText("" + total + " $");
		} else {
			Toast.makeText(this, "Reached maximum quantity", Toast.LENGTH_SHORT).show();
		}
	}

}
