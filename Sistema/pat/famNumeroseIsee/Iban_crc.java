package c_elab.pat.famNumeroseIsee;


/**
 * crc dell'iban.
 */
public class Iban_crc extends QDati {
	
	//CAMBIAMI SE CAMBIA ID_SERVIZIO
	private int idTpPagamentoAssegno 	= 105;
	private int idTpPagamentoCc			= 2; 

	
	/**
	 * @param
	 */
    public double getValue() { 
        try {
        	
        	double crc_iban = 0.0;
        	String iban = "";
        	
        	int idTpPagamento 	= (int)( records.getInteger(1, records.getIndexOfColumnName("id_tp_pagamento")));
        	if(idTpPagamento == idTpPagamentoAssegno){
        		iban = ""; 
        	}else if(idTpPagamento == idTpPagamentoCc){
        		String codiceStato 	= (String)( records.getString(1, records.getIndexOfColumnName("codice_stato")));
        		if(codiceStato.equals("IT")){
        			String cin 		= (String)( records.getString(1, records.getIndexOfColumnName("CIN_pagamento")));
                    String abi 		= (String)( records.getString(1, records.getIndexOfColumnName("ABI_pagamento")));
                    String cab 		= (String)( records.getString(1, records.getIndexOfColumnName("CAB_pagamento")));
                    String cc 		= (String)( records.getString(1, records.getIndexOfColumnName("Cc_pagamento")));
                    
                    iban = codiceStato.concat(cin).concat(abi).concat(cab).concat(cc);
        		}else{
        			iban = (String)( records.getString(1, records.getIndexOfColumnName("IBAN")));
        		}
        	}else{
        		//passo crc_iban = 0.0;
        	}
        	
        	crc_iban  = iban.hashCode();
            
            return crc_iban;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    public Iban_crc(){
    }
}
