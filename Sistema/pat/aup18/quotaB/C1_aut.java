package c_elab.pat.aup18.quotaB;

import java.util.Hashtable;

import it.clesius.clesius.util.Sys;

/** legge i valori del quadro C1 dove tipo reddito vale
 *  - ANP (assimilati) 
 *  - DIV (diversi)
 * 
 * @author s_largher
 */
public class C1_aut extends c_elab.pat.icef18.C1_aut {

	public static final String ANP = "ANP";
	public static final String DIV = "DIV";

	private Hashtable<String,Double[]> table_spese;
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {

		if (records == null)
			return 0.0;

		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		double totInvalidiGravi = 0.0;
		table_spese = new Hashtable<String,Double[]>();

		try {
			for (int i = 1; i <= records.getRows(); i++) {
				if ( ((String) records.getElement(i, 1)).equals(ANP) 
					|| ((String) records.getElement(i, 1)).equals(DIV) ) {
					
					double peso_par = records.getDouble(i, 2);
					double importo = records.getDouble(i, 3);
					String id_dich = records.getString(i,4);
					int id_tp_invalidita = records.getInteger(i, 5);// Familiari.id_tp_invalidita 
					
					if ( table_spese.containsKey(id_dich) ) {
						double imp = ((Double[])table_spese.get( id_dich ))[0].doubleValue();
						table_spese.remove(id_dich);
						table_spese.put( id_dich, new Double[]{(Double)(imp+importo), (Double)peso_par} );
					}else {
						table_spese.put( id_dich, new Double[]{ new Double(importo), new Double(peso_par) } );
					}
					
					//franchigia di 25000 euro per i disabili andrebbe fatta sulla somma dei diversi redditi ma non dovrebbe comunque presentarsi il caso (da GB)
					//			1	Invalidi non deambulanti o con bisogno di assistenza continua e ciechi assoluti	2	-2	150	0	2
					//			2	Sordi e ciechi con residuo visivo	1,25	-2	150	0	1,25
					//			3	Invalidi civili al 100% ed equiparati	0,5	18	65	0	0,5
					if(id_tp_invalidita==1 || id_tp_invalidita==2 || id_tp_invalidita==3){
						importo=Math.max(0, importo-25000);
					}
					
					//importo = importo * peso_par / 100.0;
					tot = tot +  Sys.round( importo - aggiusta, round ) * peso_par / 100.0;
				}
			}
			
			
			
			
			return tot;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}

	public Hashtable<String,Double[]> getTable_spese() {
		return table_spese;
	}

	public void setTable_spese(Hashtable<String,Double[]> table_spese) {
		this.table_spese = table_spese;
	}


}