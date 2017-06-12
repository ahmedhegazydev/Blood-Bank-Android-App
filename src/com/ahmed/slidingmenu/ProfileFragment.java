
package com.ahmed.slidingmenu;

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
import com.ahmed.login2.LoginActivity2;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
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

public class ProfileFragment extends Fragment
		implements OnClickListener, OnItemSelectedListener, AnimationListener, OnCheckedChangeListener {

	String extraUsername = "", extraPassword = "";
	String city, country, phone, firstName, lastName, email, etGender;
	DBController dbController = null;
	Context context = null;
	View viewRoot = null;
	EditText etUsername, etPass1, etPass2, etEmail, etCity, etCountry, etFirstname, etLastname, etPhone;
	Button btnUpadtaeImage = null, btnCancel = null, btnUpdateUser = null;
	Spinner spinnerBlood = null;
	ArrayAdapter<String> arrayAdapter = null;
	CheckBox cbEmail, cbWantDonor, cbConfPass;
	RadioButton rbMale, rbFemale;
	ImageView ivUserExist = null, ivPersonalImage = null;
	TextView tvEnterNewUser = null;
	Intent intent = null;
	ArrayList<HashMap<String, String>> hashMaps = null;
	///////////////////////////////////
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
	////////////////////////////////////////

	public ProfileFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		viewRoot = inflater.inflate(R.layout.fragment_profile, container, false);

		context = container.getContext();

		initVariables();

		dealWithThem();

		getDataFromDatabase();

		return viewRoot;
	}

	public String getExtraUsername() {
		return extraUsername;
	}

	public void setExtraUsername(String extraUsername) {
		this.extraUsername = extraUsername;
	}

	public String getExtraPassword() {
		return extraPassword;
	}

	public void setExtraPassword(String extraPassword) {
		this.extraPassword = extraPassword;
	}

	/**
	 * getting all user data from database users table by using the username and
	 * password that user has been logged in to home page of our application and
	 * he can update this data if he want
	 * 
	 */
	private void getDataFromDatabase() {

		dbController = new DBController(context);
		// MainActivity mainActivity = new MainActivity();
		// extraPassword = mainActivity.getPassword();
		// extraUsername = mainActivity.getUsername();

		Intent i = getActivity().getIntent();
		extraPassword = i.getStringExtra("password");
		extraUsername = i.getStringExtra("username");
		Toast.makeText(context, extraUsername + "   " + extraPassword, Toast.LENGTH_SHORT).show();

		hashMaps = dbController.getAllUsers(extraUsername, extraPassword);
		if (hashMaps.isEmpty()) {
			// Do nothing
		} else {
			// As may be there a lot arrarylists for same username and password
			// Thus we will take the last element in arraylist = one hashmap
			HashMap<String, String> hashMap = hashMaps.get(0);
			email = hashMap.get("email");
			gender = hashMap.get("gender");
			// Toast.makeText(context, gender, Toast.LENGTH_SHORT).show();
			firstName = hashMap.get("firstname");
			lastName = hashMap.get("lastname");
			city = hashMap.get("city");
			country = hashMap.get("country");
			phone = hashMap.get("phone");
			selectedBloodGroup = hashMap.get("bloodgroup");

			etCity.setText(city);
			etUsername.setText(extraUsername);
			etPass1.setText(extraPassword);
			etCountry.setText(country);
			etLastname.setText(lastName);
			etFirstname.setText(firstName);
			etEmail.setText(email);
			etPhone.setText(phone);

			if (gender.equals("Male")) {
				rbMale.setChecked(true);
			} else {
				rbFemale.setChecked(true);
			}
			if (hashMap.get("donorOrNot").equals("yes")) {
				cbWantDonor.setChecked(true);
			} else {
				cbWantDonor.setChecked(false);
			}
			if (isValidEmail(etEmail.getText().toString())) {
				cbEmail.setChecked(true);
			} else {
				cbEmail.setChecked(false);
			}

			for (int j = 0; j < strings.length; j++) {
				if (selectedBloodGroup.equals(strings[j])) {
					spinnerBlood.setSelection(j);
				} else {
					// spinnerBlood.setSelection(0);
				}
			}

			// get the image from database
			String selectQuery = "select * from " + dbController.tablename1 + " where username =    '" + extraUsername
					+ "'    and     password =   '" + extraPassword + "'      ";
			ArrayList<HashMap<String, byte[]>> hashMaps2 = dbController.getAllImagesBySql(selectQuery);
			// Toast.makeText(getActivity(), hashMaps2.size()+"",
			// Toast.LENGTH_SHORT).show();
			HashMap<String, byte[]> hashMap2 = hashMaps2.get(0);
			byte[] bs = null;
			bs = hashMap2.get("image");
			if (bs != null) {
				Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
				ivPersonalImage.setImageBitmap(bitmap);
				ivPersonalImage.setVisibility(View.VISIBLE);
				mImageBitmap = bitmap;
			} else {

			}

		}

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
				ivPersonalImage.setImageBitmap(mImageBitmap);
				ivPersonalImage.setVisibility(View.VISIBLE);
				ivPersonalImage.invalidate();
				// new FragmentRegister().setmImageBitmap(mImageBitmap);
				// path1 = selectedImage.getPath();
			}

		}

		if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == LoginActivity2.RESULT_OK)

		{
			try {
				mImageBitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(),
						Uri.parse(mCurrentPhotoPath));
				ivPersonalImage.setImageBitmap(mImageBitmap);
				ivPersonalImage.invalidate();
				// new FragmentRegister().setmImageBitmap(mImageBitmap);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ivPersonalImage.setVisibility(View.VISIBLE);
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
					tvEnterNewUser.setText("Existing user !!!!");
					ivUserExist.setBackgroundResource(R.drawable.closeicon);
				} else {
					ivUserExist.setBackgroundResource(R.drawable.trueicon);
					tvEnterNewUser.setText("Enter new not exist");
				}

			}
		}
	};

	// Another option is the built in Patterns starting with API Level 8:
	public final static boolean isValidEmail(CharSequence target) {
		if (TextUtils.isEmpty(target)) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
		}
	}

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
					cbEmail.setChecked(true);
				} else {
					cbEmail.setChecked(false);
				}
			} else {
				cbEmail.setChecked(false);
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
				cbConfPass.setChecked(true);
			} else {
				cbConfPass.setChecked(false);
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
		if (v.equals(btnUpdateUser)) {
			updateUser();
		}
		if (v.equals(btnUpadtaeImage)) {
			// new LoginActivity2().createAlertDialogLibrary(getActivity());
			createAlertDialogLibrary(getActivity());

			// ivPersonalIamge.setImageBitmap(getmImageBitmap());
		}

	}

	/**
	 * This method update the user informations in database
	 * 
	 */
	private void updateUser() {
		// TODO Auto-generated method stub

		String donor = "";
		if (cbWantDonor.isChecked()) {
			donor = "yes";
		} else {
			donor = "no";
		}

		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] photo = null;

		if (etFirstname.getText().toString().equals("")) {
			Toast.makeText(context, "Please enter firstname", Toast.LENGTH_SHORT).show();
			return;
		}
		if (etLastname.getText().toString().equals("")) {
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

		DBController dbController = new DBController(context);
		dbController.updateData(etUsername.getText().toString().trim(), etPass1.getText().toString().trim(),
				etFirstname.getText().toString().trim(), etLastname.getText().toString().trim(),
				etPhone.getText().toString().trim(), gender, selectedBloodGroup, etEmail.getText().toString().trim(),
				etCity.getText().toString().trim(), etCountry.getText().toString().trim(), donor, photo);
		Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
		// editShredPref();
		showMsg();

	}

	View view = null;
	RelativeLayout layout2 = null;

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
		layout2 = (RelativeLayout) viewRoot.findViewById(R.id.llEditProfile);
		layout2.addView(layout);

	}

	/**
	 * This method create and return animationset that may be has alot of
	 * animations for start it respectively for any view in one time
	 * 
	 * @return
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

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		if (rbMale.isChecked()) {
			gender = "Male";
		} else {
			gender = "Female";
		}

	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

		selectedBloodGroup = spinnerBlood.getSelectedItem().toString();

	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

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

	String[] strings = null;

	private void dealWithThem() {

		etUsername.addTextChangedListener(twExistingUser);
		etPass1.addTextChangedListener(twPassConfirmed);
		etPass2.addTextChangedListener(twPassConfirmed);
		etEmail.addTextChangedListener(twEmail);
		////////////////////////////
		btnUpadtaeImage.setOnClickListener(this);
		btnUpdateUser.setOnClickListener(this);
		//////////////////////////////////
		strings = getActivity().getResources().getStringArray(R.array.spinnerArray);
		ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_dropdown_item_1line, strings);
		spinnerBlood.setAdapter(arrayAdapter);
		spinnerBlood.setOnItemSelectedListener(this);
		///////////////////////////////
		rbMale.setOnCheckedChangeListener(this);
		rbFemale.setOnCheckedChangeListener(this);
		/////////////////////////////////////////

	}

	// get all views from mail xml resource
	// For dealing with them and processing
	private void initVariables() {
		View view = viewRoot.findViewById(R.id.llEditProfile);

		ScrollView view2 = (ScrollView) view.findViewById(R.id.svEditProfile);
		view2.setVerticalScrollBarEnabled(false);
		view2.setHorizontalScrollBarEnabled(false);

		View view3 = view2.findViewById(R.id.llEditProfile1);
		View view4 = view3.findViewById(R.id.llEditProfile2);
		View view5 = view4.findViewById(R.id.llEditProfile3);// main
		////////////////////////////////////////////////////////
		etFirstname = (EditText) view5.findViewById(R.id.editFirstname);
		etLastname = (EditText) view5.findViewById(R.id.editlastname);
		etPhone = (EditText) view5.findViewById(R.id.editPhone);
		/////////////////////////////////////////
		View view6 = view5.findViewById(R.id.llEditProfile4);
		etEmail = (EditText) view6.findViewById(R.id.editemail);
		cbEmail = (CheckBox) view6.findViewById(R.id.editvalidEmail);
		////////////////////////////////////////////////
		View view7 = view5.findViewById(R.id.rgEditProfile);
		rbFemale = (RadioButton) view7.findViewById(R.id.editfemale);
		rbMale = (RadioButton) view7.findViewById(R.id.editmale);
		///////////////////////////////////////
		etCity = (EditText) view5.findViewById(R.id.editcity);
		etCountry = (EditText) view5.findViewById(R.id.editcountry);
		////////////////////////////////////////////////
		View view8 = view5.findViewById(R.id.llEditProfile5);
		View view9 = view8.findViewById(R.id.llEditProfile6);
		etUsername = (EditText) view9.findViewById(R.id.editusername);
		ivUserExist = (ImageView) view9.findViewById(R.id.editexistingUser);
		tvEnterNewUser = (TextView) view8.findViewById(R.id.edittvExisting);
		etPass1 = (EditText) view8.findViewById(R.id.editpassword);
		//////////////////////////////////
		View view10 = view8.findViewById(R.id.llEditProfile7);
		etPass2 = (EditText) view10.findViewById(R.id.editconfPassReg);
		cbConfPass = (CheckBox) view10.findViewById(R.id.editconfirmed);
		//////////////////////////////////////////////
		ivPersonalImage = (ImageView) view5.findViewById(R.id.editPersonal);
		btnUpadtaeImage = (Button) view5.findViewById(R.id.editupload);
		cbWantDonor = (CheckBox) view5.findViewById(R.id.editDonor);
		spinnerBlood = (Spinner) view5.findViewById(R.id.editBloodGroup);
		/////////////////////////////////////
		View view11 = view5.findViewById(R.id.llEditProfile8);
		// btnCancel = (Button) view5.findViewById(R.id.editcancel);
		btnUpdateUser = (Button) view5.findViewById(R.id.editupdate);
		/////////////////////////////////////////////////

	}

}
