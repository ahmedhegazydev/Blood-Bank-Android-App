package com.ahmed.slidingmenu;

import java.util.ArrayList;
import java.util.HashMap;

import com.ahmed.R;
import com.ahmed.database.DBController;

import android.app.Fragment;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
//import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class RequestFragment extends Fragment implements OnClickListener {

	DBController dbController = null;
	Context ctxt = null;
	ListView lvRequest = null;
	ArrayAdapter<String> adapter = null;
	ArrayList<String> strings = null;
	String selectQuery = null;
	ArrayList<HashMap<String, String>> hashMaps = null;

	LinearLayout ll = null;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		// return super.onCreateView(inflater, container, savedInstanceState);
		View viewRoot = null;
		viewRoot = inflater.inflate(R.layout.fragment_requests, container, false);
		ctxt = container.getContext();
		dbController = new DBController(ctxt);
		selectQuery = "SELECT * FROM " + dbController.tablename2;

		return viewRoot;
	}

	LinearLayout bossLl = null;

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

		ImageView ivRefresh = (ImageView) getView().findViewById(R.id.ivRequestsRefresh);
		ivRefresh.setOnClickListener(this);
		///////////////////////////////////
		strings = new ArrayList<String>();
		adapter = new ArrayAdapter<String>(ctxt, android.R.layout.simple_list_item_1, strings);
		// lvRequest = (ListView) getView().findViewById(R.id.lvRequest);
		bossLl = (LinearLayout) getView().findViewById(R.id.reqLL);

	}

	@Override
	public void onClick(View v) {

		hashMaps = dbController.getAllRequests(selectQuery);

		if (!hashMaps.isEmpty()) {

			for (int i = 0; i < hashMaps.size(); i++) {
				String request = hashMaps.get(i).get(dbController.request);
				String quan = hashMaps.get(i).get(dbController.quantity);
				String str = "Request  " + request + "  Quantity  " + quan;

				addTexts(request, quan);

				strings.add(str);

				// adapter = new ArrayAdapter<String>(ctxt,
				// android.R.layout.simple_list_item_1, strings);
				// lvRequest.setAdapter(adapter);
				// adapter.notifyDataSetChanged();

			}

		} else {
			Toast.makeText(ctxt, "There are no requests yet now !!", Toast.LENGTH_SHORT).show();
		}

	}

	private void addTexts(String request, String quan) {
		// TODO Auto-generated method stub

		ll = new LinearLayout(getActivity());// LinearLayout
		ll.setOrientation(LinearLayout.HORIZONTAL);
		ll.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f));

		/////////////////////////////////////////

		LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,
				LayoutParams.MATCH_PARENT, .5f);
		layoutParams.setMargins(5, 5, 5, 5);

		TextView textView = new TextView(getActivity());
		textView.setText(request);
		textView.setLayoutParams(layoutParams);
		textView.setTypeface(Typeface.DEFAULT_BOLD);
		textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		textView.setTextColor(Color.DKGRAY);
		textView.setPadding(2, 2, 2, 2);
		
		TextView textView2 = new TextView(getActivity());
		textView2.setText(quan);
		textView2.setLayoutParams(layoutParams);
		textView2.setTypeface(Typeface.DEFAULT_BOLD);
		textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 25);
		textView2.setTextColor(Color.DKGRAY);
		textView2.setPadding(2, 2, 2, 2);

		ll.addView(textView);
		ll.addView(textView2);

		bossLl.addView(ll);

	}

}
