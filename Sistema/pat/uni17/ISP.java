package c_elab.pat.uni17;

import java.io.IOException;
import java.net.URISyntaxException;

import javax.xml.bind.JAXBException;

import it.clesius.datalayer.isee.business.isee159.beans.TypeSubAttestazioneList;

public class ISP extends QStudente  {
	
	boolean hasIseeConnesso = false;
	
    public double getValue() {
        try {
        	
        	it.clesius.datalayer.icef.business.isee159.Isee159Client cIcef = new it.clesius.datalayer.icef.business.isee159.Isee159Client();
        	
        	Double isp = 0.0;
        	
        	boolean riconferma 	= uni_domanda.getBoolean(1, uni_domanda.getIndexOfColumnName("riconferma"));
        	boolean paritario 	= uni_domanda.getBoolean(1, uni_domanda.getIndexOfColumnName("paritario"));
        	
        	if(riconferma){
        		//prendo icef da uni_dati 
        		isp = new Double((String)(uni_dati.getElement(10, 2))).doubleValue();
        	}else if(paritario){
        		//TODO prendo vse dal UNI_isee_parificato -> c_elaout
        		isp = new Double((String)(isee_paritario.getElement(1, 5))).doubleValue();
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
    						isp = w.getSubAttestazioni().get(0).getValori().getISP().doubleValue();
    					}else{
    						isp = 9999999.0;
    					}
    				}else{
    					isp = 9999999.0;
    				}
    			} else {
    				isp = 9999999.0;
    			}
        	}
        	
        	return isp;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 9999999.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 9999999.0;
        }
    }
    
}
