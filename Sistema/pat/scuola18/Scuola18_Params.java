package c_elab.pat.scuola18;

import java.util.Calendar;

import it.clesius.db.util.DateTimeFormat;
import it.clesius.util.General1;

public class Scuola18_Params {

	//CAMBIAMI: va cambiata ogni anno
	//datarif.set(2018, 5, 8, 23, 59); //  10 giugno 2019 ? da verificare
	public static final Calendar datarif = General1.getStringToCalendar(
			DateTimeFormat.toItDate("2019-05-10 23:59:00.0"));
}
