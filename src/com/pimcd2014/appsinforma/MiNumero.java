package com.pimcd2014.appsinforma;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Scanner;

/**
 * 
 */

/**
 * @author Alberto
 * Clase encargada de alamacenar los números y mostrarlos en la notación adecuada.
 * Es decir, con nuestra representación.
 */
public class MiNumero {
	/**
	 * elNumero almacena el número en base 8. De tal manera que no hay 8 ni 9.
	 */
	private int elNumero;
	/**
	 * losDigitos almacena la representación de los dígitos 0 al 7
	 */
	protected static char[] losDigitos= {'ȹ','^','ɛ','ŧ','ƺ','ɷ','ʔ','ʪ'};
	//{'Φ','^','ɛ','ŧ','ѯ','ɷ','ʔ','ʪ'}; CUIDADO CON LAS FUENTES (FONTS)
	// 'ȹ','^','ɛ','ŧ','ƺѮ','ɷ','ʔ','ʪ'
	//\u0239, \u005E, \u03B5, \u0167, \u01BA, \u0277, \u0294, \u026E
	private static final Hashtable<Integer, String> escritura = initEscritura();
	private static final Hashtable<String, Integer> escrituraOral = initEscrituraOral();
	private static final String SEPARADOR = " ";
	private static final String SEPARADOR_Y = " ";

	/**
	 * @param elNumero se supone que viene en base 8
	 */
	// Se puede almacenar en base 8 o en base 10, estoy aún pensandolo.
//	int elNumeroBase8 = Integer.parseInt( Integer.toString(elNumero,8) );
	public MiNumero(int elNumero) {
		this(elNumero, 8);
	}

	public MiNumero(int elNumero, int radix) {
//		System.out.println("HOLA: "+ this.elNumero +" radio:" + radix);
		this.elNumero = MiNumero.deBaseaBase(elNumero, radix, 8);
//		System.out.println("ADIOS: "+ this.elNumero +" radio:" + radix);
	}

	/**
	 * @param elNumero se supone que viene en base 8
	 * @param lzRepresenntacion contiene lo caracteres que harán de representación de los dígitos.
	 */
	public MiNumero(int elNumero, char[] laRepresentacion) {
		this(elNumero, laRepresentacion, 8);
	}

	/**
	 * @param elNumero se supone que viene en base 8
	 * @param lzRepresenntacion contiene lo caracteres que harán de representación de los dígitos.
	 */
	public MiNumero(int elNumero, char[] laRepresentacion, int radix) {
		super();
		MiNumero.losDigitos = laRepresentacion;
		if (radix == 8)
			this.elNumero = elNumero;
		else if (radix == 10)
			this.elNumero = MiNumero.aBase8(elNumero);
		else 
			this.elNumero = MiNumero.deBaseaBase(elNumero,radix,8);
	}
	
	/**
	 * Lectura de un número en modo texto y conversión a mi numero.
	 * @param strNum contine el número en represantación oral.
	 */
	public MiNumero(String strNum) throws NumberFormatException {
		
        //String input = "1 fish 2 fish red fish blue fish";
        strNum = strNum.toLowerCase();
		Scanner parserNum = new Scanner(strNum);
        int acumNum = 0;
        // variable que se usa para borrar los blancos extras para la comprobación final.
        String strAuxNum = "";
        String palabraAnt = "" , palabraNum = "";
        boolean error = false;
        while (parserNum.hasNext() && ! error) {
        	palabraNum = parserNum.next();
//System.out.println(String.format("DEBUG: palabraNum = %1$s, palabraAnt = %2$s, acumNum = %3$d",palabraNum,palabraAnt, acumNum)); 
        	if (strAuxNum.equals("")) strAuxNum = palabraNum;
        	else strAuxNum = strAuxNum + SEPARADOR + palabraNum; 
        	if (MiNumero.escrituraOral.containsKey(palabraNum)){
        		if (palabraNum.equals(escritura.get(1000)) && (acumNum !=0))
        			acumNum = acumNum *1000;
        		else if (palabraNum.equals(escritura.get(100)) && 
	        				(palabraAnt.equals(escritura.get(2)) || palabraAnt.equals(escritura.get(3)) || 
	        				 palabraAnt.equals(escritura.get(4)) || palabraAnt.equals(escritura.get(5)) || 
	        				 palabraAnt.equals(escritura.get(6)) || palabraAnt.equals(escritura.get(7))
	        				)
        				 )
        				acumNum = (acumNum / 10) * 10 + 100 * MiNumero.escrituraOral.get(palabraAnt);
        		else acumNum = acumNum + escrituraOral.get(palabraNum);
        	}
        	else error = true;
        	palabraAnt = palabraNum;
        };
        	
        parserNum.close(); 
  //      System.out.println(String.format("DEBUG_F: palabraNum = %1$s, palabraAnt = %2$s, acumNum = %3$d",palabraNum,palabraAnt, acumNum)); 
  //      System.out.println(String.format("DEBUG_F2: error = %1$b, strAuxNum = %2$s",error,strAuxNum)); 
  //      System.out.println(String.format("DEBUG_F3: MiNumero.toLongString(acumNum) = %1$s",MiNumero.toLongString(acumNum))); 
        
        if (error || !(MiNumero.toLongString(acumNum).equals(strAuxNum)))
        		throw new NumberFormatException("MiNumero: Numero MAL escrito.");
        else this.elNumero = acumNum;
	}

