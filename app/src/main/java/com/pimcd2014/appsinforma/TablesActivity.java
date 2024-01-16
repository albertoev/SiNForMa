package com.pimcd2014.appsinforma;

import java.util.Arrays;
import java.util.Random;

import com.pimcd2014.appsinforma.utilidades.UtilidadesCambioFont;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
	private int level = 0; //Valor por defecto se lee de intemt
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
		readParameters();
		setContentView(R.layout.activity_tables);
		
	    //inicializar las variables
		Ayuda = this.getResources().getString(R.string.ayuda);
		Aceptar = this.getResources().getString(R.string.aceptar);
		Perfecto = this.getResources().getString(R.string.perfecto);
		TituloApp = this.getResources().getString(R.string.app_name);
		
		externalFont = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.externalFont));
//		externalFont = Typeface.createFromAsset(getAssets(), "fonts/gloriahallelujah2.ttf");
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
		
		initButtons();
//ToDo: Parece que funciona la función de debajo sobre los Bottons:	
//		UtilidadesCambioFont.overrideFonts(this, (View) this.getCurrentFocus(), externalFont);
		initOnClick();
		newExercise(level);
	}

	// Reads the parameters
	private void readParameters() {
		Intent intent = getIntent(); // gets the previously created intent
		level = intent.getIntExtra("level", 0);
	}
	
	// Poner la fuente externa a los botones.
	private void initButtons(){
		Button but;
		int resid;
		for (int i=0; i<=7; i++) {
			for (int j=0; j<=7; j++) {
				resid = this.getResources().getIdentifier("but_num" + i + j, "id", this.getPackageName());
				but = (Button) findViewById(resid);
				but.setTypeface(externalFont);
			}
		}
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
			newExercise(level);
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
	
	//Inicailiza todos los botones con su contenido, luego borraremos los huecos.
	private void generarTablero(){
		Button but;
		int resid;
		for (int i=0; i<=7; i++) {
			for (int j=0; j<=7; j++) {
				resid = this.getResources().getIdentifier("but_num" + i + j, "id", this.getPackageName());
				but = (Button) findViewById(resid);
				MiNumero numero = new MiNumero(i*10+j);
				but.setText(numero.toString());
				but.setBackgroundColor(Color.TRANSPARENT);
					//but.setBackgroundDrawable(findViewById(R.id.but_newEx).getBackground());
				but.setClickable(false);
				but.setTypeface(externalFont);
				}
			}
	}
	private void borrarPos(int posFila,int posColumna){
		Button but;
		int resid;
		resid = this.getResources().getIdentifier("but_num" + posFila + posColumna, "id", this.getPackageName());
		but = (Button) findViewById(resid);
		//if(!but.isClickable()){
	    	but.setOnClickListener(this);
			but.setText(Ayuda);
			but.setBackgroundColor(YELLOW); // YELLOW
			but.setClickable(true);
		//}
	}

	// Creates a new exercise LEVEL 2
	private void newExercise2(){
		//Borra 1 fila y 1 columna
		Random r = new Random();
		int posFila = r.nextInt(7);
		int posColumna = r.nextInt(6);
		//Evito que se borre la misma fila que columna
		if (posColumna >= posFila) posColumna = posColumna + 1;
		generarTablero();
		//Borrar la fila posFila
		for (int i=0; i<=7; i++){
			borrarPos(posFila, i);
		};
		//Borrar la columna posColumna
		for (int i=0; i<=7; i++){
			borrarPos(i, posColumna);
		};
	}

	// Creates a new exercise LEVEL 1
	private void newExercise1(){
		//Borra dos cuadrados de tamaño 3. Pueden solaparse
		int sizeSquare = 3;
		Random r = new Random();
		int posFila, posColumna;
		generarTablero();
		//Borrar los dos cuadrados
		for (int cuadrado=1; cuadrado<=2; cuadrado++){
			posFila = r.nextInt(8-sizeSquare);
			posColumna = r.nextInt(8-sizeSquare);
			//borramos todas las posiciones del cuadrado
			for (int i=posFila; i<posFila + sizeSquare; i++) {
				for (int j=posColumna; j< posColumna + sizeSquare; j++) {
					borrarPos(i, j);
				}
			}
		};
		// Borrar otras tres posiciones aleatorias
		for (int i=0; i <3; i++){
			posFila = r.nextInt(8-sizeSquare);
			posColumna = r.nextInt(8-sizeSquare);
			borrarPos(posFila, posColumna);
			
		}
	}
	
	// Creates a new exercise LEVEL 0
    // Borra posiciones aleatorias
	private void newExercise0(){
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
	
	private void newExercise(int level){
		switch(level){
		case 0: newExercise0(); break;
		case 1: newExercise1(); break;
		case 2: newExercise2(); break;
		default: newExercise0(); break;
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
