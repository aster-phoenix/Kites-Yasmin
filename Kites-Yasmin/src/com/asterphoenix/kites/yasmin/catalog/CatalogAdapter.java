package com.asterphoenix.kites.yasmin.catalog;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.asterphoenix.kites.model.Category;
import com.asterphoenix.kites.yasmin.R;
import com.asterphoenix.kites.yasmin.R.id;
import com.asterphoenix.kites.yasmin.R.layout;

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

		//Display flower name in the TextView widget
		Category category = categoryList.get(position);
		TextView tv = (TextView) view.findViewById(R.id.textView1);
		tv.setText(category.getCategoryName());
		tv = (TextView) view.findViewById(R.id.textView2);
		tv.setText(category.getCategoryDescription());

		//Display flower photo in ImageView widget
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
