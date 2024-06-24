package c_elab.pat.icef22;


/**
 * @author l_leonardi
 */
public class Assegno_imposto extends QDatiFamiglia {

	String idTpObbligoMantenimentoAdempieInViaExtragiudiziale  = "3"; //da tabella tp_obbligo_mantenimento
	String idTpObbligoMantenimentoNonAdempie  = "4"; //da tabella tp_obbligo_mantenimento

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

			double importo_percepito = 0;
			if( records.getElement(1,4)!=null ) {
				importo_percepito = records.getDouble(1, 4);
			}
			double importo_sentenza = 0;
			if( records.getElement(1,5)!=null ) {
				importo_sentenza = records.getDouble(1, 5);
			}
			
			String ID_tp_obbligo_mantenimento = records.getString(1, 1);

			// se adempimento in via extragiudizionale si prende il max tra importo dichiarato e importo minimo assegno mantenimento
			if(ID_tp_obbligo_mantenimento!=null && ID_tp_obbligo_mantenimento.equals(idTpObbligoMantenimentoAdempieInViaExtragiudiziale))
			{
				if(records.getString(1, 2)!=null)
				{
					double assegnoDichiarato = records.getDouble(1, 2);
					ret = Math.max(assegnoDichiarato, LocalVariables.importoMinimoObbligoMantenimento);
				}
			}
			// se nessun adempimento ....
			else if(ID_tp_obbligo_mantenimento!=null && ID_tp_obbligo_mantenimento.equals(idTpObbligoMantenimentoNonAdempie))
			{
				//se c'è una sentenza si prende la parte dell'assegno di mantenimento dovuta da sentenza ma non percepita 
				if(importo_sentenza>0 ) {
					ret = Math.max(importo_sentenza - importo_percepito, 0);
				//se non c'è sentenza si prende importo minimo assegno mantenimento
				}else {
					ret = LocalVariables.importoMinimoObbligoMantenimento;
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