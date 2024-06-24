package c_elab.pat.aup18.quotaA;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class Mensile_pagato extends QQuotaA {
	
	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() {
		
		double mensilePagato = 999999;
		
		try 
		{
			/*
			//se sono passati tre mesi dal primo mese di contributo il beneficio finale non può
			//più aumentare ne' per lo sblocco delle dichiarazioni ne per lo sblocco delle domande stesse
			if(datiRinnovo!=null && datiRinnovo.getRows()>0)
			{
				int annoInizio = datiRinnovo.getInteger(1, 7);
				int meseInizio = datiRinnovo.getInteger(1, 8);
				double importo_mensile = datiRinnovo.getDouble(1, 6);
				
				if(annoInizio!=-1)
				{
					int annoFine = annoInizio;
					int meseFine = meseInizio;
					
					for(int i=0; i<3; i++)
					{
						meseFine++;
						if(meseFine>12)
						{
							annoFine++;
							meseFine = 1;
						}
					}
					
					Calendar dataAttuale = Calendar.getInstance();
					Calendar dataFine = new GregorianCalendar(annoFine, meseFine-1, 1);
					
					if(dataAttuale.getTime().getTime()>dataFine.getTime().getTime())
					{
						mensilePagato = importo_mensile;
					}
				}
			}
			*/
	
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
		
		return mensilePagato;
	}
		
}
