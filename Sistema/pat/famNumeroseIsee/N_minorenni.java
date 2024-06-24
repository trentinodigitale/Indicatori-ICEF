package c_elab.pat.famNumeroseIsee;

import java.util.List;

import it.inps.isee159.ComponenteNucleo;

/*****************************************************
                classe N_minorenni
******************************************************/


public class N_minorenni extends QIsee159{
    public double getValue() {
    	
    	it.clesius.datalayer.icef.business.isee159.Isee159Client cIcef = new it.clesius.datalayer.icef.business.isee159.Isee159Client();
    	
    	boolean hasIseeConnesso = false;
    	String rappDichFiglio = "F";
    	
    	try {
    		if (records!=null && records.getRows()>0) {
				if(records.getElement(1, records.getIndexOfColumnName("id_dichiarazione"))!=null &&
						!records.getString(1, records.getIndexOfColumnName("id_dichiarazione")).equals("") &&
						!records.getString(1, records.getIndexOfColumnName("id_dichiarazione")).toUpperCase().equals("NULL") &&
						records.getInteger(1, records.getIndexOfColumnName("id_dichiarazione"))>1 ){
					hasIseeConnesso = true;
				}
				if(hasIseeConnesso){
					//ISEE DEL BENEFICIARIO DEL TIPO_ISEE / TIPO_CALCOLO / PROTOCOLLO ATTESTAZIONE
					List<ComponenteNucleo> componentiAtt = null;
					int nFigli = 0;
					
					String ambiente = "ICEF";//System.getProperty("environment");
					if (ambiente.toUpperCase().equals("ICEF")) {
						System.out.println(records.getString(1, records.getIndexOfColumnName("protocollo")));
						componentiAtt = cIcef.getComponentiAttestazione(records.getString(1, records.getIndexOfColumnName("protocollo")));
					}
					if(componentiAtt != null && componentiAtt.size()>0){
						for(ComponenteNucleo compN:componentiAtt ){
							String rappDich = compN.getRapportoConDichiarante();
							if(rappDich.equals(rappDichFiglio)){
								nFigli ++;
							}
						}
					}
					
					/*
					 * MB e GB 02-05-2018:
					 * Torna il max tra nFigli e 3 in quanto il controllo sul numero di figli effettivo viene fatto 
					 * manualmente dagli operatori APAPI tramite report apposito che segnala le domande dove nell'attestazione 
					 * ISEE connessa vi sono meno di 3 componenti con relazione F.
					 * Non è possibile implementare un controllo automatico in base alla relazione con il dichiarante 
					 * dell'attestazione ISEE connessa. 
					 */
					
					return Math.max(nFigli, 3);
					
				}else{
					
					//non ho un'attestazione connessa lascio passare il dato auto certificato = 3
					return 3;
				}
			} else {
				return 0;
			}
    	} catch (Exception n) {
			System.out.println("Exception in " + getClass().getName() + ": " + n.toString());
			return 0;
		}
    }
    
    
    
    
    
    
    public N_minorenni(){		//{{INIT_CONTROLS
		//}}
    }
}
