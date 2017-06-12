//package com.ahmed.expandablelistview;
//
//import java.util.ArrayList;
//
//import com.ahmed.slidingmenu.R;
//
//import android.app.Activity;
//import android.os.Bundle;
//import android.widget.ExpandableListView;
//
//public class MainActivity extends Activity {
//
//	private ExpandListAdapter ExpAdapter;
//	private ArrayList<Group> ExpListItems;
//	private ExpandableListView ExpandList;
//
//	@Override
//	protected void onCreate(Bundle savedInstanceState) {
//		super.onCreate(savedInstanceState);
//		setContentView(R.layout.activity_main);
//
//		ExpandList = (ExpandableListView) findViewById(R.id.exp_list);
//		ExpListItems = SetStandardGroups();
//		ExpAdapter = new ExpandListAdapter(MainActivity.this, ExpListItems);
//		ExpandList.setAdapter(ExpAdapter);
//
//	}
//
//	public ArrayList<Group> SetStandardGroups() {
//
//		String group_names[] = { "Group A", "Group B", "Group C", "Group D",
//				"Group E", "Group F", "Group G", "Group H" };
//
//		String country_names[] = { "Brazil", "Mexico", "Croatia", "Cameroon",
//				"Netherlands", "chile", "Spain", "Australia", "Colombia",
//				"Greece", "Ivory Coast", "Japan", "Costa Rica", "Uruguay",
//				"Italy", "England", "France", "Switzerland", "Ecuador",
//				"Honduras", "Agrentina", "Nigeria", "Bosnia and Herzegovina",
//				"Iran", "Germany", "United States", "Portugal", "Ghana",
//				"Belgium", "Algeria", "Russia", "Korea Republic" };
//
//		int Images[] = { R.drawable.brazil, R.drawable.mexico,
//				R.drawable.croatia, R.drawable.cameroon, R.drawable.netherland,
//				R.drawable.chile, R.drawable.spain, R.drawable.australia,
//				R.drawable.colombia, R.drawable.greece, R.drawable.ivorycoast,
//				R.drawable.japan, R.drawable.costarica, R.drawable.uruguay,
//				R.drawable.italy, R.drawable.england, R.drawable.france,
//				R.drawable.switzerland, R.drawable.ecuador,
//				R.drawable.honduras, R.drawable.argentina, R.drawable.nigeria,
//				R.drawable.bosnian, R.drawable.iran, R.drawable.germany,
//				R.drawable.usa, R.drawable.portugal, R.drawable.ghana,
//				R.drawable.belgium, R.drawable.algeria, R.drawable.russia,
//				R.drawable.korea };
//
//		ArrayList<Group> list = new ArrayList<Group>();
//
//		ArrayList<Child> ch_list;
//
//		int size = 4;
//		int j = 0;
//
//		for (String group_name : group_names) {
//			Group gru = new Group();
//			gru.setName(group_name);
//
//			ch_list = new ArrayList<Child>();
//			for (; j < size; j++) {
//				Child ch = new Child();
//				ch.setName(country_names[j]);
//				ch.setImage(Images[j]);
//				ch_list.add(ch);
//			}
//			gru.setItems(ch_list);
//			list.add(gru);
//
//			size = size + 4;
//		}
//
//		return list;
//	}
//}
