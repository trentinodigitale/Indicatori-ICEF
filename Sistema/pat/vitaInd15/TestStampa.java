package c_elab.pat.vitaInd15;

import it.clesius.db.sql.RunawayData;
import it.clesius.db.util.Table;

import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.util.Calendar;
import java.util.Hashtable;
import java.util.Vector;

public class TestStampa {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Hashtable h = new Hashtable();
		//h.put("servletName", "http://192.168.30.222:8080/clesius/icef/servlet/data"); 
		//h.put("servletName", "http://127.0.0.1:8080/clesius/icef/servlet/data");
		h.put("servletName","http://192.168.30.222:8080/clesius/icef/servlet/data");
		h.put("applAut", "clesio");
		h.put("serialization", "true");
		try {
			String id_domanda = "5583094";
			long time = System.currentTimeMillis();

			String requeststr = "http://192.168.30.222:8080/clesius/icef/servlet/ac12";
			String parameters = "force=1&mode=DB_SINGLE_GET&ID_domanda="+ id_domanda;
			String c_elaoutTable = "ric_c_elaout";
			String c_elainTable = "ric_c_elain";
			
			parameters = parameters+ "&appletPath=/clesius/isee/&anno=1998&Random="+ java.lang.Math.random();

			System.out.println("ac12.jsp >>parameters:" + parameters);
			System.out.println("ac12.jsp >>c_elainTable:" + c_elainTable);
			System.out.println("ac12.jsp >>c_elaoutTable:" + c_elaoutTable);
			System.out.println("ac12.jsp >> elaborazione :" + id_domanda);

			
			RunawayData hrun = new it.clesius.db.sql.servlet.RDServlet();
			hrun.init(h);
			Table dom = hrun.executeQuery("SELECT  id_servizio,id_periodo,data_presentazione FROM domande inner join doc on doc.id=domande.id_domanda where id_domanda="
						+ id_domanda);
			
			
			//1			  	2		 	3	    		4				5		6	 	7	  	8	 	9		10	 	11	  	12   	13  	14     		15				16
			Table conf = hrun.executeQuery("SELECT  Id_servizio,	Id_periodo,	Inizio_periodo,	Fine_periodo,	Sup,	Max1,	Max2,	Max3,	Max4,	Min1,	Min2,	Min3,	Min4,	Detraz_65,	Inizio_vettore,progressivo_elab FROM AC_configurazione where " 
					+" id_servizio="+dom.getInteger(1, 1)
					+" and id_periodo="+dom.getInteger(1, 2)+" order by Inizio_periodo asc"
				
					);
			
			String out_rete = "";
			if(dom.getInteger(1, 1)==30500){
				hrun.executeUpdate("update AC_dati set progressivo_elab=0,ICEF_prec=0 where id_domanda="+ id_domanda);
			}
			out_rete = it.clesius.util.HttpUtil.doPOST(requeststr,parameters);
			
			if (conf.getRows()==2 && dom.getInteger(1, 1)==30500) {
				
				Table c_elaout = hrun.executeQuery("SELECT node,numeric_value FROM "
						+ c_elaoutTable + "  where id_domanda="
						+ id_domanda);
				
				//copio icef precedente
				hrun.executeUpdate("UPDATE AC_dati	SET	AC_dati.progressivo_elab = 1,AC_dati.ICEF_prec = numeric_value FROM AC_dati INNER JOIN  "
						+ c_elaoutTable
						+ " ON AC_dati.id_domanda = "
						+ c_elaoutTable
						+ ".id_domanda where AC_dati.id_domanda="
						+ id_domanda
						+ " and  "
						+ c_elaoutTable
						+ ".id_domanda="
						+ id_domanda
						+ " and node='ICEF'");
				
				out_rete = it.clesius.util.HttpUtil.doPOST(requeststr,parameters);
				Vector transaction = new Vector();
				for (int i = 1; i <= 12; i++) {
					String scurrentIndex = "";
					if (i < 10) {
						scurrentIndex = "0";
					}
					scurrentIndex = scurrentIndex + "" + i;
					for (int x = 1; x <= c_elaout.getRows(); x++) {
						if (c_elaout.getString(x, 1).compareTo("ac" + scurrentIndex) == 0) {
							transaction.add("update " + c_elaoutTable
									+ " set numeric_value="
									+ c_elaout.getDouble(x, 2)
									+ " where node='ac" + scurrentIndex
									+ "' and id_domanda=" + id_domanda);
						}
					}
				}
				hrun.executeTransaction(transaction);
			} 

			Table c_elain = hrun.executeQuery("SELECT node,input_value FROM " + c_elainTable + "  where id_domanda=" + id_domanda);
			Table c_elaout = hrun.executeQuery("SELECT node,numeric_value FROM "+ c_elaoutTable + "  where id_domanda="	+ id_domanda);
			String mesi[] = { "Gennaio", "Febbraio", "Marzo", "Aprile", "Maggio", "Giugno", "Luglio", "Agosto", "Settembre", "Ottoble", "Novembre", "Dicembre" };

			String array_V_1_6[]	= { "0", "0", "0", "0", "0", "0" };
			String array_V_7_18[]	= { "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0", "0" };
			double V_1_6			= 0;
			double V_7_18			= 0;
			
			
			for (int r = 1; r <= c_elain.getRows(); r++) {
				if ((c_elain.getString(r, 1)).equals("V_1_6")) {
					V_1_6			= c_elain.getDouble(r, 2);
				} else if ((c_elain.getString(r, 1)).equals("V_7_18")) {
					V_7_18			= c_elain.getDouble(r, 2);
				}
			}
			 
			if (V_1_6 > 1) {
				java.math.BigDecimal dV_1_6	=	new java.math.BigDecimal(V_1_6);
				String temp16				=	"" + dV_1_6.toPlainString();
				temp16						=	temp16.substring(1, temp16.length());
				  
				if (temp16.indexOf(".") > -1) {
					temp16 = temp16.substring(1, temp16.indexOf("."));
				}
				//System.out.println("v16  " + temp16);
				for (int i = 0; i < temp16.length(); i++) {
					
					//System.out.println("i " +i);
					array_V_1_6[i] = String.valueOf(temp16.charAt(i));
					//System.out.println("v16 " + array_V_1_6[i]);
				}
			}
			if (V_7_18 > 1) {
				java.math.BigDecimal dV_7_18	=	new java.math.BigDecimal(V_7_18);
				String temp718				=	"" + dV_7_18.toPlainString();
				temp718						=	temp718.substring(1, temp718.length());
				if (temp718.indexOf(".") > -1) {
					temp718 = temp718.substring(1, temp718.indexOf("."));
				}
				//System.out.println("v718 " + temp718);
				for (int i = 0; i < temp718.length(); i++) {
					
					//System.out.println("i " +i);
					array_V_7_18[i] = String.valueOf(temp718.charAt(i));
					//System.out.println("v718 " + array_V_7_18[i]);
				}
			}

			Calendar c = dom.getCalendar(1, 3);
			int anno = c.get(Calendar.YEAR);
			int mese = c.get(Calendar.MONTH);
			if (mese <= 5) {
				anno = anno - 1;
			}

			Calendar ctmp = Calendar.getInstance();
			ctmp.set(Calendar.YEAR, anno);

			int currentIndex = 1;
			String tabData = "<center><TABLE border='1' align='center'>";
			int totaleGiorni = 0;
			int totaleGiornisu = 0;
			double totaleContributo = 0;
			String tabDatatmp="";
			tabDatatmp = tabDatatmp
					+ "<tr><td colspan='4'>Risultato suddiviso per i mesi del anno:<b>"
					+ anno + "</b></td></tr>";
			tabDatatmp = tabDatatmp
					+ "<tr><td><b>Mese</b><td><b>Giorni</b><td><b>Livello</b><td><b>AC</b></td></tr>";
			for (int i = 0; i < 6; i++) {
				tabDatatmp = tabDatatmp + "<tr>";
				int posMese = 0;
				posMese = 6 + currentIndex - 1;
				if (posMese >= 12) {
					posMese = posMese - 12;
				}
				ctmp.set(Calendar.MONTH, posMese);
				ctmp.set(Calendar.DAY_OF_MONTH, 1);
				tabDatatmp = tabDatatmp + "<td>" + mesi[posMese] + "</td>";
				String scurrentIndex = "";
				if (currentIndex < 10) {
					scurrentIndex = "0";
				}
				scurrentIndex = scurrentIndex + currentIndex;
				for (int x = 1; x <= c_elain.getRows(); x++) {
					if (c_elain.getString(x, 1).compareTo(
							"Gg" + scurrentIndex) == 0) {
						totaleGiorni = totaleGiorni + (int) new Double(c_elain.getString(x, 2)).doubleValue();
						totaleGiornisu = totaleGiornisu + ctmp.getActualMaximum(Calendar.DAY_OF_MONTH);
						tabDatatmp = tabDatatmp
								+ "<td><b>"
								+ (int) new Double(c_elain.getString(x, 2)).doubleValue()
								+ "/"
								+ ctmp.getActualMaximum(Calendar.DAY_OF_MONTH)
								+ "</b></td>";
						break;
					}
				}
				tabDatatmp = tabDatatmp + "<td><b>" + array_V_1_6[i] + "</b></td>";

				for (int x = 1; x <= c_elaout.getRows(); x++) {
					if (c_elaout.getString(x, 1).compareTo("ac" + scurrentIndex) == 0) {
						totaleContributo = totaleContributo + c_elaout.getDouble(x, 2);
						java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("######0.00");
						String output = myFormatter.format(c_elaout.getDouble(x, 2));
						tabDatatmp = tabDatatmp + "<td><b>" + output + "</b></td>";

						break;
					}
				}
				tabDatatmp = tabDatatmp + "</tr>";
				currentIndex++;
			}

			
			if(dom.getInteger(1, 1)==30500){
				tabData=tabData+tabDatatmp;
			}else{
				totaleGiorni = 0;
				totaleGiornisu = 0;
				totaleContributo = 0;
				currentIndex=7;
			}
			
			
			
			tabData = tabData
					+ "<tr><td colspan='4'>Risultato suddiviso per i mesi del anno:<b>"
					+ (anno + 1) + "</b></td></tr>";
			tabData = tabData
					+ "<tr><td><b>Mese</b><td><b>Giorni</b><td><b>Livello</b><td><b>AC</b></td></tr>";
				ctmp.set(Calendar.YEAR, anno + 1);
					for (int i = 0; i < 12; i++) {
				tabData = tabData + "<tr>";
				int posMese = 0;
				posMese = 6 + currentIndex - 1;
				if (posMese >= 12) {
					posMese = posMese - 12;
				}

				String scurrentIndex = "";
				if (currentIndex < 10) {
					scurrentIndex = "0";
				}
				scurrentIndex = scurrentIndex + currentIndex;

				ctmp.set(Calendar.MONTH, posMese);
				ctmp.set(Calendar.DAY_OF_MONTH, 1);
				tabData = tabData + "<td>" + mesi[posMese] + "</td>";
				for (int x = 1; x <= c_elain.getRows(); x++) {
					if (c_elain.getString(x, 1).compareTo(
							"Gg" + scurrentIndex) == 0) {
						totaleGiorni = totaleGiorni
								+ (int) new Double(c_elain.getString(x, 2))
										.doubleValue();
						totaleGiornisu = totaleGiornisu
								+ ctmp.getActualMaximum(Calendar.DAY_OF_MONTH);
						tabData = tabData
								+ "<td><b>"
								+ (int) new Double(c_elain.getString(x, 2)).doubleValue()
								+ "/"
								+ ctmp.getActualMaximum(Calendar.DAY_OF_MONTH)
								+ "</b></td>";
						break;
					}
				}
				tabData = tabData + "<td><b>" + array_V_7_18[i] + "</b></td>";

				for (int x = 1; x <= c_elaout.getRows(); x++) {
					if (c_elaout.getString(x, 1).compareTo(
							"ac" + scurrentIndex) == 0) {
						totaleContributo = totaleContributo
								+ c_elaout.getDouble(x, 2);
						java.text.DecimalFormat myFormatter = new java.text.DecimalFormat(
								"######0.00");
						String output = myFormatter.format(c_elaout.getDouble(x, 2));
						tabData = tabData + "<td><b>" + output
								+ "</b></td>";
						break;
					}
				}
				tabData = tabData + "</tr>";
				currentIndex++;
			}

			tabData = tabData
					+ "<tr><td colspan='2'>Totale giorni:</td><td colspan='2'><b>"
					+ totaleGiorni + "/" + totaleGiornisu
					+ "</b></td></tr>";
			java.text.DecimalFormat myFormatter = new java.text.DecimalFormat("######0.00");
			String output = myFormatter.format(totaleContributo);
			tabData = tabData
					+ "<tr><td colspan='2'>Totale contributo:</td><td colspan='2'><b>"
					+ output + "</b></td></tr>";
			tabData = tabData + "</TABLE></center>";
			out_rete = out_rete.replace("inizio", tabData);

			if (hrun.executeQuery("select * from ac_variazioni where id_domanda="+ id_domanda).getRows() == 0) {
				out_rete = out_rete.replace("inizio", "");
			} else {
				out_rete = out_rete.replace("inizio", tabData);
			}

			//concatenzione dei baos contenuti nell'ArrayList
			byte[] retBaos = out_rete.getBytes();

		
			
			BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream("D:\\xxx.html"));
			bos.write(retBaos);
			bos.flush();
			bos.close();
			
			/*ServletOutputStream ouputStream =  response.getOutputStream();
			response.setContentType("text/html");
			ouputStream.write(retBaos);
			ouputStream.close();  
			//test
			/*FileWriter fstream = new FileWriter("d:\\out.html");
			BufferedWriter out = new BufferedWriter(fstream);
			out.write(out_rete);
			//Close the output stream
			out.close();*/

		} catch (Exception e) {
			e.printStackTrace();
		
		}

	}

}
