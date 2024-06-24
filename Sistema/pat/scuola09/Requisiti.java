package c_elab.pat.scuola09;

import java.util.Calendar;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 
 * @author a_mazzon
 *
 * La classe Requisiti verifica se il requisito dell'età inferiore ai 21 anni al 05/06/2010 dello studente è rispettata.
 */
public class Requisiti extends QStudente {
    
	private static Log log = LogFactory.getLog( Requisiti.class );
	private Calendar datarif  = Calendar.getInstance();

    /**
     * Costruttore
     */
	public Requisiti(){
		//CAMBIAMI: va cambiata ogni anno
		datarif.set(2010, 5, 5, 23, 59); // 5 giugno 2010
	}
	
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
    public double getValue() {
    	
		Calendar datanascita = Calendar.getInstance();
    	int tot = 0;
        try {
        	try {
        		datanascita = datiScuolaStudente.getCalendar(1, 1);
        	} catch (Exception e) {
	            log.error("Errore di lettura della data di nascita per l'elemento studente della domanda " + IDdomanda, e);
	            datanascita.set(1900, 0, 1, 0, 0);
	        }

        	// studente che ha compiuto i 21 al 05/06/2010
	        Calendar data21anni = (Calendar)datarif.clone();
	        data21anni.add(Calendar.YEAR, -21);
	        if (datanascita.after(data21anni)) {
	        	tot++;
	        } 

            return tot;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
    

}
