package c_elab.pat.barch15;

import it.clesius.db.sql.RunawayData;
import it.clesius.db.sql.servlet.RDServlet;

import java.util.Hashtable;

/**
 * legge i valori del quadro E 
 * 
 * @author s_largher
 */
public class Mq_particelle extends QTerreniBar {
   
	// per test
    public static void main(String[] a){
    	Hashtable h = new Hashtable();
    	h.put("servletName", "http://192.168.30.222:8080/clesius/icef/servlet/data");
    	h.put("applAut","clesio");
    	h.put("serialization","true");
    	try{
    		RunawayData    hrun= new RDServlet(); 
    		hrun.init(h);
    		Mq_particelle qd=new Mq_particelle();
    		qd.setVariables("5483887", 2012, 6005, 6005, ""+107, true);
    		qd.init(hrun);
    		//System.out.println("CCCCCCCCCCCC");
    		qd.getValue();
    	} catch (Exception e){}
    }
	
	
	/**
	 * ritorna il valore double da assegnare all'input node
	 * 
	 * @return double
	 */
	public double getValue() {

		double MQ_terreno_oggetto_intervento = 0.0;
		
		for (int i = 1; i <= records.getRows(); i++) {
			try {
				// se immobile oggetto dell'intervento 				
				if (records.getElement(i, 14) != null) {
					MQ_terreno_oggetto_intervento += records.getDouble(i, 15);
				}
			} catch (NullPointerException n) {
				System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
				return 0.0;
			} catch (NumberFormatException nfe) {
				System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
				return 0.0;
			}
		}		
		return MQ_terreno_oggetto_intervento;
	}
}