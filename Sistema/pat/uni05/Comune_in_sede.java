/**
 *Created on 18-giu-2004
 */

package c_elab.pat.uni05;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge se il comune di residenza dello studente è tra quelli in sede
 *
 * @author g_barbieri
 */
public class Comune_in_sede extends ElainNode {
    
	Table tmpTable;
	
    /** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
    }
    
    /** 
     * 
     */
    private double checkForzaInSede() {
        try {
        	// se forza borsa in sede è vero allora ritorna che il comune fuori sede
        	// per questo caso è da considerare in sede
            if ( java.lang.Math.abs(new Integer((String)(tmpTable.getElement(1,2))).intValue()) != 0 ) {
                return 1.0;
            } else {
                return 0.0;
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }

    /** 
     * 
     */
    private double checkForzaFuoriSede() {
        try {
        	// se forza borsa fuori sede è vero allora ritorna che il comune in sede
        	// per questo caso è da considerare fuori sede
            if ( java.lang.Math.abs(new Integer((String)(tmpTable.getElement(1,1))).intValue()) != 0 ) {
                return 0.0;
            } else {
                return 1.0;
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 1.0;
        }
    }

    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        
        // se non trova records il comune è fuori sede
    	if (records == null)  {
            //return 0.0;
    		return checkForzaInSede();
    	}
        
        try {
        	// se trova un record il comune è in sede
            if (records.getRows() > 0) {
                //return 1.0;
            	return checkForzaFuoriSede();
            // se non trova records il comune è fuori sede
            } else {
                //return 0.0;
        		return checkForzaInSede();
            }
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        }
    }
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
        
        super.init(dataTransfer);
        StringBuffer sql = new StringBuffer();
        
        //                            1                                   2
        sql.append(
        "SELECT UNI_Studenti.forza_borsa_fuori_sede, UNI_Studenti.forza_borsa_in_sede ");
        sql.append("FROM UNI_Studenti ");
        sql.append("WHERE UNI_Studenti.ID_domanda = ");
        sql.append(IDdomanda);
        
        doQuery(sql.toString());

        tmpTable = records;
        
        sql = null;
        sql = new StringBuffer();
        
        /**
         *  Controlla la residenza dello studente nella dichiarazione ICEF collegata alla domanda
         *
         */
          if(servizio==1010){         
              sql.append(" SELECT DISTINCT Dich_icef.ID_comune_residenza ");
              sql.append(" FROM Familiari INNER JOIN");
              sql.append(" Dich_icef ON Familiari.ID_dichiarazione = Dich_icef.ID_dichiarazione INNER JOIN");
              sql.append(" UNI_R_Comuni_in_sede ON Dich_icef.ID_comune_residenza = UNI_R_Comuni_in_sede.ID_comune INNER JOIN");
              sql.append(" UNI_Studenti ON Familiari.ID_domanda = UNI_Studenti.ID_domanda INNER JOIN");
              sql.append(" R_Relazioni_parentela ON Familiari.ID_relazione_parentela = R_Relazioni_parentela.ID_relazione_parentela INNER JOIN");
              sql.append(" UNI_R_Corsi_laurea ON UNI_Studenti.ID_corso_laurea = UNI_R_Corsi_laurea.ID_corso_laurea AND"); 
              sql.append(" UNI_Studenti.ID_anno_accademico = UNI_R_Corsi_laurea.ID_anno_accademico AND"); 
              sql.append(" UNI_Studenti.ID_universita = UNI_R_Corsi_laurea.ID_universita AND UNI_Studenti.ID_facolta = UNI_R_Corsi_laurea.ID_facolta AND"); 
              sql.append(" UNI_R_Comuni_in_sede.ID_anno_accademico = UNI_R_Corsi_laurea.ID_anno_accademico AND"); 
              sql.append(" UNI_R_Comuni_in_sede.ID_sede_corso = UNI_R_Corsi_laurea.ID_sede_corso AND");
              sql.append(" UNI_R_Comuni_in_sede.ID_anno_accademico = UNI_Studenti.ID_anno_accademico");
              sql.append(" WHERE (R_Relazioni_parentela.ruolo = 1) AND (Familiari.ID_domanda ='");
              sql.append(IDdomanda);
              sql.append("');");
          }//riconferme
          else{
              
            sql.append(
            "SELECT DISTINCT UNI_R_Comuni_in_sede.ID_comune ");
            sql.append("FROM Domande ");
            sql.append(
            "INNER JOIN UNI_Studenti ON Domande.ID_domanda = UNI_Studenti.ID_domanda ");
            sql.append(
            "INNER JOIN UNI_R_Corsi_laurea ON (UNI_R_Corsi_laurea.ID_anno_accademico = UNI_Studenti.ID_anno_accademico AND UNI_R_Corsi_laurea.ID_corso_laurea = UNI_Studenti.ID_corso_laurea AND UNI_R_Corsi_laurea.ID_facolta = UNI_Studenti.ID_facolta AND UNI_R_Corsi_laurea.ID_universita = UNI_Studenti.ID_universita) ");
            sql.append(
            "INNER JOIN UNI_R_Sedi_corsi ON UNI_R_Sedi_corsi.ID_sede_corso = UNI_R_Corsi_laurea.ID_sede_corso ");
            sql.append(
            "INNER JOIN UNI_R_Comuni_in_sede ON (UNI_R_Sedi_corsi.ID_sede_corso = UNI_R_Comuni_in_sede.ID_sede_corso AND UNI_R_Sedi_corsi.ID_anno_accademico = UNI_R_Comuni_in_sede.ID_anno_accademico) ");
            sql.append(
            "AND UNI_R_Comuni_in_sede.ID_comune = Domande.ID_comune_residenza AND UNI_Studenti.ID_anno_accademico = UNI_R_Comuni_in_sede.ID_anno_accademico ");
            sql.append("WHERE Domande.ID_domanda = ");
            sql.append(IDdomanda);

          }
        
        doQuery(sql.toString());
        //System.out.print(sql.toString());

    }
    
    /** Comune_in_sede constructor */
    public Comune_in_sede(){
    }
}
