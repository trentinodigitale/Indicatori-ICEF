package c_elab.pat.autoinv12;

/**
 * @author s_largher
 */
public class Assegno_imposto extends QAssegno_imposto {
	
	//VERIFICAMI: vanno verificate ogni anno
	String idTpObbligoMantenimentoAdempieInViaExtragiudiziale  = "2"; //da tabella tp_obbligo_mantenimento
	double importoMinimoObbligoMantenimentoAdempieInViaExtragiudiziale  = 3600; //3600 euro
	
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
			if(ID_tp_obbligo_mantenimento!=null && ID_tp_obbligo_mantenimento.equals("2"))
			{
				if(records.getString(1, 2)!=null)
				{
					double assegnoDichiarato = records.getDouble(1, 2);
					ret = Math.max(assegnoDichiarato, importoMinimoObbligoMantenimentoAdempieInViaExtragiudiziale);
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