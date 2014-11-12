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

import com.asterphoenix.kites.model.Category;
import com.asterphoenix.kites.yasmin.R;

public class CatalogAdapter extends ArrayAdapter<Category> {
	
	private Context context;
	private List<Category> categoryList;
	
//	private LruCache<Integer, Bitmap> imageCache;

	public CatalogAdapter(Context context, int resource, List<Category> objects) {
		super(context, resource, objects);
		this.context = context;
		this.categoryList = objects;
		
//		final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
//		final int cacheSize = maxMemory / 8;
//		imageCache = new LruCache<>(cacheSize);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		LayoutInflater inflater = 
				(LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.item_category, parent, false);

		Category category = categoryList.get(position);
		TextView tv = (TextView) view.findViewById(R.id.categoryName);
		tv.setText(category.getCategoryName());
		tv = (TextView) view.findViewById(R.id.categoryDesc);
		tv.setText(category.getCategoryDescription());
		if (category.getImageBytes() != null) {
			try {
				ImageView iv = (ImageView) view.findViewById(R.id.categoryImage);
				byte[] bytes = Base64.decode(category.getImageBytes(), Base64.DEFAULT);
				iv.setImageBitmap(BitmapFactory.decodeByteArray(bytes,0, bytes.length));
			} catch (Exception e) {
				Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
				Log.d("Cat", e.getMessage());
			}
		}
		return view;
	}

}
