package c_elab.pat.icef18;

public class Ultra65 extends Q_componenti {

	public Ultra65() {
	
	}

	public double getValue() {

		try {
			double val= getNComponentiUltra65();
			if(val==1) {
				return 0.2;
			}
			return 0.0;
		} catch (NullPointerException n) {
			System.out.println("Null pointer in " + getClass().getName() + ": "	+ n.toString());
			return 0.0;
		}
	}
}
