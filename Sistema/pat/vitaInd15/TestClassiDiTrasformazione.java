package c_elab.pat.vitaInd15;

import it.clesius.db.sql.RunawayData;
import it.clesius.db.sql.servlet.RDServlet;
import it.clesius.db.util.Table;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

public class TestClassiDiTrasformazione {

	public static class QDatiTest extends QDati{

	}
	public static class QImmobiliareTest extends QImmobiliare{

	}
	public static class QDatiFamigliaTest extends c_elab.pat.icef13.Assegno_imposto{

	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RunawayData    hrun= new RDServlet();

		String id_domanda="5433054";

		Hashtable h = new Hashtable();
		//h.put("servletName", "http://192.168.30.222:8080/clesius/icef/servlet/data"); 
		//h.put("servletName", "http://127.0.0.1:8080/clesius/icef/servlet/data");
		h.put("servletName", "http://172.31.16.75:8080/clesius/icef/servlet/data");
		//h.put("servletName", "http://192.168.30.30:8080/clesius/icef/servlet/data");
		h.put("applAut","clesio");
		h.put("serialization","true");


		///5673468	100	2012-12-05 00:00:00.000	NULL	2012-12-05 00:00:00.000	1,00	3106
		try{
			hrun.init(h);
			QDatiTest qd=new QDatiTest();
			Invalidi in =new Invalidi();
			//Detraz_65 d=new Detraz_65();
			IA_ast xx=new IA_ast();
			QImmobiliareTest qi=new QImmobiliareTest();
			qi.setVariables("6637118", 2013, 30501, 30501, ""+107, true);
			qi.init(hrun);
			boolean usaDetrazioneMaxValoreNudaProprieta = false;
			boolean gratuito = true;
			qi.getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta);
		
			

			qd.setVariables("6637118", 2013, 30501, 30501, ""+107, true);
			qd.init(hrun);

		}catch(Exception e){
			e.printStackTrace();
		}


	}

}
