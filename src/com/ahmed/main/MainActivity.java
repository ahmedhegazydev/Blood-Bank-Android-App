package com.ahmed.main;

import com.ahmed.R;
import com.ahmed.login2.LoginActivity2;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

@SuppressLint("NewApi")
public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		ImageView imageView = new ImageView(getApplicationContext());
		imageView.setImageResource(R.drawable.blood4);
		imageView.setScaleType(ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, 
				LayoutParams.MATCH_PARENT));

		setContentView(imageView);

		////////////////////////////////// Starting thread
		new Handler().postDelayed(new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				finish();
				startActivity(new Intent(getApplicationContext(), LoginActivity2.class));
			}
		}, 70);

	}

	@Override
	public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
		// TODO Auto-generated method stub
		super.onPostCreate(savedInstanceState, persistentState);

	}

}
