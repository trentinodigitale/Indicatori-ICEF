package c_elab.pat.asscura;

public class Min3 extends QDati {

public double getValue() {
		
		try {
			return getMin3();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
