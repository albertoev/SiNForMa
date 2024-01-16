package com.pimcd2014.appsinforma;

import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class WordsParamsActivity extends Activity implements OnClickListener, OnFocusChangeListener{
	protected Typeface externalFont;
	
	// Todos los números están en base 10.
	private int fromNum;
	private int incr;
	private boolean ascend;
	
	private int toNum;
	private int curNum; //currentNum
	
	private TextView tFromTo;
	private EditText tWordNum;
	private TextView tLastNum;
	private Button bNewEx;
	private Button bNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_words_params);

		externalFont = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.externalFont));
		  // "fonts/DroidSerif.ttf");
		  // "fonts/gloriahallelujah2.ttf");

		tFromTo = (TextView) findViewById(R.id.txtFromTo);
		tWordNum = (EditText) findViewById(R.id.txtWordNum);
		tLastNum = (TextView) findViewById(R.id.txtLastNum);
		bNewEx =  (Button) findViewById(R.id.but_newEx);
		bNext =  (Button) findViewById(R.id.but_next);
				
		readParameters();
		setTitle();
		initOnClick();
		newExercise();
	}
	
	// Reads the parameters
	private void readParameters() {
		Intent intent = getIntent(); // gets the previously created intent
		fromNum = intent.getIntExtra("fromNumber", 1);
		incr = intent.getIntExtra("increment", 1);
		ascend = intent.getBooleanExtra("ascendent", true);
	}

	// Sets the title activity
	private void setTitle() {
		String sign = "+";
		if (!ascend) sign = "-";
		setTitle(String.format(this.getResources().getString(R.string.title_activity_words), sign, incr));	
	}
	
	// Initializes OnClick events
	private void initOnClick() {
		bNewEx.setOnClickListener(this);
		bNext.setOnClickListener(this);
		tWordNum.setOnFocusChangeListener(this);
		}
	
	@Override
	public void onClick(View v) {
		int butPressID = ((Button) v).getId();

		switch (butPressID) {
		case R.id.but_newEx:
			newExercise();
			break;
		case R.id.but_next:
			nextWordNumber();
			break;
		}
	}
	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		EditText elText = (EditText) v;
		if (hasFocus)
		  elText.selectAll();
		else 
			elText.setText(this.getResources().getString(R.string.escribe_palabra));
	}

	
	// Creates a new exercise
	private void newExercise(){
		String formatoText = this.getResources().getString(R.string.escribe_palabras);
		String formatoUlt = this.getResources().getString(R.string.ultima_palabra);
		
		bNewEx.setEnabled(false);
		bNext.setEnabled(true);
		
		Random r = new Random();
		int min = 5;
		int max = 15;
		// r.nextInt(max - min + 1) + min;
		int howMany = r.nextInt(max - min + 1) + min;
		
		if (ascend) {
			if (fromNum ==-1) fromNum = r.nextInt(512 - howMany*incr); //Math.pow(8,3)
			curNum = fromNum; 
			toNum = fromNum + howMany*incr;
		} else {
			if (fromNum == -1) fromNum = r.nextInt(512 - howMany*incr) + howMany*incr; //Math.pow(8,3)
			curNum = fromNum; 
			toNum = fromNum - howMany*incr;
		}
		
		tFromTo.setText(String.format(formatoText, new MiNumero(fromNum), new MiNumero(toNum)));
		tLastNum.setText(String.format(formatoUlt, new MiNumero(fromNum)));
	}

	// Checks if the word-number is correct or not and asks the next
	private void nextWordNumber(){
		String formatoUlt = this.getResources().getString(R.string.ultima_palabra);
		// wordNum = Palabra tecleada por el usuario.
		String wordNum = tWordNum.getText().toString();
		MiNumero elNum = new MiNumero(curNum, 10);
		if (wordNum.equals(elNum.toLongString())) {
			if (curNum == toNum) {
				showMessage(this.getResources().getString(R.string.perfecto_Fin));
				bNewEx.setEnabled(true);
				bNext.setEnabled(false);
			} else {
				showMessage(this.getResources().getString(R.string.perfecto_Sig));
				if (ascend) curNum = curNum + incr;
				else curNum= curNum - incr;
			}
			tLastNum.setText(String.format(formatoUlt, MiNumero.toString(curNum)));
		} else {
			showMessage(this.getResources().getString(R.string.ooh_OtraVez));
		}
	}
	
	// Shows a popup message with an Ok button
	private void showMessage(CharSequence message) {
		AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
		// set dialog
		alertDialogBuilder
			.setTitle(this.getResources().getString(R.string.app_name))
			.setMessage(message)
			.setCancelable(false)
			.setPositiveButton(this.getResources().getString(R.string.aceptar),new DialogInterface.OnClickListener() {
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
