package com.ahmed.login2;


import java.util.ArrayList;
import java.util.HashMap;

import com.ahmed.R;
import com.ahmed.database.DBController;
//import com.ahmed.login.LoginActivity;
import com.ahmed.slidingmenu.MainActivity;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.SpannableString;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

public class FragmentLogin extends Fragment implements
OnClickListener, OnCheckedChangeListener {
	View viewRoot = null;
	Context context = null;
	EditText etUsername, etPassword = null;
	CheckBox cbShowPass, cbValidUsername, cbValidPass;
	TextView tvForgetPassword = null;
	Button btnLogin = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		viewRoot = inflater.inflate(R.layout.activity_login, container, false);// False
																				// here
																				// is
																				// important
																				// as
		// There is another overriden inflate method without boolean param ,
		// without it will give error as app.v4.

		context = container.getContext();
		init();

		return viewRoot;
	}
	
	/**
	 * Getting the views firstly, For processing them
	 */

	private void init() {

		View view23 = viewRoot.findViewById(R.id.llLogin1);
		ScrollView view = (ScrollView) view23.findViewById(R.id.svLogin3);
		view.setVerticalScrollBarEnabled(false);
		view.setHorizontalScrollBarEnabled(false);
		////////////////////////
		View view22 = view.findViewById(R.id.llLogin12);
		btnLogin = (Button) view22.findViewById(R.id.btnLoginButton);
		tvForgetPassword = (TextView) view22.findViewById(R.id.tvLoginForgetPass);
		//////////////////////////////////////////////
		View view2 = view22.findViewById(R.id.llLogin2);
		View view3 = view2.findViewById(R.id.llLogin3);
		etUsername = (EditText) view3.findViewById(R.id.etLoginUsername);
		cbValidUsername = (CheckBox) view3.findViewById(R.id.cbLoginUserValid);
		////////////////////////////////////////
		View view4 = view22.findViewById(R.id.llLogin4);
		etPassword = (EditText) view4.findViewById(R.id.etLoginPassword);
		cbValidPass = (CheckBox) view4.findViewById(R.id.cbLoginPassValid);
		/////////////////////////////////////////
		cbShowPass = (CheckBox) view2.findViewById(R.id.cbLoginShowPass);
		///////////////////////////////////////
		btnLogin.setOnClickListener(this);
		etUsername.addTextChangedListener(textWatcher1);
		etPassword.addTextChangedListener(textWatcher2);
		etPassword.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if (keyCode == KeyEvent.KEYCODE_ENTER) {
					login();
				}
				else {
					return false;
				}
				
				return true;
			}
		});
		////////////////////////////////////////////
		SpannableString content = new SpannableString("Oops forgot my password");
		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
		tvForgetPassword.setText(content);
		tvForgetPassword.setOnClickListener(this);
		////////////////////////////////////////////
		cbShowPass.setOnCheckedChangeListener(this);
		////////////////////

		AdView adView = (AdView) view22.findViewById(R.id.adViewLogin);
		AdRequest adRequest = new AdRequest.Builder()
				.addTestDevice("025ACB586FDC1C445457E7C8021E203B").
				build();
		adView.loadAd(adRequest);
		adView.setAdListener(adListener);
		
		
		
		

	}

	
	
	AdListener adListener = new AdListener() {

		@Override
		public void onAdClosed() {
			super.onAdClosed();
			Log.i("Ahmed", "ahmed-ad Closed");
			
			
		}

		@Override
		public void onAdFailedToLoad(int errorCode) {
			super.onAdFailedToLoad(errorCode);
			Log.i("Ahmed", "ahmed-ad FailedToLoaded");
			
			
		}

		@Override
		public void onAdLeftApplication() {
			super.onAdLeftApplication();
			
			Log.i("Ahmed", "ahmed-ad LeftApplication");
			
		}

		@Override
		public void onAdLoaded() {
			super.onAdLoaded();
			
			Log.i("Ahmed", "ahmed-ad loaded");
			
		}

		@Override
		public void onAdOpened() {
			super.onAdOpened();
			Log.i("Ahmed", "ahmed-ad Opened");
			
			
		}
		
	};
	
	
	TextWatcher textWatcher1 = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			checkUsernameInDatabase(s.toString().trim());
		}

		private void checkUsernameInDatabase(String s) {
			DBController controller = new DBController(context);
			String selectQuery = "select * from " + controller.tablename1 + " where username  = '" + s.toString().trim()
					+ "'      ";
			ArrayList<HashMap<String, String>> arrayList = controller.getAllUsers(selectQuery);
			if (arrayList.isEmpty()) {
				cbValidUsername.setChecked(false);
				// boo1 = false;
			} else {
				cbValidUsername.setChecked(true);
				// boo1 = true;
			}

		}
	};

	TextWatcher textWatcher2 = new TextWatcher() {
		@Override
		public void onTextChanged(CharSequence s, int start, int before, int count) {
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
		}

		@Override
		public void afterTextChanged(Editable s) {
			checkPassInDatabase(s.toString().trim());
		}

		private void checkPassInDatabase(String s) {
			DBController controller = new DBController(context);
			String selectQuery = "select * from " + controller.tablename1 + " where password  = '" + s.toString().trim()
					+ "'      ";
			ArrayList<HashMap<String, String>> arrayList = controller.getAllUsers(selectQuery);
			if (arrayList.isEmpty()) {
				cbValidPass.setChecked(false);
				// boo2 = false;
			} else {
				cbValidPass.setChecked(true);
				// boo2c= true;
			}
		}
	};

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.equals(btnLogin)) {
			login();
		} else {

		}

		if (v.equals(tvForgetPassword)) {
			forgetPassword();
		}

	}

	Animation inFromBottom, outToTop = null;

	LinearLayout forgetPass = null;

	/**
	 * This method set up the views of froget password
	 * for enabling the user to edit his password
	 * 
	 */
	private void forgetPassword() {
		// TODO Auto-generated method stub
		inFromBottom = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_from_bottom);
		outToTop = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_to_top);
		////////////////////////
		LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
		forgetPass = (LinearLayout) layoutInflater.inflate(R.layout.forgetpassword, null);
		////////////////////////
		forgetPass.startAnimation(inFromBottom);
		((RelativeLayout) viewRoot).addView(forgetPass);
		/////////////////////
		forgetPass2(forgetPass);

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
					cbOkUserExist.setChecked(true);
				} else {
					cbOkUserExist.setChecked(false);
				}

			} else {
				cbOkUserExist.setChecked(false);
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
					&& (pass1.getText().toString().trim().equals(pass2.getText().toString().trim()))) {
				confirmed.setChecked(true);

			} else {
				confirmed.setChecked(false);
			}
		}
	};

	EditText pass1, pass2;
	CheckBox cbOkUserExist = null, confirmed = null;

	private void forgetPass2(LinearLayout forgetPass2) {
		View view = forgetPass2.findViewById(R.id.llForget1);
		View view2 = view.findViewById(R.id.llForget2);

		View view3 = view2.findViewById(R.id.llForget3);

		View view5 = view3.findViewById(R.id.llForget4);
		final EditText username = (EditText) view5.findViewById(R.id.etUnForget);
		cbOkUserExist = (CheckBox) view5.findViewById(R.id.cbForget);
		username.addTextChangedListener(twExistingUser);

		pass1 = (EditText) view3.findViewById(R.id.pass1);

		View view4 = view3.findViewById(R.id.llForget5);
		confirmed = (CheckBox) view4.findViewById(R.id.cbConfPassForget);
		pass2 = (EditText) view4.findViewById(R.id.pass2);

		pass2.addTextChangedListener(textWatcherPass);
		pass1.addTextChangedListener(textWatcherPass);
		// pass1.addTextChangedListener(textWatcher1);
		// pass2.addTextChangedListener(textWatcher2);

		View view6 = view3.findViewById(R.id.llForget6);
		Button edit = (Button) view6.findViewById(R.id.editPass);
		ImageView cancel = (ImageView) view6.findViewById(R.id.cancel);
		///////////////////////////////
		edit.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (username.getText().toString().equals("")) {
					Toast.makeText(context, "Enter username", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!cbOkUserExist.isChecked()) {
					Toast.makeText(context, "Invalid username", Toast.LENGTH_SHORT).show();
					return;
				}
				if (pass1.getText().toString().trim().length() == 0) {
					Toast.makeText(context, "Enter new password", Toast.LENGTH_SHORT).show();
					return;
				}
				if (pass2.getText().toString().trim().length() == 0) {
					Toast.makeText(context, "Confirm password", Toast.LENGTH_SHORT).show();
					return;
				}
				if (!confirmed.isChecked()) {
					Toast.makeText(context, "Passwords are not matched", Toast.LENGTH_SHORT).show();
					return;
				}

				editPassword(username.getText().toString(), pass1.getText().toString());
			}
		});
		/////////////////////////////////
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (pass1.getText().toString().trim().length() == 0
						&& pass2.getText().toString().trim().length() == 0) {
					forgetPass.startAnimation(outToTop);
					((RelativeLayout) viewRoot).removeView(forgetPass);
					viewRoot.invalidate();
				} else {
					creatAlert(getActivity(), "Do u want to leave this page ??");
				}

			}

		});

	}

	/**
	 * This method updates the using the matched username from user
	 * in the database table
	 * 
	 * @param string
	 * @param passwo1
	 */
	protected void editPassword(String string, String passwo1) {

		DBController dbController = new DBController(context);
		dbController.updatePassword(string, passwo1);
		Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();

		forgetPass.startAnimation(outToTop);
		((RelativeLayout) viewRoot).removeView(forgetPass);

	}

	/**
	 * This method will warn the user whn he is leaving from the current page
	 * but there is data in current page as the view didn't closed normally
	 * 
	 * @param context
	 * @param string
	 */
	private void creatAlert(Context context, String string) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setTitle("Warning");
		builder.setIcon(R.drawable.warning);
		builder.setMessage(string);
		builder.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				forgetPass.startAnimation(outToTop);
				((RelativeLayout) viewRoot).removeView(forgetPass);
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
		if (alertDialog.isShowing()) {
			return;
		}
		alertDialog.show();

	}

	/**
	 * Check if the username and password already exist in out users database tabel
	 * if yes, The user can go to the home page and see our application
	 * 
	 */
	private void login() {
		String username = this.etUsername.getText().toString().trim();
		String password = this.etPassword.getText().toString().trim();
		if (username.length() == 0 && password.length() == 0) {
			Toast.makeText(context, "please enter Username and Password", Toast.LENGTH_SHORT).show();
		} else {
			loginWithValidInfo(username, password);
		}

	}

	private void loginWithValidInfo(String username2, String password2) {
		DBController controller = new DBController(context);
		ArrayList<HashMap<String, String>> arrayList = controller.getAllUsers(username2, password2);
		if (arrayList.isEmpty()) {
			Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
		} else {
			getActivity().finish();
			Intent i = new Intent(context, MainActivity.class);
			i.putExtra("username", etUsername.getText().toString().trim());
			i.putExtra("password", etPassword.getText().toString().trim());
			startActivity(i);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub

		if (!isChecked) {
			// show password
			etPassword.setTransformationMethod(PasswordTransformationMethod.getInstance());
		} else {
			// hide password
			etPassword.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
		}

	}

}
