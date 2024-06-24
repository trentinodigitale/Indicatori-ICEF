package c_elab.pat.asscura;

public class Min2 extends QDati {

public double getValue() {
		
		try {
			return getMin2();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
