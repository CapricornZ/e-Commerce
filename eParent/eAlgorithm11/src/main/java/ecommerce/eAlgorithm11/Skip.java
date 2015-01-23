package ecommerce.eAlgorithm11;

import ecommerce.base.ITrueAndFalse;

public class Skip {
	static public boolean exam(ITrueAndFalse taf){
		return !taf.getResult().get(0) && !taf.getResult().get(1);
	}
}
