package com.pimcd2014.appsinforma;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;


public class MainActivity extends Activity implements OnClickListener {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);
		initOnClick();
	}
	
	// Initializes OnClick events
	private void initOnClick() {			
		findViewById(R.id.butTables).setOnClickListener(this);
		findViewById(R.id.butPencils).setOnClickListener(this);
		findViewById(R.id.butWords).setOnClickListener(this);
		findViewById(R.id.butInc).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int butPressID = ((Button) v).getId();
		String game = "";
		boolean isWords = false;
		
		switch (butPressID){
		case R.id.butTables: game = "TablesActivity"; break;
		case R.id.butPencils: game = "PencilsActivity"; break;
		case R.id.butWords: game = "WordsActivity"; isWords = true; break;
		case R.id.butInc: game = "WordsActivity"; isWords = false; break;
		}

		try {
			Class<?> clazz = Class.forName("com.pimcd2014.appsinforma." + game);
		    Intent intent = new Intent(this, clazz);
		    intent.putExtra("isWord", isWords);
		    startActivity(intent);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
}