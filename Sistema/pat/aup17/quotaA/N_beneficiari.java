package c_elab.pat.aup17.quotaA;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.PassaValoriIcef;

/**
 * 
 * @author g_barbieri
 *
 */
public class N_beneficiari extends ElainNode {

	public N_beneficiari() {	
	}

	protected void reset() {
		ElabContext.getInstance().resetSession( N_beneficiari.class.getName(), IDdomanda );
	}

	public void init(RunawayData dataTransfer) {

//		ElabSession session =  ElabContext.getInstance().getSession(  N_beneficiari.class.getName(), IDdomanda  );
//
//		if (!session.isInitialized()) {
//			super.init(dataTransfer);
//			StringBuffer sql = new StringBuffer();
//
////			sql = new StringBuffer();
////			sql.append("SELECT distinct ID_dichiarazione from ");
////			sql.append("AUP_dati_mese where id_domanda=");
////			sql.append(IDdomanda);
//			String componentiPV =  PassaValoriIcef.getID_dichiarazioniDomanda(IDdomanda); //classe metodo DefComponentiDich
//			if(componentiPV != null && componentiPV.length()>0){
//				//sql.append(" AND ID_dichiarazione in ");
//				//sql.append(componentiPV);
//			}
////			doQuery(sql.toString());
//		}else{
//			records = session.getRecords();
//		}
	}


	public double getValue() {

		//da forzare se i servizi sociali hanno indicato Numero Beneficiari
		//solo se è indicato caso sociale =1
		
		if(  PassaValoriIcef.getForzaBeneficiari(IDdomanda) !=null && PassaValoriIcef.getForzaBeneficiari(IDdomanda)>0){
			return PassaValoriIcef.getForzaBeneficiari(IDdomanda);
		}
		
		String componentiPV =  PassaValoriIcef.getID_dichiarazioniDomanda(IDdomanda); //classe metodo DefComponentiDich
		if(componentiPV != null && componentiPV.length()>0){
			return componentiPV.split(",").length;
		}else{
			return 0;
		}
//		if (records == null)
//			return 0.0;
//		try {
//			return records.getRows();
//		} catch (NullPointerException n) {
//			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
//			return 0.0;
//		} catch (NumberFormatException nfe) {
//			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
//			return 0.0;
//		}
	}
}