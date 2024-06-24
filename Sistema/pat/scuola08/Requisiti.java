package c_elab.pat.scuola08;

/**
 * 
 * @author a_mazzon
 *
 * La classe Requisiti verifica se il requisito della residenza in provincia di Trento del richiedente è rispettata.
 */
public class Requisiti extends QStudente {
    
    /**
     * Costruttore
     */
	public Requisiti(){
	}
	
	/**
	 * Controlla che la provincia di residenza del richiedente sia TN. Se è vero ritorna 1 altrimenti 0.
	 * 
	 * @return double
	 */
    public double getValue() {
        try {
            if (((String)(records.getElement(1,1))).equals("TN")) {
                return 1.0;
            } else {
                return 0.0;
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }

}
