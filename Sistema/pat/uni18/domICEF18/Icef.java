package c_elab.pat.uni18.domICEF18;

import c_elab.pat.uni18.QStudente;

public class Icef extends QStudente {
    
	//caso in cui UNI_domanda la domanda è 1317 e riconferma = 1 -> il dato ICEF va preso da UNI_dati.ICEF
	//caso in cui UNI_domanda la domanda è 1317 e riconferma = 0 -> e parificato = 0 --> il dato ICEF va preso dalla domanda 1217 con filtro codice fiscale. 
	//																					Prendere da C_elaout il nodo ICEF10
	//																e parificato = 1 --> il dato ICEF va preso dalla domanda 1517 con filtro codice fiscale
	//																					Prendere da C_elaout il nodo ICEF10 (o ICEF)
	
    public double getValue() {
    	
    	Double icef = 0.0;
    	
        try {
        	
        	boolean riconferma 	= uni_domanda.getBoolean(1, uni_domanda.getIndexOfColumnName("riconferma"));
        	boolean paritario 	= uni_domanda.getBoolean(1, uni_domanda.getIndexOfColumnName("paritario"));
        	if(riconferma){
        		//prendo icef da uni dati 
        		icef = new Double((String)(uni_dati.getElement(9, 2))).doubleValue();
        	}else if(paritario){
        		//prendo icef dal servizio 1517 
        		if(icef_paritario.getElement(1, icef_paritario.getIndexOfColumnName("icef_parificato")) != null){
        			icef = icef_paritario.getDouble(1, icef_paritario.getIndexOfColumnName("icef_parificato"));
        		}else{
        			icef = 9.0; // passo 9 se non trovo nessuan domanda. Nel plugin questo valore viene trasformato in null, dato che 9 corrisponde a non aver fatto l'icef
        		}
        	}else{
        		//prendo icef da c_elaout.icef10 del servizio 1217
        		if( icef10.getElement(1, icef10.getIndexOfColumnName("icef10")) != null){
        			icef = icef10.getDouble(1, icef10.getIndexOfColumnName("icef10"));
        		}else{
        			icef = 9.0; // passo 9 se non trovo nessuan domanda. Nel plugin questo valore viene trasformato in null, dato che 9 corrisponde a non aver fatto l'icef
        		}
        	}
        	return icef;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 9.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 9.0;
        }
    }
    
}
