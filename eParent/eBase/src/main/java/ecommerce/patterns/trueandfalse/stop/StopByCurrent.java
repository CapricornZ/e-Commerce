package ecommerce.patterns.trueandfalse.stop;

import ecommerce.base.ITrueAndFalse;

public class StopByCurrent implements IStop {
	
	private int MAX;
	public void setMax(int val){this.MAX = val;}

	@Override
	public boolean match(ITrueAndFalse taf) {
		return this.MAX==taf.getCurrent();
	}

}
