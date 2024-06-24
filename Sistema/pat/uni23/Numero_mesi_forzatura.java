package c_elab.pat.uni23;

/** legge il numero di mesi da forzare
 *
 */
public class Numero_mesi_forzatura extends QStudente {
    
	//se non ho fatto una scelta cioè mi arriva da UNI_dati = -1 forzo a 99
	
	double valoreNoForzatura = -1.0; //valore == -1 corrisponde a nessuna forzatura
	
    public double getValue() {
        try {
        	String verificaPresenzaValore = (String)(uni_dati.getElement(8,2));
        	if(verificaPresenzaValore == null){
        		return 99;
        	}        	
        	double valoreNumeroMesiForzatura = new Double((String)(uni_dati.getElement(8,2))).doubleValue();
            if(valoreNumeroMesiForzatura == valoreNoForzatura){
            	return 99;
            }else{
            	return valoreNumeroMesiForzatura;
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
