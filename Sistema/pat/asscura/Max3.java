package c_elab.pat.asscura;

public class Max3 extends QDati {

public double getValue() {
		
		try {
			return getMax3();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
	}

}
