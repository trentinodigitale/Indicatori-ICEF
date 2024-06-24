package c_elab.pat.uni17;

import c_elab.clesius.ElabStaticContext;
import c_elab.clesius.ElabStaticSession;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

public class Borsa_fuori extends c_elab.pat.uni17.QStudente{
        
	// i valori del sup si trovano nella tabella UNI_R_params
	// in base al servizio e alla tipologia di uni_domanda.id_politica_opera prendo id_tp_regola_opera in UNI_tp_politica_opera e il valore del nodo in UNI_R_params
	
	
	protected Table uni_r_parmas_valore;
	String			node = "Borsa_fuori";		
	
	public void init(RunawayData dataTransfer) {

		ElabStaticSession session =  ElabStaticContext.getInstance().getSession( QStudente.class.getName(), IDdomanda, "records" );
		if (!session.isInitialized()) {
			super.init(dataTransfer);
		    
			int idPoliticaOpera = uni_domanda.getInteger(1, uni_domanda.getIndexOfColumnName("ID_politica_opera"));
        	int idServizio 	= uni_domanda.getInteger(1, uni_domanda.getIndexOfColumnName("ID_servizio"));
			
        	//QUERY IN UNI_TP_POLITICA_OPERA -> ID_TP_REGOLA_OPERA --> PER ARRIVARE AL VALORE DEL NODE IN UNI_R_PARAMS
            StringBuffer sql = new StringBuffer();
            sql.append("select UNI_R_params.node, UNI_R_params.valore ");
            sql.append("from UNI_tp_politica_opera  ");
            sql.append("inner join UNI_R_params on UNI_tp_politica_opera.id_servizio = UNI_R_params.id_servizio   ");
            sql.append(" and UNI_tp_politica_opera.ID_tp_regola_opera = UNI_R_params.id_tp_regola_opera ");
            sql.append(" where UNI_tp_politica_opera.ID_servizio = ");
            sql.append(idServizio);
            sql.append(" and UNI_tp_politica_opera.ID_tp_politica_opera = ");
            sql.append(idPoliticaOpera);
            sql.append("  and UNI_R_params.node = '");
            sql.append(node);
            sql.append("';");

            doQuery(sql.toString());
            uni_r_parmas_valore = records;
            
            session.setAttribute("uni_r_parmas_valore", uni_r_parmas_valore);
		}
	}
	
    public double getValue() {
        try {
        	
        	double valore_borsa_fuori_min = 0;
        	
        	if (uni_r_parmas_valore == null){
        		valore_borsa_fuori_min = 0.0;
        	}else{
        		valore_borsa_fuori_min = new Double((String)(uni_r_parmas_valore.getElement(1, 2))).doubleValue();
        	}
        	
        	return valore_borsa_fuori_min;
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
		
    }
    
    public Borsa_fuori(){		
    	
    }
    
    
    protected void reset() {
    	
	}
}
