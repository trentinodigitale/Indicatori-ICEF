package c_elab.pat.icef09;

import java.util.Date;
import java.util.*;

/*Questa classe, con vari metodi; che sostanzialmente si richiamano l'un l'altro fino ad
*arrivare al metodo centrale del programma:public static double getPercUsuf(int eta,int durata,String tipo),
*legge dei valori da due tabelle registrate in due array(2*n): aVita e aContratto.
*Nei metodi getValore*() la percentuale ricavata nella tabella viene moltiplicata per il 
*valore(valore ICI dell'immobile) dato in imput al metodo.
*/

public class Usufrutto {

	/*Nella prima riga sono indicati gli anni di durata del contratto di usufrutto,
	*nella seconda la percentuale corrispondente del valore dell'immobile
	*/
	private static final float[][] aContratto04 = { // ordine crescente!!!
		// tasso 2,5%  dal 1/1/2004
		{ 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 12.0f, 15.0f, 20.0f, 25.0f, 30.0f, 35.0f, 40.0f, 50.0f },
		{ 0.02f, 0.05f, 0.07f, 0.09f, 0.12f, 0.14f, 0.16f, 0.18f, 0.20f, 0.22f, 0.26f, 0.31f, 0.39f, 0.46f, 0.52f, 0.58f, 0.63f, 0.71f },
	};
    private static final float[][] aContratto08 = { // ordine crescente!!!
		// tasso 3% dal 1/1/2008
		{ 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 12.0f, 15.0f, 20.0f, 25.0f, 30.0f, 35.0f, 40.0f, 50.0f },
		{ 0.03f, 0.06f, 0.08f, 0.11f, 0.14f, 0.16f, 0.19f, 0.21f, 0.23f, 0.26f, 0.30f, 0.36f, 0.45f, 0.52f, 0.59f, 0.64f, 0.69f, 0.77f },
	};
	private static final float[][] aContratto10 = { // ordine crescente!!!
		// tasso 1% dal 1/1/2010
		{ 1.0f, 2.0f, 3.0f, 4.0f, 5.0f, 6.0f, 7.0f, 8.0f, 9.0f, 10.0f, 12.0f, 15.0f, 20.0f, 25.0f, 30.0f, 35.0f, 40.0f, 50.0f },
		{ 0.01f, 0.02f, 0.03f, 0.04f, 0.05f, 0.06f, 0.07f, 0.08f, 0.09f, 0.09f, 0.11f, 0.14f, 0.18f, 0.22f, 0.26f, 0.29f, 0.33f, 0.39f },
	};

	/*Nella prima riga è indicata l'età dell'usufruttuario
	*nella seconda la corrispondente percentuale del valore dell'immobile
	*/
	private static final float[][] aVita04 = { // ordine crescente!!!!
		{ 20.0f, 30.0f, 40.0f, 45.0f, 50.0f, 53.0f, 56.0f, 60.0f, 63.0f, 66.0f, 69.0f, 72.0f, 75.0f, 78.0f, 82.0f, 86.0f, 92.0f, 99.0f },
		{ 0.95f, 0.9f, 0.85f, 0.8f, 0.75f, 0.7f, 0.65f, 0.6f, 0.55f, 0.5f, 0.45f, 0.4f, 0.35f, 0.3f, 0.25f, 0.2f, 0.15f, 0.1f },
	};
	private static final float[][] aVita08 = { // ordine crescente!!!!
		{ 20.0f, 30.0f, 40.0f, 45.0f, 50.0f, 53.0f, 56.0f, 60.0f, 63.0f, 66.0f, 69.0f, 72.0f, 75.0f, 78.0f, 82.0f, 86.0f, 92.0f, 99.0f },
		{ 0.9525f, 0.9f, 0.8475f, 0.795f, 0.7425f, 0.69f, 0.6375f, 0.585f, 0.5325f, 0.48f, 0.4275f, 0.375f, 0.3225f, 0.27f, 0.2175f, 0.165f, 0.1125f, 0.06f },
	};
	private static final float[][] aVita10 = { // ordine crescente!!!!
		{ 20.0f, 30.0f, 40.0f, 45.0f, 50.0f, 53.0f, 56.0f, 60.0f, 63.0f, 66.0f, 69.0f, 72.0f, 75.0f, 78.0f, 82.0f, 86.0f, 92.0f, 99.0f },
		{ 0.95f, 0.9f, 0.85f, 0.8f, 0.75f, 0.7f, 0.65f, 0.6f, 0.55f, 0.5f, 0.45f, 0.4f, 0.35f, 0.3f, 0.25f, 0.2f, 0.15f, 0.1f },
	};
    
