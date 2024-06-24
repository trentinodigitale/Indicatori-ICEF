package c_elab.pat.icef13;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author l_leonardi
 */
public class Assegno_imposto extends QDatiFamiglia {


	//CAMBIAMI - VERIFICAMI: vanno verificate ogni anno 4800 come da delibera
	/**
	 */
	
	String idTpObbligoMantenimentoAdempieInViaExtragiudiziale  = "3"; //da tabella tp_obbligo_mantenimento
	String idTpObbligoMantenimentoNonAdempie  = "4"; //da tabella tp_obbligo_mantenimento
	double importoMinimoObbligoMantenimento  = 4800; //4800 euro

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{

		double ret = 0.0;

		try 
		{
			if(records==null)
			{
				return ret;
			}
			if(records.getRows()<1)
			{
				return ret;
			}


			String ID_tp_obbligo_mantenimento = records.getString(1, 1);

			Date dataPresentazione=records.getCalendar(1, 3).getTime();
			SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
			Date datelimit1 = sdf.parse("01-07-2013");

			if(dataPresentazione.compareTo(datelimit1)<0){
				if(ID_tp_obbligo_mantenimento!=null && ID_tp_obbligo_mantenimento.equals("2"))
				{
					if(records.getString(1, 2)!=null)
					{
						double assegnoDichiarato = records.getDouble(1, 2);
						ret = Math.max(assegnoDichiarato, 3600);
					}
				}
					
			}else{

				// se adempimento in via extragiudizionale si prende il max tra importo dichiarato e importo minimo assegno mantenimento
				if(ID_tp_obbligo_mantenimento!=null && ID_tp_obbligo_mantenimento.equals(idTpObbligoMantenimentoAdempieInViaExtragiudiziale))
				{
					if(records.getString(1, 2)!=null)
					{
						double assegnoDichiarato = records.getDouble(1, 2);
						ret = Math.max(assegnoDichiarato, importoMinimoObbligoMantenimento);
					}
				}
				// se nessun adempimento si prende importo minimo assegno mantenimento
				if(ID_tp_obbligo_mantenimento!=null && ID_tp_obbligo_mantenimento.equals(idTpObbligoMantenimentoNonAdempie))
				{
					ret = importoMinimoObbligoMantenimento;
				}
			}

		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		return ret;
	}
}