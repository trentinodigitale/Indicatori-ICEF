package c_elab.pat.uni17.domICEF17;


public class Id_domanda_icef extends c_elab.pat.uni17.QStudente{
    
    public double getValue() {
    	
    	Double id_domanda = 0.0;
    	
        try {
        	
        	boolean riconferma 	= uni_domanda.getBoolean(1, uni_domanda.getIndexOfColumnName("riconferma"));
        	boolean paritario 	= uni_domanda.getBoolean(1, uni_domanda.getIndexOfColumnName("paritario"));
        	if(riconferma){
        		//il valore dell'icef viene passato da Opera hanno loro i riferimenti degli id_domanda
        	}else if(paritario){
        		//prendo icef dal servizio 1517 
        		if(icef_paritario.getElement(1, icef_paritario.getIndexOfColumnName("id_domanda_parificata")) != null){
        			id_domanda = icef_paritario.getDouble(1, icef_paritario.getIndexOfColumnName("id_domanda_parificata"));
        		}else{
        			id_domanda = 0.0; // passo 9 se non trovo nessuan domanda. Nel plugin questo valore viene trasformato in null, dato che 9 corrisponde a non aver fatto l'icef
        		}
        	}else{
        		//prendo id_domanda del servizio 1217
        		if( icef10.getElement(1, icef10.getIndexOfColumnName("id_domanda_dic_icef")) != null){
        			id_domanda = icef10.getDouble(1, icef10.getIndexOfColumnName("id_domanda_dic_icef"));
        		}else{
        			id_domanda = 0.0; // passo 9 se non trovo nessuan domanda. Nel plugin questo valore viene trasformato in null, dato che 9 corrisponde a non aver fatto l'icef
        		}
        	}
        	return id_domanda;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
    
}
