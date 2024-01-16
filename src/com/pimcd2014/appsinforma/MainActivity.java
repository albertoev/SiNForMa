package com.pimcd2014.appsinforma;

import android.app.Activity;
import android.content.Intent;
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
	}

	@Override
	public void onClick(View v) {
		int butPressID = ((Button) v).getId();
		String game = "";
		
		switch (butPressID){
		case R.id.butTables: game = "TablesActivity"; break;
		case R.id.butPencils: game = "PencilsActivity"; break;
		case R.id.butWords: game = "WordsActivity"; break;
		}

		try {
			Class<?> clazz = Class.forName("com.pimcd2014.appsinforma." + game);
		    Intent intent = new Intent(this, clazz);
		    startActivity(intent);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}		
	}
}