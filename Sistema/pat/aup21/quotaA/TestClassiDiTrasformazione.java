package c_elab.pat.aup21.quotaA;

import java.util.Hashtable;

import it.clesius.db.sql.RunawayData;
import it.clesius.db.sql.servlet.RDServlet;
import it.clesius.db.util.Table;

public class TestClassiDiTrasformazione {



	/**
	 * @param args
	 */
	public static void main(String[] args) {
		RunawayData    hrun= new RDServlet();

		Hashtable h = new Hashtable();
		//h.put("servletName", "http://192.168.30.222:8080/clesius/icef/servlet/data"); 
		//h.put("servletName", "http://127.0.0.1:8080/clesius/icef/servlet/data");
		h.put("servletName", "http://172.31.16.75:8080/clesius/icef/servlet/data");
		h.put("applAut","clesio");
		h.put("serialization","true");

		try{
			hrun.init(h);
			Table toTest=hrun.executeQuery("select top 100 id_domanda from Domande where ID_servizio=9430");
			for(int i=1;i<=toTest.getRows();i++) {
				int id_domanda=toTest.getInteger(i, 1);
				Franchigia_nuovi_redd qd=new Franchigia_nuovi_redd();
				qd.setVariables(""+id_domanda, 2018, 70019, 70019, ""+107, true);
				qd.init(hrun);qd.getValue();
				//System.out.println(qd.getValue());
			}


		}catch(Exception e){
			e.printStackTrace();
		}


	}

}
