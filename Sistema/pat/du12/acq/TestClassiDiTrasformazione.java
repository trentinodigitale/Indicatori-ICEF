package c_elab.pat.du12.acq;

import java.util.Hashtable;

import it.clesius.db.sql.RunawayData;
import it.clesius.db.sql.servlet.RDServlet;

public class TestClassiDiTrasformazione {

	public static String C1 = 				"C1";
	public static String C2 = 				"C2";
	public static String C3 = 				"C3";
	public static String C4 = 				"C4";
	public static String C5 = 				"C5";
	public static String D = 				"D";
	public static String E = 				"E";
	public static String F = 				"F";
	public static String N_componenti = 	"N";
	public static String Particolarita = 	"P";
	
	public static String auto = 			"Auto";
	public static String ici = 				"Ici";

	public static class DefComponentiDichTest extends DefComponentiDich{

		@Override
		protected void reset() {
			// TODO Auto-generated method stub
			
		}

	}
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
		
			DefComponentiDich qd=new DefComponentiDichTest();
			qd.setVariables("5045671", 2013, 30500, 30501, ""+107, true);
			qd.init(hrun);
			qd.getDefinizioneComponentiDichiarazione(C1);
			
			


		}catch(Exception e){
			e.printStackTrace();
		}


	}

}
