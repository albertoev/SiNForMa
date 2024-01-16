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
	private boolean isWord = true;
	
	// 10 botones, luego 10 opciones.
	private static int fromNums[]   ={   1,    1,    0,    0,    0,   -1,    -1,    -1,   -1,   -1};
//	private static int toNums[]     ={  -1,   -1,   -1,   -1,   -1,   -1,    -1,    -1,   -1,   -1};
	private static int incrs[]      ={   1,    2,    2,    4,    8,    1,     1,     2,    4,    8};
	private static boolean is_asc[] ={true, true, true, true, true, true, false,  true, true, true};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		readParameters();
		setContentView(R.layout.activity_words);
		this.setTitle(this.titleAct());
		initOnClick();
	}
	// Reads the parameters
	private void readParameters() {
		Intent intent = getIntent(); // gets the previously created intent
		isWord = intent.getBooleanExtra("isWord", true);
	}
	
	private String titleAct(){
		String title;
		if (isWord)  title = this.getResources().getString(R.string.title_activity_words);
		else title = this.getResources().getString(R.string.title_activity_nums);
		return title;
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
			but.setText(this.strButtonTxt(i-1));
		}
	}

	/**
	 * Función que traduce un número a formato Num o formato palabras.
	 * @param i el número a traducir en base 10;
	 * @param esNum indica si lo quiero en formato número o palabras
	 * @return la representación de i en MiNúmero en formato número o palabra. 
	 */
	private static String numToTxt2(int i, boolean esNum){
		String strNum;
		if (i<0) strNum ="?";
		else strNum = MiNumero.numToTxt(i, 10, esNum);
		
		return strNum;
	}
	private String strButtonTxt(int i){
		String formatoText;
		String strFromNum;
		String strToNum;
		String strIncr;
		String strAsc;
		
		if (is_asc[i]) strAsc = "Asc";
		else strAsc = "Desc";

		if (isWord){
			formatoText = this.getResources().getString(R.string.str_activity_words);
		} else {
			formatoText = this.getResources().getString(R.string.str_activity_nums);
		}

		strFromNum = WordsActivity.numToTxt2(fromNums[i],isWord);
		strToNum = "?"; //WordsActivity.numToTxt2(-1, isWord);
		strIncr = WordsActivity.numToTxt2(incrs[i], isWord);
		return String.format(formatoText, strFromNum, strToNum, strIncr, strAsc);
	}

	/**
	 * Método que transforma de ID a indice de los arrays de toNum, fromNum, etc.
	 * @param butID
	 * @return
	 */
	private int numBoton(int butID){
		int numBot;
	    switch (butID) {
	    case R.id.butWords1:  numBot = 0; break;
	    case R.id.butWords2:  numBot = 1; break;
	    case R.id.butWords3:  numBot = 2; break;
	    case R.id.butWords4:  numBot = 3; break;
	    case R.id.butWords5:  numBot = 4; break;
	    case R.id.butWords6:  numBot = 5; break;
	    case R.id.butWords7:  numBot = 6; break;
	    case R.id.butWords8:  numBot = 7; break;
	    case R.id.butWords9:  numBot = 8; break;
	    case R.id.butWords10: numBot = 9; break;
	    default:              numBot = -1; break;
	    };

	    return numBot;
	}
	@Override
	public void onClick(View v) {
		int butPressID = ((Button) v).getId();
		int butI = numBoton(butPressID); 

		try {
			Class<?> clazz = Class.forName("com.pimcd2014.appsinforma.WordsParamsActivity");
		    Intent intent = new Intent(this, clazz);
		    // Activity parameters: fromNumber, increment, ascendent
		    // fromNumber = -1 represents random initial value
	    	intent
    			.putExtra("fromNumber", fromNums[butI])
    			.putExtra("increment", incrs[butI])
    			.putExtra("ascendent", is_asc[butI])
    			.putExtra("isWord", isWord);
		    startActivity(intent);
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.print("La cagué.");
		}
	}
}
