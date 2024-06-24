package c_elab.pat.du17.fnum;



/** query per il calcolo dei componenti con residenza triennale della domanda del reddito di garanzia ICEF
 * @author l_leonardi
 */
public class N_figli extends QFamiglieNumerose {
	

	/** ritorna il valore double da assegnare all'input node
	 * @see it.clesius.apps2core.ElainNode#getValue()
	 * @return double
	 */
	public double getValue() 
	{

		double nFigli = 0.0;

		try 
		{
			
			/*
			Per figli si intendono i figli del richiedente e del coniuge o convivente del
			richiedente, senza limiti di età purché risultino a carico del nucleo del richiedente; si
			considera a carico il figlio che nell’anno di riferimento ha percepito un reddito
			personale inferiore ai 6.000,00 euro. Tale reddito viene definito quale sommatoria
			dei redditi considerati ai fini della valutazione della condizione economica ICEF ad
			esclusione dei redditi da indicare nel quadro C5 del modello di dichiarazione
			sostitutiva ICEF.
			Sono equiparati ai figli:
			a) il concepito, quando la data presunta del parto è determinata entro la fine
			dell’anno in cui il richiedente presenta domanda;
			b) i minori in stato di affido familiare in base a quanto previsto dall’art. 4,
			comma 3, della LP 1/2011.
			
			N_figli = n_concepiti + n_affidati + componenti in base alla relazione di parentela "Figlio o equiparato" se più giovani del beneficiario 
					  con controllo che sum(c1+c2+c3+c4)<6000 
			*/
			nFigli = getNumeroFigli() + getNumeroConcepiti() + getNumeroAffidati();
			
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		} catch (NumberFormatException nfe) {
			System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
			return 0.0;
		} catch (Exception e) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ e.toString());
			return 0.0;
		}
		return nFigli;
	}
}