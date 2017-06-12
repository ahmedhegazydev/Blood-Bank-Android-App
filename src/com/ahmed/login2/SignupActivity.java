package com.ahmed.login2;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SignupActivity extends AsyncTask<String, Void, String> {

	private Context context;
	private ProgressDialog progressDialog = null;
	private String title = null;
	private String message = null;

	public SignupActivity(Context context, String title, String message) {
		this.context = context;
		this.title = title;
		this.message = message;

	}

	protected void onPreExecute() {

		progressDialog = new ProgressDialog(context);
		progressDialog.setTitle(title);
		progressDialog.setMessage(message);
		progressDialog.setCancelable(false);
		progressDialog.setCanceledOnTouchOutside(false);
		progressDialog.show();

	}

	@Override
	protected String doInBackground(String... dataFromUser) {
		String firstName = dataFromUser[0];
		String lastName = dataFromUser[1];
		String userName = dataFromUser[2];
		String passWord = dataFromUser[3];
		String city = dataFromUser[4];
		String country = dataFromUser[5];
		String email = dataFromUser[6];
		String phone = dataFromUser[7];
		String wantBeDonor = dataFromUser[8];
		String bloodGroup = dataFromUser[9];
		String gender = dataFromUser[10];
		///////
		// The image profile

		String link;
		String data;
		BufferedReader bufferedReader;
		String result;

		try {
			data = "?firstname=" + URLEncoder.encode(firstName, "UTF-8");
			data += "&lastname=" + URLEncoder.encode(lastName, "UTF-8");
			data += "&username=" + URLEncoder.encode(userName, "UTF-8");
			data += "&password=" + URLEncoder.encode(passWord, "UTF-8");
			data += "&email=" + URLEncoder.encode(email, "UTF-8");
			data += "&phone=" + URLEncoder.encode(phone, "UTF-8");
			data += "&city=" + URLEncoder.encode(city, "UTF-8");
			data += "&country=" + URLEncoder.encode(country, "UTF-8");
			data += "&wantdonor=" + URLEncoder.encode(wantBeDonor, "UTF-8");
			data += "&bloodgroup=" + URLEncoder.encode(bloodGroup, "UTF-8");

			link = "http://ahmedmohamedali.pe.hu/get_image2.php/blood_insert.php" + data;
			URL url = new URL(link);
			HttpURLConnection con = (HttpURLConnection) url.openConnection();

			bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));
			result = bufferedReader.readLine();
			return result;
		} catch (Exception e) {
			return new String("Exception: " + e.getMessage());
		}
	}

	@Override
	protected void onPostExecute(String result) {
		String jsonStr = result;
		if (jsonStr != null) {
			try {
				JSONObject jsonObj = new JSONObject(jsonStr);
				String query_result = jsonObj.getString("query_result");
				if (query_result.equals("SUCCESS")) {
					Toast.makeText(context, "Data inserted successfully. Signup successful.", Toast.LENGTH_SHORT)
							.show();
				} else if (query_result.equals("FAILURE")) {
					Toast.makeText(context, "Data could not be inserted. Signup failed.", Toast.LENGTH_SHORT).show();
				} else {
					Toast.makeText(context, "Couldn't connect to remote database.", Toast.LENGTH_SHORT).show();
				}
			} catch (JSONException e) {
				e.printStackTrace();
				Toast.makeText(context, "Error parsing JSON data.", Toast.LENGTH_SHORT).show();
			}
		} else {
			Toast.makeText(context, "Couldn't get any JSON data.", Toast.LENGTH_SHORT).show();
		}
	}
}