	public void inc(int i){
		elNumero = MiNumero.aBase8(MiNumero.aBase10(elNumero)+i);
	}

	public MiNumero incS(int i){
		MiNumero elNumero2 = new MiNumero(MiNumero.aBase8(MiNumero.aBase10(this.elNumero)+i));
		return elNumero2;
	}

	public void dec(int i){
		elNumero = MiNumero.aBase8(MiNumero.aBase10(this.elNumero)-i);
	}

	public MiNumero decS(int i){
		MiNumero elNumero2 = new MiNumero(MiNumero.aBase8(MiNumero.aBase10(this.elNumero)-i));
		return elNumero2;
	}

	/*
	 * Método privado que sirve para transformar a escritura guiri-guiri el número.
	 * Inicializa el array escritura.
	 */
	private static Hashtable<Integer, String> initEscritura(){
		Hashtable<Integer, String> miTabla = new Hashtable<Integer, String>(32);
		
		// Unidades
		miTabla.put(0, "na");
		miTabla.put(1, "ro");
		miTabla.put(2, "mes");
		miTabla.put(3, "cus");
		miTabla.put(4, "cleta");
		miTabla.put(5, "prio");
		miTabla.put(6, "burte");
		miTabla.put(7, "betu");
		
		// Decenas
		miTabla.put(10, "moel");
		miTabla.put(11, "ero");
		miTabla.put(12, "zere");
		miTabla.put(13, "blere");
		miTabla.put(14, "moelcleta");
		miTabla.put(15, "moelprio");
		miTabla.put(16, "moelburte");
		miTabla.put(17, "moelbetu");

		miTabla.put(20, "retene");
		miTabla.put(21, "retenero");
		miTabla.put(22, "retenemes");
		miTabla.put(23, "retenecus");
		miTabla.put(24, "retenecleta");
		miTabla.put(25, "reteneprio");
		miTabla.put(26, "reteneburte");
		miTabla.put(27, "retenebetu");
		
		miTabla.put(30, "cutane");
		miTabla.put(31, "cutanero");

		miTabla.put(40, "cletane");
		miTabla.put(50, "pritane");
		miTabla.put(60, "burtane");
		miTabla.put(70, "betuane");
		
		// Centenas
		miTabla.put(100, "molen");
		
		// Millares
		miTabla.put(1000, "lime");
		
		return miTabla;
	}
	/*
	 * Método privado que sirve para transformar a escritura guiri-guiri el número.
	 * Inicializa el array escritura.
	 */
	private static Hashtable<String, Integer> initEscrituraOral(){
		Hashtable<String, Integer> miTabla = new Hashtable<String, Integer>(32);
		
		//invertir la tabla de escritura para poder hacer la lectura más sencilla.
		
		Iterator <Entry<Integer,String>> itTablaHas = escritura.entrySet().iterator();
		Entry<Integer,String> elElem;
		while (itTablaHas.hasNext()) {
			elElem = itTablaHas.next();
			miTabla.put(elElem.getValue(), elElem.getKey());
		};
		
		return miTabla;
	}

	/*
	 * Función que convierte un número en base 8 a base 10.
	 */
	private static int aBase10(int num){
		return Integer.valueOf(Integer.toString(num), 8);
	}

	/*
	 * Función que convierte un número en base 10 a base 8.
	 */
	private static int aBase8(int num){
		return Integer.parseInt( Integer.toString(num,8) );
	}
	
	private static int deBaseaBase(int num, int baseInicial, int baseFinal){
		int numBase10 = Integer.valueOf(Integer.toString(num), baseInicial);
		int numBaseFinal = Integer.parseInt( Integer.toString(numBase10,baseFinal) );
		return numBaseFinal;
	}

    /*
     * ToDo: Función privada genérica para el cambio de representación
	private toGenericString(){
		
	}
     */

