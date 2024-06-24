package c_elab.pat.aup17.quotaA;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolservlet.icef.PassaValoriIcef;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public class Check extends QQuotaA {



	
	public double getValue() {
		Double check =  PassaValoriIcef.getCheck(IDdomanda); //classe metodo DefComponentiDich
		if(check!=null){
			return check;
		}else{
			return 1000000000;
		}
		
	}

	
}
