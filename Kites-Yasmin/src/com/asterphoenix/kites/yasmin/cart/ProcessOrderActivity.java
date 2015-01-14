package com.asterphoenix.kites.yasmin.cart;

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

import com.asterphoenix.kites.model.Order;
import com.asterphoenix.kites.model.Order.OrderStatus;
import com.asterphoenix.kites.model.Order.OrderType;
import com.asterphoenix.kites.model.OrderItem;
import com.asterphoenix.kites.yasmin.MainActivity;
import com.asterphoenix.kites.yasmin.R;
import com.asterphoenix.kites.yasmin.api.CartAPI;
import com.asterphoenix.kites.yasmin.api.CryptoAPI;
import com.asterphoenix.kites.yasmin.catalog.CatalogActivity;
import com.asterphoenix.kites.yasmin.helper.CryptoHelper;
import com.asterphoenix.kites.yasmin.helper.EncryptedOrderData;
import com.asterphoenix.kites.yasmin.helper.EncryptedOrderItem;
import com.asterphoenix.kites.yasmin.helper.HandShakeData;

public class ProcessOrderActivity extends Activity {

	private long customerID;
	private boolean isDelivery;
	private String shippingAddress;
	private Order order;
	private CryptoHelper crypto;
	private EncryptedOrderData eOrder;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_processorder);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		customerID = getIntent().getExtras().getLong("customerID");
		isDelivery = getIntent().getExtras().getBoolean("isDelivery");
		if (isDelivery) {
			shippingAddress = getIntent().getExtras().getString("shippingAddress");
		}
		Log.i("zzzzzzzzzzzzz", "got extras");
		cryptoHandshake();
		//		sendOrder();
	}

	public void onHome(View view) {
		Intent intent = new Intent(this, MainActivity.class);
		startActivity(intent);
	}

	public void onExit(View view) {
		finish();
		System.exit(0);
	}

	public void sendOrder() {
		if (isOnline()) {
			RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatalogActivity.ENDPOINT).build();
			CartAPI api = adapter.create(CartAPI.class);
			api.addOrder(eOrder, new Callback<String>() {
				
				@Override
				public void success(String arg0, Response arg1) {
					TextView tv = (TextView) findViewById(R.id.orderNumber);
					tv.setText(arg0);
					order.setOrderStatus(OrderStatus.Pendding);
					writeOrderFile(order);
				}
				
				@Override
				public void failure(RetrofitError arg0) {
					Toast.makeText(ProcessOrderActivity.this, "Network error", Toast.LENGTH_LONG).show();
				}
			});
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
	}

	public void cryptoHandshake() {
		crypto = CryptoHelper.getInstance();
		HandShakeData data = new HandShakeData();
		data.setClientPublicKey(crypto.getPublicAsString());
		data.setIv("dump");
		data.setRemotePublicKey("dump");
		data.setSecretKey("dump");
		readOrderFile();
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatalogActivity.ENDPOINT).build();
		CryptoAPI api = adapter.create(CryptoAPI.class);
		api.handShake(data, new Callback<HandShakeData>() {
			
			@Override
			public void success(HandShakeData arg0, Response arg1) {
				crypto.setIvFromString(arg0.getIv());
				crypto.setPublicKeyFromString(arg0.getRemotePublicKey());
				crypto.setSecretKeyFromString(arg0.getSecretKey());
				Log.i("zzzzzzzzzzzzz", "done handshake");
				encryptOrder();
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				Log.i("zzzzzzzzzzzzz", "f");
				Log.i("zzzzzzzzzzzzz", arg0.getMessage());
			}
		});
	}

	public void encryptOrder() {
		eOrder = new EncryptedOrderData();
		eOrder.setCustomerID(crypto.encodeAES(String.valueOf(order.getCustomerID())));
		eOrder.setTotalPrice(crypto.encodeAES(String.valueOf(order.getTotalPrice())));
		eOrder.setOrderType(crypto.encodeAES(order.getOrderType().toString()));
		eOrder.setShippingAddress(crypto.encodeAES(order.getShippingAddress()));
		List<EncryptedOrderItem> list = new ArrayList<EncryptedOrderItem>();
		for (OrderItem i : order.getOrders()) {
			EncryptedOrderItem ei = new EncryptedOrderItem();
			ei.setProductID(crypto.encodeAES(String.valueOf(i.getProductID())));
			ei.setQty(crypto.encodeAES(String.valueOf(i.getQty())));
			ei.setProductName(crypto.encodeAES(i.getProductName()));
			list.add(ei);
		}
		eOrder.setOrders(list);
		Log.i("zzzzzzzzzzzzz", "done encrypting");
		sendOrder();
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
		if (isDelivery) {
			order.setOrderType(OrderType.Delivery);
			order.setShippingAddress(shippingAddress);
		} else {
			order.setOrderType(OrderType.Pickup);
			order.setShippingAddress("No-Shipping");
		}
		order.setCustomerID(customerID);
	}
	
	public void writeOrderFile(Order order) {
		try {
			this.deleteFile("order_file");
			FileOutputStream fos = this.openFileOutput("order_file", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(order);
			oos.close();
			fos.close();
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
