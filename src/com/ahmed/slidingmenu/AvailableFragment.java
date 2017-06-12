package com.ahmed.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;

import com.ahmed.R;
import com.ahmed.database.DBController;
import com.ahmed.login2.LoginActivity2;
import com.ahmed.rounded_imageview.RoundedImageView;
import com.google.android.gms.appindexing.AppIndexApi.AppIndexingLink;
import com.google.android.gms.drive.internal.al;

import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class AvailableFragment extends Fragment implements OnClickListener, OnCheckedChangeListener {

	View rootView = null;
	Context context = null;
	ArrayList<HashMap<String, String>> arrayList = null;
	DBController dbController = null;
	LinearLayout layout = null;
	ArrayList<String> bloodGroups = new ArrayList<String>();
	String[] spinnerEelements = null;
	int count = 0;
	ArrayList<Integer> integers = new ArrayList<Integer>();
	//////////////////////////////
	Spinner bloadGroups = null;
	Button sendRequest = null;

	public AvailableFragment() {
	}

	View view5 = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_available, container, false);

		View view = rootView.findViewById(R.id.avail1);
		View view2 = view.findViewById(R.id.avail2);
		View view3 = view2.findViewById(R.id.avail3);
		View view4 = view3.findViewById(R.id.avail4);

		view5 = view2.findViewById(R.id.avail5);

		context = container.getContext();

		init(view4);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		// addViews(spinnerEelements, integers);

	}

	/**
	 * 
	 * @param view3
	 */
	private void init(View view3) {
		// TODO Auto-generated method stub
		layout = (LinearLayout) view3;
		// get all blood groups
		dbController = new DBController(context);
		String selectQuery = "select * from " + dbController.tablename1;
		arrayList = dbController.getAllUsers(selectQuery);
		if (!arrayList.isEmpty()) {

			// get all registereed blood groups from daabase
			for (int i = 0; i < arrayList.size(); i++) {
				HashMap<String, String> hashMap = arrayList.get(i);
				String bloodGroup = hashMap.get("bloodgroup");
				bloodGroups.add(bloodGroup);

			}

			/// get all elements of spinner blooad group as may be 1000000
			/// elements
			spinnerEelements = context.getResources().getStringArray(R.array.spinnerArray);

			// count them
			// i = 1 to reject Select Group at index 0 in spinner
			for (int i = 0; i < spinnerEelements.length; i++) {
				for (int j = 0; j < bloodGroups.size(); j++) {
					if (spinnerEelements[i].equals(bloodGroups.get(j).toString())) {
						count++;
					}
				}
				integers.add(count);
				count = 0;

			}

			ArrayList<Integer> integers2 = new ArrayList<Integer>();
			for (int i = 1; i < integers.size(); i++) {
				integers2.add(integers.get(i));
			}
			// Toast.makeText(context, integers2.toString(),
			// Toast.LENGTH_LONG).show();
			addViews(spinnerEelements, integers2);

		} else {
			// do nothing

		}

		/////////////////////////////////////////////////////////
		sendRequest = (Button) view5.findViewById(R.id.sendRequest);
		sendRequest.setOnClickListener(this);
		///////

	}

	/**
	 * 
	 * @param spinnerEelements2
	 * @param integers
	 */
	private void addViews(String[] spinnerEelements2, ArrayList<Integer> integers) {

		ArrayList<String> strings = new ArrayList<String>();
		for (int i = 1; i < spinnerEelements2.length; i++) {
			strings.add(spinnerEelements2[i]);
		}

		// Show the list for user
		for (int i = 0; i < strings.size(); i++) {

			LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT, 1.0f);
			layoutParams.setMargins(3, 3, 3, 3);
			//////////////////////////////
			LinearLayout layout = new LinearLayout(context);
			layout.setOrientation(LinearLayout.HORIZONTAL);
			layout.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
			layout.setLayoutParams(layoutParams);
			layout.setPadding(8, 8, 8, 8);
			layout.setBackgroundResource(R.drawable.round_and_border);
			// layout.setBackgroundResource(R.drawable);
			///////////////////////////

			TextView name = new TextView(context);
			name.setText(strings.get(i));
			name.setPadding(5, 5, 5, 5);
			name.setTypeface(Typeface.DEFAULT_BOLD);
			name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
			// name.setBackgroundResource(R.drawable.round_and_border);
			name.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, .5f));
			/////////////////////////////////////
			// RelativeLayout layout2 = new RelativeLayout(context);
			// layout2.setLayoutParams(new
			///////////////////////////////////// LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT,
			///////////////////////////////////// .2f));
			View view = LayoutInflater.from(context).inflate(R.layout.rounded_imageview, null);
			view.setLayoutParams(
					new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, .5f));
			// view.setPadding(20, 5, 5, 5);
			View view2 = view.findViewById(R.id.llRound);
			// ImageView iv = (ImageView) view2.findViewById(R.id.ivRound);
			TextView view3 = (TextView) view2.findViewById(R.id.tvCount);
			if (i < integers.size()) {
				view3.setText(integers.get(i) + "");// to convert int to string
			}
			///////////////////////////////
			layout.addView(name);
			layout.addView(view);
			///////////////
			this.layout.addView(layout);

		}

	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public boolean isNetworkAvailable(final Context context) {
		final ConnectivityManager connectivityManager = ((ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE));
		return connectivityManager.getActiveNetworkInfo() != null
				&& connectivityManager.getActiveNetworkInfo().isConnected();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.equals(sendRequest)) {
			// checkInternetConn();
			userSendRequestAlert();
		} else {

		}
	}

	boolean toggle = false;
	LinearLayout view2;
	LinearLayout.LayoutParams paraCb = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
			LayoutParams.MATCH_PARENT, .1f);
	LinearLayout.LayoutParams paraSpinner = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,
			LayoutParams.MATCH_PARENT, .9f);
	LinearLayout.LayoutParams paraMainLayout = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
			LayoutParams.MATCH_PARENT, 1f);

	/**
	 * 
	 */
	private void userSendRequestAlert() {

		LayoutInflater layoutInflater = getActivity().getLayoutInflater();
		View view = layoutInflater.inflate(R.layout.send_requ_alert, null);
		AlertDialog.Builder builder = new AlertDialog.Builder(context).setView(view);
		AlertDialog alertDialog = builder.create();
		if (alertDialog.isShowing()) {
			return;
		} else {
			alertDialog.show();
		}

		view2 = (LinearLayout) view.findViewById(R.id.alerBlaBla1);
		view2.setLayoutParams(paraMainLayout);

		final CheckBox cbToggleViews = new CheckBox(context);
		cbToggleViews.setOnCheckedChangeListener(this);
		cbToggleViews.setLayoutParams(paraCb);

		final EditText quan = (EditText) view.findViewById(R.id.alertQuan);

		final Spinner spinner = new Spinner(context);
		spinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
				getActivity().getResources().getStringArray(R.array.spinnerArray)));
		spinner.setLayoutParams(paraSpinner);
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
				selectedGroup = new String(spinner.getSelectedItem().toString());
				Toast.makeText(getActivity(), selectedGroup, Toast.LENGTH_SHORT).show();
			}

			@Override
			public void onNothingSelected(AdapterView<?> parent) {
			}
		});

		view2.addView(spinner);// default
		view2.addView(cbToggleViews);

		name = new EditText(context);
		name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
		name.setHint("Enter Blood Name");
		name.setLayoutParams(paraSpinner);

		Button alertSendRequ = (Button) view.findViewById(R.id.alertSendRequest);
		alertSendRequ.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!cbToggleViews.isChecked()) {// spinner

					if (spinner.getSelectedItemPosition() == 0) {
						Toast.makeText(context, "Please, Select blood group !!", Toast.LENGTH_SHORT).show();
						return;
					}
					if (quan.getText().toString().isEmpty()) {
						Toast.makeText(context, "Quantity is empty !!", Toast.LENGTH_SHORT).show();
						return;
					}
					dbController.insertRequest(selectedGroup, quan.getText().toString());
					Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show();

				} else {
					// edittext
					if (name.getText().toString().trim().length() == 0) {
						Toast.makeText(context, "Blood group is empty !!", Toast.LENGTH_SHORT).show();
						return;
					}
					if (quan.getText().toString().isEmpty()) {
						Toast.makeText(context, "Quantity is empty !!", Toast.LENGTH_SHORT).show();
						return;
					}
					dbController.insertRequest(name.getText().toString(), quan.getText().toString());
					Toast.makeText(context, "Sent", Toast.LENGTH_SHORT).show();
				}
			}
		});

	}

	/**
	 * 
	 * @param title
	 * @param msg
	 * @param text1
	 * @param clickListener1
	 */
	private void createAlert(String title, String msg, String text1, DialogInterface.OnClickListener clickListener1) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		AlertDialog alertDialog = null;
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setCancelable(true);
		builder.setIcon(R.drawable.warning);
		builder.setPositiveButton(text1, clickListener1);
		alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(true);

		if (alertDialog.isShowing()) {
			return;
		}
		alertDialog.show();

	}

	/**
	 * 
	 */
	DialogInterface.OnClickListener clickListener2 = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
			saveInServerDatabase();
		}
	};

	/**
	 * 
	 * @param msg
	 * @param title
	 * @param text1
	 * @param clickListener1
	 * @param text2
	 * @param clickListener2
	 */
	private void createAlert1(String msg, String title, String text1, DialogInterface.OnClickListener clickListener1,
			String text2, DialogInterface.OnClickListener clickListener2) {
		// TODO Auto-generated method stub
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		AlertDialog alertDialog = null;
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setCancelable(true);
		builder.setIcon(R.drawable.warning);
		builder.setPositiveButton(text1, clickListener1);
		builder.setNegativeButton(text2, clickListener2);
		alertDialog = builder.create();
		alertDialog.setCanceledOnTouchOutside(true);

		if (alertDialog.isShowing()) {
			return;
		}
		alertDialog.show();

	}

	protected void saveInServerDatabase() {
		// TODO Auto-generated method stub

	}

	DialogInterface.OnClickListener clickListener1 = new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.cancel();
		}
	};

	/**
	 * 
	 */
	private void checkInternetConn() {
		if (!isNetworkAvailable(getActivity())) {
			createAlert("Warning", "No Internet Connection", "OK", clickListener1);
		} else {

			createAlert1("Warning", "Do u want to save in server database ???", "OK", clickListener1, "NO,Thanks",
					clickListener1);

		}

	}

	private void sendRequest() {

	}

	public String selectedGroup = null;
	EditText name = null;

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

		if (isChecked) {

			name = new EditText(context);
			name.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_AUTO_COMPLETE);
			name.setHint("Enter Blood Name");
			name.setLayoutParams(paraSpinner);
			name.requestFocus();

			view2.removeViewAt(0);
			view2.addView(name, 0);

		} else {

			final Spinner spinner = new Spinner(context);
			spinner.setAdapter(new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
					getActivity().getResources().getStringArray(R.array.spinnerArray)));
			spinner.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
			spinner.setLayoutParams(paraSpinner);
			spinner.setOnItemSelectedListener(new OnItemSelectedListener() {
				@Override
				public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
					selectedGroup = new String(spinner.getSelectedItem().toString());
				}

				@Override
				public void onNothingSelected(AdapterView<?> parent) {

				}
			});

			view2.removeViewAt(0);
			view2.addView(spinner, 0);

		}

	}

}
