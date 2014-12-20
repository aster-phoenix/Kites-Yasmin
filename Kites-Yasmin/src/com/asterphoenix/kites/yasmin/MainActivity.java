package com.asterphoenix.kites.yasmin;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.asterphoenix.kites.yasmin.cart.CartActivity;
import com.asterphoenix.kites.yasmin.catalog.CatalogActivity;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals("cart")) {
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
}
