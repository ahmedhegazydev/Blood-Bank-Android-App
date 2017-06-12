//package com.ahmed.backendless;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.TextView;
//import android.util.Log;
//
//import com.ahmed.R;
//import com.backendless.Backendless;
//import com.backendless.async.callback.AsyncCallback;
//import com.backendless.exceptions.BackendlessFault;
//import java.util.HashMap;
//import java.util.Map;
//
////import com.examples.dss_blood_bank.Defaults;
//
//public class MainActivity extends Activity {
//	@Override
//	public void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//
//		// Backendless.setUrl(Defaults.SERVER_URL);
//		// Backendless.initApp(getApplicationContext(), Defaults.APPLICATION_ID,
//		// Defaults.API_KEY);
//
//		String server_url = getApplicationContext().getString(R.string.server_url);
//		String app_id = getApplicationContext().getString(R.string.app_id_backendless);
//		String api_key = getApplicationContext().getString(R.string.api_key);
//
//		Backendless.setUrl(server_url);
//		Backendless.initApp(getApplicationContext(), app_id, api_key);
//
//		HashMap testObject = new HashMap<>();
//		testObject.put("foo", "bar");
//		Backendless.Data.of("TestTable").save(testObject, new AsyncCallback<Map>() {
//			@Override
//			public void handleResponse(Map response) {
//				TextView label = new TextView(MainActivity.this);
//				label.setText("Object is saved in Backendless. Please check in the console.");
//				setContentView(label);
//			}
//
//			@Override
//			public void handleFault(BackendlessFault fault) {
//				Log.e("MYAPP", "Server reported an error " + fault.getMessage());
//			}
//		});
//
//		TextView label = new TextView(this);
//		label.setText("Hello world!");
//
//		setContentView(label);
//	}
//}
