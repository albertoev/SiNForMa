/**
 * 
 */
package com.pimcd2014.appsinforma.utilidades;

import java.util.Random;

/**
 * @author Alberto
 * En esta clase almacenaré las utilidades sobre los números necesarias.
 * Tales como el cambio de base, etc...
 */
public final class UtilidadesNum {
	public static int generaAleatorio(Random r, int minVal, int maxVal){
		if (r == null) r = new Random();

		return r.nextInt(maxVal - minVal + 1) + minVal;
	}
}