	public static float getPercNudaProprieta_aVita(
		Calendar calNascita,
		Calendar calRif, 
		int yearRif) {
		return 1.0f - getPercUsuf_aVita(calNascita, calRif, yearRif);
	}

	public static long getValoreNudaProprieta_aVita(
		double valore,
		Calendar calNascita,
		Calendar calRif, 
		int yearRif) {
		return (long) (valore * getPercNudaProprieta_aVita(calNascita, calRif, yearRif));
	}

	public static long getValoreUsuf_aVita(
		double valore,
		Calendar calNascita,
		Calendar calRif, 
		int yearRif) {
		return (long) (valore * getPercUsuf_aVita(calNascita, calRif, yearRif));
	}

	public static float getPercUsuf_aVita(
		Calendar calNascita,
		Calendar calRif, 
		int yearRif) {
		return getPercUsuf_aVita(getEta(calNascita, calRif), yearRif);
	}

	public static float getPercUsuf_aVita(
		String codFisc,
		java.util.Date dataRif, 
		int yearRif) {

		//  System.out.println("getPercUsuf_aVita  ");

		Calendar calRif = Calendar.getInstance();
		if (dataRif != null)
			calRif.setTime(dataRif);

		return getPercUsuf_aVita(codFisc, calRif, yearRif);
	}

	public static int getEta(Date dataNascita, Date dataRif) {
		//System.out.println("getEta = " + dataNascita + " " + dataRif);
		Calendar calNascita = Calendar.getInstance();
		calNascita.setTime(dataNascita);
		Calendar calRif = Calendar.getInstance();
		if (dataRif != null)
			calRif.setTime(dataRif);
		//System.out.println("getEta = " + calNascita + " " + calRif);
		//System.out.println( " anno " + calRif.get(calRif.YEAR) + " " + calNascita.get(calNascita.YEAR));

		return getEta(calNascita, calRif);

	}

