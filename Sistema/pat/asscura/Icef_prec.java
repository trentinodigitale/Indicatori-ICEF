package c_elab.pat.asscura;

public class Icef_prec extends QDati {

	public double getValue() {
		try {
			return getICEF_prec();
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}
}


