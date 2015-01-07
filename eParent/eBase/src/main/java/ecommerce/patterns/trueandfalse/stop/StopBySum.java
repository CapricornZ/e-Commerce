package ecommerce.patterns.trueandfalse.stop;

import ecommerce.base.ITrueAndFalse;

public class StopBySum implements IStop {
	
	private int MAX;
	public void setMax(int val){this.MAX = val;}

	@Override
	public boolean match(ITrueAndFalse taf) {
		return taf.getSum()>=this.MAX;
	}
}
