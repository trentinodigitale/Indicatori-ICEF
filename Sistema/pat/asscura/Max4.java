package c_elab.pat.asscura;

public class Max4 extends QDati {

public double getValue() {
		
		try {
			return getMax4();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
