package c_elab.pat.ITEA19; 



/**
 * 
 * @author a_pichler
 * 0 non fai niente
 * 1 calcoli l'icef attualizzato e la variazione significativa
 * 2 calcoli l'icef attualizzato 
 * 
 */
public class Calcolo_variazione_significativa extends QSopraggiuntaInvalidita { 
	
	public double getValue() {

	
		
		try {
			if(datiEpuInv!=null && datiEpuInv.getRows()>0){
				String id_domandaDaVerificare =datiEpuInv.getString( 1,datiEpuInv.getIndexOfColumnName("ID_domandaDaVerificare"));
				if(id_domandaDaVerificare!=null && id_domandaDaVerificare.length()>0){
					if(id_domandaDaVerificare.equals(IDdomanda)){
						
						return 1;
						
					}else{
						double varSignificatica=getVariazioneSignificativa(id_domandaDaVerificare);
						if(varSignificatica==1.0){
							return 2;
						}else if(varSignificatica==0.0){
							return 0;
						}else{
							return 0;
						}
						
					}
				}
				
			}
			//SE NON TROVO LA DOMANDA DA CUI PARTIRE PER IL CALCOLO DELLA VERIFICA C'E' QUALCOSA CHE NON VA PERCHE' IL PRESUPPOSTO E' CHE CI SIA 
			//ALMENO UNA DOMANDA EPU--- DISCUTIBILE SE VIENE CANCELLATA LA DOMANDA O IL SOGGETTO VENISSE TOLTO
			//SE NON TROVO NULLA FORSE LA VERIFICA DELLA FATTIBILITA' ANDREBBE FATTA SU QUESTA DOMANDA
			
			return 0;
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
	
	public Double getVariazioneSignificativa(String IDdomandaVerificaVarSignificativa) {
		try {
			
		
			StringBuffer sb = new StringBuffer();
			sb.append("SELECT ID_domanda,numeric_value ");
			sb.append("FROM            C_ElaOUT ");
			sb.append(" WHERE        (ID_domanda = "+IDdomandaVerificaVarSignificativa+") AND (node = N'variazione_significativa')");
				
			//System.out.println(sb.toString());
			doQuery(sb.toString());
			
			if(records!=null && records.getRows()>0){
				return new Double((String)records.getString( 1,records.getIndexOfColumnName("numeric_value"))).doubleValue();
				
			}
			return 0.0;
			
		} catch (Exception e) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + e.toString());
			return 0.0;
		}
	}
	
}