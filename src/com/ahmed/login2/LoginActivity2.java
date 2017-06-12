package com.ahmed.login2;

import java.io.InputStream;
import java.util.ArrayList;

import com.ahmed.R;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TabHost;

@SuppressLint("NewApi")
@SuppressWarnings("deprecation")
public class LoginActivity2 extends FragmentActivity/* TabActivity */ implements 
ViewPager.OnPageChangeListener {

	// animals_images babies_images

	ActionBar actionBar = null;
//	TabHost th = null;
	String indicators[] = { "Login", "Register" };
	@SuppressWarnings("rawtypes")
	// Class classes[] = {Animals.class, Birds.class, Love.class,
	// Horror.class, Moving.class, Flowers.class, Islamic.class, Nature.class
	// , Desktop.class};
	ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	ViewPager viewPager = null;
	FragmentPageAdapter fragmentPageAdapter = null;
	////////////////////////////////

	AlertDialog.Builder builder = null;
	AlertDialog alertDialog = null;
	//private static Uri selectedImage;
//	private static InputStream imageStream;
//	private static long startTime, endTime;
//	private static final int SELECT_PHOTO = 100;
	///////////////////////////////////////////////
//	private static final int LOAD_VIDEO = 00, LOAD_IMAGE = 11;
	static final int REQUEST_IMAGE_CAPTURE = 1;
//	private static String mCurrentPhotoPath;
	final int TAKE_PHOTO = 922;
	Bitmap mImageBitmap = null;
	////////////////////////
	private UiLifecycleHelper uiHelper;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		uiHelper = new UiLifecycleHelper(this, statusCallback);
		uiHelper.onCreate(savedInstanceState);
//		FacebookSdk.sdkInitialize(getApplicationContext());
//		AppEventsLogger.activateApp(this);
		
		setContentView(R.layout.start_page);

		// this.th = this.getTabHost();
		checkWifi();
		init();

		// new DBController(getApplicationContext()).deleteAllRecords();

	}

	@Override
	public void onResume() {
		super.onResume();
		uiHelper.onResume();
	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public void onSaveInstanceState(Bundle savedState) {
		super.onSaveInstanceState(savedState);
		uiHelper.onSaveInstanceState(savedState);
	}
	
	

	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			if (state.isOpened()) {
				Log.d("MainActivity", "Facebook session opened.");
			} else if (state.isClosed()) {
				Log.d("MainActivity", "Facebook session closed.");
			}
		}
	};
	
	
		
	/**
	 * 
	 */
	private void init() {

		this.actionBar = this.getActionBar();
		this.viewPager = (ViewPager) this.findViewById(R.id.pager);
		this.actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		for (int i = 0; i < indicators.length; i++) {
			// this.actionBar.addTab(actionBar.newTab().setText(indicators[i]).setTabListener(this));
			this.actionBar.addTab(actionBar.newTab().setText(indicators[i]).setTabListener(new TabListener() {

				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {

				}

				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) {
					viewPager.setCurrentItem(tab.getPosition());
				}

				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {

				}
			}));
		}
		// this.actionBar.addTab(actionBar.newTab().setText("Animals").setTabListener(this));
		// this.actionBar.addTab(actionBar.newTab().setText("Flowers").setTabListener(this));
		//

		fragments.add(new FragmentLogin());
		fragments.add(new FragmentRegister());

		this.fragmentPageAdapter = new FragmentPageAdapter(this.getSupportFragmentManager(), this.fragments);

		this.viewPager.setAdapter(fragmentPageAdapter);
		this.viewPager.setOnPageChangeListener(this);
		
		
		
		
//		AdView adView = (AdView) findViewById(R.id.adViewStartPage);
//		AdRequest adRequest = new AdRequest.Builder()
//				.addTestDevice("025ACB586FDC1C445457E7C8021E203B").
//				build();
//		adView.loadAd(adRequest);
		//adView.setAdListener(adListener);
		

	}

	///////////////////////////////////////////

	@Override
	public void onPageScrollStateChanged(int arg0) {

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int arg0) {
		this.actionBar.setSelectedNavigationItem(arg0);
	}

	// private void init() {
	// for (int i = 0; i < indicators.length; i++) {
	// TabSpec spec = th.newTabSpec(indicators[i]);
	// spec.setIndicator(indicators[i]);
	// spec.setContent(new Intent(MainActivity.this, classes[i]));
	// this.th.addTab(spec);
	// }
	// }

	class GridViewAdapter extends BaseAdapter {

		// public GridViewAdapter(Context context, ) {
		// // TODO Auto-generated constructor stub
		// }

		@Override
		public int getCount() {
			return 0;
		}

		@Override
		public Object getItem(int arg0) {
			return null;
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			return null;
		}
	}

	public boolean isNetworkAvailable(final Context context) {
		final ConnectivityManager connectivityManager = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE));
		return connectivityManager.getActiveNetworkInfo() != null
				&& connectivityManager.getActiveNetworkInfo().isConnected();
	}

	/**
	 * This method check the wifi if itis enabled
	 * then check internert connection
	 * otherwise enable the wifi
	 * 
	 */
	private void checkWifi() {
		final WifiManager wifi = (WifiManager) getSystemService(Context.WIFI_SERVICE);
		if (!wifi.isWifiEnabled()) {
			new AlertDialog.Builder(LoginActivity2.this).setTitle("Error").setIcon(R.drawable.warning)
					.setMessage("Wifi is disabled, Do u want to enable it?")
					.setPositiveButton("Enable", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface arg0, int arg1) {
							// startActivity(new
							// Intent(Settings.ACTION_WIRELESS_SETTINGS));
							wifi.setWifiEnabled(true);// Turn off Wifi
						}
					}).setNegativeButton("NO", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.cancel();
						}
					}).show();

		} else {
			checkInternetConn();
		}

	}

	/**
	 * This function will check the internet connection
	 * then toasting user 
	 */
	private void checkInternetConn() {
		if (!isNetworkAvailable(LoginActivity2.this)) {
			new AlertDialog.Builder(LoginActivity2.this).setNeutralButton("OK", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.cancel();
				}
			}).setTitle("Error").setMessage("No internet connection").setIcon(R.drawable.warning).show();
		}
	}

}
