package c_elab.pat.aup18.quotaA;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolservlet.icef.PassaValoriIcef;
import it.clesius.clesius.util.Sys;

/**
 * 
 * @author g_barbieri
 *
 */
public class Tutti_lavorano extends ElainNode {

	public Tutti_lavorano() {	
	}

	protected void reset() {
		ElabContext.getInstance().resetSession( Tutti_lavorano.class.getName(), IDdomanda );
	}

	public void init(RunawayData dataTransfer) {

		ElabSession session =  ElabContext.getInstance().getSession(  Tutti_lavorano.class.getName(), IDdomanda  );

		if (!session.isInitialized()) {
			super.init(dataTransfer);
			StringBuffer sql = new StringBuffer();

			sql = new StringBuffer();
			//											   1				                	   2						      3                                      4
			sql.append("SELECT AUP_familiari.non_disponibile_lavoro, AUP_familiari.perdita_lav_dim_volontarie, AUP_familiari.senza_copertPrev, AUP_familiari.con_coperPrev_parziale ");
			sql.append("FROM AUP_familiari ");
			sql.append("WHERE AUP_familiari.ID_domanda = ");
			sql.append(IDdomanda);
			doQuery(sql.toString());
		}else{
			records = session.getRecords();
		}
	}


	public double getValue() {

		if( PassaValoriIcef.getForzaContributo(IDdomanda)!=null && PassaValoriIcef.getForzaContributo(IDdomanda)>0){
			return 1.0;
		}
		
		
		if (records == null)
			return 0.0;
		
		
		//da forzare se i servizi sociali hanno indicato Forzatura contributo intero 
		//solo se è indicato  caso sociale =1 da verificare esclusione

		double tutti_lavorano = 1.0;

		//per default tutti i componenti lavorano, appena trova un componente in condizione di non lavoro fa return 0
		try {
			for (int i = 1; i <= records.getRows(); i++) {
				//verifica se ci sono non disponibili al lavoro  
				//questa condizione non dovrebbe esistere in quanto sempre intercettata dalla servlet della quota A
				if(records.getInteger(i,1)==1){
					return 0.0;
				}
				//verifica se ci sono soggetti che si sono licenziati volontariamente  
				//questa condizione non dovrebbe esistere in quanto sempre intercettata dalla servlet della quota A
				if(records.getInteger(i,2)==1){
					return 0.0;
				}
				//verifica se ci sono soggetti con meno di 3 mesi di copertura previdenziale  
				if(records.getInteger(i,3)==1){
					return 0.0;
				}
				//verifica se ci sono soggetti con meno di 6 mesi di copertura previdenziale  
				if(records.getInteger(i,4)==1){
					return 0.0;
				}
			}

			return tutti_lavorano;

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}