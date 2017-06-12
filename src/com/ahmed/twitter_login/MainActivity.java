//package com.ahmed.twitter_login;
//
//import com.ahmed.R;
//import com.facebook.login.LoginFragment;
//
//
//
//import android.support.v4.app.Fragment;
//import android.support.v4.app.FragmentActivity;
//import android.support.v4.app.FragmentTransaction;
//
////import android.app.FragmentTransaction;
////import android.app.Fragment;
//
//import android.app.Activity;
//import android.content.SharedPreferences;
//import android.os.Bundle;
//
//
//public class MainActivity extends FragmentActivity{
//	
//	SharedPreferences pref;
//	 
//    private static String CONSUMER_KEY = "Your API Key here";
//    private static String CONSUMER_SECRET = "Your API Secret here";
// 
// 
//	
//	
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main_twitter);
//		
//		pref = getPreferences(0);
//        SharedPreferences.Editor edit = pref.edit();
//        edit.putString("CONSUMER_KEY", getString(R.string.twitter_Consumer_Key_API_Key));
//        edit.putString("CONSUMER_SECRET", getString(R.string.twitter_Consumer_Secret_API_Secret));
//        edit.commit();
// 
//        Fragment login = new LoginFragment();
//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(R.id.content_frame, login);
//        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
//        ft.addToBackStack(null);
//        ft.commit();
//        
//		
//		
//		
//	}
//	
//
//}
