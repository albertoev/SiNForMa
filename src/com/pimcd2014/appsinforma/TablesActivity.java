package com.pimcd2014.appsinforma;

import java.util.Arrays;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Spinner;

public class TablesActivity extends Activity implements OnClickListener {

    protected Typeface externalFont;

    private Spinner mNum1;
	private Spinner mNum2;
	private final int YELLOW = 0xFFF5EA25;
	private final int RED = 0xFFE50215;
	private final int GREEN = 0xFF34A304;
	private String Ayuda;
	private String Aceptar;
	private String Perfecto;
	private String TituloApp;
/*	private final String Ayuda = "?";
	private final String Aceptar = "Aceptar";
	private final String Perfecto = "Perfecto!";
	private final String Nuevo = "Nuevo";
	private final String Validar = "Validar";
	private final String TituloApp = "AppSiNForMa";
*/
	
//	private String nuevo = this.getResources().getString(R.string.newEx);
//	private String validar = this.getResources().getString(R.string.isCorrect);
	// OnCreate event
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tables);
		
	    //inicializar las variables
		Ayuda = this.getResources().getString(R.string.ayuda);
		Aceptar = this.getResources().getString(R.string.aceptar);
		Perfecto = this.getResources().getString(R.string.perfecto);
		TituloApp = this.getResources().getString(R.string.app_name);
		
		externalFont = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.externalFont));
	    //"fonts/arial2.ttf");
		  // "fonts/DroidSerif.ttf");
		  // "fonts/gloriahallelujah2.ttf");

		mNum1 = (Spinner) findViewById(R.id.spinner1);
		mNum2 = (Spinner) findViewById(R.id.spinner2);
		MySpinnerAdapter adapter2 = new MySpinnerAdapter(
				this,//CUIDADO era getContext()
		        R.layout.my_spinner_style,
		        Arrays.asList(getResources().getStringArray(R.array.numCU_array)),
		        externalFont
		);
		//adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mNum2.setAdapter(adapter2);
		
		MySpinnerAdapter adapter1 = new MySpinnerAdapter(
				this,
		        R.layout.my_spinner_style,
		        Arrays.asList(getResources().getStringArray(R.array.numCG_array)),
		        externalFont
		);
	
		//adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		mNum1.setAdapter(adapter1);
		
		initOnClick();
		newExercise();
	}
	
	// Initializes OnClick events
	private void initOnClick() {			
		findViewById(R.id.but_newEx).setOnClickListener(this);
		findViewById(R.id.but_iscorrect).setOnClickListener(this);
	}

	// Responses to OnClick events
	@Override
	public void onClick(View v) {
		int butPressID = ((Button) v).getId();
		String butPress = ((Button) v).getText().toString();
		//·Poner un botón de ayuda que explique el juego en un showMessage()?
		if (butPressID == R.id.but_newEx) {
//		if (butPress.equals(Nuevo)) {
			newExercise();
		} else if (butPressID == R.id.but_iscorrect) {
//			} else if (butPress.equals(Validar)) {
			validateExercise();
		} else {
			changeValue((Button) v);
		}
	}
	
	// Changes the value of the button clicked
	private void changeValue(Button bClicked) {
		String text;
		if (mNum1.getSelectedItem().equals("0")) {
			text= (String) mNum2.getSelectedItem();
		}
		else {
			text= (String) mNum1.getSelectedItem() + (String) mNum2.getSelectedItem();
		}
		bClicked.setText(text);		
	}
	
	// Shows a popup message with an Ok button
	private void showMessage(CharSequence message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set dialog
		alertDialogBuilder
			.setTitle(TituloApp)
			.setMessage(message)
			.setCancelable(false)
			.setPositiveButton(Aceptar, new DialogInterface.OnClickListener() {
				public void onClick(DialogInterface dialog,int id) {
					// if this button is clicked, close the dialog
					dialog.cancel();
				}
			})
		;
 		// create alert dialog
		AlertDialog alertDialog = alertDialogBuilder.create();
		// show it
		alertDialog.show();
	}
	
	// Creates a new exercise
	private void newExercise(){
		Random r = new Random();
		Button but;
		int resid;
		for (int i=0; i<=7; i++) {
			for (int j=0; j<=7; j++) {
				resid = this.getResources().getIdentifier("but_num" + i + j, "id", this.getPackageName());
				but = (Button) findViewById(resid);
				// nextFloat() returns a pseudo-random uniformly distributed  
				// float in the half-open range [0.0, 1.0)
				if (r.nextFloat() < 0.20){
					but.setOnClickListener(this);
					but.setText(Ayuda);
					but.setBackgroundColor(YELLOW); // YELLOW
					but.setClickable(true);
				}
				else {
					MiNumero numero = new MiNumero(i*10+j);
					but.setText(numero.toString());
					but.setBackgroundColor(Color.TRANSPARENT);
					//but.setBackgroundDrawable(findViewById(R.id.but_newEx).getBackground());
					but.setClickable(false);
				but.setTypeface(externalFont);
				}
			}
		}
	}
	
	// Creates a string from numbers ij
	private String ijToString(int i, int j) {
		String value;
		if (i == 0) {
			value = Integer.toString(j);
		}
		else {
			value = Integer.toString(i) + Integer.toString(j);	
		}
		return value;		
	}
	
	// Validates the exercise
	private void validateExercise() {
		Boolean allCorrect = true;
		Button but;
		int resid;
		for (int i=0; i<=7; i++) {
			for (int j=0; j<=7; j++) {
				resid = this.getResources().getIdentifier("but_num" + i + j, "id", this.getPackageName());
				but = (Button) findViewById(resid);
				if (but.isClickable()) {
					if (but.getText().equals(Ayuda)) {
						allCorrect = false;
					}
					else if (!but.getText().equals(new MiNumero(i*10+j).toString())) {
						but.setBackgroundColor(RED); // RED
						allCorrect = false;
					}
					else but.setBackgroundColor(GREEN); // GREEN
				} 
			}
		}		
		if (allCorrect) showMessage(Perfecto);
	}
}
