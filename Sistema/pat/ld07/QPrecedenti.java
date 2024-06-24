/**
 *Created on 23-mag-2006 
 */
package c_elab.pat.ld07;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

/** legge i dati dei contributi assegnati nei trimestri precedenti
 *
 * @author g_barbieri
 */
public abstract class QPrecedenti extends ElainNode {
    
    protected double          t_consumato = 0;
    protected double          settimane = 0;
    
    /** inizializza la Table records con i valori letti dalla query sul DB
     * @see it.clesius.apps2core.ElainNode#init(it.clesius.db.sql.RunawayData)
     * @param dataTransfer it.clesius.db.sql.RunawayData
     */
    public void init(RunawayData dataTransfer) {
    	ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QPrecedenti.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
        	int id_soggetto;
        	int id_riferimento;
            super.init(dataTransfer);
            
            StringBuffer sql = new StringBuffer();
            //                  1                            2
            sql.append(
            "SELECT Domande.ID_soggetto, PNS_versamenti.ID_riferimento ");
            sql.append("FROM Domande INNER JOIN PNS_versamenti ON Domande.ID_domanda = PNS_versamenti.ID_domanda ");
            sql.append("WHERE Domande.ID_domanda = ");
            sql.append(IDdomanda);
            doQuery(sql.toString());

            id_soggetto = getInteger(1,1); 
            id_riferimento = getInteger(1,2); 
            
            sql = null;
            sql = new StringBuffer();
            //                 1                             2                                       3                                     4
            sql.append(
            "SELECT Domande.ID_domanda, PNS_versamenti.ID_riferimento, C_ElaOUT.numeric_value AS settimane, C_ElaOUT_1.numeric_value AS t_consumato ");
            sql.append("FROM (((Domande INNER JOIN Doc ON Domande.ID_domanda = Doc.ID) INNER JOIN PNS_versamenti ON Domande.ID_domanda = PNS_versamenti.ID_domanda) INNER JOIN C_ElaOUT ON Domande.ID_domanda = C_ElaOUT.ID_domanda) INNER JOIN C_ElaOUT AS C_ElaOUT_1 ON Domande.ID_domanda = C_ElaOUT_1.ID_domanda ");
            sql.append("GROUP BY Domande.ID_domanda, PNS_versamenti.ID_riferimento, C_ElaOUT.numeric_value, C_ElaOUT_1.numeric_value, C_ElaOUT.node, C_ElaOUT_1.node, Doc.ID_tp_stato, Domande.ID_servizio, Domande.ID_soggetto ");
            sql.append("HAVING (((C_ElaOUT.node)='settimane') AND ((C_ElaOUT_1.node)='t_consumato') AND ((Doc.ID_tp_stato)>=3000) AND ((Domande.ID_servizio)=");
            sql.append(servizio);
            sql.append(") AND ((Domande.ID_domanda) <> ");
            sql.append(IDdomanda);
            sql.append(") AND ((Domande.ID_soggetto) = ");
            sql.append(id_soggetto);
            sql.append(")) ORDER BY PNS_versamenti.ID_riferimento DESC");
            doQuery(sql.toString()); 
            
            int i = 1;
			while (true) {
				if (id_riferimento < getInteger(i,2)) {
					//errore!!
					settimane = 0;
					t_consumato = 0;
					break;
				}
				if (id_riferimento > getInteger(i,2)) {
					settimane = getDouble(i,3);
					t_consumato = getDouble(i,4);
					break;
				}
				i++;
			}
            
			session.setInitialized(true);
			session.setRecords( records );
			session.setAttribute("t_consumato", new Double(t_consumato));
			session.setAttribute("settimane", new Double(settimane));
			
        } else {
			records = session.getRecords();
			t_consumato = ((Double)session.getAttribute("t_consumato")).doubleValue();
			settimane = ((Double)session.getAttribute("settimane")).doubleValue();
        }
    }
    
    /** resetta le variabili statiche
     * @see it.clesius.apps2core.ElainNode#reset()
     */
    protected void reset() {
    	ElabStaticContext.getInstance().resetSession( QPrecedenti.class.getName(), IDdomanda, "records" );
        settimane = 0;
        t_consumato = 0;
    }
    
    /** QPrecedenti constructor */
    public QPrecedenti(){
    }
    
    /** ritorna il valore double da assegnare all'input node
     * @return double
     */
    public double getValue() {
        return 0.0;
    }
}
