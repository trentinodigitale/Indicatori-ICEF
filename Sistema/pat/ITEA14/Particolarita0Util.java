package c_elab.pat.ITEA14;

import it.clesius.db.sql.DBException;
import it.clesius.db.util.Table;

import java.io.IOException;
import java.util.HashMap;

import c_elab.pat.ITEA14.util.Qutil;

/** query per i dati della domanda
 * @author g_barbieri
 */
public class Particolarita0Util extends Qutil {

	public Table getTable(String IDdomanda) throws DBException, IOException {

		HashMap parmas = new HashMap();	
		parmas.put("id_domanda", IDdomanda);
		loadSQL( parmas, "particolarita0" );
		return getTable();
	}

	protected void reset() {	
	}
}
