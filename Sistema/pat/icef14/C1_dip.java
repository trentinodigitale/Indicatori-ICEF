package c_elab.pat.icef14;

import java.util.Hashtable;

import it.clesius.clesius.util.Sys;

/** legge i valori del quadro C1 dove tipo reddito vale 
 *  - DIP (dipendente), 
 * 
 * @author s_largher
 */
public class C1_dip extends QC1 {

	public static final String DIP = "DIP";
	
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
		
		table_spese = new Hashtable<String,Double[]>();
		
		try {
			for (int i = 1; i <= records.getRows(); i++) {
				if ( records.getString(i, 1).equals(DIP) ) {
					
					double peso_par = records.getDouble(i, 2);
					double importo = records.getDouble(i, 3);
					String id_dich = records.getString(i,4);
					
					if ( table_spese.containsKey(id_dich) ) {
						double imp = ((Double[])table_spese.get( id_dich ))[0].doubleValue();
						table_spese.remove(id_dich);
						table_spese.put( id_dich, new Double[]{(Double)(imp+importo), (Double)peso_par} );
					}else {
						table_spese.put( id_dich, new Double[]{ new Double(importo), new Double(peso_par) } );
					}
					
					//importo = importo * peso_par / 100.0;
					
					tot = tot +  Sys.round( importo - aggiusta, round ) * peso_par / 100.0;
				}
			}
			return tot;
		} catch (NullPointerException n) {
			n.printStackTrace();
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