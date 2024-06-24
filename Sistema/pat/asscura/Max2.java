package c_elab.pat.asscura;

public class Max2 extends QDati {

public double getValue() {
		
		try {
			return getMax2();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
