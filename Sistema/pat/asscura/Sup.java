package c_elab.pat.asscura;

public class Sup extends QDati {

	public double getValue() {

		try {
			return getSup();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}

	}

}
