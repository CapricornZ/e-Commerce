package ecommerce.patterns.trueandfalse;

import ecommerce.base.ITrueAndFalse;
import ecommerce.patterns.trueandfalse.stop.IStop;

public class DoNotStop implements IStop {

	@Override
	public boolean match(ITrueAndFalse taf) {
		return false;
	}

}