	public static int getEta(Calendar calNascita, Calendar calRif) {
		//System.out.println( " anno " + calRif.YEAR + " " + calNascita.YEAR);
		int eta = 0;
		if (calRif == null)
			calRif = Calendar.getInstance();

		eta = calRif.get(Calendar.YEAR) - calNascita.get(Calendar.YEAR);

		if (calRif.get(Calendar.MONTH) - calNascita.get(Calendar.MONTH) < 0) {
			eta = eta - 1;
		}

		if (calRif.get(Calendar.MONTH) == calNascita.get(Calendar.MONTH)
			&& calRif.get(Calendar.DAY_OF_MONTH)
				< calNascita.get(Calendar.DAY_OF_MONTH)) {
			eta = eta - 1;
		}

		//        if(dataNascita.getYear()>= 90) { // l'utente è nato tra il 1890  e 1900
		//            eta += 100;
		//        }

		//System.out.println("usufrutto = " + eta);

		return eta;
	}
	////////////////////////////////////////////////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Esegue il controllo del codice fiscale
	 * ritorna true o false a seconda che il valore del codice fiscale sia o meno
	 * corretto..
	 */
	private static boolean CheckCodiceFiscale(String cod) {
		int i, tot, resto;
		//char    let_ctrl;

		cod = cod + "                ";
		cod = cod.substring(0, 16);

		tot = 0;
		resto = 0;

		for (i = 0; i <= 14; i++) {
			if ((i / 2) == ((i + 1) / 2)) { // pari
				switch (cod.charAt(i)) {
					case 'A' :
						tot = tot + 1;
						break;
					case '0' :
						tot = tot + 1;
						break;
					case 'B' :
						tot = tot + 0;
						break;
					case '1' :
						tot = tot + 0;
						break;
					case 'C' :
						tot = tot + 5;
						break;
					case '2' :
						tot = tot + 5;
						break;
					case 'D' :
						tot = tot + 7;
						break;
					case '3' :
						tot = tot + 7;
						break;
					case 'E' :
						tot = tot + 9;
						break;
					case '4' :
						tot = tot + 9;
						break;
					case 'F' :
						tot = tot + 13;
						break;
					case '5' :
						tot = tot + 13;
						break;
					case 'G' :
						tot = tot + 15;
						break;
					case '6' :
						tot = tot + 15;
						break;
					case 'H' :
						tot = tot + 17;
						break;
					case '7' :
						tot = tot + 17;
						break;
					case 'I' :
						tot = tot + 19;
						break;
					case '8' :
						tot = tot + 19;
						break;
					case 'J' :
						tot = tot + 21;
						break;
					case '9' :
						tot = tot + 21;
						break;
					case 'K' :
						tot = tot + 2;
						break;
					case 'L' :
						tot = tot + 4;
						break;
					case 'M' :
						tot = tot + 18;
						break;
					case 'N' :
						tot = tot + 20;
						break;
					case 'O' :
						tot = tot + 11;
						break;
					case 'P' :
						tot = tot + 3;
						break;
					case 'Q' :
						tot = tot + 6;
						break;
					case 'R' :
						tot = tot + 8;
						break;
					case 'S' :
						tot = tot + 12;
						break;
					case 'T' :
						tot = tot + 14;
						break;
					case 'U' :
						tot = tot + 16;
						break;
					case 'V' :
						tot = tot + 10;
						break;
					case 'W' :
						tot = tot + 22;
						break;
					case 'X' :
						tot = tot + 25;
						break;
					case 'Y' :
						tot = tot + 24;
						break;
					case 'Z' :
						tot = tot + 23;
						break;
				}
			} else { // dispari
				switch (cod.charAt(i)) {
					case 'A' :
						tot = tot + 0;
						break;
					case '0' :
						tot = tot + 0;
						break;
					case 'B' :
						tot = tot + 1;
						break;
					case '1' :
						tot = tot + 1;
						break;
					case 'C' :
						tot = tot + 2;
						break;
					case '2' :
						tot = tot + 2;
						break;
					case 'D' :
						tot = tot + 3;
						break;
					case '3' :
						tot = tot + 3;
						break;
					case 'E' :
						tot = tot + 4;
						break;
					case '4' :
						tot = tot + 4;
						break;
					case 'F' :
						tot = tot + 5;
						break;
					case '5' :
						tot = tot + 5;
						break;
					case 'G' :
						tot = tot + 6;
						break;
					case '6' :
						tot = tot + 6;
						break;
					case 'H' :
						tot = tot + 7;
						break;
					case '7' :
						tot = tot + 7;
						break;
					case 'I' :
						tot = tot + 8;
						break;
					case '8' :
						tot = tot + 8;
						break;
					case 'J' :
						tot = tot + 9;
						break;
					case '9' :
						tot = tot + 9;
						break;
					case 'K' :
						tot = tot + 10;
						break;
					case 'L' :
						tot = tot + 11;
						break;
					case 'M' :
						tot = tot + 12;
						break;
					case 'N' :
						tot = tot + 13;
						break;
					case 'O' :
						tot = tot + 14;
						break;
					case 'P' :
						tot = tot + 15;
						break;
					case 'Q' :
						tot = tot + 16;
						break;
					case 'R' :
						tot = tot + 17;
						break;
					case 'S' :
						tot = tot + 18;
						break;
					case 'T' :
						tot = tot + 19;
						break;
					case 'U' :
						tot = tot + 20;
						break;
					case 'V' :
						tot = tot + 21;
						break;
					case 'W' :
						tot = tot + 22;
						break;
					case 'X' :
						tot = tot + 23;
						break;
					case 'Y' :
						tot = tot + 24;
						break;
					case 'Z' :
						tot = tot + 25;
						break;
				} // switch
			} //  else
		}

		resto = tot - (tot / 26) * 26; //tot Mod 26

		if (cod.charAt(15) == (resto + 65))
			return true;
		else
			return false;
	}

