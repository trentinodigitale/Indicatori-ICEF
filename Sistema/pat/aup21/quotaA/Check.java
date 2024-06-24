package c_elab.pat.aup21.quotaA;

import it.clesius.evolservlet.icef.PassaValoriIcef;

public class Check extends QQuotaA {



	
	public double getValue() {
		Double check =  PassaValoriIcef.getCheck(IDdomanda); //classe metodo DefComponentiDich
		if(check!=null){
			return check;
		}else{
			return 1000000000;
		}
		
	}

	
}
