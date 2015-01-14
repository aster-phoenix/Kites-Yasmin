package com.asterphoenix.kites.yasmin;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.asterphoenix.kites.yasmin.cart.CartActivity;
import com.asterphoenix.kites.yasmin.catalog.CatalogActivity;
import com.asterphoenix.kites.yasmin.catalog.OrderStatusActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
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
			Intent intent = new Intent(MainActivity.this, CartActivity.class);
			startActivity(intent);
			return true;
		}
		return false;
	}
	
	public void onStartButtonClicked(View v) {
		Intent intent = new Intent(MainActivity.this, CatalogActivity.class);
		startActivity(intent);
	}
	
	public void onStatusButtonClicked(View v) {
		Intent intent = new Intent(MainActivity.this, OrderStatusActivity.class);
		startActivity(intent);
	}
}
