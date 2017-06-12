package com.ahmed.login2;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import com.ahmed.R;
import com.ahmed.database.DBController;
import com.ahmed.gps_tracker.GpsTracker;
import com.ahmed.slidingmenu.AvailableFragment;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesClient.ConnectionCallbacks;
import com.google.android.gms.common.GooglePlayServicesClient.OnConnectionFailedListener;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.plus.PlusClient;
import com.google.android.gms.plus.model.people.Person;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
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
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentRegister extends Fragment
		implements OnClickListener, OnCheckedChangeListener, OnItemSelectedListener, AnimationListener {
	View viewRoot = null;
	Context context = null;
	EditText etUsername, etPass1, etFirstName, etLastName, etPass2, etCity, etCountry, etEmail, etPhone;
	CheckBox cbvalidEmail, cbpassConfirmed, cbWantDonor;
	RadioButton rbMale, rbFemale;
	ImageView ivValiUsername, ivPersonalIamge;
	TextView tvExistingUser;
	Button btnRegister, btnUploadImage;

	////////////////////////////////
	private LoginButton loginBtn;
	private TextView username;
	////////////////////////////////////
	private PlusClient mPlusClient;
	private int REQUEST_CODE_RESOLVE_ERR = 301;

	public ImageView getIvPersonalIamge() {
		return ivPersonalIamge;
	}

	public void setIvPersonalIamge(ImageView ivPersonalIamge) {
		this.ivPersonalIamge = ivPersonalIamge;
	}

	Spinner spinnerBloodGroups = null;
	//////////////////////////////////////////////
	AlertDialog.Builder builder = null;
	AlertDialog alertDialog = null;
	private static Uri selectedImage;
	private static InputStream imageStream;
	private static long startTime, endTime;
	private static final int SELECT_PHOTO = 100;
	/////////////////////////////////////////////////
	private static final int LOAD_VIDEO = 00, LOAD_IMAGE = 11;
	static final int REQUEST_IMAGE_CAPTURE = 1;
	private static String mCurrentPhotoPath;
	final int TAKE_PHOTO = 922;
	Bitmap mImageBitmap = null;
	///////////////////////////
	double longitude;
	double lattitude;
	Location location = null;
	////////////////////////////
	String gender = "";
	String selectedBloodGroup = "";
	Boolean emailBoo = false, confPassBoo = false;
	////////////////////////////

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);

		// FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
		// AppEventsLogger.activateApp(getActivity());
		//

		viewRoot = inflater.inflate(R.layout.register_activity, container, false);
		context = container.getContext();

		init();
		dealWithThem();
		getCityAndAcountry();

		return viewRoot;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode) {
		case SELECT_PHOTO:
			if (resultCode == LoginActivity2.RESULT_OK) {
				selectedImage = data.getData();
				try {
					imageStream = getActivity().getContentResolver().openInputStream(selectedImage);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				mImageBitmap = BitmapFactory.decodeStream(imageStream);
				ivPersonalIamge.setImageBitmap(mImageBitmap);
				ivPersonalIamge.setVisibility(View.VISIBLE);
				ivPersonalIamge.invalidate();
				// new FragmentRegister().setmImageBitmap(mImageBitmap);
				// path1 = selectedImage.getPath();
			}
		}

		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == LoginActivity2.RESULT_OK)

		{
			try {
				mImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
						Uri.parse(mCurrentPhotoPath));
				ivPersonalIamge.setImageBitmap(mImageBitmap);
				ivPersonalIamge.invalidate();
				// new FragmentRegister().setmImageBitmap(mImageBitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ivPersonalIamge.setVisibility(View.VISIBLE);
		}

		if (requestCode == REQUEST_CODE_RESOLVE_ERR && resultCode == LoginActivity2.RESULT_OK) {
			mPlusClient.disconnect();
			mPlusClient.connect();
		}

	}

	/**
	 * Showing alertdialog with two buttons one of them to take photo from your
	 * phone built in Gallery and the other for taking camera pic then set the
	 * taken photo as imagebitmap to image view
	 * 
	 * @param context
	 */
	public void createAlertDialogLibrary(Context context) {
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
						if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null) {
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
		alertDialog.setCanceledOnTouchOutside(true);
		if (alertDialog.isShowing()) {
			return;
		}
		alertDialog.show();

	}

	/**
	 * Create formatted image file in phone storage for the future use
	 * 
	 * @return Camera Image file
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

	LoginButton loginButton = null;

	/**
	 * Finding all views for accessing them From my xml that has been inflated
	 * through Overridden method onCreateView for fragment activity
	 */
	private void init() {
		View view = viewRoot.findViewById(R.id.rr1);
		ScrollView view2 = (ScrollView) view.findViewById(R.id.rr2);
		view2.setVerticalScrollBarEnabled(false);
		view2.setHorizontalScrollBarEnabled(false);
		View view3 = view2.findViewById(R.id.rr3);
		View view4 = view3.findViewById(R.id.rr4);
		etFirstName = (EditText) view4.findViewById(R.id.regfirstname);
		etLastName = (EditText) view4.findViewById(R.id.reglastname);
		View view5 = view4.findViewById(R.id.rr5);
		etEmail = (EditText) view5.findViewById(R.id.regemail);
		cbvalidEmail = (CheckBox) view5.findViewById(R.id.regvalidEmail);
		etPhone = (EditText) view4.findViewById(R.id.regPhone);
		View view6 = view4.findViewById(R.id.rr6);
		rbMale = (RadioButton) view6.findViewById(R.id.regmale);
		rbFemale = (RadioButton) view6.findViewById(R.id.regfemale);
		etCity = (EditText) view4.findViewById(R.id.regcity);
		etCountry = (EditText) view4.findViewById(R.id.regcountry);
		View view7 = view3.findViewById(R.id.rr7);
		tvExistingUser = (TextView) view7.findViewById(R.id.regtvExisting);
		View view8 = view7.findViewById(R.id.rr8);
		etUsername = (EditText) view8.findViewById(R.id.regusername);
		ivValiUsername = (ImageView) view8.findViewById(R.id.regexistingUser);
		etPass1 = (EditText) view7.findViewById(R.id.regpassword);
		View view9 = view7.findViewById(R.id.rr9);
		etPass2 = (EditText) view9.findViewById(R.id.regconfPassReg);
		cbpassConfirmed = (CheckBox) view9.findViewById(R.id.regconfirmed);
		ivPersonalIamge = (ImageView) view3.findViewById(R.id.regPersonal);
		btnUploadImage = (Button) view3.findViewById(R.id.regupload);
		cbWantDonor = (CheckBox) view3.findViewById(R.id.regDonor);
		spinnerBloodGroups = (Spinner) view3.findViewById(R.id.regBloodGroup);

		btnRegister = (Button) view3.findViewById(R.id.register);
		///////////////////////////////////////////////////

		// Tester :
		// AdView adView = (AdView) view3.findViewById(R.id.adViewRegister);
		// AdRequest adRequest = new
		// AdRequest.Builder().addTestDevice("025ACB586FDC1C445457E7C8021E203B").build();
		// adView.loadAd(adRequest);

		//
		// If this is the testing device you should call AddTestDevice("your
		// tester device code you can got it from logCat when you start the
		// App"). If not it should work fine in the production environment.
		//

		// Production:

		AdView mAdView = (AdView) view3.findViewById(R.id.adViewRegister);
		AdRequest adRequest = new AdRequest.Builder().build();
		mAdView.loadAd(adRequest);

		loginButton = (LoginButton) view3.findViewById(R.id.face_login_button);
		loginButton.setReadPermissions(Arrays.asList("public_profile", "email", "user_birthday", "user_friends"));
		// If using in a fragment
		// loginButton.setFragment(this);
		loginButton.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					Toast.makeText(getActivity(), "You are currently logged in as " + user.getName(), Toast.LENGTH_LONG)
							.show();

					// etFirstName.setText(user.getFirstName().toString());
					// etLastName.setText(user.getLastName().toString());
					// if (user.getProperty("gender").toString() == "male") {
					// rbMale.setChecked(true);
					// }else {
					// rbFemale.setChecked(true);
					// }
					// etEmail.setText(user.getProperty("email").toString());

				} else {
					Toast.makeText(getActivity(), "You are not logged in.", Toast.LENGTH_LONG).show();
				}
			}
		});
		////////////////////////////////////////////////////////////////////////

		mPlusClient = new PlusClient.Builder(getActivity(), new ConnectionCallbacks() {
			@Override
			public void onDisconnected() {

			}

			@Override
			public void onConnected(Bundle arg0) {
//				Person person = mPlusClient.getCurrentPerson();
//				etFirstName.setText(person.getDisplayName().toString());
//				//etLastName.setText(person.getNickname().toString());
//				if (person.getGender() == 0) {//0 == male
//					rbMale.setChecked(true);
//				} else {
//					rbFemale.setChecked(true);
//				}
				
				
//				showMsg(person.getDisplayName());
//				showMsg(person.getId());
//				showMsg(person.getGender() + "");

			}
		}, new OnConnectionFailedListener() {

			@Override
			public void onConnectionFailed(ConnectionResult result) {

				if (result.hasResolution()) {

					try {
						result.startResolutionForResult(getActivity(), REQUEST_CODE_RESOLVE_ERR);
					} catch (SendIntentException e) {
						mPlusClient.disconnect();
						mPlusClient.connect();
					}
				}
			}
		}).build();

		SignInButton btn = (SignInButton) view3.findViewById(R.id.PlusOneButton);
		btn.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!mPlusClient.isConnected()) {
					mPlusClient.connect();
					showMsg("signed in google+");
				} else if (mPlusClient.isConnected()) {
					{
						mPlusClient.clearDefaultAccount();
						mPlusClient.disconnect();
						showMsg("signed out of google+");
					}
				}

			}
		});

	}

	/**
	 * Create toast via any string 
	 * @param string
	 */
	void showMsg(String string) {
		Toast.makeText(getActivity(), string, Toast.LENGTH_SHORT).show();
	}

	/**
	 * Accessing the views here and adding the required listeners
	 */
	private void dealWithThem() {

		etEmail.addTextChangedListener(twEmail);
		etPass1.addTextChangedListener(twPassConfirmed);
		etPass2.addTextChangedListener(twPassConfirmed);
		etUsername.addTextChangedListener(twExistingUser);

		////////////////////////////////////////////
		String[] strings = getResources().getStringArray(R.array.spinnerArray);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, strings);
		spinnerBloodGroups.setAdapter(arrayAdapter);
		spinnerBloodGroups.setOnItemSelectedListener(this);
		/////////////////////////////////////////

		btnRegister.setOnClickListener(this);
		btnUploadImage.setOnClickListener(this);
		///////////////////////
		rbMale.setOnCheckedChangeListener(this);
		rbFemale.setOnCheckedChangeListener(this);

	}

	TextWatcher twExistingUser = new TextWatcher() {

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
				DBController controller = new DBController(context);
				String selectQuery = "select * from " + controller.tablename1 + " where username  = '"
						+ s.toString().trim() + "' ";
				ArrayList<HashMap<String, String>> hashMaps = controller.getAllUsers(selectQuery);
				if (!hashMaps.isEmpty()) {
					// Already exist
					tvExistingUser.setText("Existing user !!!!");
					ivValiUsername.setBackgroundResource(R.drawable.closeicon);
				} else {
					ivValiUsername.setBackgroundResource(R.drawable.trueicon);
					tvExistingUser.setText("Enter new not exist");
				}

			}
		}
	};

	TextWatcher twEmail = new TextWatcher() {
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
					cbvalidEmail.setChecked(true);
				} else {
					cbvalidEmail.setChecked(false);
				}
			} else {
				cbvalidEmail.setChecked(false);
			}
		}
	};
	TextWatcher twPassConfirmed = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			if (s.toString().trim().length() != 0
					&& (etPass1.getText().toString().trim().equals(etPass2.getText().toString().trim()))) {
				cbpassConfirmed.setChecked(true);
			} else {
				cbpassConfirmed.setChecked(false);
			}
		}
	};

	/**
	 * 
	 * get address from maxresults = 1 by specific location then get country
	 * name from this address
	 * 
	 * @param context
	 * @param latitude
	 * @param longitude
	 * @return countryName Egypt
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

	/**
	 * get country name get city name then set them as text auto if the internet
	 * connection is available to City and Country EditText For helping user
	 */
	private void getCityAndAcountry() {
		GpsTracker gpsTracker = new GpsTracker(context);
		if (gpsTracker.canGetLocation) {
			lattitude = gpsTracker.getLatitude();
			longitude = gpsTracker.getLongitude();
			String city = getLocationName(context, lattitude, longitude);
			String countryName = getCountryName(context, lattitude, longitude);
			// Toast.makeText(context, city, Toast.LENGTH_LONG).show();
			// Toast.makeText(context, countryName, Toast.LENGTH_LONG).show();
			etCountry.setText(countryName);
			etCity.setText(city);
		} else {
			gpsTracker.createAlert();
		}

	}

	/**
	 * Using the Geocoder class get all possible cities by using specific
	 * location lat and lon and return one one from possible ones
	 * 
	 * @param context
	 * @param lattitude
	 * @param longitude
	 * @return
	 */
	public String getLocationName(Context context, double lattitude, double longitude) {
		String cityName = "";
		Geocoder gcd = new Geocoder(/* getBaseContext() */context, Locale.getDefault());
		try {
			List<Address> addresses = gcd.getFromLocation(lattitude, longitude, 10);
			for (Address adrs : addresses) {
				if (adrs != null) {
					String city = adrs.getLocality();
					if (city != null && !city.equals("")) {
						cityName = city;
						// System.out.println("city :: " + cityName);
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

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.equals(btnRegister)) {
			registerUser();
		}
		if (v.equals(btnUploadImage)) {
			// new LoginActivity2().createAlertDialogLibrary(getActivity());
			createAlertDialogLibrary(getActivity());

			// ivPersonalIamge.setImageBitmap(getmImageBitmap());
		}

	}

	public Bitmap getmImageBitmap() {
		return mImageBitmap;
	}

	public void setmImageBitmap(Bitmap mImageBitmap) {
		this.mImageBitmap = mImageBitmap;
	}

	String donor = "";

	/**
	 * Register user in sqlite local database Register user in server database
	 * and it is as a plus checking if all fields is satisfied correctly
	 * otherwise confirm user
	 * 
	 */
	private void registerUser() {
		if (cbWantDonor.isChecked()) {
			donor = "yes";
		} else {
			donor = "no";
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] photo = null;

		if (etFirstName.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter firstname", Toast.LENGTH_SHORT).show();
			return;
		}
		if (etLastName.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter lastname", Toast.LENGTH_SHORT).show();
			return;
		}
		if (etEmail.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter email", Toast.LENGTH_SHORT).show();
			return;
		}
		if (etPhone.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter phone number", Toast.LENGTH_SHORT).show();
			return;
		}
		if (gender == null) {
			Toast.makeText(context, "Please enter gender", Toast.LENGTH_SHORT).show();
			return;
		}
		if (etCountry.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter country", Toast.LENGTH_SHORT).show();
			return;
		}
		if (etCity.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter city", Toast.LENGTH_SHORT).show();
			return;
		}
		if (etUsername.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter username", Toast.LENGTH_SHORT).show();
			return;
		}

		if (etPass1.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter password", Toast.LENGTH_SHORT).show();
			return;
		}

		if (!etPass1.getText().toString().equals(etPass2.getText().toString())) {
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

		//saveInServerDatabase();

		DBController dbController = new DBController(context);
		boolean status = dbController.insertIntoDb(etFirstName.getText().toString().trim(),
				etLastName.getText().toString().trim(), etEmail.getText().toString().trim(), gender,
				etCountry.getText().toString().trim(), etCity.getText().toString().trim(),
				etUsername.getText().toString().trim(), etPass1.getText().toString().trim(),
				etPhone.getText().toString().trim(), donor, photo, selectedBloodGroup);
		Toast.makeText(context, "Registereed Successfully", Toast.LENGTH_SHORT).show();
		// editShredPref();
		showMsg();
		
		

	}

	/**
	 * 
	 */
	private void saveInServerDatabase() {
		// TODO Auto-generated method stub

		if (!new AvailableFragment().isNetworkAvailable(getActivity())) {

			new AlertDialog.Builder(getActivity()).setTitle("Warning").setMessage("No Internet Connection !!!")
					.setIcon(R.drawable.warning).setCancelable(true)
					.setNeutralButton("OK, I Know That", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					}).show();

		} else {
			String firstName = etFirstName.getText().toString().trim();
			String lastName = etLastName.getText().toString().trim();
			String username = etUsername.getText().toString().trim();
			String password = etPass1.getText().toString().trim();
			String email = etEmail.getText().toString().trim();
			String phone = etPhone.getText().toString().trim();
			String city = etCity.getText().toString().trim();
			String country = etCountry.getText().toString().trim();

			SignupActivity signUp = new SignupActivity(getActivity(), "Please Wait ...", "Inserting in database");
			signUp.execute(firstName, lastName, username, password, email, phone, city, country, donor,
					selectedBloodGroup, gender);

		}

	}

	View view = null;
	RelativeLayout layout2 = null;

	/**
	 * showing animated message after user registered successfully or something
	 * like that
	 */
	private void showMsg() {
		// TODO Auto-generated method stub
		LayoutInflater layoutInflater = LayoutInflater.from(context);
		view = layoutInflater.inflate(R.layout.regeistered_msg, null);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.registeredSuccess);
		layout.startAnimation(createFadInOutAnim());

		// RelativeLayout.LayoutParams layoutParams =
		// (RelativeLayout.LayoutParams) layout.getLayoutParams();
		// layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL|RelativeLayout.CENTER_VERTICAL,
		// RelativeLayout.TRUE);
		// layout.setLayoutParams(layoutParams);
		layout2 = (RelativeLayout) viewRoot.findViewById(R.id.rr1);
		layout2.addView(layout);

	}

	/**
	 * Create translation and fade fade out animations then set them into set
	 * for affecting respectively on any view
	 * 
	 * @return AnimationSet
	 */
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

	/**
	 * This function take CharSequence and check if it is formated as email
	 * address or not
	 * 
	 * @param target
	 * @return boolean
	 */
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

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		// if (isChecked) {
		// this.gender = (String) buttonView.getText().toString();
		// Toast.makeText(context, gender, Toast.LENGTH_SHORT).show();
		// } else {
		//
		// }

		if (rbMale.isChecked()) {
			gender = "Male";
		} else {
			gender = "Female";
		}

	}

	//////////////////////////////////////////////////////
	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

		selectedBloodGroup = spinnerBloodGroups.getSelectedItem().toString();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	////////////////////////////////////////////////////////
	@Override
	public void onAnimationStart(Animation animation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onAnimationEnd(Animation animation) {
		// TODO Auto-generated method stub
		layout2.removeView(view);

	}

	@Override
	public void onAnimationRepeat(Animation animation) {
		// TODO Auto-generated method stub

	}

}
