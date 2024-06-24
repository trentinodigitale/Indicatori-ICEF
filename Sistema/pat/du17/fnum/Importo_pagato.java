package c_elab.pat.du17.fnum;

import java.util.Calendar;

public class Importo_pagato extends QFamiglieNumerose {
	
	private int numeroGiorniDaDataPresentazione = 30;
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double importoPagato = 999999;
		
		try 
		{
			//se sono passati n giorni dalla data di presentazione della domanda il beneficio finale non può
			//più aumentare ne' per lo sblocco delle dichiarazioni ne per lo sblocco delle domande stesse
			if(datiImportoPagato!=null && datiImportoPagato.getRows()>0)
			{
				double importo = datiImportoPagato.getDouble(1, 1);
				
				Calendar dataAttuale = Calendar.getInstance();
				dataAttuale.set(Calendar.HOUR_OF_DAY, 0);
				dataAttuale.set(Calendar.MINUTE, 0);
				dataAttuale.set(Calendar.SECOND, 0);
				dataAttuale.set(Calendar.MILLISECOND, 0);
				Calendar dataFine = datiFamiglieNumerose.getCalendar(1, 2);
				dataFine.add(Calendar.DATE,numeroGiorniDaDataPresentazione);
				
				//System.out.println("dataAttuale: "+dataAttuale.getTime().toString());
				//System.out.println("dataFine: "+dataFine.getTime().toString());
				
				if(dataAttuale.getTime().getTime()>dataFine.getTime().getTime())
				{
					importoPagato = importo;
				}
			}
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		
		return importoPagato;
	}
		
}