	/**
	 * Muestra el número almacenado en nuestra representaci�n
	 */
	@Override
	public String toString() {
		char[] strNumeroRepresentado;
		String strElNumero = Integer.toString(elNumero);
		int longElNumero = strElNumero.length();
		strNumeroRepresentado = new char[longElNumero];
		int auxNumero = elNumero;
		int digito;
		for (int i=longElNumero-1; i>=0; i--){
			digito = auxNumero % 10;
			auxNumero = auxNumero / 10;
			strNumeroRepresentado[i]= losDigitos[ digito ];
		}
		return new String (strNumeroRepresentado)	;
	}

	/**
	 * Muestra el número almacenado en nuestra representaci�n
	 */
	public static String toString(int elNum) {
		MiNumero elNumMio = new MiNumero(elNum);
		return elNumMio.toString();
	}

	public static String toString(int elNum, int radix) {
		MiNumero elNumMio = new MiNumero(MiNumero.deBaseaBase(elNum, radix, 8));
		return elNumMio.toString();
	}

	/**
	 * Muestra el número escrito con notación texto almacenado en nuestra representación
	 */
	public String toLongString() {
		StringBuilder strLongNumeroRepresentado = new StringBuilder();
		
		if (MiNumero.escritura.containsKey(elNumero)) {
			strLongNumeroRepresentado.append(escritura.get(elNumero));
		} else if ((20 < elNumero) && (elNumero < 30)) {
			strLongNumeroRepresentado .append(escritura.get(20));
			strLongNumeroRepresentado .append(SEPARADOR_Y);
			strLongNumeroRepresentado .append(escritura.get(elNumero % 10));
		} else if ((30 < elNumero) && (elNumero < 40)) {
			strLongNumeroRepresentado .append(escritura.get(30));
			strLongNumeroRepresentado .append(SEPARADOR_Y);
			strLongNumeroRepresentado .append(escritura.get(elNumero % 10));
    	} else if ((40 < elNumero) && (elNumero < 50)) {
			strLongNumeroRepresentado .append(escritura.get(40));
			strLongNumeroRepresentado .append(SEPARADOR_Y);
			strLongNumeroRepresentado .append(escritura.get(elNumero % 10));
		} else if ((50 < elNumero) && (elNumero < 60)) {
			strLongNumeroRepresentado .append(escritura.get(50));
			strLongNumeroRepresentado .append(SEPARADOR_Y);
			strLongNumeroRepresentado .append(escritura.get(elNumero % 10));
		} else if ((60 < elNumero) && (elNumero < 70)) {
			strLongNumeroRepresentado .append(escritura.get(60));
			strLongNumeroRepresentado .append(SEPARADOR_Y);
			strLongNumeroRepresentado .append(escritura.get(elNumero % 10));
		} else if ((70 < elNumero) && (elNumero < 100)) {
			strLongNumeroRepresentado .append(escritura.get(70));
			strLongNumeroRepresentado .append(SEPARADOR_Y);
			strLongNumeroRepresentado .append(escritura.get(elNumero % 10));
		} else if ((100 < elNumero) && (elNumero < 1000)) {
			MiNumero numeroC = new MiNumero (elNumero / 100); // Centenas
			MiNumero numeroD = new MiNumero (elNumero % 100); // Decenas
			if (elNumero / 100 != 1){
				strLongNumeroRepresentado.append(numeroC.toLongString());
				strLongNumeroRepresentado.append(SEPARADOR);
			}
			strLongNumeroRepresentado .append(escritura.get(100));
			strLongNumeroRepresentado .append(SEPARADOR);
			strLongNumeroRepresentado .append(numeroD.toLongString());
		} else if (1000 < elNumero) {

			// ALGORÍTMO CASI ITERATIVO: Sólo recursivo en los millares
  			
			int elNumM_Aux = elNumero;
			int elNumC_Aux;
 			while (elNumM_Aux > 1){
 				elNumC_Aux = elNumM_Aux % 1000;
 				elNumM_Aux = elNumM_Aux / 1000;
 				strLongNumeroRepresentado.insert(0, new MiNumero(elNumC_Aux).toLongString());
 				if ( elNumM_Aux != 0 ) strLongNumeroRepresentado.insert(0, SEPARADOR);
				if ( elNumM_Aux > 0 ) strLongNumeroRepresentado.insert(0, escritura.get(1000));
				if ( elNumM_Aux > 1 ) strLongNumeroRepresentado.insert(0, SEPARADOR);
 			}

/* 
			// ALGORíTMO RECURSIVO: Funciona 
 			MiNumero elNumM = new MiNumero (elNumero / 1000); // Millares

			MiNumero elNumC = new MiNumero (elNumero % 1000); // Centenas
			if (elNumero / 1000 != 1){
				strLongNumeroRepresentado.append(elNumM.toLongString());
				strLongNumeroRepresentado.append(SEPARADOR);
			}
			strLongNumeroRepresentado.append(escritura.get(1000));
			strLongNumeroRepresentado.append(SEPARADOR);
			strLongNumeroRepresentado.append(elNumC.toLongString());

*/ 		} else strLongNumeroRepresentado.append("ERROR NO EN RANGO");

		return strLongNumeroRepresentado.toString();
	}
	public static String toLongString(int elNum) {
		MiNumero elNumMio = new MiNumero(elNum);
		return elNumMio.toLongString();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + elNumero;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MiNumero other = (MiNumero) obj;
		if (elNumero != other.elNumero)
			return false;
		return true;
	}


