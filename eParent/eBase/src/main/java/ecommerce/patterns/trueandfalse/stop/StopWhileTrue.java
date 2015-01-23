package ecommerce.patterns.trueandfalse.stop;

import java.util.List;

import ecommerce.base.ITrueAndFalse;

public class StopWhileTrue implements IStop {

	@Override
	public boolean match(ITrueAndFalse taf) {
		
		List<Boolean> result = taf.getResult();
		int pos = taf.getResultPos() - 1;
		return result.get(pos);
	}

}
