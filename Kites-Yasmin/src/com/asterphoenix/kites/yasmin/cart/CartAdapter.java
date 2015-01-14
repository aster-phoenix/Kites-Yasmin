package com.asterphoenix.kites.yasmin.cart;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asterphoenix.kites.model.Order;
import com.asterphoenix.kites.model.OrderItem;
import com.asterphoenix.kites.yasmin.R;

public class CartAdapter extends ArrayAdapter<OrderItem> {
	
	private Context context;
	private List<OrderItem> orderItems;
	private Order order;
	private TextView totalLabel;
	
	public CartAdapter(Context context, int resource, List<OrderItem> objects) {
		super(context, resource, objects);
		this.context = context;
		this.orderItems = objects;
	}
	
	
	public void setOrder(Order order) {
		this.order = order;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater inflater = 
				(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_cart, parent, false);
		
		OrderItem oi = orderItems.get(position);
		TextView tv = (TextView) view.findViewById(R.id.cartProductName);
		tv.setText(oi.getProductName());
		tv = (TextView) view.findViewById(R.id.cartProductQTY);
		tv.setText(String.valueOf(oi.getQty()));
		tv = (TextView) view.findViewById(R.id.cartProductPrice);
		tv.setText(String.valueOf(oi.getQty() * oi.getProductPrice()));
		if (oi.getImageBytes() != null) {
			try {
				ImageView iv = (ImageView) view.findViewById(R.id.cartProductImage);
				byte[] bytes = Base64.decode(oi.getImageBytes(), Base64.DEFAULT);
				iv.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0, bytes.length));
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
				Log.i("xxxxxxxxxxx", e.getMessage());
			}
		}
		
		order = calculateTotal(order);
		totalLabel.setText("" + order.getTotalPrice());
		
		Button b = (Button) view.findViewById(R.id.cartPlusBtn);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				View view = (View) arg0.getParent();
				TextView tv = (TextView) view.findViewById(R.id.cartProductQTY);
				float q = Float.parseFloat(tv.getText().toString());
				q++;
				tv.setText(String.valueOf(q));
				tv = (TextView) view.findViewById(R.id.cartProductPrice);
				float p = Float.parseFloat(tv.getText().toString());
				p = (p/(q-1))*q;
				tv.setText(String.valueOf(p));
				tv = (TextView) view.findViewById(R.id.cartProductName);
				for (OrderItem oi : orderItems) {
					if (oi.getProductName().equals(tv.getText().toString())) {
						oi.setQty(q);
						oi.setProductPrice(p);
					}
				}
				order.setOrders(orderItems);
				order = calculateTotal(order);
				totalLabel.setText("" + order.getTotalPrice());
				writeOrderFile(order);
			}
		});
		
		b = (Button) view.findViewById(R.id.cartMinusBtn);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				View view = (View) arg0.getParent();
				TextView tv = (TextView) view.findViewById(R.id.cartProductQTY);
				float q = Float.valueOf(tv.getText().toString());
				if (q != 1) {
					q--;
					tv.setText(String.valueOf(q));
					tv = (TextView) view.findViewById(R.id.cartProductPrice);
					float p = Float.parseFloat(tv.getText().toString());
					p = (p/(q+1))*q;
					tv.setText(String.valueOf(p));
					tv = (TextView) view.findViewById(R.id.cartProductName);
					for (OrderItem oi : orderItems) {
						if (oi.getProductName().equals(tv.getText().toString())) {
							oi.setQty(q);
							oi.setProductPrice(p);
						}
					}
					order.setOrders(orderItems);
					order = calculateTotal(order);
					totalLabel.setText("" + order.getTotalPrice());
					writeOrderFile(order);
				}
			}
		});
		
		b = (Button) view.findViewById(R.id.CartRemoveBtn);
		b.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {
				View view = (View) arg0.getParent();
				TextView tv = (TextView) view.findViewById(R.id.cartProductName);
				for (OrderItem oi : orderItems) {
					if (oi.getProductName().equals(tv.getText().toString())) {
						orderItems.remove(oi);
						Toast.makeText(context, "Item removed from cart", Toast.LENGTH_LONG).show();
					}
				}
				order.setOrders(orderItems);
				order = calculateTotal(order);
				totalLabel.setText("" + order.getTotalPrice());
				writeOrderFile(order);
				notifyDataSetChanged();
			}
		});
		
		return view;
	}
	
	public void writeOrderFile(Order order) {
		try {
			context.deleteFile("order_file");
			FileOutputStream fos = context.openFileOutput("order_file", Context.MODE_PRIVATE);
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(order);
			oos.close();
			fos.close();
		} catch (Exception e) {
		}
	}
	
	public Order calculateTotal(Order order) {
		float total = 0;
		for (OrderItem i : order.getOrders()) {
			total += (i.getProductPrice());
		}
		order.setTotalPrice(total);
		return order;
	}
	
	public void setTotalLabel(TextView tv) {
		totalLabel = tv;
	}

}
