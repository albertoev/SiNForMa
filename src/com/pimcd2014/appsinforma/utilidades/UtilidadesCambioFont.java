/**
 * 
 */
package com.pimcd2014.appsinforma.utilidades;

import java.lang.reflect.Field;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

/**
 * @author Alberto
 * En esta clase meteré todas las funciones necesarias para el cambio de fonts
 * de la aplicación. Serán todos métodos static.
 */
public final class UtilidadesCambioFont {
	/**
	 * Método que usa reflexión para el camibio de font de un NumberPicker.
	 * Cogido de internet y no sé muy bien como está codificado, pero funciona.
	 * @param numberPicker el que hay que cambiar de font
	 * @param color el color de fondo a cambiar
	 * @param externalFont el nuevo tipo de font.
	 * @return True si se ha cambiado el tipo de letra y Falso si no se ha podido cambiar.
	 */
	public static boolean setNumberPickerFont(NumberPicker numberPicker, Typeface externalFont)
	{
	    final int count = numberPicker.getChildCount();
	    for(int i = 0; i < count; i++){
	        View child = numberPicker.getChildAt(i);
	        if(child instanceof EditText){
	            try{
	                Field selectorWheelPaintField = numberPicker.getClass()
	                    .getDeclaredField("mSelectorWheelPaint");
	                selectorWheelPaintField.setAccessible(true);
	                EditText editText = (EditText) child;
	                editText.setTypeface(externalFont);
	                Paint paint = (Paint) selectorWheelPaintField.get(numberPicker);
	                paint.setTypeface(externalFont);

	                numberPicker.invalidate();
	                return true;
	            } catch(NoSuchFieldException e){
	                Log.w("setNumberPickerTextColor", e);
	            } catch(IllegalArgumentException e){
	                Log.w("setNumberPickerTextColor", e);
	            } catch (IllegalAccessException e) {
	                Log.w("setNumberPickerTextColor", e);
				}
	        }
	    }
	    return false;
	}

	/**
	 * Método que usa reflexión para el camibio de font de un NumberPicker.
	 * Cogido de internet y no sé muy bien como está codificado, pero funciona.
	 * @param numberPicker el que hay que cambiar de font
	 * @param color el color de fondo a cambiar
	 * @param externalFont el nuevo tipo de font.
	 * @return True si se ha cambiado el tipo de letra y Falso si no se ha podido cambiar.
	 */
	public static boolean setNumberPickerTextColor(NumberPicker numberPicker, int color, Typeface externalFont)
	{
	    final int count = numberPicker.getChildCount();
	    for(int i = 0; i < count; i++){
	        View child = numberPicker.getChildAt(i);
	        if(child instanceof EditText){
	            try{
	                Field selectorWheelPaintField = numberPicker.getClass()
	                    .getDeclaredField("mSelectorWheelPaint");
	                selectorWheelPaintField.setAccessible(true);
	                EditText editText = (EditText) child;
//	                editText.setTextColor(color);
//	                editText.setTextSize(complexUnitType, textSize);
//	                editText.setTypeface(editText.Typeface, style);
//	                editText.setTextColor(color);
	                editText.setTypeface(externalFont);
	                Paint paint = (Paint) selectorWheelPaintField.get(numberPicker);
//	                paint.TextSize =  TypedValue.ApplyDimension(complexUnitType, textSize, numberPicker.Resources.DisplayMetrics);
//	                paint.Color = color;
	                paint.setTypeface(externalFont);
//	                ((Paint)selectorWheelPaintField.get(numberPicker)).setColor(color);

	                numberPicker.invalidate();
	                return true;
	            } catch(NoSuchFieldException e){
	                Log.w("setNumberPickerTextColor", e);
	            } catch(IllegalArgumentException e){
	                Log.w("setNumberPickerTextColor", e);
	            } catch (IllegalAccessException e) {
	                Log.w("setNumberPickerTextColor", e);
				}
	        }
	    }
	    return false;
	}
/**
 * EN PROCESO: NO funciona correctamente aún.
 * Método para intentar algo más general cambiar la font de todo los botones y demás que 
 * hay en la aplicación.
 * @param context
 * @param v
 * @param externalFont
 */
	public static void overrideFonts(final Context context, final View v, Typeface externalFont) {
	    try {
	    	if (v instanceof NumberPicker) {
            UtilidadesCambioFont.setNumberPickerFont(((NumberPicker) v), externalFont);
	    	} else if (v instanceof ViewGroup) {
	            ViewGroup vg = (ViewGroup) v;
	            for (int i = 0; i < vg.getChildCount(); i++) {
	                View child = vg.getChildAt(i);
	                UtilidadesCambioFont.overrideFonts(context, child, externalFont);
	            }
	        } else if (v instanceof EditText) {
	            ((EditText) v).setTypeface(externalFont);
	        } else if (v instanceof Button) {
	            ((Button) v).setTypeface(externalFont);
	        } else if (v instanceof TextView) {
	            ((TextView) v).setTypeface(externalFont);
	        }
	    } catch (Exception e) {
	    }
	}

	   private void setNumberPickerProperties(DatePicker dp)
	   {
	           LinearLayout l = (LinearLayout)dp.getChildAt(0);
	           if(l!=null)
	           {
	                   l = (LinearLayout)l.getChildAt(0);
	                   if(l!=null)
	                   {
	                           for(int i=0;i<3;i++)
	                           {
	                                   NumberPicker np = (NumberPicker)l.getChildAt(i);
	                                   if(np!=null)
	                                   {
	                                           EditText et = (EditText)np.getChildAt(1);
	                                           et.setTextColor(Color.BLACK);
	                                   }
	                           }
	                   }
	           }
	   }
	   
}
