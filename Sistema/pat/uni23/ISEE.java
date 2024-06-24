package c_elab.pat.uni23;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import it.clesius.datalayer.isee.business.isee159.beans.TypeSubAttestazioneList;

public class ISEE extends QStudente  {
    
	//TODO quando ci sarà una ricorferma isee (dal prossimo anno) se nuova domanda cerco il valore isee dall'attestazione
	// se riconferma il valore isee devo leggerlo da UNI_dati -> pos 9
	
	boolean hasIseeConnesso = false;
	
    public double getValue() {
        try {
        	
        	it.clesius.datalayer.icef.business.isee159.Isee159Client cIcef = new it.clesius.datalayer.icef.business.isee159.Isee159Client();
        	
        	Double isee = 0.0;
        	
        	boolean riconferma 	= uni_domanda.getBoolean(1, uni_domanda.getIndexOfColumnName("riconferma"));
        	boolean paritario 	= uni_domanda.getBoolean(1, uni_domanda.getIndexOfColumnName("paritario"));
        	
        	if(riconferma){
        		//prendo icef da uni_dati 
        		isee = new Double((String)(uni_dati.getElement(9, 2))).doubleValue();
        	}else if(paritario){
        		//prendo vse dal UNI_isee_parificato -> c_elaout
        		isee = new Double((String)(isee_paritario.getElement(1, 3))).doubleValue();
        	}else{
        		//prendo isee dall'attestazione connessa alla domanda -> domande.id_dichiarazione -> ise159_attestaziona tipologia di isee Universitario (r_servizi.)
        		
        		if (isee_connesso!=null && isee_connesso.getRows()>0) {
    				if(isee_connesso.getElement(1, isee_connesso.getIndexOfColumnName("id_dichiarazione"))!=null &&
    						!isee_connesso.getString(1, isee_connesso.getIndexOfColumnName("id_dichiarazione")).equals("") &&
    						!isee_connesso.getString(1, isee_connesso.getIndexOfColumnName("id_dichiarazione")).toUpperCase().equals("NULL") &&
    						isee_connesso.getInteger(1, isee_connesso.getIndexOfColumnName("id_dichiarazione"))>1 ){
    					hasIseeConnesso = true;
    				}
    				if(hasIseeConnesso){
    					//ISEE DEL BENEFICIARIO DEL TIPO_ISEE / TIPO_CALCOLO / PROTOCOLLO ATTESTAZIONE
    					TypeSubAttestazioneList w = null;
    					
    					String ambiente ="ICEF";// System.getProperty("environment");
    					if (ambiente.toUpperCase().equals("ICEF")) {
    						try {
								w = cIcef.getSubAttestazione(
										isee_connesso.getString(1, isee_connesso.getIndexOfColumnName("protocollo")), 
										isee_connesso.getString(1, isee_connesso.getIndexOfColumnName("codice_fiscale")), 
										Short.parseShort(isee_connesso.getString(1, isee_connesso.getIndexOfColumnName("id_tp_isee_richiesto"))), 
										(Short)null);
							} catch (IOException e) {
								e.printStackTrace();
							} catch (URISyntaxException e) {
								e.printStackTrace();
							} catch (JAXBException e) {
								e.printStackTrace();
							}
    					} 
    					if(w.getSubAttestazioni()!=null && w.getSubAttestazioni().size()>0 && 
    							w.getSubAttestazioni().get(0).isTrovato()){
    						//TODO controllare che prenda isee in ISEEMin -> StudenteUniversitario -> Valori
    						isee = w.getSubAttestazioni().get(0).getValori().getISEE().doubleValue();
    					}else{
    						isee = 9999999.0;
    					}
    				}else{
    					isee = 9999999.0;
    				}
    			} else {
    				isee = 9999999.0;
    			}
        	}
        	
        	return isee;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 9999999.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 9999999.0;
        }
    }
    
}
