package com.pimcd2014.appsinforma;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.NumberPicker;
import android.widget.EditText;

public class MyNumberPicker extends NumberPicker {

	private Context context;
	private Typeface tfs;

	public MyNumberPicker(Context context) {
		super(context);
		this.context = context;
		tfs = Typeface.createFromAsset(context.getAssets(),"fonts/arial2.ttf");
	}

	public MyNumberPicker(Context context, AttributeSet attrs) {
		 super(context, attrs);
		 this.context = context;
		 tfs = Typeface.createFromAsset(context.getAssets(),"fonts/arial2.ttf");
	}

	@Override
	public void addView(View child) {
		 super.addView(child);
		 updateView(child);
	}

	@Override
	public void addView(View child, int index, android.view.ViewGroup.LayoutParams params) {
		 super.addView(child, index, params);
		 updateView(child);
	}

	@Override
	public void addView(View child, android.view.ViewGroup.LayoutParams params) {
		 super.addView(child, params);
		 updateView(child);
	}

	private void updateView(View view) {
		 if(view instanceof EditText){
		   ((EditText) view).setTypeface(tfs);
		   ((EditText) view).setTextSize(25);
		   ((EditText) view).setTextColor(Color.RED);
		 }
	}
}
