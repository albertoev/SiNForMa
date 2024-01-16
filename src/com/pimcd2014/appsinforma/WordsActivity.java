package com.pimcd2014.appsinforma;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class WordsActivity extends Activity implements OnClickListener {

	protected Typeface externalFont;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_words);
		initOnClick();
	}
	
	// Initializes OnClick events
	private void initOnClick() {	
		Button but;
		int resid;
		
		externalFont = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.externalFont));
		  // "fonts/DroidSerif.ttf");
		  // "fonts/gloriahallelujah2.ttf");

		for (int i=1; i<=10; i++) {
			resid = this.getResources().getIdentifier("butWords" + i, "id", this.getPackageName());
			but = (Button) findViewById(resid);
			but.setTypeface(externalFont);
			but.setOnClickListener(this);
		}
	}

	@Override
	public void onClick(View v) {
		int butPressID = ((Button) v).getId();
//		String butPress = ((Button) v).getText().toString();

		try {
			Class<?> clazz = Class.forName("com.pimcd2014.appsinforma.WordsParamsActivity");
		    Intent intent = new Intent(this, clazz);
		    // Activity parameters: fromNumber, increment, ascendent
		    // fromNumber = -1 represents random initial value
		    switch (butPressID) {
		    case R.id.butWords1: 
		    	intent
		    		.putExtra("fromNumber", 1)
		    		.putExtra("increment",1)
		    		.putExtra("ascendent",true);
		    	break;
		    case R.id.butWords2:
		    	intent
		    		.putExtra("fromNumber", 1)
		    		.putExtra("increment",2)
		    		.putExtra("ascendent",true);
		    	break;
		    case R.id.butWords3:
		    	intent
		    		.putExtra("fromNumber", 0)
		    		.putExtra("increment",2)
		    		.putExtra("ascendent",true);
		    	break;
		    case R.id.butWords4:
		    	intent
		    		.putExtra("fromNumber", 0)
		    		.putExtra("increment",4)
		    		.putExtra("ascendent",true);
		    	break;
		    case R.id.butWords5:
		    	intent
		    		.putExtra("fromNumber", 0)
		    		.putExtra("increment",8)
		    		.putExtra("ascendent",true);
		    	break;
		    case R.id.butWords6:
		    	intent
		    		.putExtra("fromNumber", -1)
		    		.putExtra("increment",1)
		    		.putExtra("ascendent",true);
		    	break;
		    case R.id.butWords7:
		    	intent
		    		.putExtra("fromNumber", -1)
		    		.putExtra("increment",1)
		    		.putExtra("ascendent",false);
		    	break;
		    case R.id.butWords8:
		    	intent
		    		.putExtra("fromNumber", -1)
		    		.putExtra("increment",2)
		    		.putExtra("ascendent",true);
		    	break;
		    case R.id.butWords9:
		    	intent
		    		.putExtra("fromNumber", -1)
		    		.putExtra("increment",4)
		    		.putExtra("ascendent",true);
		    	break;
		    case R.id.butWords10:
		    	intent
		    		.putExtra("fromNumber", -1)
		    		.putExtra("increment",8)
		    		.putExtra("ascendent",true);
		    	break;
		    };
/*		    if (butPress.equals("Words ro-?-ro")) {
		    	intent
		    		.putExtra("fromNumber", 1)
		    		.putExtra("increment",1)
		    		.putExtra("ascendent",true);
		    } else if (butPress.equals("Words ro-?-mes")) {
		    	intent
		    		.putExtra("fromNumber", 1)
		    		.putExtra("increment",2)
		    		.putExtra("ascendent",true);
		    } else if (butPress.equals("Words na-?-mes")) {
		    	intent
		    		.putExtra("fromNumber", 0)
		    		.putExtra("increment",2)
		    		.putExtra("ascendent",true);
		    } else if (butPress.equals("Words na-?-cleta")) {
		    	intent
		    		.putExtra("fromNumber", 0)
		    		.putExtra("increment",4)
		    		.putExtra("ascendent",true);
		    } else if (butPress.equals("Words na-?-moel")) {
		    	intent
		    		.putExtra("fromNumber", 0)
		    		.putExtra("increment",8)
		    		.putExtra("ascendent",true);
			} else if (butPress.equals("Words ?-?-ro asc")) {
		    	intent
		    		.putExtra("fromNumber", -1)
		    		.putExtra("increment",1)
		    		.putExtra("ascendent",true);
			} else if (butPress.equals("Words ?-?-ro desc")) {
				intent
		    		.putExtra("fromNumber", -1)
		    		.putExtra("increment",1)
		    		.putExtra("ascendent",false);
		    } else if (butPress.equals("Words ?-?-mes")) {
		    	intent
		    		.putExtra("fromNumber", -1)
		    		.putExtra("increment",2)
		    		.putExtra("ascendent",true);
		    } else if (butPress.equals("Words ?-?-cleta")) {
		    	intent
		    		.putExtra("fromNumber", -1)
		    		.putExtra("increment",4)
		    		.putExtra("ascendent",true);
		    } else if (butPress.equals("Words ?-?-moel")) {
		    	intent
		    		.putExtra("fromNumber", -1)
		    		.putExtra("increment",8)
		    		.putExtra("ascendent",true);
		    }
*/		    
		    startActivity(intent);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.print("La cagué.");
		}
	}
}
