package com.pimcd2014.appsinforma;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class PencilsActivity extends Activity  implements OnClickListener {

	protected Typeface externalFont;
	
	private int rndNum;
	private TextView textNumP1;
	private TextView textNumP8;
	private TextView textNumP64;
	
	private int numP1;
	private int numP8;
	private int numP64;
	
	private TextView strEjercicio;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_pencils);
		
	    //inicializar las variables
		externalFont = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.externalFont));
//	    externalFont = Typeface.createFromAsset(getAssets(), "fonts/arial2.ttf");
		  // "fonts/DroidSerif.ttf");
		  // "fonts/gloriahallelujah2.ttf");

		textNumP1 = (TextView) findViewById(R.id.txtNumP1);
		textNumP8 = (TextView) findViewById(R.id.txtNumP8);
		textNumP64 = (TextView) findViewById(R.id.txtNumP64);
		strEjercicio = ((TextView) findViewById(R.id.txtRndNum)); 

		textNumP1.setTypeface(externalFont);
		textNumP8.setTypeface(externalFont);
		textNumP64.setTypeface(externalFont);
		strEjercicio.setTypeface(externalFont);
		
		initOnClick();
		newExercise();
	}
	
	// Initializes OnClick events
	private void initOnClick() {			
		findViewById(R.id.butPadd1).setOnClickListener(this);
		findViewById(R.id.butPadd8).setOnClickListener(this);
		findViewById(R.id.butPadd64).setOnClickListener(this);
		findViewById(R.id.butPdel1).setOnClickListener(this);
		findViewById(R.id.butPdel8).setOnClickListener(this);
		findViewById(R.id.butPdel64).setOnClickListener(this);
		findViewById(R.id.but_newEx).setOnClickListener(this);
		findViewById(R.id.but_iscorrect).setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		int butPressID = ((Button) v).getId();

		switch (butPressID) {
		case R.id.butPadd1: addPencil(); break;
		case R.id.butPadd8: addBox(); break;
		case R.id.butPadd64: addCase(); break;
		case R.id.butPdel1: delPencil(); break;
		case R.id.butPdel8: delBox(); break;
		case R.id.butPdel64: delCase(); break;
		case R.id.but_newEx: newExercise(); break;
		case R.id.but_iscorrect: validateExercise(); break;
		};
	}
	
	// Creates a new exercise
	private void newExercise(){
		String formatoText = this.getResources().getString(R.string.mensaje_pencils);
		Random r = new Random();
		rndNum = r.nextInt(512); //Math.pow(8,3)
		strEjercicio.setText(String.format(formatoText,MiNumero.toString(rndNum, 10)));
		
		numP1 = 0;
		numP8 = 0;
		numP64 = 0;
		
		if (r.nextInt() < 0.5) {
			((Button) findViewById(R.id.butPadd1)).setEnabled(true);
			((Button) findViewById(R.id.butPdel1)).setEnabled(true);
		}
		else {
			((Button) findViewById(R.id.butPadd1)).setEnabled(false);
			((Button) findViewById(R.id.butPdel1)).setEnabled(false);
		}
		
		textNumP1.setText(MiNumero.toString(numP1,10));
		textNumP8.setText(MiNumero.toString(numP8,10));
		textNumP64.setText(MiNumero.toString(numP64,10));
	}

	// Adds one pencil
	private void addPencil() {
		if (numP1<512) {
			numP1 = numP1 + 1;
			textNumP1.setText(MiNumero.toString(numP1, 10));
		}
	}
	
	// Adds one box = 8 pencils
	private void addBox() {
		if (numP8<64) {
			numP8 = numP8 + 1;
			textNumP8.setText(MiNumero.toString(numP8, 10));
		}
	}
	
	// Adds one case = 8 boxes = 64 pencils
	private void addCase() {
		if (numP64<8) {
			numP64 = numP64 + 1 ;
			textNumP64.setText(MiNumero.toString(numP64,10));
		}
	}
	
	// Del one pencil
	private void delPencil() {
		if (numP1>0) {
			numP1 = numP1-1;
			textNumP1.setText(MiNumero.toString(numP1, 10));
		}
	}
	
	// Del one box = 8 pencils
	private void delBox() {
		if (numP8>0) {
			numP8 = numP8 - 1;
			textNumP8.setText(MiNumero.toString(numP8, 10));
		}
	}
	
	// Del one case = 8 boxes = 64 pencils
	private void delCase() {
		if (numP64>0) {
			numP64= numP64 - 1;
			textNumP64.setText(MiNumero.toString(numP64, 10));
		}
	}
	
	// Validates the exercise
	private void validateExercise() {
		boolean isCorrect;		
		int total = numP1 + numP8*8 + numP64*64;
		int dif = total - rndNum;
		
		isCorrect = (((((Button) findViewById(R.id.butPadd1)).isEnabled()) && (dif == 0)) ||
				    (!(((Button) findViewById(R.id.butPadd1)).isEnabled()) && (0 <= dif) && (dif <=7)));
				
		if (isCorrect) showMessage(this.getString(R.string.perfecto));
		else showMessage(this.getString(R.string.ooh_OtraVez));
	}
	
	// Shows a popup message with an Ok button
	private void showMessage(CharSequence message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set dialog
		alertDialogBuilder
			.setTitle(this.getString(R.string.app_name))
			.setMessage(message)
			.setCancelable(false)
			.setPositiveButton(this.getString(R.string.aceptar),new DialogInterface.OnClickListener() {
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
	
}
