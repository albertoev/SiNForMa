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
	private NumberPicker npNums3;
	private NumberPicker npNums2;
	private NumberPicker npNums1;
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
			npNums3 = (NumberPicker) findViewById(R.id.NumberPicker3);
			npNums2 = (NumberPicker) findViewById(R.id.NumberPicker2);
			npNums1 = (NumberPicker) findViewById(R.id.NumberPicker1);
//			NumberPicker npNum = (NumberPicker) findViewById(R.id.NumberPicker);
//			npNums = new MyNumberPicker(npNum.getContext());
		}
		
		tFromTo.setTypeface(externalFont);
		tLastNum.setTypeface(externalFont);
		if (isWord) tWordNum.setTypeface(externalFont);
		else {
			UtilidadesCambioFont.setNumberPickerFont(npNums3, externalFont);
			UtilidadesCambioFont.setNumberPickerFont(npNums2, externalFont);
			UtilidadesCambioFont.setNumberPickerFont(npNums1, externalFont);
		}
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
	
	private void initNumberPickers(){
		String[] str_npNums1 = new String[8];
		String[] str_npNums2 = new String[9];
		String[] str_npNums3 = new String[8];
		
		for(int i=0; i < 8; i++) {
			   str_npNums1[i] = MiNumero.toString(i);
			}
		npNums1.setMinValue(0);
		npNums1.setDisplayedValues(str_npNums1);
		npNums1.setValue(0);
		npNums1.setMaxValue(7);
		npNums1.setWrapSelectorWheel(false);

		for(int i=0; i <= 8; i++) {
			   if (i == 0) str_npNums2[i] = " ";
			   else str_npNums2[i] = MiNumero.toString(i-1);
			}
		npNums2.setMinValue(0);
		npNums2.setDisplayedValues(str_npNums2);
		npNums2.setValue(0);
		npNums2.setMaxValue(8);
		npNums2.setWrapSelectorWheel(false);

		for(int i=0; i < 8; i++) {
			   if (i == 0) str_npNums3[i] = " ";
			   else str_npNums3[i] = MiNumero.toString(i);
			}
		npNums3.setMinValue(0);
		npNums3.setDisplayedValues(str_npNums3);
		npNums3.setValue(0);
		npNums3.setMaxValue(7);
		npNums3.setWrapSelectorWheel(false);

		
	}
	
/*	private void initNumberPicker(){
		// Create the array of numbers that will populate the number picker
		str_npNums1 = new String[toNum-fromNum+1];
		for(int i=0; i < toNum-fromNum+1; i++) {
		   str_npNums[i] = MiNumero.toString(fromNum + i, 10 );
		}
		
		npNums.setMinValue(0);
		npNums.setValue(0);
		npNums.setDisplayedValues(str_npNums);
		npNums.setMaxValue(toNum-fromNum);
		npNums.setWrapSelectorWheel(false);
	}
*/	
	private void setButtonsTxt(){
		String formatoText;
		String strFromNum;
		String strToNum;
		int previousNum;
		
		if (isWord){
			formatoText = this.getResources().getString(R.string.escribe_palabras);
		} else {
			formatoText = this.getResources().getString(R.string.escribe_numeros);
		}
		if (ascend) previousNum = fromNum - incr;
		else previousNum = fromNum + incr;

		strFromNum = MiNumero.numToTxt(fromNum, 10, isWord);
		strToNum = MiNumero.numToTxt(toNum, 10, isWord);
		tFromTo.setText(String.format(formatoText, strFromNum, strToNum));
		
	//+"\nNumeros desde "+fromNum+" hasta "+toNum+"("+howMany+")-"+curNum);
		setButtonInfTxt(previousNum);
	}

	private void setButtonInfTxt(int num){
		String formatoUlt;
		String strNum;
		if (isWord){
			formatoUlt = this.getResources().getString(R.string.ultima_palabra);
		} else {
			formatoUlt = this.getResources().getString(R.string.ultimo_numero);
		}
		strNum = MiNumero.numToTxt(num, 10, isWord);
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
		if (!isWord) initNumberPickers();
	}
	
	/**
	 * Lee el número introducido por el usuario. Si isWord lo lee del tWordNum y sino lo lee del npNums
	 * @return el String con el número introducido por el usuario.
	 */
	private String leerTextoNum(){
		String strText;
		if (isWord) strText = tWordNum.getText().toString();
		else {
			int dig1 = npNums1.getValue();
			int dig2 = npNums2.getValue()-1;
			if (dig2 < 0)  dig2 = 0;
			int dig3 = npNums3.getValue();
			strText = MiNumero.toString( dig3*100 + dig2*10 + dig1 );
		}
		
		return strText;
	}

	/**
	 * Convierte un número en String dependiendo de si isWord o no devuelve el longString o el string.
	 * @param num Número en base 10 a convertir en String
	 * @return devuelve el toLonString si isWord y el toString en caso contrario.
	 */
	private String elStrNum(int num){
		return MiNumero.numToTxt(num, 10, !isWord);
	}

	// Checks if the word-number is correct or not and asks the next
	private void nextWordNumber(){		
		// wordNum = Palabra tecleada por el usuario.
		String wordNum = leerTextoNum();
		int oldCurNum = curNum;
		
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

			setButtonInfTxt(oldCurNum);
		} else {
			showMessage(this.getResources().getString(R.string.ooh_OtraVez));
		};
		
		//setButtonInfTxt(curNum-incr);
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
