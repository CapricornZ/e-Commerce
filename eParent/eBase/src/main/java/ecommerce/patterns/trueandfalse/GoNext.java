package ecommerce.patterns.trueandfalse;

import ecommerce.base.ITrueAndFalse;
import ecommerce.patterns.trueandfalse.gonext.IGoNext;

public class GoNext implements IGoNext {

	@Override
	public int GetNext(ITrueAndFalse taf) {
		return taf.getMetaPos();
	}
}
