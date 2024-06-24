package c_elab.pat.ITEA19; 


/**
 * 
 * @author a_pichler
 * 
 *  0 non fai niente - IGNORA TUTTO
 *  1 calcola la verifica e valuta questa verifica e calcola l'ICEF attualizzato
 *  2 calcola solo l'ICEF attualizzato
 *
 */
public class Sopraggiunta_invalidita extends QSopraggiuntaInvalidita { 
	
	public double getValue() {

		double sopraggiuntaInvalidita = 0.0;
			
			try 
			{
				if(datiEpuInv != null && datiEpuInv.getRows()>0)
				{
					sopraggiuntaInvalidita = 1.0;
				}else{
					sopraggiuntaInvalidita = 0.0;
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
			return sopraggiuntaInvalidita;
			

		}
}