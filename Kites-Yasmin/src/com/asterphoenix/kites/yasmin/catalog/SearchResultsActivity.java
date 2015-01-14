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

import com.asterphoenix.kites.model.Product;
import com.asterphoenix.kites.yasmin.R;
import com.asterphoenix.kites.yasmin.api.CatalogAPI;
import com.asterphoenix.kites.yasmin.cart.CartActivity;

public class SearchResultsActivity extends ListActivity {
	
	private List<Product> productList;
	private ProgressBar pb;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_searchresult);
		pb = (ProgressBar) findViewById(R.id.searchPB);
		pb.setVisibility(View.INVISIBLE);
		handleIntent(getIntent());
	}
	
	@Override
	protected void onNewIntent(Intent intent) {
		setIntent(intent);
		handleIntent(intent);
	}
	
	public void handleIntent(Intent intent) {
		if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
			String query = intent.getStringExtra(SearchManager.QUERY);
			doSearch(query);
		}
		
	}
	
	public void doSearch(String q) {
		if (isOnline()) {
			requestData(q);
		} else {
			Toast.makeText(this, "Network isn't available", Toast.LENGTH_LONG).show();
		}
	}
	
	protected void requestData(String q) {

		pb.setVisibility(View.VISIBLE);
		RestAdapter adapter = new RestAdapter.Builder().setEndpoint(CatalogActivity.ENDPOINT).build();
		CatalogAPI api = adapter.create(CatalogAPI.class);
		api.getProductsByName(q, new Callback<List<Product>>() {
			
			@Override
			public void success(List<Product> arg0, Response arg1) {
				productList = arg0;
				updateDisplay();
				pb.setVisibility(View.INVISIBLE);
			}
			
			@Override
			public void failure(RetrofitError arg0) {
				Toast.makeText(SearchResultsActivity.this, "Network error" + arg0.getMessage(), Toast.LENGTH_LONG).show();
				pb.setVisibility(View.INVISIBLE);
			}
		});

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
			Intent intent = new Intent(SearchResultsActivity.this, CartActivity.class);
			startActivity(intent);
			return true;
		}
		return false;
	}
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		long productID = productList.get(position).getProductID();
		Intent intent = new Intent(SearchResultsActivity.this, ProductDetailActivity.class);
		intent.putExtra("productID", productID);
		startActivity(intent);
	}
	
	protected void updateDisplay() {
		SearchResultAdapter adapter = new SearchResultAdapter(this, R.layout.item_product, productList);
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
