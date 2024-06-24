package c_elab.pat.uni23;

public class Preso_alloggio extends QStudente {

	//Preso_alloggio non legge più il dato in UNI_dati.PRESO_ALLOGGIO (pos 7), ma il nuovo dato che arriva dalle richieste Opera CONTRATTO_ALLOGGIO (pos 13)
	//anche se il significato del nodo è variato è stato deciso di utilizzare lo stesso nodo in seguito a una modifica alla rete.
	//il dato CONTRATTO_ALLOGGIO indica se è presente un contratto a prescindere che sia contratto privato da un contratto opera
	//questo dato viene passato tramite una procedura d'ufficio dell'opera
	
	//PRIMA in caso di studente sede = 2 (quindi fuori sede) se preso_alloggio = 2 (cioè non avevo accettato l'alloggio) 
	//si aveva una variazione dei nodi in uscita: alloggio e alloggio_idoneo che andavano a 0 perché automaticamente lo studente veniva escluso dalla graduaotria
	//quindi in qualche modo si variava la graduatoria dell'alloggio in caso di studente fuori sede che non accettava l'allogio
	
	//ADESSO la graduatoria dell'alloggio non deve dipendere dall'informazione della presenza del'accettazione dell'alloggio opera, ma soltando dalla condizione economica, se chiede alloggio, merito, esclusione ufficio  
	//per fare questa modifica dalle rete è stato tolto il collegamento tra 'fuori_sede' e 'alloggio'
	//la graduatoria verrà gestita dall'opera. Una volta generata, lo studente accetta o non tramite una procedura separata. Questa informazione non deve interagire con la graduatoria
	
	//SAREBBE LOGICO CAMBIARE IL NOME DEL NODO IN 'Contratto_alloggio'
	
    public double getValue() {
        try {
        	//0=sconosciuto, 1=Si, 2=No
        	String valore = (String)(uni_dati.getElement(13,2));
        	if(valore != null){
        		double val = new Double(valore).doubleValue();
        		if(val == 0 || val == 2){ //bisogna trattare nello stesso modo se l'informazione del contratto è sconosciuta oppure non si ha il contratto
        			return 2.0;
        		}else{
        			return 1.0;
        		}
        	}else{
        		//se il valore non è presente passo 2 di default
        		return 2.0;
        	}
        } catch(NullPointerException n) {
            System.out.println("Null pointer in " + getClass().getName() + ": " + n.toString());
            return 0.0;
        } catch (NumberFormatException nfe) {
            System.out.println("ERROR NumberFormatException in " + getClass().getName() + ": " + nfe.toString());
            return 0.0;
        }
    }
}