	public static long getValoreUsuf_aTermine(double valore, int durata, int yearRif) {
		return (long) (valore * getPercUsuf_aTermine(durata, yearRif));
	}

	public static long getValoreNudaProprieta_aTermine(
		double valore,
		int durata, 
		int yearRif) {
		return (long) (valore * getPercNudaProprieta_aTermine(durata, yearRif));
	}

	public static float getPercNudaProprieta_aTermine(int durata, int yearRif) {
		return (1.0f - getPercUsuf_aTermine(durata, yearRif));
	}

	public static float getPercUsuf_aTermine(int durata, int yearRif) {

		int i = 0;
        // prima del 2008
		if (yearRif < 2008) {
			if (durata <= aContratto04[0][aContratto04[0].length - 1]) {
				while (durata > aContratto04[0][i]) {
					i++;
				}
			} else {
				i = aContratto04[0].length - 1;
			}
			return aContratto04[1][i];
        // nel 2008 e 2009
		} else if ( yearRif == 2008 || yearRif == 2009 ) {
			if (durata <= aContratto08[0][aContratto08[0].length - 1]) {
				while (durata > aContratto08[0][i]) {
					i++;
				}
			} else {
				i = aContratto08[0].length - 1;
			}
			return aContratto08[1][i];
        // a partire dal 2010
		} else {
			if (durata <= aContratto10[0][aContratto10[0].length - 1]) {
				while (durata > aContratto10[0][i]) {
					i++;
				}
			} else {
				i = aContratto10[0].length - 1;
			}
			return aContratto10[1][i];
		}
	}

	public static long getValoreNudaProprieta_aVita(double valore, int eta, int yearRif) {
		return (long) (valore * getPercNudaProprieta_aVita(eta, yearRif));
	}

	public static long getValoreNudaProprieta_aVita(
		double valore,
		Date dataNascita,
		Date dataRif, 
		int yearRif) {
		return (long)
			(valore * getPercNudaProprieta_aVita(dataNascita, dataRif, yearRif));
	}

	public static long getValoreUsuf_aVita(
		double valore,
		Date dataNascita,
		Date dataRif, 
		int yearRif) {
		return (long) (valore * getPercUsuf_aVita(dataNascita, dataRif, yearRif));
	}

	public static long getValoreUsuf_aVita(double valore, int eta, int yearRif) {
		return (long) (valore * getPercUsuf_aVita(eta, yearRif));
	}

	public static float getPercNudaProprieta_aVita(
		Date dataNascita,
		Date dataRif, 
		int yearRif) {
		return 1.0f - getPercUsuf_aVita(dataNascita, dataRif, yearRif);
	}

	public static float getPercNudaProprieta_aVita(
		String codFisc,
		Date dataRif, 
		int yearRif) {
		return (1.0f - getPercUsuf_aVita(codFisc, dataRif, yearRif));
	}

	public static float getPercNudaProprieta_aVita(int eta, int yearRif) {
		return (1.0f - getPercUsuf_aVita(eta, yearRif));
	}

	public static long getValoreNudaProprieta_aVita(
		double valore,
		String codFisc,
		Date dataRif, 
		int yearRif) {
		return (long) (valore * getPercNudaProprieta_aVita(codFisc, dataRif, yearRif));
	}

	public static long getValoreUsuf_aVita(
		double valore,
		String codFisc,
		Date dataRif, 
		int yearRif) {
		return (long) (valore * getPercUsuf_aVita(codFisc, dataRif, yearRif));
	}

	/*Non viene contemplato il caso di una persona nata prima del 1900
	*/

