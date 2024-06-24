package c_elab.pat.icef20;

import it.clesius.apps2core.ElainNode;
import it.clesius.apps2core.ricalcolo.client.memory.MemoryCache;
import it.clesius.db.sql.DBException;
import it.clesius.db.util.Table;
import it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefinizioneComponentiDichAbstract;
import it.clesius.web.dashboard.viewmodel.LiteMainDashBoardVM;

public abstract class DefComponentiDich extends ElainNode 
{
	
	/**
	 * testPrintln(String text) 
	 */
	public void testPrintln(String text) 
	{
		//System.out.println(text);
	}
	
	/**
	 * @param @param defDichType preso dalla classe it.clesius.evolextension.icef.domanda.definiz_componenti_dich.DefDichElabConst
	 * @return stringa di ID_dichiarazioni per la sql.<BR> 
	 * Esempio ritorno: " (12345677, 12345678, 12345679) "<BR>
	 * Esempio definizione clausola WHERE" AND Familiari.ID_dichiarazione in " + getDefinizioneComponentiDichiarazione(...); 
	 */
	public String getDefinizioneComponentiDichiarazione(String defDichType)
	{
		String classe_definizione_componenti_dich = null;
		String ret = null;
		
		Table t = null;
		StringBuffer sql = new StringBuffer();

		//
		sql = new StringBuffer();
		//						        		1
		sql.append("SELECT R_Servizi.classe_definizione_componenti_dich_ICEF,id_domanda ");
		sql.append("FROM Domande INNER JOIN R_Servizi ON Domande.ID_servizio = R_Servizi.ID_servizio ");
		sql.append("INNER JOIN Doc ON Domande.ID_domanda = Doc.ID ");
		sql.append("WHERE (Domande.ID_domanda = ");
		sql.append(IDdomanda);
		sql.append(")");
		
		
		try {

			Table tb=MemoryCache.getInstance().getMemoryCacheTable(this.getClass(),IDdomanda,IDdomanda);
	        if(tb==null){
	        	t = dataTransfer.executeQuery(sql.toString());
	        	MemoryCache.getInstance().addMemoryCache(this.getClass(),IDdomanda,t,"id_domanda");
	        	tb=MemoryCache.getInstance().getMemoryCacheTable(this.getClass(),IDdomanda,IDdomanda);
	        }
	        t=tb;
	    	classe_definizione_componenti_dich = (String)t.getElement(1, 1);
			testPrintln("classe_definizione_componenti_dich = "+classe_definizione_componenti_dich + " per domanda: "+IDdomanda + ", defDichType = "+defDichType);
	
		} catch (DBException dbe) {
			System.out.println("Errore in DefComponentiDich.getDefComponentiDich DBException per domanda: "+IDdomanda + ", defDichType = "+defDichType + ":");
			dbe.printStackTrace();
		}
		
//		try {
//			t = dataTransfer.executeQuery(sql.toString());
//			classe_definizione_componenti_dich = (String)t.getElement(1, 1);
//			testPrintln("classe_definizione_componenti_dich = "+classe_definizione_componenti_dich + " per domanda: "+IDdomanda + ", defDichType = "+defDichType);
//						
//		} catch (DBException dbe) {
//			System.out.println("Errore in DefComponentiDich.getDefComponentiDich DBException per domanda: "+IDdomanda + ", defDichType = "+defDichType + ":");
//			dbe.printStackTrace();
//		}
		
		if(classe_definizione_componenti_dich!= null && classe_definizione_componenti_dich.length()>0)
		{
			ret = doDefinizioneComponentiDichiarazione(defDichType,classe_definizione_componenti_dich,IDdomanda,""+servizio);			
			testPrintln("Definizione = "+ret + " per domanda: "+IDdomanda + ", defDichType = "+defDichType);
			
		}
		else
		{
			testPrintln("classe_definizione_componenti_dich non definita per domanda: "+IDdomanda + ", defDichType = "+defDichType);
		}
		
		return ret;
	}
	
	/**
	 * doDefinizioneComponentiDichiarazione
	 * che chiama le classi che si istanziano dal database    
	 */
	private String doDefinizioneComponentiDichiarazione(String defDichType, String classe_controllo_duplicati, String id_domanda, String ID_servizio) 
	{
		String ret = null;
		try
		{
			Class dcClass=null;
			try {
				dcClass = LiteMainDashBoardVM.getSingletonInstance().getClass(classe_controllo_duplicati);
			}catch(Exception e) {
				
			}
			if(dcClass==null) {
				dcClass = Class.forName(classe_controllo_duplicati);
			}
			DefinizioneComponentiDichAbstract definizioneComponenti = (DefinizioneComponentiDichAbstract) dcClass.newInstance();
			String[] tmpArray = definizioneComponenti.doDefinizioneComponentiDich(defDichType ,dataTransfer, id_domanda, ID_servizio);
			ret = convertiArrayToStringForSql(tmpArray);
		}
		catch (Exception e) 
		{
			String textError = "Errore creazione classe DefComponentiDich.doDefinizioneComponentiDichiarazione per domanda: "+id_domanda;
			System.out.println(textError);
			e.printStackTrace();
			ret = null;
		}
		return ret;
	}
	
	/**
	 * @param arrayString array di ID_dichiarazione
	 * @return conversione array in stringa. Ritorna null se l'array è null, la stringa " (-9999) " se l'array è vuoto
	 */
	private String convertiArrayToStringForSql(String[] arrayString)
	{
		String ret = null;
		
		try {
			if(arrayString == null)
			{
				return ret;
			}
			
			ret = " (";
			if(arrayString.length==0)
			{
				ret += "-9999";
			}
			else
			{			
				for (int i = 0; i < arrayString.length; i++) 
				{
					ret += arrayString[i];
					
					if( (i+1) == (arrayString.length))
					{
						//è l'ultimo
					}
					else
					{
						//non è l'ultimo
						ret += ", ";
					}
				}
			}
			ret += ") ";
			
		} catch (Exception e) {
			System.out.println("ERROR in DefComponentiDich.convertiArrayToStringForSql: "
					+ e);
			ret = null;
		}
		
		return ret;
	}
	
}
