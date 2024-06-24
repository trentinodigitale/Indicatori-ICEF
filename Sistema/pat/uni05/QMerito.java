/**
 *Created on 18-giu-2004
 */
package c_elab.pat.uni05;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge i dati del merito del corso di laurea dello studente
 *  modificato 25/8/05 per tener conto della sospensione
 * @author g_barbieri
 */
public abstract class QMerito extends ElainNode {
    
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QMerito.class.getName(), IDdomanda, "records" );

		if (!session.isInitialized()) {
		 	
        	int anno_rif_merito = 1900; 
            
			super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            
            //                         1                            2
            sql.append(
            "SELECT UNI_Studenti.anno_prima_imm, UNI_Studenti.beneficiato_sospensione ");
            sql.append("FROM UNI_Studenti ");
            sql.append("WHERE UNI_Studenti.ID_domanda = ");
            sql.append(IDdomanda);
            
            //System.out.println(sql.toString());
            doQuery(sql.toString());
            
            try {
                anno_rif_merito = new Integer((String)(records.getElement(1,1))).intValue() + java.lang.Math.abs(new Integer((String)(records.getElement(1,2))).intValue());
            } catch(NullPointerException n) {
                System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            } catch (NumberFormatException nfe) {
                System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            }
            
            sql = null;
            sql = new StringBuffer();
            
            //                          1                       2                    3                        4
            sql.append(
            "SELECT UNI_R_Merito.minimo_deroga, UNI_R_Merito.minimo, UNI_R_Merito.massimo, UNI_R_Merito.minimo_premio ");
            sql.append("FROM UNI_R_Merito ");
            sql.append(
            //"INNER JOIN UNI_Studenti ON (UNI_R_Merito.ID_anno_accademico = UNI_Studenti.ID_anno_accademico AND UNI_R_Merito.ID_corso_laurea = UNI_Studenti.ID_corso_laurea AND UNI_R_Merito.ID_facolta = UNI_Studenti.ID_facolta AND UNI_R_Merito.ID_universita = UNI_Studenti.ID_universita AND UNI_R_Merito.anno_prima_imm = UNI_Studenti.anno_prima_imm) ");
            "INNER JOIN UNI_Studenti ON (UNI_R_Merito.ID_anno_accademico = UNI_Studenti.ID_anno_accademico AND UNI_R_Merito.ID_corso_laurea = UNI_Studenti.ID_corso_laurea AND UNI_R_Merito.ID_facolta = UNI_Studenti.ID_facolta AND UNI_R_Merito.ID_universita = UNI_Studenti.ID_universita) ");
            //sql.append("WHERE UNI_Studenti.ID_domanda = ");
            sql.append("WHERE UNI_R_Merito.anno_prima_imm = " + anno_rif_merito + " AND UNI_Studenti.ID_domanda = ");
            sql.append(IDdomanda);
            
            //System.out.println(sql.toString());            
            doQuery(sql.toString());
            
            session.setInitialized(true);
			session.setRecords( records );
		} else {
			records = session.getRecords();
		}
    }
    
    /** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QMerito.class.getName(), IDdomanda, "records" );
    }
    
    /** QMerito constructor */
    public QMerito(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}

