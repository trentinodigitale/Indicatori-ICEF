package c_elab.pat.odont10;

import it.clesius.apps2core.ElainNode;
import it.clesius.db.sql.RunawayData;


/** 
 * @author s_largher
 */
public class ID extends ElainNode {

	
	public ID() {	}

	protected void reset() {
	}

	public void init(RunawayData dataTransfer) {
	}

    public double getValue() {
    	
    	return new Double(IDdomanda).doubleValue();
    }
}