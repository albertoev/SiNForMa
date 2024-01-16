package com.pimcd2014.appsinforma;

import java.util.Random;


import com.pimcd2014.appsinforma.utilidades.UtilidadesCambioFont;
import com.pimcd2014.appsinforma.utilidades.UtilidadesNum;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;

public class WordsParamsActivity extends Activity implements OnClickListener, OnFocusChangeListener{
	protected Typeface externalFont;
	
	// Todos los números están en base 10.
	private int fromNum;
	private int incr;
	private boolean ascend;
	
	private int toNum;
	private int curNum; //currentNum
	private boolean isWord = true;
	
	private TextView tFromTo;
	private EditText tWordNum;
	private NumberPicker npNums;
	private String[] str_npNums;
	private TextView tLastNum;
	private Button bNewEx;
	private Button bNext;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		readParameters();
		setContentView();
		
		externalFont = Typeface.createFromAsset(getAssets(), getResources().getString(R.string.externalFont));

		tFromTo = (TextView) findViewById(R.id.txtFromTo);
		tLastNum = (TextView) findViewById(R.id.txtLastNum);
		bNewEx =  (Button) findViewById(R.id.but_newEx);
		bNext =  (Button) findViewById(R.id.but_next);
		if (isWord) tWordNum = (EditText) findViewById(R.id.txtWordNum);
		else {
			npNums = (NumberPicker) findViewById(R.id.NumberPicker);
//			NumberPicker npNum = (NumberPicker) findViewById(R.id.NumberPicker);
//			npNums = new MyNumberPicker(npNum.getContext());
		}
		
		tFromTo.setTypeface(externalFont);
		tLastNum.setTypeface(externalFont);
		if (isWord) tWordNum.setTypeface(externalFont);
		else UtilidadesCambioFont.setNumberPickerFont(npNums, externalFont);
//		UtilidadesCambioFont.overrideFonts(this, (View) this.getCurrentFocus(), externalFont);
		//else numberPickerCambiaFont();
		//else ((EditText) npNums.getChildAt(1)).setTypeface(externalFont);
		//else overrideFonts(this, npNums);
		
		setTitle();
		initOnClick();
		newExercise();
	}
	
	private void setContentView(){
		if (isWord) setContentView(R.layout.activity_words_params);
		else setContentView(R.layout.activity_inc_params);
	}

	// Reads the parameters
	private void readParameters() {
		Intent intent = getIntent(); // gets the previously created intent
		fromNum = intent.getIntExtra("fromNumber", 1);
		incr = intent.getIntExtra("increment", 1);
		isWord = intent.getBooleanExtra("isWord", true);
		ascend = intent.getBooleanExtra("ascendent", true);
	}

	// Sets the title activity
	private void setTitle() {
		String sign = "+";
		int titleID;
		if (isWord) titleID = R.string.title2_activity_words;
		else titleID = R.string.title2_activity_nums;
		if (!ascend) sign = "-";
		setTitle(String.format(this.getResources().getString(titleID), sign, MiNumero.numToTxt(incr, 10, isWord)));	
	}
	
	// Initializes OnClick events
	private void initOnClick() {
		bNewEx.setOnClickListener(this);
		bNext.setOnClickListener(this);
		// tWordNum.setOnFocusChangeListener(this);
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
	
	private void initNumberPicker(int howMany){
		// Create the array of numbers that will populate the number picker
		str_npNums = new String[toNum-fromNum+1];
		for(int i=0; i < toNum-fromNum+1; i++) {
		   str_npNums[i] = MiNumero.toString(fromNum + i, 10 );
		}
		
		npNums.setMinValue(0);
		npNums.setValue(0);
		npNums.setDisplayedValues(str_npNums);
		npNums.setMaxValue(toNum-fromNum);
		npNums.setWrapSelectorWheel(false);
	}
	
	private void setButtonsTxt(){
		String formatoText;
		String strFromNum;
		String strToNum;
		if (isWord){
			formatoText = this.getResources().getString(R.string.escribe_palabras);
			strFromNum = new MiNumero(fromNum,10).toString();
			strToNum = new MiNumero(toNum,10).toString();
		} else {
			formatoText = this.getResources().getString(R.string.escribe_numeros);
			strFromNum = new MiNumero(fromNum,10).toLongString();
			strToNum = new MiNumero(toNum,10).toLongString();
		}
		tFromTo.setText(String.format(formatoText, strFromNum, strToNum));
	//+"\nNumeros desde "+fromNum+" hasta "+toNum+"("+howMany+")-"+curNum);
		setButtonInfTxt(fromNum);
	}

	private void setButtonInfTxt(int num){
		String formatoUlt;
		String strNum;
		if (isWord){
			formatoUlt = this.getResources().getString(R.string.ultima_palabra);
			strNum = new MiNumero(num,10).toString();
		} else {
			formatoUlt = this.getResources().getString(R.string.ultimo_numero);
			strNum = new MiNumero(num,10).toLongString();
		}
		tLastNum.setText(String.format(formatoUlt, strNum));
		//+"\nNumero :"+curNum);
	}

	
	// Creates a new exercise
	private void newExercise(){
		
		bNewEx.setEnabled(false);
		bNext.setEnabled(true);
		
		Random r = new Random();
		int howMany = UtilidadesNum.generaAleatorio(new Random(), 5, 15);
		
		if (ascend) {
			if (fromNum ==-1) fromNum = UtilidadesNum.generaAleatorio(r, 0, 512 - howMany*incr);//Math.pow(8,3)
			curNum = fromNum; 
			toNum = fromNum + howMany*incr;
		} else {
			if (fromNum == -1) fromNum = UtilidadesNum.generaAleatorio(r, 0, 512 - howMany*incr) + howMany*incr; //Math.pow(8,3)
			curNum = fromNum; 
			toNum = fromNum - howMany*incr;
		}
		
		setButtonsTxt();
		if (!isWord) initNumberPicker(howMany);
	}
	
	/**
	 * Lee el número introducido por el usuario. Si isWord lo lee del tWordNum y sino lo lee del npNums
	 * @return el String con el número introducido por el usuario.
	 */
	private String leerTextoNum(){
		String strText;
		if (isWord) strText = tWordNum.getText().toString();
		else strText = str_npNums[npNums.getValue()];
		return strText;
	}

	/**
	 * Convierte un número en String dependiendo de si isWord o no devuelve el longString o el string.
	 * @param num Número en base 10 a convertir en String
	 * @return devuelve el toLonString si isWord y el toString en caso contrario.
	 */
	private String elStrNum(int num){
		MiNumero elNum = new MiNumero(num, 10);
		String strElNum;

		if (isWord) strElNum = elNum.toLongString();
		else strElNum = elNum.toString();

		return strElNum;
	}

	// Checks if the word-number is correct or not and asks the next
	private void nextWordNumber(){		
		// wordNum = Palabra tecleada por el usuario.
		String wordNum = leerTextoNum();
		
		if (wordNum.equals(elStrNum(curNum))) {
			if (curNum == toNum) {
				showMessage(this.getResources().getString(R.string.perfecto_Fin));
				bNewEx.setEnabled(true);
				bNext.setEnabled(false);
			} else {
				showMessage(this.getResources().getString(R.string.perfecto_Sig));
				if (ascend) curNum = curNum + incr;
				else curNum= curNum - incr;
			}

			setButtonInfTxt(curNum);
		} else {
			showMessage(this.getResources().getString(R.string.ooh_OtraVez));
		};
		
		setButtonInfTxt(curNum);
		if (isWord) tWordNum.setText("");
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
