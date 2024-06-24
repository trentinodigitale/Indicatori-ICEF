package c_elab.pat.aup18;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;
import it.clesius.evolservlet.icef.PassaValoriIcef;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import c_elab.pat.aup18.quotaA.QQuotaA;
import c_elab.pat.icef.util.ElabContext;
import c_elab.pat.icef.util.ElabSession;

public class Incremento extends ElainNode {

	
	public double getValue() {
		return PassaValoriIcef.getIncremento(IDdomanda); 	
	}

	@Override
	protected void reset() {
	
	}
	
	public void init(RunawayData dataTransfer) {
		
	}
	
}
