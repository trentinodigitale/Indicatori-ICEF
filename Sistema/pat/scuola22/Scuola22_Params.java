package c_elab.pat.scuola22;

import java.util.Calendar;

import it.clesius.db.util.DateTimeFormat;
import it.clesius.util.General1;

public class Scuola22_Params {

	//CAMBIAMI: va cambiata ogni anno FATTO
	//datarif.set(2018, 5, 8, 23, 59); //  10 giugno 2019 ? da verificare
	public static final Calendar datarif = General1.getStringToCalendar(
			DateTimeFormat.toItDate("2022-05-09 23:59:00.0"));
}
