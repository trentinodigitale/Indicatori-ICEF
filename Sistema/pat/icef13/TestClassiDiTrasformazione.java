package c_elab.pat.icef13;

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



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RunawayData    hrun= new RDServlet();


		Hashtable h = new Hashtable();
		//h.put("servletName", "http://192.168.30.222:8080/clesius/icef/servlet/data"); 
		//h.put("servletName", "http://192.168.30.222:8080/clesius/icef/servlet/data");
		h.put("servletName", "http://172.31.16.75:8080/clesius/icef/servlet/data");
		h.put("applAut","clesio");
		h.put("serialization","true");

		try{
			hrun.init(h);
			QDomanda qd=new QDomanda();
			qd.setVariables("5792354", 2012, 15007, 15007, ""+107, true);
			qd.init(hrun);
			System.out.println(qd.isMonogenitore());
			
			Assegno_imposto qds=new Assegno_imposto();
			qds.setVariables("5792354", 2012, 15007, 15007, ""+107, true);
			qds.init(hrun);
			System.out.println(qds.getValue()); 
			
			

		}catch(Exception e){
			e.printStackTrace();
		}


	}

}
