package c_elab.pat.icef18;

import it.clesius.clesius.util.Sys;

import java.util.Enumeration;
import java.util.Hashtable;

/** 
 * 
 * @author s_largher
 */
public class Spese_redditi extends DefComponentiDich {
	
	private C1_aut ca1;
	private C1_dip cd1;
	private C2_agr c2;
	private C3_imp c3;
	private C4_part c4;
	

	
	
	public Spese_redditi() {}
	
	protected void reset() {
		if(ca1!=null) {
			ca1.reset();
		}
		if(cd1!=null) {
			cd1.reset();
		}
		if(c2!=null) {
			c2.reset();
		}
		if(c3!=null) {
			c3.reset();
		}
		if(c4!=null) {
			c4.reset();
		}
	}

	private void updateTableTot(Hashtable<String,Double[]> table_spese ) {

		if( table_spese!=null && table_spese.size()>0 ) {
			Enumeration<String> spese = table_spese.keys();
			while ( spese.hasMoreElements() ) {
				String id_dich = (String)spese.nextElement();
				if ( table_spese_tot.containsKey(id_dich) ) {
					double imp = ((Double[])table_spese_tot.get( id_dich ))[0].doubleValue();
					double peso_par = ((Double[])table_spese_tot.get( id_dich ))[1].doubleValue();
					double imp_next = ((Double[])table_spese.get( id_dich ))[0].doubleValue();
					table_spese_tot.remove(id_dich);
					table_spese_tot.put( id_dich, new Double[]{ new Double(imp+imp_next), new Double(peso_par)} );
				}else {
					table_spese_tot.put( id_dich, (Double[])table_spese.get(id_dich) );
				}
			}
		}
	}
	
	private Hashtable<String,Double[]> table_spese_tot;
	
	/** ritorna il valore double da assegnare all'input node
	 * @return double
	 */
	public double getValue() {
		
		double tot = 0.0;
		double round = 1.0;
		double aggiusta = 0.01;
		try {
			table_spese_tot = new Hashtable<String,Double[]>();
			Hashtable<String,Double[]> table_spese_cd1 = new Hashtable<String,Double[]>();
			
			ca1 = new C1_aut();
			ca1.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
			ca1.init(dataTransfer);
			ca1.getValue();
			updateTableTot( ca1.getTable_spese());
			
			cd1 = new C1_dip();
			cd1.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
			cd1.init(dataTransfer);
			cd1.getValue();
			table_spese_cd1 = cd1.getTable_spese();
			
			c2 = new C2_agr();
			c2.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
			c2.init(dataTransfer);
			c2.getValue();
			updateTableTot( c2.getTable_spese() );		
			
			c3 = new C3_imp();
			c3.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
			c3.init(dataTransfer);
			c3.getValue();
			updateTableTot( c3.getTable_spese() );		
		
			c4 = new C4_part();
			c4.setVariables(this.IDdomanda, this.IDdichiarazione, this.anno, this.periodo, this.servizio, this.IDente, this.euro);
			c4.init(dataTransfer);
			c4.getValue();
			updateTableTot( c4.getTable_spese() );	
			
			//scorrere hashtables
			//ricavo importo per singola dich 
			//sommo importi
			Enumeration<String> tot_spese = table_spese_tot.keys();
			Enumeration<String> tot_cd = table_spese_cd1.keys();
			
			while(tot_spese.hasMoreElements())  {
				String id_dich = (String)tot_spese.nextElement();
				double imp = ((Double[])table_spese_tot.get(id_dich))[0].doubleValue();
				double peso_par = ((Double[])table_spese_tot.get(id_dich))[1].doubleValue();
				double min = Math.min( imp * ( 1 - LocalVariables.C_IMP) , LocalVariables.DMA );
				min = min * peso_par / 100.0;
				tot += Sys.round( min - aggiusta, round );
			}

			while(tot_cd.hasMoreElements())  {
				String id_dich = (String)tot_cd.nextElement();
				double imp = ((Double[])table_spese_cd1.get(id_dich))[0].doubleValue();
				double peso_par = ((Double[])table_spese_cd1.get(id_dich))[1].doubleValue();
				double min = Math.min( imp * ( 1 - LocalVariables.C_DIP) , LocalVariables.DMD );
				min = min * peso_par / 100.0;
				tot += Sys.round( min - aggiusta, round );
			}
			
			return Sys.round( tot - aggiusta, round );
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		}
	}
}