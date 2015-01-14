package com.asterphoenix.kites.yasmin.catalog;

import java.util.List;

import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Toast;

import com.asterphoenix.kites.model.Category;
import com.asterphoenix.kites.yasmin.R;
import com.asterphoenix.kites.yasmin.api.CatalogAPI;
import com.asterphoenix.kites.yasmin.cart.CartActivity;

public class CatalogActivity extends ListActivity {

	public static final String ENDPOINT = "http://192.168.137.1:8080/Kites-Alice";
	private ProgressBar pb;

	private List<Category> categoryList;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_catalog);

		pb = (ProgressBar) findViewById(R.id.categoryPB);
		pb.setVisibility(View.INVISIBLE);

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
		if (item.getItemId() == R.id.action_exit) {
			return true;
		}
		if (item.getItemId() == R.id.action_cart) {
			Intent intent = new Intent(CatalogActivity.this, CartActivity.class);
			startActivity(intent);
			return true;
		}
		return false;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		String categoryName = categoryList.get(position).getCategoryName();
		Intent intent = new Intent(CatalogActivity.this, ProductActivity.class);
		intent.putExtra("categoryName", categoryName);
		startActivity(intent);
	}

	protected void requestData() {

		pb.setVisibility(View.VISIBLE);
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(ENDPOINT).build();
		CatalogAPI api = adapter.create(CatalogAPI.class);
		api.getCatalog(new Callback<List<Category>>() {

			@Override
			public void success(List<Category> arg0, Response arg1) {
				categoryList = arg0;
				updateDisplay();
				pb.setVisibility(View.INVISIBLE);
			}

			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(CatalogActivity.this, arg0.getMessage(), Toast.LENGTH_LONG).show();
				pb.setVisibility(View.INVISIBLE);
			}
		});

	}

	protected void updateDisplay() {
		CatalogAdapter adapter = new CatalogAdapter(this, R.layout.item_category, categoryList);
		setListAdapter(adapter);
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
