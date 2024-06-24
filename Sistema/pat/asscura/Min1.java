package c_elab.pat.asscura;

public class Min1 extends QDati {

public double getValue() {
		
		try {
			return getMin1();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
