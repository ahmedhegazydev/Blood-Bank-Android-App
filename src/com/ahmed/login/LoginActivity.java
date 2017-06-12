//package com.ahmed.login;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.HashMap;
//
//import com.ahmed.R;
//import com.ahmed.database.DBController;
//import com.ahmed.facebooklogin.FaceLogin;
//import com.ahmed.register.RegisterActivity;
//import com.ahmed.slidingmenu.MainActivity;
//import com.facebook.UiLifecycleHelper;
//import com.facebook.model.CreateGraphObject;
//import com.facebook.model.GraphUser;
//import com.facebook.widget.LoginButton;
//import com.facebook.widget.LoginButton.UserInfoChangedCallback;
//
////import android.R;
//import android.app.Activity;
//import android.app.AlertDialog;
//import android.content.Context;
//import android.content.DialogInterface;
//import android.content.Intent;
//import android.content.SharedPreferences;
//import android.content.SharedPreferences.Editor;
//import android.graphics.Color;
//import android.graphics.Typeface;
//import android.os.Bundle;
//import android.text.Editable;
//import android.text.InputType;
//import android.text.SpannableString;
//import android.text.TextWatcher;
//import android.text.method.HideReturnsTransformationMethod;
//import android.text.method.PasswordTransformationMethod;
//import android.text.style.UnderlineSpan;
//import android.util.TypedValue;
//import android.view.Gravity;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.View.OnClickListener;
//import android.view.ViewGroup.LayoutParams;
//import android.view.animation.Animation;
//import android.view.animation.AnimationUtils;
//import android.widget.Button;
//import android.widget.CheckBox;
//import android.widget.CompoundButton;
//import android.widget.CompoundButton.OnCheckedChangeListener;
//import android.widget.EditText;
//import android.widget.ImageView;
//import android.widget.ImageView.ScaleType;
//import android.widget.LinearLayout;
//import android.widget.ScrollView;
//import android.widget.TextView;
//import android.widget.Toast;
//import android.widget.ViewFlipper;
//
//public class LoginActivity extends Activity implements OnClickListener, OnCheckedChangeListener {
//
//	ScrollView scrollView = null;
//	LinearLayout linearLayout = null;
//	Context context = null;
//	ImageView login, register = null;
//	EditText username, password = null;
//	CheckBox showPassword = null, validUsername, validPass = null;
//	TextView forgetPass = null;
//	ViewFlipper vf = null;
//	Animation animation, animation2;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		// TODO Auto-generated method stub
//		super.onCreate(savedInstanceState);
//
//		// ifUserIsNew();
//		init();
//		getExtrasUnamePass();
//		
//		//new DBController(context).deleteAllRecords();
//
//	}
//
//	private void ifUserIsNew() {
//		// TODO Auto-generated method stub
//
//		// The user is new
//		sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
//		if (sharedpreferences.contains(name)) {
//			if (sharedpreferences.contains(pass)) {
//				Intent i = new Intent(this, RegisterActivity.class);
//				startActivity(i);
//			}
//		} else {
//			init();
//			getExtrasUnamePass();
//
//		}
//
//	}
//
//	private void getExtrasUnamePass() {
//		// TODO Auto-generated method stub
//
//		Intent intent = getIntent();
//		if (intent.hasExtra("username")) {
//			String username = intent.getStringExtra("username");
//			LoginActivity.this.username.setText(username);
//		}
//
//		if (intent.hasExtra("password")) {
//			String pass = intent.getStringExtra("password");
//			LoginActivity.this.password.setText(pass);
//		}
//
//	}
//
//	private void init() {
//		//////////////////////////////////
//		LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
//		LinearLayout.LayoutParams layoutParams3 = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
//				LayoutParams.WRAP_CONTENT);
//		LinearLayout.LayoutParams layoutParams33 = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
//				LayoutParams.WRAP_CONTENT);
//		layoutParams33.setMargins(5, 5, 5, 5);
//		LinearLayout.LayoutParams layoutParams4 = new LinearLayout.LayoutParams(
//				android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
//				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
//		layoutParams4.setMargins(0, 7, 0, 7);
//		///////////////////////////////////
//		context = getApplicationContext();
//		vf = new ViewFlipper(context);
//		vf.setLayoutParams(layoutParams);
//		this.setContentView(vf);
//		/////////////////////////////////////////
//
//		scrollView = new ScrollView(context);
//		scrollView.setLayoutParams(layoutParams);
//		scrollView.setBackgroundResource(R.drawable.solid2);
//		// scrollView.setBackgroundColor(Color.parseColor("#b9bedb"));
//		scrollView.setPadding(10, 10, 10, 10);
//
//		////////////////////////////////
//		LinearLayout boss = null;
//		boss = new LinearLayout(context);
//		boss.setOrientation(LinearLayout.VERTICAL);
//		boss.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//		boss.setPadding(4, 4, 4, 4);
//		boss.setLayoutParams(layoutParams);
//		boss.setBackgroundResource(R.drawable.round_and_border);
//
//		//////////////////////////////////////////
//		linearLayout = new LinearLayout(context);
//		linearLayout.setOrientation(LinearLayout.VERTICAL);
//		linearLayout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
//		linearLayout.setPadding(4, 4, 4, 4);
//		linearLayout.setLayoutParams(layoutParams);
//		linearLayout.setBackgroundResource(R.drawable.round_and_border);
//		////////////////////////////////////////////////
//		LinearLayout.LayoutParams layoutParams5 = new LinearLayout.LayoutParams(
//				android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
//				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, 1.0f);
//		layoutParams5.setMargins(3, 3, 3, 3);
//		LinearLayout layout4 = new LinearLayout(context);
//		layout4.setLayoutParams(layoutParams5);
//		layout4.setOrientation(LinearLayout.HORIZONTAL);
//		validUsername = new CheckBox(context);
//		validUsername.setEnabled(false);
//		validUsername
//				.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
//						android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, .2f));
//		username = new EditText(context);
//		username.setHint("Enter username");
//		username.addTextChangedListener(textWatcher1);
//		username.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
//				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, .8f));
//		layout4.addView(username);
//		layout4.addView(validUsername);
//		//////////////////////////////////////////////////
//		LinearLayout layout3 = new LinearLayout(context);
//		layout3.setLayoutParams(layoutParams5);
//		layout3.setOrientation(LinearLayout.HORIZONTAL);
//		validPass = new CheckBox(context);
//		validPass.setEnabled(false);
//		validPass.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
//				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, .2f));
//		password = new EditText(context);
//		password.setHint("Enter password");
//		password.addTextChangedListener(textWatcher2);
//		password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
//		password.setLayoutParams(new LinearLayout.LayoutParams(android.widget.LinearLayout.LayoutParams.WRAP_CONTENT,
//				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT, .8f));
//		layout3.addView(password);
//		layout3.addView(validPass);
//
//		///////////////////////////////////////
//		showPassword = new CheckBox(context);
//		showPassword.setText("Show password");
//		showPassword.setTypeface(Typeface.DEFAULT_BOLD);
//		showPassword.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
//		showPassword.setOnCheckedChangeListener(this);
//		showPassword.setTextColor(Color.DKGRAY);
//		showPassword.setLayoutParams(layoutParams4);
//		///////////////////////////////////
//		forgetPass = new TextView(context);
//		forgetPass.setOnClickListener(this);
//		LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
//				android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
//				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
//		params.setMargins(3, 3, 3, 3);
//		forgetPass.setLayoutParams(params);
//		SpannableString content = new SpannableString("Forget password ???");
//		content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//		forgetPass.setText(content);
//		forgetPass.setTypeface(Typeface.DEFAULT_BOLD);
//		forgetPass.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
//		///////////////////////////////
//		LinearLayout.LayoutParams layoutParams2 = new LinearLayout.LayoutParams(
//				android.widget.LinearLayout.LayoutParams.MATCH_PARENT,
//				android.widget.LinearLayout.LayoutParams.WRAP_CONTENT);
//		LinearLayout layout = new LinearLayout(context);
//		layout.setOrientation(LinearLayout.HORIZONTAL);
//		layout.setGravity(Gravity.CENTER_HORIZONTAL);
//		layout.setLayoutParams(layoutParams2);
//		login = new ImageView(context);
//		login.setBackgroundResource(R.drawable.login);
//		login.setOnClickListener(this);
//		login.setScaleType(ScaleType.CENTER_CROP);
//		login.setLayoutParams(layoutParams3);
//
//		register = new ImageView(context);
//		register.setBackgroundResource(R.drawable.add_user);
//		register.setOnClickListener(this);
//		register.setScaleType(ScaleType.CENTER_CROP);
//		register.setLayoutParams(layoutParams3);
//
//		layout.addView(login);
//		layout.addView(register);
//		/////////////////////////////////
//		linearLayout.addView(layout4);
//		linearLayout.addView(layout3);
//		linearLayout.addView(showPassword);
//		linearLayout.addView(layout);
//		linearLayout.addView(forgetPass);
//		//////////////////////////////////
//		LinearLayout linearLayout2 = null;
//		linearLayout2 = new LinearLayout(context);
//		linearLayout2.setOrientation(LinearLayout.VERTICAL);
//		linearLayout2.setPadding(4, 4, 4, 4);
//		linearLayout2.setLayoutParams(layoutParams33);
//		linearLayout2.setBackgroundResource(R.drawable.round_and_border);
//
//		getLoginButton(linearLayout2);
//
//		///////////////////
//		boss.addView(linearLayout);
//		boss.addView(linearLayout2);
//		///////////////////////////////////
//		scrollView.addView(boss);
//		///////////////////////
//		vf.addView(scrollView, 0);
//		vf.setDisplayedChild(0);
//
//	}
//
//	private void getLoginButton(LinearLayout linearLayout) {
//		// TODO Auto-generated method stub
//		LayoutInflater layoutInflater = LayoutInflater.from(getApplicationContext());
//		LinearLayout layout = (LinearLayout) layoutInflater.inflate(R.layout.face_and_google, null);
//		linearLayout.addView(layout);
//		/////////////////////////////////
//		layout.findViewById(R.id.tvFace).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				faceLogin();
//			}
//		});
//		layout.findViewById(R.id.tvGoogle).setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				googleLogin();
//			}
//		});
//
//	}
//
//	protected void googleLogin() {
//		// TODO Auto-generated method stub
//
//		
//		
//	}
//
//	protected void faceLogin() {
//		// TODO Auto-generated method stub
//		startActivity(new Intent(this, FaceLogin.class));
//		
//		
//		
//	}
//
//	SharedPreferences sharedpreferences = null;
//	public static final String MyPREFERENCES = "MyPrefs";
//	public static final String name = "nameKey";
//	public static final String pass = "passwordKey";
//	private UiLifecycleHelper uiHelper;
//
//	@Override
//	public void onSaveInstanceState(Bundle savedState) {
//		super.onSaveInstanceState(savedState);
//
//	}
//
//	@Override
//	public void onDestroy() {
//		super.onDestroy();
//	}
//
//	@Override
//	protected void onResume() {
//		// TODO Auto-generated method stub
//		super.onResume();
//
//	}
//
//	@Override
//	protected void onPause() {
//		// TODO Auto-generated method stub
//		super.onPause();
//	}
//
//	@Override
//	public void onClick(View v) {
//		// TODO Auto-generated method stub
//		if (v.equals(login)) {
//			login();
//		} else {
//			if (v.equals(register)) {
//				register();
//			} else {
//				//forgetPassword();
//			}
//		}
//
//	}
//
//	private void forgetPassword() {
//		// TODO Auto-generated method stub
//		animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
//		animation2 = AnimationUtils.loadAnimation(context, android.R.anim.slide_in_left);
//		//////////////////////////////////////////
//		LayoutInflater layoutInflater = LayoutInflater.from(context);
//		LinearLayout forgetPass = (LinearLayout) layoutInflater.inflate(R.layout.forgetpassword, null);
//		vf.setInAnimation(animation2);
//		vf.addView(forgetPass, 1);
//		vf.setDisplayedChild(1);
//		//////////////////////////////////////////////////
//		forgetPassword2(forgetPass);
//	}
//
//	TextWatcher textWatcher1 = new TextWatcher() {
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//			checkUsernameInDatabase(s.toString().trim());
//		}
//
//		private void checkUsernameInDatabase(String s) {
//			DBController controller = new DBController(context);
//			String selectQuery = "select * from " + controller.tablename1 + " where username  = '" + s.toString().trim()
//					+ "'      ";
//			ArrayList<HashMap<String, String>> arrayList = controller.getAllUsers(selectQuery);
//			if (arrayList.isEmpty()) {
//				validUsername.setChecked(false);
//				boo1 = false;
//			} else {
//				validUsername.setChecked(true);
//				boo1 = true;
//			}
//
//		}
//	};
//	TextWatcher textWatcher2 = new TextWatcher() {
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//			checkPassInDatabase(s.toString().trim());
//		}
//
//		private void checkPassInDatabase(String s) {
//			// TODO Auto-generated method stub
//			DBController controller = new DBController(context);
//			String selectQuery = "select * from " + controller.tablename1 + " where password  = '" + s.toString().trim()
//					+ "'      ";
//			ArrayList<HashMap<String, String>> arrayList = controller.getAllUsers(selectQuery);
//			if (arrayList.isEmpty()) {
//				validPass.setChecked(false);
//				boo2 = false;
//			} else {
//				validPass.setChecked(true);
//				boo2 = true;
//			}
//		}
//	};
//
//	boolean boo1 = false, boo2 = false;
//	CheckBox cbOkUserExist = null;
//	
//	TextWatcher twExistingUser = new TextWatcher() {
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//			// TODO Auto-generated method stub
//			if (s.toString().trim().length() != 0) {
//				DBController controller = new DBController(context);
//				String selectQuery = "select * from " + controller.tablename1 + " where username  = '"
//						+ s.toString().trim() + "' ";
//				ArrayList<HashMap<String, String>> hashMaps = controller.getAllUsers(selectQuery);
//				if (!hashMaps.isEmpty()) {
//					
//					cbOkUserExist.setChecked(true);
//				} else {
//					
//					cbOkUserExist.setChecked(false);
//				}
//
//			}
//		}
//	};
//	
//	CheckBox confirmed = null;
//	TextWatcher textWatcherPass = new TextWatcher() {
//
//		@Override
//		public void onTextChanged(CharSequence s, int start, int before, int count) {
//		}
//
//		@Override
//		public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//		}
//
//		@Override
//		public void afterTextChanged(Editable s) {
//			// TODO Auto-generated method stub
//			if (s.toString().trim().length() != 0
//					&& (pass1.getText().toString().trim().equals(pass2.getText().toString().trim()))) {
//				confirmed.setChecked(true);
//				
//			} else {
//				confirmed.setChecked(false);
//			}
//		}
//	};
//	
//	EditText pass1, pass2;
//	private void forgetPassword2(LinearLayout forgetPass2) {
//		// TODO Auto-generated method stub
////		View view = forgetPass2.findViewById(R.id.llForget);
////		final EditText username = (EditText) view.findViewById(R.id.etUnForget);
////		cbOkUserExist = (CheckBox) view.findViewById(R.id.cbForget);
////		username.addTextChangedListener(twExistingUser);
////		pass1 = (EditText) forgetPass2.findViewById(R.id.pass1);
////		View view2 = forgetPass2.findViewById(R.id.llForget2);
////		confirmed = (CheckBox) view2.findViewById(R.id.cbConfPassForget);
////		pass2 = (EditText) view2.findViewById(R.id.pass2);
////		pass2.addTextChangedListener(textWatcherPass);
////		pass1.addTextChangedListener(textWatcherPass);
////		//pass1.addTextChangedListener(textWatcher1);
////		//pass2.addTextChangedListener(textWatcher2);
////		Button edit = (Button) forgetPass2.findViewById(R.id.editPass);
////		ImageView cancel = (ImageView) forgetPass2.findViewById(R.id.cancel);
////		///////////////////////////////
////		final String passwo1 = pass1.getText().toString().trim();
////		final String passwo2 = pass2.getText().toString().trim();
////		//////////////////////////////
////		edit.setOnClickListener(new OnClickListener() {
////			@Override
////			public void onClick(View v) {
////				if (username.getText().toString().equals("")) {
////					Toast.makeText(context, "Enter username", Toast.LENGTH_SHORT).show();
////					return;
////				}
////				if (!cbOkUserExist.isChecked()) {
////					Toast.makeText(context, "Invalid username", Toast.LENGTH_SHORT).show();
////					return;
////				}
////				if (pass1.getText().toString().trim().length() == 0) {
////					Toast.makeText(context, "Enter new password", Toast.LENGTH_SHORT).show();
////					return;
////				}
////				if (pass2.getText().toString().trim().length()==0) {
////					Toast.makeText(context, "Confirm password", Toast.LENGTH_SHORT).show();
////					return;
////				}
////				if (!confirmed.isChecked()) {
////					Toast.makeText(context, "Passwords are not matched", Toast.LENGTH_SHORT).show();
////					return;
////				}
////				
////				
////				editPassword(username.getText().toString(), pass1.getText().toString());
////			}
////		});
////		/////////////////////////////////
////		cancel.setOnClickListener(new OnClickListener() {
////			@Override
////			public void onClick(View v) {
////				if (pass1.length() == 0 && pass2.length() == 0) {
////					vf.setOutAnimation(animation);
////					vf.setDisplayedChild(0);
////				} else {
////
////					creatAlert(LoginActivity.this, "Do u want to leave this page ??");
////				}
////
////			}
////
////		});
//
//	}
//
//	protected void editPassword(String string, String passwo1) {
//		// TODO Auto-generated method stub
//		
//		
//		DBController dbController= new DBController(context);
//		dbController.updatePassword(string, passwo1);
//		Toast.makeText(context, "Updated Successfully", Toast.LENGTH_SHORT).show();
//		
//		
//		
//		
//		
//	}
//
//	private void creatAlert(Context context, String string) {
//		AlertDialog.Builder builder = new AlertDialog.Builder(context);
//		builder.setTitle("Warning");
//		builder.setMessage(string);
//		builder.setPositiveButton("Leave", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				vf.setOutAnimation(animation);
//				vf.setDisplayedChild(0);
//			}
//		});
//		builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				dialog.cancel();
//			}
//		});
//		builder.setCancelable(false);
//		AlertDialog alertDialog = null;
//		alertDialog = builder.create();
//		alertDialog.show();
//
//	}
//
//	private void register() {
//		// TODO Auto-generated method stub
//		startActivity(new Intent(context, RegisterActivity.class));
//	}
//
//	private void login() {
//		// TODO Auto-generated method stub
//		String username = this.username.getText().toString().trim();
//		String password = this.password.getText().toString().trim();
//		if (username.length() == 0 && password.length() == 0) {
//			Toast.makeText(context, "please enter Username and Password", Toast.LENGTH_SHORT).show();
//		} else {
//			loginWithValidInfo(username, password);
//		}
//
//	}
//
//	private void loginWithValidInfo(String username2, String password2) {
//		// TODO Auto-generated method stub
//		DBController controller = new DBController(context);
//		ArrayList<HashMap<String, String>> arrayList = controller.getAllUsers(username2, password2);
//		if (arrayList.isEmpty()) {
//			//
//			Toast.makeText(context, "Invalid Username or Password", Toast.LENGTH_SHORT).show();
//		} else {
//			Intent i = new Intent(LoginActivity.this, MainActivity.class);
//			i.putExtra("username", username.getText().toString().trim());
//			i.putExtra("password", password.getText().toString().trim());
//			startActivity(i);
//		}
//		// if (boo1 && boo2) {
//		// startActivity(new Intent(LoginActivity.this, MainActivity.class));
//		// }
//	}
//
//	@Override
//	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//		// TODO Auto-generated method stub
//
//		if (!isChecked) {
//			// show password
//			password.setTransformationMethod(PasswordTransformationMethod.getInstance());
//		} else {
//			// hide password
//			password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
//		}
//
//	}
//
//}
