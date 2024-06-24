package c_elab.pat.ITEA16; 

/**
 * 
 * @author a_pichler
 *
 */
public class Sopraggiunta_provvidenza extends QSopraggiuntaInvalidita { 
	
		
	public double getValue() {

	double sopraggiuntaProvvidenza = 0.0;
		
		try 
		{
			if(datiEpuInv != null && datiEpuInv.getRows()>0)
			{
				sopraggiuntaProvvidenza=new Double((String)(datiEpuInv.getElement(1,2))).doubleValue();
			}
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Exception in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		return sopraggiuntaProvvidenza;
		

	}

	
}