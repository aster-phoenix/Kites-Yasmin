package com.asterphoenix.kites.yasmin.catalog;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.asterphoenix.kites.model.Product;
import com.asterphoenix.kites.yasmin.R;

public class ProductAdapter extends ArrayAdapter<Product> {
	
	private Context context;
	private List<Product> productList;
	
//	private LruCache<Integer, Bitmap> imageCache;

	public ProductAdapter(Context context, int resource, List<Product> objects) {
		super(context, resource, objects);
		this.context = context;
		this.productList = objects;
		
//		final int maxMemory = (int)(Runtime.getRuntime().maxMemory() /1024);
//		final int cacheSize = maxMemory / 8;
//		imageCache = new LruCache<>(cacheSize);
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

//		Bitmap bitmap = imageCache.get((int) category.getCategoryID());
//		if (bitmap != null) {
//			ImageView image = (ImageView) view.findViewById(R.id.imageView1);
//			image.setImageBitmap(category.getBitmap());
//		}
//		else {
//			CategoryAndView container = new CategoryAndView();
//			container.category = category;
//			container.view = view;
//			
//			ImageLoader loader = new ImageLoader();
//			loader.execute(container);
//		}

		return view;
	}

//	class CategoryAndView {
//		public Category category;
//		public View view;
//		public Bitmap bitmap;
//	}
//
//	private class ImageLoader extends AsyncTask<CategoryAndView, Void, CategoryAndView> {
//
//		@Override
//		protected CategoryAndView doInBackground(CategoryAndView... params) {
//
//			CategoryAndView container = params[0];
//			Category flower = container.category;
//
//			try {
//				String imageUrl = "" + Category.getPhoto();
//				InputStream in = (InputStream) new URL(imageUrl).getContent();
//				Bitmap bitmap = BitmapFactory.decodeStream(in);
//				cat.setBitmap(bitmap);
//				in.close();
//				container.bitmap = bitmap;
//				return container;
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//
//			return null;
//		}
//		
//		@Override
//		protected void onPostExecute(CategoryAndView result) {
//			ImageView image = (ImageView) result.view.findViewById(R.id.imageView1);
//			image.setImageBitmap(result.bitmap);
////			result.flower.setBitmap(result.bitmap);
//			imageCache.put(result.flower.getProductId(), result.bitmap);
//		}
//
//	}

}