	public static float getPercUsuf_aVita(String codFisc, Calendar calRif, int yearRif) {

		try {
			codFisc.toString();
		} catch (NullPointerException n) {
			codFisc = "";
		}

		if (!(CheckCodiceFiscale(codFisc))) {

			//ritorna una persona con età 0
			return getPercUsuf_aVita(0, yearRif);

		} else {
			// trovo le sottostringhe del codice fiscale che mi interessano
			String giorno = codFisc.substring(9, 11);
			char mese = codFisc.charAt(8);
			String anno = codFisc.substring(6, 8);

			// a partire dalla stringa indicante il giorno trovo il giorno effettivo
			int mioGiorno = (new Integer(giorno)).intValue();
			if (mioGiorno > 40) {
				mioGiorno -= 40;
			}

			// a partire dalla stringa indicante il mese trovo il  mese
			int mioMese = 0;
			switch (mese) {
				case 'A' :
					mioMese = 1;
					break;

				case 'B' :
					mioMese = 2;
					break;

				case 'C' :
					mioMese = 3;
					break;

				case 'D' :
					mioMese = 4;
					break;

				case 'E' :
					mioMese = 5;
					break;

				case 'H' :
					mioMese = 6;
					break;

				case 'L' :
					mioMese = 7;
					break;

				case 'M' :
					mioMese = 8;
					break;

				case 'P' :
					mioMese = 9;
					break;

				case 'R' :
					mioMese = 10;
					break;

				case 'S' :
					mioMese = 11;
					break;

				case 'T' :
					mioMese = 12;
					break;

				default :
					System.out.println(
						"Controllare il mese del codice fiscale");
					break;
			}

			// trovo l'anno
			int mioAnno = (new Integer(anno)).intValue();

			// creo una data dopo il 1970 per aggirare il problema Date e poi la setto ai valori che ho calcolato
			Calendar cal = Calendar.getInstance();
			cal.set(mioAnno, mioMese - 1, mioGiorno);

			/*    Date miaData = new Date(74,13,6);
			    miaData.setYear(mioAnno);
			    miaData.setMonth(mioMese -1);
			    miaData.setDate(mioGiorno);*/

			return getPercUsuf_aVita(cal, calRif, yearRif);
		}
	}
	
	/*la data di nascita deve essere>=1900 per una persona nata tra il 1890 e il 1990
	* aumentatare la data di immissione di 100
	*/
	public static float getPercUsuf_aVita(Date dataNascita, Date dataRif, int yearRif) {

		return getPercUsuf_aVita(getEta(dataNascita, dataRif), yearRif);

	}
	
	/* Questo metodo è il nucleo centrale del programma.
	*
	*Se eta o durata sono maggiori dell'ultimo elemento dell'array, esse sono considerate
	*uguali a questo.
	*/
	public static float getPercUsuf_aVita(int eta, int yearRif) {

		int i = 0;

        // prima del 2008
		if (yearRif < 2008) {
			if (eta <= aVita04[0][aVita04[0].length - 1]) {
				while (eta > (aVita04[0][i])) {
					i++;
				}
			} else {
				i = aVita04[0].length - 1;
			}
			//System.out.println("PercUsuf_aVita = " + aVita[1][i]);
			return aVita04[1][i];
        // nel 2008 e 2009
		} else if (yearRif == 2008 || yearRif == 2009) {
			if (eta <= aVita08[0][aVita08[0].length - 1]) {
				while (eta > (aVita08[0][i])) {
					i++;
				}
			} else {
				i = aVita08[0].length - 1;
			}
			//System.out.println("PercUsuf_aVita = " + aVita[1][i]);
			return aVita08[1][i];
        // a partire dal 2010
		} else {
			if (eta <= aVita10[0][aVita10[0].length - 1]) {
				while (eta > (aVita10[0][i])) {
					i++;
				}
			} else {
				i = aVita10[0].length - 1;
			}
			//System.out.println("PercUsuf_aVita = " + aVita[1][i]);
			return aVita10[1][i];
		}
	}

	public Usufrutto() {
	}

	public void main() {

	}
}
