//package com.ahmed.facebooklogin;
//
//import java.util.Arrays;
//
//import com.ahmed.R;
//import com.facebook.Session;
//import com.facebook.SessionState;
//import com.facebook.UiLifecycleHelper;
//import com.facebook.model.GraphUser;
//import com.facebook.widget.LoginButton;
//import com.facebook.widget.LoginButton.UserInfoChangedCallback;
//
////import android.R;
//import android.content.Intent;
//import android.os.Bundle;
//import android.support.v4.app.FragmentActivity;
//import android.util.Log;
//import android.widget.TextView;
//
//public class FaceLogin extends FragmentActivity {
//	
//	//keytool.exe -exportcert -alias androiddebugkey -keystore "C:\Users\Ahmed\.android\debug.keystore"| "E:\JavaEE projects\facebook-android-sdk-master\openssl-1.0.2j-fips-x86_64\OpenSSL\bin\openssl" sha1 -binary | "E:\JavaEE projects\facebook-android-sdk-master\openssl-1.0.2j-fips-x86_64\OpenSSL\bin\openssl" base64
//	//password : android
//	
//	private LoginButton loginBtn;
//	private TextView username;
//	private UiLifecycleHelper uiHelper;
//	
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		uiHelper = new UiLifecycleHelper(this, statusCallback);
//		uiHelper.onCreate(savedInstanceState);
//		
//		setContentView(R.layout.facelogin_activity);
//		username = (TextView) findViewById(R.id.username);
//		
//		
//		loginBtn = (LoginButton) findViewById(R.id.fb_login_button);
//		
//		loginBtn.setReadPermissions(Arrays.asList("email"));
//		loginBtn.setUserInfoChangedCallback(new UserInfoChangedCallback() {
//			@Override
//			public void onUserInfoFetched(GraphUser user) {
//				if (user != null) {
//					username.setText("You are currently logged in as " + user.getName());
//				} else {
//					username.setText("You are not logged in.");
//				}
//			}
//		});
//	}
//	
//	
//	
//	private Session.StatusCallback statusCallback = new Session.StatusCallback() {
//		@Override
//		public void call(Session session, SessionState state,
//				Exception exception) {
//			if (state.isOpened()) {
//				Log.d("MainActivity", "Facebook session opened.");
//			} else if (state.isClosed()) {
//				Log.d("MainActivity", "Facebook session closed.");
//			}
//		}
//	};
//	
//	@Override
//	public void onResume() {
//		super.onResume();
//		uiHelper.onResume();
//	}
//
//	@Override
//	public void onPause() {
//		super.onPause();
//		uiHelper.onPause();
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//		uiHelper.onDestroy();
//	}
//
//	@Override
//	public void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		uiHelper.onActivityResult(requestCode, resultCode, data);
//	}
//
//	@Override
//	public void onSaveInstanceState(Bundle savedState) {
//		super.onSaveInstanceState(savedState);
//		uiHelper.onSaveInstanceState(savedState);
//	}
//}