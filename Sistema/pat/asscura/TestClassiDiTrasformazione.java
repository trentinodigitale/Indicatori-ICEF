package c_elab.pat.asscura;

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
		RunawayData					hrun	= new RDServlet();
		Hashtable<String, String>	h		= new Hashtable<String, String>();
//		h.put("servletName", "http://192.168.30.235:8080/clesius/icef/servlet/data"); 
//		h.put("servletName", "http://127.0.0.1:8080/clesius/icef/servlet/data");
		h.put("servletName", "http://172.31.16.72:8080/clesius/icef/servlet/data");
		h.put("applAut","clesio");
		h.put("serialization","true");

		///5673468	100	2012-12-05 00:00:00.000	NULL	2012-12-05 00:00:00.000	1,00	3106
		try{
			hrun.init(h);
			Precedente qd=new Precedente();
				qd.setVariables("11215017", 2018, 30517, 30500, ""+107, true);
			qd.trace=2;
			qd.init(hrun);

			System.out.println(qd.getValue());
			System.out.println(qd.initNumeroGiorniPresenzaMese());
			for(int i=1;i<=12;i++) {
				System.out.println(qd.getNumeroGiorniPresenzaMese(i));
			}
			System.out.println("getMeseInizio:"+qd.getMeseInizio());
			System.out.println("getProfili:"+qd.getProfili());
			System.out.println("V_1_6:"+qd.getV_1_6());
			System.out.println("V_7_18:"+qd.getV_7_18());
			System.out.println("check:"+qd.getCheck());
			System.out.println("check:"+qd.getSup());
			System.out.println("fine:"+qd.getMeseFine());
			System.out.println("inizio:"+qd.getMeseInizio());
			System.out.println("cessazione:"+qd.getCessazione());
			System.out.println("revoche:"+qd.getRevoche());
			System.out.println(qd.getNumeroGiorniPresenzaMese(19));
			System.out.println(qd.initNumeroGiorniPresenzaMese());
			System.out.println("----------------------------"+qd.getNumeroGiorniPresenzaMese(7));
			
			/*System.out.println("V_19_30:"+qd.getV_19_30());
			System.out.println("getIdDomandaUscita:"+qd.getIdDomandaUscita());
			System.out.println("revoche:"+qd.getRevoche());*/
			/*System.out.println(qd.getV_7_18());
				System.out.println(qd.getV_7_18());
				System.out.println(in.getValue());
				System.out.println(d.getValue());
				System.out.println(xx.getValue());*/

			/*System.out.println("--------------------------------------------");
				System.out.println("--------------------------------------------");
				qd.shiftMesi=6;
				qd.trace=2;


				System.out.println("--------------------------------------------");
				System.out.println("--------------------------------------------");
				System.out.println("--------------------------------------------");
				System.out.println("--------------------------------------------");
				System.out.println("--------------------------------------------");
				System.out.println("--------------------------------------------");


				System.out.println(qd.getNumeroGiorniPresenzaMese(1));
				System.out.println("V_1_6:"+qd.getV_1_6());
				System.out.println(qd.getV_7_18());*/
			//}

			/*boolean usaDetrazioneMaxValoreNudaProprieta = false;
			boolean gratuito = true;
			System.out.println(qi.getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta));

			usaDetrazioneMaxValoreNudaProprieta = false;
			gratuito = false;
			System.out.println(qi.getValoreImmobili(gratuito,usaDetrazioneMaxValoreNudaProprieta));
			System.out.println(qdf.getValue());

			C2_agr_ast aC2_agr_ast=new C2_agr_ast();
			aC2_agr_ast.setVariables("5505641", 2012, 30500, 30500, ""+107, true);
			aC2_agr_ast.init(hrun);
			System.out.println(aC2_agr_ast.getValue());*/
			/*System.out.println(qd.getCheck());
			System.out.println(qd.is19());
			System.out.println(qd.getMeseInizio());
			System.out.println(qd.getMeseFine());*/


		}catch(Exception e){
			e.printStackTrace();
		}


	}

}
