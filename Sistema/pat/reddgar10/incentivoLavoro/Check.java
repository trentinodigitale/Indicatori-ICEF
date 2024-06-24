package c_elab.pat.reddgar10.incentivoLavoro; 

import java.util.GregorianCalendar;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.DateTimeFormat;
import it.clesius.db.util.Table;
import it.clesius.util.General1;

/** 
 *@author a_pichler 
 */

public class Check extends ElainNode { 
protected int id_servizio_automatismo=30000;
protected int id_servizio_sociale=30001;
	  
protected Table datiDomandaCollegata;
protected Table datiDomandaCollegataPresente;

/**
	 * 
	 *  La domanda è idonea:
	 *  1 se la domanda collegata del reddito di garanzia è in erogazione nel periodo di inizio lavoro, trasmessa ed ha un importo mensile positivo
	 *  2 se nessuno lavora nella domanda di reddito di garanzia connessa
	 *
	 * ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		boolean nessunoLavora=false;
		boolean domanda_collegata_presente=false;
		try
		{
			String id_domanda_collegata= (String)datiDomandaCollegata.getElement(1, 1);
			
			c_elab.pat.reddgar10.Check check = new c_elab.pat.reddgar10.Check();
			check.setVariables ( id_domanda_collegata , "0" , 0 , 0 , id_servizio_automatismo , "0" , false );
			check.init ( dataTransfer );
			nessunoLavora =check.domandaInCuiNessunoLavora();
			check.reset();
			
			if(datiDomandaCollegataPresente==null || datiDomandaCollegataPresente.getRows()==0){
				
				return 1.0;
			}
			
			if(!nessunoLavora){
				return 2.0;
				
			}else{
				return 0.0;
			}
		
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ".Check: "	+ e.toString());
			return 0.0;
		}
		
	}
	
	
	public void init(RunawayData dataTransfer) {
	   	super.init(dataTransfer);
       	try {
        GregorianCalendar data_inizio_lavoro;
        
		StringBuffer sql = new StringBuffer();
		
		//              	  		   1        			2
		sql.append("SELECT ID_domanda_collegata, GR_Incentivo_lavoro.data_inizio_lavoro"); 
		sql.append(" FROM GR_Incentivo_lavoro ");
		sql.append(" WHERE (ID_domanda =");
		sql.append(IDdomanda);
		sql.append(")");
		
		doQuery(sql.toString());
		datiDomandaCollegata = records;
		String id_domanda_collegata="";
		if(datiDomandaCollegata!=null && datiDomandaCollegata.getRows()>0){
			
		id_domanda_collegata= (String)datiDomandaCollegata.getElement(1, 1);
		data_inizio_lavoro = General1.getStringToCalendar(DateTimeFormat.toItDate((String)datiDomandaCollegata.getElement(1, 2)));
						
		
		sql = new StringBuffer();
		
		sql.append("SELECT     Domande.ID_domanda ");
		sql.append(" FROM         Domande INNER JOIN");
		sql.append(" C_ElaIN AS mese ON Domande.ID_domanda = mese.ID_domanda INNER JOIN");
		sql.append(" C_ElaIN AS mesi ON Domande.ID_domanda = mesi.ID_domanda INNER JOIN");
		sql.append(" C_ElaIN AS anno ON Domande.ID_domanda = anno.ID_domanda INNER JOIN");
		sql.append(" C_ElaOUT ON Domande.ID_domanda = C_ElaOUT.ID_domanda INNER JOIN");
		sql.append(" Doc ON Domande.ID_domanda = Doc.ID INNER JOIN");
		sql.append(" C_ElaIN AS mesi_revoca ON Domande.ID_domanda = mesi_revoca.ID_domanda");
		sql.append(" WHERE     (C_ElaOUT.node = N'mensile') AND (C_ElaOUT.numeric_value > 0) AND (Doc.ID_tp_stato > 3000) ");
		sql.append("  AND (Domande.ID_servizio = "+id_servizio_automatismo+" OR Domande.ID_servizio = "+id_servizio_sociale+")  ");
	    sql.append(" AND (Domande.ID_ente_erogatore > 0) AND (mese.node = N'Mese') AND (mesi.node = N'Mesi') AND ");
		sql.append(" (anno.node = N'anno') AND (mesi_revoca.node = N'mesi_revoca') AND "+it.clesius.db.util.DateTimeFormat.toSqlDate(data_inizio_lavoro.getTime())+">= dbo.Date(CONVERT(int, ");
		sql.append(" anno.input_value), CONVERT(int, mese.input_value), 1) AND "+it.clesius.db.util.DateTimeFormat.toSqlDate(data_inizio_lavoro.getTime())+" < DATEADD(mm, ");
		sql.append(" mesi.input_value - mesi_revoca.input_value, dbo.Date(CONVERT(int, anno.input_value), CONVERT(int, mese.input_value), 1))");
		sql.append(" AND Domande.ID_domanda=");
		sql.append(id_domanda_collegata);
		doQuery(sql.toString());
		datiDomandaCollegataPresente = records;
		
		
		}
		
       	} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	public void reset() {
		datiDomandaCollegata = null;
		datiDomandaCollegataPresente=null;
	}
}