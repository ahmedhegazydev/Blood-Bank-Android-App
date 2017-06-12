package com.ahmed.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.StringTokenizer;

import com.ahmed.R;
import com.ahmed.database.DBController;
import com.ahmed.rounded_imageview.RoundedImageView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;

@SuppressLint("NewApi")
public class SearchForFragment extends Fragment implements OnItemSelectedListener, OnItemClickListener {

	Context context = null;
	Spinner spinnerSearch = null;
	ListView lvDonors = null;
	ArrayAdapter<String> adapter1 = null, adapter2;

	public SearchForFragment() {

	}

	LinearLayout rootView = null;
	ScrollView sv = null;
	LinearLayout ll2 = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

		View view = (View) inflater.inflate(R.layout.fragment_search_donor, container, false);
		RelativeLayout layout = (RelativeLayout) view.findViewById(R.id.mainSearch);
		View view2 = layout.findViewById(R.id.svSearch);
		rootView = (LinearLayout) view2.findViewById(R.id.childSearch);
		context = container.getContext();

		sv = (ScrollView) rootView.findViewById(R.id.svSearch1);
		ll2 = (LinearLayout) sv.findViewById(R.id.llSearch);

		init(context, rootView);
		return view;
	}

	/**
	 * getting the spinner view that will be used for searching by user for
	 * accessing it in future
	 * 
	 * @param context
	 * @param rootView
	 */
	private void init(Context context, LinearLayout rootView) {
		spinnerSearch = (Spinner) rootView.findViewById(R.id.searchDonorSpinner);
		ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(context,
				android.R.layout.simple_dropdown_item_1line, getResources().getStringArray(R.array.spinnerArray));
		spinnerSearch.setAdapter(spinnerArrayAdapter);
		spinnerSearch.setOnItemSelectedListener(this);
		////////////////////////////////
		// lvDonors = (ListView) rootView.findViewById(R.id.donorList);
		// adapter1 = new ArrayAdapter<String>(context,
		// android.R.layout.simple_list_item_1, arr);
		// lvDonors.setAdapter(adapter1);
		// lvDonors.setOnItemClickListener(this);

	}

	ArrayList<String> arr = new ArrayList<String>(), phones = new ArrayList<String>();

	/**
	 * Getting all donors using the selected name from spinner view and set view
	 * for each that has all info that user want to know about this donor
	 * 
	 * @param string
	 */
	protected void getAllDonors(String string) {
		// TODO Auto-generated method stub
		DBController controller = new DBController(context);
		String selectQuery = "select * from " + controller.tablename1 + " where bloodgroup  = '" + string + "' ";
		ArrayList<HashMap<String, String>> hashMaps = controller.getAllUsers(selectQuery);
		final ArrayList<HashMap<String, byte[]>> hashMaps2 = controller.getAllImagesBySql(selectQuery);
		ArrayList<byte[]> bs = new ArrayList<byte[]>();
		final ArrayList<String> firstnames = new ArrayList<String>();
		final ArrayList<String> lastnames = new ArrayList<String>();
		final ArrayList<String> bloodGroups = new ArrayList<String>();

		if (!hashMaps.isEmpty()) {

			// HashMap<String, String> hashMap = hashMaps.get(0);
			// Toast.makeText(context, hashMap.get("firstname") + " " +
			// hashMap.get("lastname"), Toast.LENGTH_SHORT).show();
			for (int i = 0; i < hashMaps.size(); i++) {
				HashMap<String, String> hashMap = hashMaps.get(i);
				// Toast.makeText(context, hashMap.get("firstname") + " " +
				// hashMap.get("lastname"), Toast.LENGTH_SHORT).show();
				firstnames.add(hashMap.get("firstname"));
				lastnames.add(hashMap.get("lastname"));
				phones.add(hashMap.get("phone"));
				bloodGroups.add(hashMap.get("bloodgroup"));
				// bs.add(hashMap.get("iamge"));
				// Toast.makeText(getActivity(), hashMap.get("phone"),
				// Toast.LENGTH_SHORT).show();
			}

			ll2.removeAllViews();
			// ll2 = (LinearLayout) sv.findViewById(R.id.llSearch);
			for (int i = 0; i < firstnames.size(); i++) {

				LinearLayout layout = new LinearLayout(getActivity());
				layout.setOrientation(LinearLayout.HORIZONTAL);
				layout.setGravity(Gravity.CENTER_VERTICAL);
				//////////////////////////////////////
				ImageView imageView = new ImageView(getActivity());
				imageView.setImageResource(R.drawable.user);
				imageView.setScaleType(ScaleType.CENTER_CROP);
				
				/////////////////////////////////////////
				TextView textView = new TextView(context);
				textView.setText(firstnames.get(i) + "  " + lastnames.get(i));
				textView.setTag(phones.get(i));
				textView.setTypeface(Typeface.DEFAULT_BOLD);
				textView.setTextColor(Color.DKGRAY);
				LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
						LayoutParams.WRAP_CONTENT);
				layoutParams.setMargins(3, 3, 3, 3);
				textView.setLayoutParams(layoutParams);
				textView.setBackgroundResource(R.drawable.round_and_border);
				textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
				final int j = i;

				layout.setPadding(6, 6, 6, 6);
				layout.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// makeCall(v.getTag().toString());
						byte[] bs = null;
						HashMap<String, byte[]> arrayList = hashMaps2.get(j);
						if (arrayList.get("image") != null) {
							bs = arrayList.get("image");
						}
						// showInfo(null, "", "", "", "");
						showInfo(bs, firstnames.get(j), lastnames.get(j), phones.get(j), bloodGroups.get(j));
					}
				});
				
				/////////////////////////
				layout.addView(imageView);
				layout.addView(textView);
				////////////////////////

				ll2.addView(layout);
			}

		} else {
			// do nothing
			ll2.removeAllViews();
		}

	}

	AlertDialog.Builder builder = null;
	AlertDialog alertDialog = null;

	/**
	 * This method create alert dialog has view that contains all info that
	 * donor want to know about him phone number, blood group, firstname,
	 * lastname and his image the he can call him for getting help
	 * 
	 * @param bs
	 * @param firstName
	 * @param lastName
	 * @param phoneNumber
	 * @param bloodGroup
	 */
	private void showInfo(byte[] bs, String firstName, String lastName, final String phoneNumber, String bloodGroup) {
		// TODO Auto-generated method stub
		builder = new AlertDialog.Builder(context);
		LayoutInflater inflater = LayoutInflater.from(context);
		View view = inflater.inflate(R.layout.donor_info, null);
		builder.setView(view);
		builder.setCancelable(true);
		alertDialog = builder.create();
		if (alertDialog.isShowing()) {
			return;
		}
		alertDialog.show();

		View view2 = view.findViewById(R.id.i1);
		View view3 = view2.findViewById(R.id.i2);
		View view4 = view3.findViewById(R.id.i3);
		TextView fn = (TextView) view4.findViewById(R.id.infoFirstname);
		TextView ln = (TextView) view4.findViewById(R.id.infoLastname);
		TextView phone = (TextView) view4.findViewById(R.id.infoPhone);
		TextView bloodGro = (TextView) view4.findViewById(R.id.infoBloodGroup);
		ImageView image = (ImageView) view4.findViewById(R.id.infoImage);
		ImageView call = (ImageView) view3.findViewById(R.id.infoCall);

		////////////////////////////////////////
		fn.setText(firstName);
		ln.setText(lastName);
		bloodGro.setText(bloodGroup);
		phone.setText(phoneNumber);

		Bitmap bitmap = BitmapFactory.decodeByteArray(bs, 0, bs.length);
		image.setImageBitmap(bitmap);

		//////////////////////////////

		call.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				makeCall(phoneNumber);

			}
		});

	}

	private ListView creatListView(ArrayList<String> arrayList) {
		// TODO Auto-generated method stub
		ListView lv = new ListView(context);
		lv.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
		lv.setBackgroundResource(R.drawable.round_and_border);
		lv.setTag("lvDonors");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1,
				arrayList);
		lv.setAdapter(adapter);
		lv.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				// ListView Clicked item index
				int itemPosition = position;
				// ListView Clicked item value
				String itemValue = (String) lvDonors.getItemAtPosition(position);
				makeCall(itemPosition);

			}

		});

		return lv;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
		// TODO Auto-generated method stub

		phones.clear();
		arr.clear();
		getAllDonors(spinnerSearch.getSelectedItem().toString());
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		// ListView Clicked item index
		int itemPosition = position;
		// ListView Clicked item value
		String itemValue = (String) lvDonors.getItemAtPosition(position);
		makeCall(position);

	}

	/**
	 * Make intent with the action CALL for using the built in phone caller and
	 * dialer
	 * 
	 * @param pos
	 */
	private void makeCall(int pos) {
		// TODO Auto-generated method stub
		String phoneNuber = "";
		for (int i = 0; i < phones.size(); i++) {
			if (pos == i) {
				Toast.makeText(context, phones.get(i), Toast.LENGTH_SHORT).show();
				phoneNuber = phones.get(i);
			}
		}

		if (!phoneNuber.isEmpty()) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + phoneNuber));
			context.startActivity(intent);
		}

	}

	private void makeCall(String phoneNumber) {
		// TODO Auto-generated method stub
		// String phoneNuber = "";
		// for (int i = 0; i < phones.size(); i++) {
		// if (pos == i) {
		// Toast.makeText(context, phones.get(i), Toast.LENGTH_SHORT).show();
		// phoneNuber = phones.get(i);
		// }
		// }

		if (!phoneNumber.isEmpty()) {
			Intent intent = new Intent(Intent.ACTION_CALL);
			intent.setData(Uri.parse("tel:" + phoneNumber));
			context.startActivity(intent);
		}

	}

}