	/**
	 * @param args
	 */

	public static void main(String[] args) {
/*		String mio = "\u0239";
		System.out.println("ȹ, ^, ɛ, ŧ, ƺ, ɷ, ʔ, ʪ");
		System.out.print("a: "); System.out.println("\u2300");
		System.out.print("0: "); System.out.println("\u0239");
		System.out.print("1: "); System.out.println("\u005E");
		System.out.print("2: "); System.out.println("\u03B5");
		System.out.print("3: "); System.out.println("\u0167");
		System.out.print("4: "); System.out.println("\u01BA");
		System.out.print("5: "); System.out.println("\u0277");
		System.out.print("6: "); System.out.println("\u0294");
		System.out.print("7: "); System.out.println("\u026E");
		System.out.println(mio);

//		System.out.println("ȹ = "+(int) 'ȹ'+" en hex: 0239 ====\u0239===="+ Character.getName(569)+" "+ (char) Character.getDirectionality(569));
//		System.out.println("ƺ = "+(int) 'ƺ'+" en hex: 01BA ====\u01BA===="+ Character.getName(442)+" "+ (char) Character.getDirectionality(422));
//		System.out.println("^ = "+(int) '^'+" en hex: 005E ====\u005E===="+ Character.getName(94)+" "+ (char) Character.getDirectionality(94));

		System.out.println(MiNumero.aBase10(10));
		int elNum = 52014;
		MiNumero elNumero1 = new MiNumero(elNum);
		System.out.println(elNum + " = " + elNumero1 );

		int elNum2 =12226;
		MiNumero elNumero2 = new MiNumero(elNum2);
		System.out.println(elNum2 + " = " + elNumero2 + ". En guiri-guiri oral: " + elNumero2.toLongString());
//		System.out.println(Integer.toString(19, 8));

		int elNum3 =125742226;
		MiNumero elNumero3 = new MiNumero(elNum3);
		System.out.println(elNum3 + " = " + elNumero3 + ". En guiri-guiri oral: " + elNumero3.toLongString());
*/
		String elNum4 = "na";
		MiNumero elNumero4 = new MiNumero(elNum4);
		System.out.println(elNum4 + " = " + elNumero4 + ". En guiri-guiri oral: " + elNumero4.toLongString()
				+ ". En base 8: " + elNumero4.elNumero);
		
/*		int elNum5 = 10;
		MiNumero elNumero5 = new MiNumero(elNum5, 10);
		System.out.println("ElNumero: "+ elNumero5.elNumero);
		System.out.println(elNum5 + " = " + elNumero5 + ". En guiri-guiri oral: " + elNumero5.toLongString());
		// A base 8
		String strNum = Integer.toString(elNum5, 8);
		System.out.println(String.format("%1$d en base 10 es el número %2$d en base 8", elNum5, MiNumero.deBaseaBase(elNum5,10,8)));
		System.out.println(String.format("%1$d en base 10 es el número %2$s en base 8", elNum5, MiNumero.aBase8(elNum5)));
		// A base 10
		int elNumB10 = Integer.parseInt(Integer.toString(elNum5), 8);
		System.out.println(String.format("%1$d en base 8 es el número %2$d en base 10", elNum5, MiNumero.aBase10(elNum5)));
		System.out.println(String.format("%1$d en base 8 es el número %2$d en base 10", elNum5, MiNumero.deBaseaBase(elNum5,8,10)));
		// el numero en base 2 es el numero en base 8
*/
		try{
			String elNum6 = "prio lime burte molen cletane";
			MiNumero elNumero6 = new MiNumero(elNum6);
			System.out.println(elNum6 + " = " + elNumero6 + ". En guiri-guiri oral: " + elNumero6.toLongString()
				+ " En base 8 normal = " + elNumero6.elNumero);
		} catch (NumberFormatException e){
			System.out.println("ERROR: Número mal escrito.");
		}
		
	}

}
