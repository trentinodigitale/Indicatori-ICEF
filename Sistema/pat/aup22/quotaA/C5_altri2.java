package c_elab.pat.aup22.quotaA;

import c_elab.pat.icef22.DefComponentiDich;
import it.clesius.clesius.util.Sys;
import it.clesius.db.sql.DBException;
import it.clesius.db.sql.RunawayData;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst;
import it.clesius.evolservlet.icef.PassaValoriIcef;
import it.clesius.db.util.Table;

/** query per il quadro C5 della dichiarazione ICEF
 * @author g_barbieri
 */
public class C5_altri2 extends DefComponentiDich {

	double patrimonioImmobiliareAttualizzato;
	
	/** C5_altri constructor */
	public C5_altri2() {
	}

	/** resetta le variabili statiche
	 * @see it.clesius.apps2core.ElainNode#reset()
	 */
	protected void reset() {
	}

	/** inizializza la Table records con i valori letti dalla query sul DB
	 * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
	 * @param dataTransfer it.clesius.db.sql.RunawayData
	 */
	public void init(RunawayData dataTransfer) {

		super.init(dataTransfer);
		StringBuffer sql = new StringBuffer();

		if(!IDdomanda.startsWith("*"))
		{
			
            patrimonioImmobiliareAttualizzato = 0d;  
                                
			//AUP 2020: attualizzazione redditi: prendo i canoni di locazione attiva             
        	sql = new StringBuffer();
            //      				  1					2  					3			
            sql.append("SELECT causa_emergenza_2, imm_imp_mese_prec_2, imm_imp_due_mesi_prec_2");
            sql.append(" FROM aup_dati");
            sql.append(" WHERE id_domanda = ");
            sql.append(IDdomanda);
            doQuery(sql.toString());
            String causaEmergenza = "";
            if(records!=null && records.getRows()>0) {
            	for (int i = 1; i <= records.getRows(); i++) {
                    causaEmergenza = records.getString(i, 1);   
                    if("1".equals(causaEmergenza)) {
	                	double importoMesePrec = records.getDouble(i, 2);
	                	double importoDueMesiPrec = records.getDouble(i, 3); 
	                	//(mese precedente + due mesi precedenti) / 2 (media) moltiplicato per 12 mesi, quindi moltiplico direttamente per 6
	                	patrimonioImmobiliareAttualizzato = (importoMesePrec + importoDueMesiPrec) *6;	                    	
                    }
            	}
            }            	
            
        	sql = new StringBuffer();
            //      			   1							
            sql.append("SELECT indennita_2 ");
            sql.append(" FROM aup_dati");
            sql.append(" WHERE id_domanda = ");
            sql.append(IDdomanda);
            doQuery(sql.toString());

            if(records!=null && records.getRows()>0) {
            	for (int i = 1; i <= records.getRows(); i++) { 
                    if("1".equals(causaEmergenza)) {
	                	double indennita = records.getDouble(i, 1);
	                	//(mese precedente + due mesi precedenti) / 2 (media) moltiplicato per 12 mesi, quindi moltiplico direttamente per 6
	                	patrimonioImmobiliareAttualizzato = patrimonioImmobiliareAttualizzato + indennita;	                    	
                    }
            	}
            }   
            
            double patrimonioFinanziarioAttualizzato = 0d;
			if("1".equals(causaEmergenza)) {
				sql = new StringBuffer();
				sql.append("select sum(consistenza) from aup_patrimonio_finanziario where semestre = 2 and id_domanda = ");
				sql.append(IDdomanda);
				try {
					Table t2 = dataTransfer.executeQuery(sql.toString());
					patrimonioFinanziarioAttualizzato = (double)t2.getDouble(1, 1);
					patrimonioFinanziarioAttualizzato = patrimonioFinanziarioAttualizzato - 3000;
					if(patrimonioFinanziarioAttualizzato < 0d) {
						patrimonioFinanziarioAttualizzato = 0d;
					}
				} catch (DBException e) {
					patrimonioFinanziarioAttualizzato = 0d;
					e.printStackTrace();
				}	
				
				patrimonioImmobiliareAttualizzato = patrimonioImmobiliareAttualizzato + patrimonioFinanziarioAttualizzato;	
				
			}            
            
			//modalità normale con domanda
            sql = new StringBuffer();
			//                                      1                              2
			sql.append("SELECT R_Relazioni_parentela.peso_reddito, Redditi_altri.importo ");
			sql.append("FROM Familiari ");
			sql.append("INNER JOIN Redditi_altri ON Familiari.ID_dichiarazione = Redditi_altri.ID_dichiarazione ");
			sql.append("INNER JOIN Domande ON Familiari.ID_domanda = Domande.ID_domanda ");
			sql.append("INNER JOIN R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela ");
			sql.append("WHERE Domande.ID_domanda = ");
			sql.append(IDdomanda);
			sql.append(" and ID_tp_erogazione not in('051' ,'058' ,'060', '061', '062', '103','115', '501', '505')");
			

			
			//Inizio aggiunta eventuale definizione di componenti (servizio ANF, prestito sull'onore, ...)
			String defDichType = DefDichElabConst.C5;
			String componenti = this.getDefinizioneComponentiDichiarazione(defDichType); //classe metodo DefComponentiDich
			if(componenti != null && componenti.length()>0)
			{
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componenti);

				testPrintln(sql.toString());
			}
			String componentiPV =  PassaValoriIcef.getID_dichiarazioni(IDdomanda); //classe metodo DefComponentiDich
			if(componentiPV != null && componentiPV.length()>0){
				sql.append(" AND Familiari.ID_dichiarazione in ");
				sql.append(componentiPV);
			}
			//Fine aggiunta eventuale definizione di componenti
		}
		else
		{
			//modalità calcolo dichiarazione ICEF in tabelle STAT_C_...

			String id_dichiarazione = IDdomanda.substring(1);

			//              	1           	2
			sql.append("SELECT     100 AS peso_reddito, importo ");
			sql.append("FROM         Redditi_altri ");
			sql.append("WHERE     (ID_dichiarazione = ");
			sql.append(id_dichiarazione);
			sql.append(")");
		}

		doQuery(sql.toString());

	}

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0 + patrimonioImmobiliareAttualizzato;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				tot = tot + Sys.round(records.getDouble(i, 2) - aggiusta, round) * records.getDouble(i, 1) / 100.0;
			}
			tot = tot + patrimonioImmobiliareAttualizzato;
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}
