/**
 *Created on 21-giu-2007
 */

package c_elab.pat.sport11;

import it.clesius.apps2core.ElainNode;
import java.util.Hashtable;
import java.util.Collections;


/** 
 * @author a_pichler
 */
public class Merito extends QStudente {
private static double conv_naz_assoluta_max=100;
private static double conv_naz_categoria_max=80;
private static double part_assoluta_max=30;
private static double part_categoria_max=20;
 
    public double getValue() {
    	if (records == null)
			return 0.0;

		try {
			double conv_naz_assoluta=0.0;
			double conv_naz_categoria=0.0;
			double part_assoluta=0.0;
			double part_categoria=0.0;
			double punti=records.getDouble(1, 10);
			Hashtable<String,Double> h = new Hashtable<String,Double>();

			
			if(java.lang.Math.abs(records.getInteger(1, 1))>0){
				conv_naz_assoluta=conv_naz_assoluta_max;				
			}
			if(java.lang.Math.abs(records.getInteger(1, 2))>0){
				conv_naz_categoria=conv_naz_categoria_max;				
			}
			if(java.lang.Math.abs(records.getInteger(1, 4))>0){
				part_assoluta=part_assoluta_max;				
			}
			if(java.lang.Math.abs(records.getInteger(1, 5))>0){
				part_categoria=part_categoria_max;				
			}	
			
			h.put( "conv_naz_assoluta",new Double(conv_naz_assoluta));
			h.put( "conv_naz_categoria",new Double(conv_naz_categoria));
			h.put( "part_assoluta",new Double(part_assoluta));
			h.put( "part_categoria",new Double(part_categoria));
			h.put( "punti",new Double(punti));
			
			return Collections.max(h.values());
		
        } catch(NullPointerException n) {
        	System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
