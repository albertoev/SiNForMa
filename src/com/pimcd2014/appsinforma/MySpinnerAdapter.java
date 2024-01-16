/**
 * 
 */
package com.pimcd2014.appsinforma;

import java.util.List;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * @author Alberto
 * 
 * En esta clase configuraré los Spinner para que tengan el tipo de letra
 * apropiado.
 *
 */
public class MySpinnerAdapter extends ArrayAdapter<String> {
    // Initialise custom font, for example:
    protected Typeface externalFont;

    // (In reality I used a manager which caches the Typeface objects)
    // Typeface font = FontManager.getInstance().getFont(getContext(), BLAMBOT);

    MySpinnerAdapter(Context context, int resource, List<String> items) {
        super(context, resource, items);
		externalFont = Typeface.createFromAsset(getContext().getAssets(), getContext().getResources().getString(R.string.externalFont));
/*        externalFont =
        	Typeface.createFromAsset(getContext().getAssets(), "fonts/arial2.ttf");
		                // "fonts/DroidSerif.ttf");
    									// "fonts/gloriahallelujah2.ttf");
*/
}

    MySpinnerAdapter(Context context, int resource, List<String> items, Typeface externalFont) {
        super(context, resource, items);
        this.externalFont = externalFont;
   }


    // Affects default (closed) state of the spinner
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getView(position, convertView, parent);
        view.setTypeface(externalFont);
        return view;
    }

    // Affects opened state of the spinner
    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        TextView view = (TextView) super.getDropDownView(position, convertView, parent);
        view.setTypeface(externalFont);
        return view;
    }
  
/*
    private OnItemSelectedListener OnCatSpinnerCL = new AdapterView.OnItemSelectedListener() {
        public void onItemSelected(AdapterView<?> parent, View view, int pos,
                long id) {

            ((TextView) parent.getChildAt(0)).setTextColor(Color.BLUE);
            ((TextView) parent.getChildAt(0)).setTextSize(5);



        }

        public void onNothingSelected(AdapterView<?> parent) {

        }
    };
*/
}

