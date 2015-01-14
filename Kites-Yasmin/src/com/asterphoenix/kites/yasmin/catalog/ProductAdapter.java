package com.asterphoenix.kites.yasmin.catalog;

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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.asterphoenix.kites.model.Product;
import com.asterphoenix.kites.yasmin.R;

public class ProductAdapter extends ArrayAdapter<Product> {
	
	private Context context;
	private List<Product> productList;
	
	public ProductAdapter(Context context, int resource, List<Product> objects) {
		super(context, resource, objects);
		this.context = context;
		this.productList = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = 
				(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_product, parent, false);

		Product product = productList.get(position);
		TextView tv = (TextView) view.findViewById(R.id.productName);
		tv.setText(product.getProductName());
		tv = (TextView) view.findViewById(R.id.productDesc);
		tv.setText(product.getProductDescription());
		tv = (TextView) view.findViewById(R.id.productPrice);
		tv.setText(String.valueOf(product.getProductPrice()) + " $ ");
		if (product.getImageBytes() != null) {
			try {
				ImageView iv = (ImageView) view.findViewById(R.id.productImage);
				byte[] bytes = Base64.decode(product.getImageBytes(), Base64.DEFAULT);
				iv.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0, bytes.length));
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
				Log.d("Cat", e.getMessage());
			}
		}
		return view;
	}
}
