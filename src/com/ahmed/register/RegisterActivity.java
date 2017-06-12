package com.ahmed.register;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.ahmed.R;
import com.ahmed.database.DBController;
import com.ahmed.gps_tracker.GpsTracker;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
//import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class RegisterActivity extends Activity
		implements OnClickListener, OnCheckedChangeListener, AnimationListener, OnItemSelectedListener {

	Context context = null;
	double longitude;
	double lattitude;
	Location location = null;
	/////////////////////////////////////
	EditText firstname, lastname, email, username, country, city, password, confirmPass;
	Button register, cancel;
	RadioButton male, female;
	CheckBox confirmed = null, emailValid = null;
	GpsTracker gpsTracker = null;
	AssetManager assetManager = null;
	ImageView existingUsername = null;

	TextWatcher textWatcherEmail = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (s.toString().trim().length() != 0) {
				if (isValidEmail((CharSequence) s.toString().trim())) {
					emailValid.setChecked(true);
				} else {
					emailValid.setChecked(false);
				}
			} else {
				emailValid.setChecked(false);
			}
		}
	};
	TextWatcher textWatcherPass = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (s.toString().trim().length() != 0
					&& (password.getText().toString().trim().equals(confirmPass.getText().toString().trim()))) {
				confirmed.setChecked(true);
			} else {
				confirmed.setChecked(false);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.register_activity);

		init();
		getCityAndAcountry();

	}

	String strFirstname, strLastName, strEmail, strusername, strPass, strConfPass;
	String gender = null;
	EditText phone;
	ImageView personalImg = null;
	Spinner bloodGroup = null;
	CheckBox wantDonor = null;
	Button uploadImg = null;

	/**
	 * Firstly, Getting all view by finding them by id for processing and
	 * accessing them as i kike
	 * 
	 */
	private void init() {
		///////////////////////////
		context = RegisterActivity.this;
		//////////////////////////////
		phone = (EditText) findViewById(R.id.regPhone);
		firstname = (EditText) findViewById(R.id.regfirstname);
		lastname = (EditText) findViewById(R.id.reglastname);
		email = (EditText) findViewById(R.id.regemail);
		country = (EditText) findViewById(R.id.regcountry);
		city = (EditText) findViewById(R.id.regcity);
		username = (EditText) findViewById(R.id.regusername);
		username.addTextChangedListener(twExistingUser);
		password = (EditText) findViewById(R.id.regpassword);
		confirmPass = (EditText) findViewById(R.id.regconfPassReg);
		emailValid = (CheckBox) findViewById(R.id.regvalidEmail);
		confirmed = (CheckBox) findViewById(R.id.regconfirmed);

		personalImg = (ImageView) findViewById(R.id.regPersonal);
		wantDonor = (CheckBox) findViewById(R.id.regDonor);
		bloodGroup = (Spinner) findViewById(R.id.regBloodGroup);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(RegisterActivity.this,
				android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.spinnerArray)); // selected
																											// item
																											// will
																											// look
																											// like
																											// a
																											// spinner
																											// set
																											// from
																											// XML
		spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		bloodGroup.setAdapter(spinnerArrayAdapter);
		bloodGroup.setOnItemSelectedListener(this);
		uploadImg = (Button) findViewById(R.id.regupload);
		uploadImg.setOnClickListener(this);

		////////////////////////////////////////

		/////////////////////////////////////////////
		register = (Button) findViewById(R.id.register);
		// cancel = (Button) findViewById(R.id.regcancel);
		//////////////////////////////////
		male = (RadioButton) findViewById(R.id.regmale);
		female = (RadioButton) findViewById(R.id.regfemale);
		//////////////////////////////////
		confirmPass.addTextChangedListener(textWatcherPass);
		email.addTextChangedListener(textWatcherEmail);
		register.setOnClickListener(this);
		cancel.setOnClickListener(this);
		//////////////////////////////
		male.setOnCheckedChangeListener(this);
		female.setOnCheckedChangeListener(this);
		//////////////////////////////
		existingUsername = (ImageView) findViewById(R.id.regexistingUser);

		//////////////////////////////////////////////

		//////////////////////////////////
		// assetManager = getAssets();
		// Typeface typeface = Typeface.createFromAsset(assetManager,
		// "fonts/GoodDog.otf");
		// male.setTypeface(typeface);
		// female.setTypeface(typeface);
		//

		addAds();

	}

	private void addAds() {
		// TODO Auto-generated method stub
		// https://www.google.com/admob/
		AdView adView = (AdView) findViewById(R.id.adViewRegister);
		AdRequest adRequest = new AdRequest.Builder().addTestDevice("A9CE07A2C959A9C174254B84F5139414").build();
		adView.loadAd(adRequest);
	}

	/**
	 * Get country name using location lat and long if and only if the internet
	 * connection is enabled
	 * 
	 * @param context
	 * @param latitude
	 * @param longitude
	 * @return
	 */
	public static String getCountryName(Context context, double latitude, double longitude) {
		Geocoder geocoder = new Geocoder(context, Locale.getDefault());
		List<Address> addresses = null;
		String countryName = null;
		try {
			addresses = geocoder.getFromLocation(latitude, longitude, 1);
			Address result;

			if (addresses != null && !addresses.isEmpty()) {
				countryName = addresses.get(0).getCountryName();
			}

		} catch (IOException ignored) {
			// do something
			return null;
		}
		return countryName;
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();

		// gpsTracker = new GpsTracker(context);

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();

		if (gpsTracker != null && !gpsTracker.getLocationManager().equals(null)) {
			gpsTracker.stopUsingGps();
		}

	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

	}

	/**
	 * This method uses both of getCountryName and getCityName methods for
	 * setting the texts in the fields automatically
	 * 
	 */
	private void getCityAndAcountry() {
		context = RegisterActivity.this;
		gpsTracker = new GpsTracker(context);
		if (gpsTracker.canGetLocation) {
			lattitude = gpsTracker.getLatitude();
			longitude = gpsTracker.getLongitude();
			String city = getLocationName(context, lattitude, longitude);
			String countryName = getCountryName(context, lattitude, longitude);
			// Toast.makeText(context, city, Toast.LENGTH_LONG).show();
			// Toast.makeText(context, countryName, Toast.LENGTH_LONG).show();

			RegisterActivity.this.country.setText(countryName);
			RegisterActivity.this.city.setText(city);
		} else {
			gpsTracker.createAlert();
		}

	}

	/**
	 * getting the city name using current location's lat amd long
	 * 
	 * @param context
	 * @param lattitude
	 * @param longitude
	 * @return
	 */
	public String getLocationName(Context context, double lattitude, double longitude) {

		String cityName = "Not Found";
		Geocoder gcd = new Geocoder(/* getBaseContext() */context, Locale.getDefault());
		try {

			List<Address> addresses = gcd.getFromLocation(lattitude, longitude, 10);

			for (Address adrs : addresses) {
				if (adrs != null) {

					String city = adrs.getLocality();
					if (city != null && !city.equals("")) {
						cityName = city;
						System.out.println("city ::  " + cityName);
					} else {

					}
					// // you should also try with addresses.get(0).toSring();

				}

			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return cityName;

	}

	private static final int LOAD_VIDEO = 00, LOAD_IMAGE = 11;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private static String mCurrentPhotoPath;
	final int TAKE_PHOTO = 922;

	/**
	 * Creating the image file in storage of phone for saving the selected image
	 * bytes within it in future
	 * 
	 * @return
	 * @throws IOException
	 */
	private File createImageFile() throws IOException {
		// Create an image file name
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
		String imageFileName = "JPEG_" + timeStamp + "_";
		File storageDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
		File image = File.createTempFile(imageFileName, // prefix
				".jpg", // suffix
				storageDir // directory
		);
		// Save a file: path for use with ACTION_VIEW intents
		mCurrentPhotoPath = "file:" + image.getAbsolutePath();
		return image;
	}

	Bitmap mImageBitmap = null;

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == RESULT_OK) {
				selectedImage = data.getData();
				try {
					imageStream = getContentResolver().openInputStream(selectedImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mImageBitmap = BitmapFactory.decodeStream(imageStream);
				personalImg.setImageBitmap(mImageBitmap);
				// path1 = selectedImage.getPath();
				// bmpimg1 = yourSelectedImage;
				personalImg.invalidate();
			}
		}

		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
			try {
				mImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(),
						Uri.parse(mCurrentPhotoPath));
				personalImg.setImageBitmap(mImageBitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	AlertDialog.Builder builder = null;
	AlertDialog alertDialog = null;
	private static Uri selectedImage;
	private static InputStream imageStream;
	private static long startTime, endTime;
	private static final int SELECT_PHOTO = 100;

	/**
	 * 
	 * @param context
	 */
	private void createAlertDialogLibrary(Context context) {
		builder = new AlertDialog.Builder(context).setMessage("Get Photo")
				.setPositiveButton("From Gallery", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
						photoPickerIntent.setType("image/*");
						startActivityForResult(photoPickerIntent, SELECT_PHOTO);
					}
				}).setNegativeButton("From Camera", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
						if (cameraIntent.resolveActivity(getPackageManager()) != null) {
							File photoFile = null;
							try {
								photoFile = createImageFile();
							} catch (IOException ex) {
							}
							if (photoFile != null) {
								cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
								startActivityForResult(cameraIntent, REQUEST_IMAGE_CAPTURE);
							}
						}
					}
				});
		alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(false);
		alertDialog.show();

	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.equals(register)) {
			registerUser();
		} else {
			// if (v.equals(cancel)) {
			// cancelReg();
			// } else {
			//
			// }

		}
		if (v.equals(uploadImg)) {
			getImage();
		}
	}

	private void getImage() {
		// TODO Auto-generated method stub
		createAlertDialogLibrary(context);

	}

	private void cancelReg() {
		// TODO Auto-generated method stub
		if (username.getText().toString().trim().length() != 0 || password.getText().toString().trim().length() != 0
				|| country.getText().toString().trim().length() != 0 || city.getText().toString().trim().length() != 0
				|| email.getText().toString().trim().length() != 0
				|| firstname.getText().toString().trim().length() != 0
				|| lastname.getText().toString().trim().length() != 0) {
			showAlert(context, "Do u want to leave this page ??");
		} else {
			finish();
			// startActivity(new Intent(RegisterActivity.this,
			// LoginActivity.class));
		}

	}

	/**
	 * 
	 * @param context
	 * @param string
	 */
	private void showAlert(Context context, String string) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Warning");
		builder.setMessage(string);
		builder.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				finish();
				// startActivity(new Intent(RegisterActivity.this,
				// LoginActivity.class));
			}
		});
		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.cancel();
			}
		});
		builder.setCancelable(false);
		AlertDialog alertDialog = null;
		alertDialog = builder.create();
		alertDialog.show();

	}

	private void registerUser() {

		// ahmed mohammed a@gmail.com male egypt cairo ahmed ali

		String donor = "";
		if (wantDonor.isChecked()) {
			donor = "yes";
		} else {
			donor = "no";
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] photo = null;

		if (firstname.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter firstname", Toast.LENGTH_SHORT).show();
			return;
		}
		if (lastname.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter lastname", Toast.LENGTH_SHORT).show();
			return;
		}
		if (email.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show();
			return;
		}
		if (phone.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter phone number", Toast.LENGTH_SHORT).show();
			return;
		}
		if (gender == null) {
			Toast.makeText(context, "Please enter gender", Toast.LENGTH_SHORT).show();
			return;
		}
		if (country.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter country", Toast.LENGTH_SHORT).show();
			return;
		}
		if (city.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter city", Toast.LENGTH_SHORT).show();
			return;
		}
		if (username.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter username", Toast.LENGTH_SHORT).show();
			return;
		}

		if (password.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!password.getText().toString().equals(confirmPass.getText().toString())) {
			Toast.makeText(context, "Passwords are not matched", Toast.LENGTH_SHORT).show();
			return;
		}
		if (mImageBitmap == null) {
			Toast.makeText(context, "Please select personal image", Toast.LENGTH_SHORT).show();
			return;
		} else {
			// Bitmap bitmap =
			// ((BitmapDrawable)getResources().getDrawable(R.drawable.common)).getBitmap();
			Bitmap bitmap = mImageBitmap;
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
			photo = baos.toByteArray();
		}
		if (selectedBloodGroup.equals("Select Group")) {
			Toast.makeText(context, "Please select blood group", Toast.LENGTH_SHORT).show();
			return;
		}

		DBController dbController = new DBController(context);
		boolean status = dbController.insertIntoDb(firstname.getText().toString().trim(),
				lastname.getText().toString().trim(), email.getText().toString().trim(), gender,
				country.getText().toString().trim(), city.getText().toString().trim(),
				username.getText().toString().trim(), password.getText().toString().trim(),
				phone.getText().toString().trim(), donor, photo, selectedBloodGroup);
		Toast.makeText(context, "Registereed Successfully", Toast.LENGTH_SHORT).show();
		// editShredPref();
		// showMsg();

	}

	TextWatcher twExistingUser = new TextWatcher() {

		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
			// TODO Auto-generated method stub

		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
			// TODO Auto-generated method stub

		}

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (s.toString().trim().length() != 0) {
				DBController controller = new DBController(context);
				String selectQuery = "select * from " + controller.tablename1 + " where username  = '"
						+ s.toString().trim() + "' ";
				ArrayList<HashMap<String, String>> hashMaps = controller.getAllUsers(selectQuery);
				if (!hashMaps.isEmpty()) {
					// Already exist
					((TextView) findViewById(R.id.regtvExisting)).setText("Existing user !!!!");
					existingUsername.setBackgroundResource(R.drawable.closeicon);
				} else {
					existingUsername.setBackgroundResource(R.drawable.trueicon);
					((TextView) findViewById(R.id.regtvExisting)).setText("Enter new not exist");
				}

			}
		}
	};

	SharedPreferences sharedpreferences = null;

	private void editShredPref() {
		// TODO Auto-generated method stub
		// sharedpreferences = getSharedPreferences(LoginActivity.MyPREFERENCES,
		// Context.MODE_PRIVATE);
		// Editor editor = sharedpreferences.edit();
		// String u = username.getText().toString();
		// String p = password.getText().toString();
		// editor.putString(LoginActivity.name, u);
		// editor.putString(LoginActivity.pass, p);
		// editor.commit();

	}

	private AnimationSet createFadInOutAnim() {
		// TODO Auto-generated method stub

		// Animation inFromLeft = AnimationUtils.loadAnimation(context,
		// android.R.anim.slide_in_left);
		Animation inFromLeft = AnimationUtils.loadAnimation(context, R.anim.slide_in_from_left);

		Animation fadeOut = new AlphaAnimation(1, 0);
		fadeOut.setInterpolator(new AccelerateInterpolator()); // and this
		fadeOut.setStartOffset(1000);
		fadeOut.setDuration(2000);

		AnimationSet animation = new AnimationSet(false); // change to false
		animation.addAnimation(inFromLeft);
		animation.addAnimation(fadeOut);
		animation.setAnimationListener(this);

		return animation;
	}

	// private void showMsg() {
	// // TODO Auto-generated method stub
	// LayoutInflater layoutInflater = LayoutInflater.from(context);
	// LinearLayout layout = (LinearLayout)
	// layoutInflater.inflate(R.layout.regeistered_msg, null);
	// //RelativeLayout relativeLayout = (RelativeLayout)
	// findViewById(R.id.registerPage);
	// layout.startAnimation(createFadInOutAnim());
	// // RelativeLayout.LayoutParams layoutParams =
	// // (RelativeLayout.LayoutParams) layout.getLayoutParams();
	// // layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,
	// // RelativeLayout.TRUE);
	// // layout.setLayoutParams(layoutParams);
	// //relativeLayout.addView(layout);
	//
	// }

	// Another option is the built in Patterns starting with API Level 8:
	public final static boolean isValidEmail(CharSequence target) {
		if (TextUtils.isEmpty(target)) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}

	// @Override
	// public void onCheckedChanged(RadioGroup group, int checkedId) {
	// // TODO Auto-generated method stub
	//
	// }

	Boolean emailBoo = false, confPassBoo = false;

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (isChecked) {
			this.gender = (String) buttonView.getText();
			// Toast.makeText(context, gender, Toast.LENGTH_SHORT).show();
		} else {

		}

	}

	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub

		// Intent i = new Intent(RegisterActivity.this, LoginActivity.class);
		// i.putExtra("username", username.getText().toString().trim());
		// i.putExtra("password", password.getText().toString().trim());
		// startActivity(i);

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

	String selectedBloodGroup = null;

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

		selectedBloodGroup = bloodGroup.getSelectedItem().toString();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

}
