package c_elab.pat.asscura;

public class Max1 extends QDati {

public double getValue() {
		
		try {
			return getMax1();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
