package ecommerce.patterns.trueandfalse.stop;

import java.util.List;

import ecommerce.base.ITrueAndFalse;

public class StopWhileNX implements IStop {

	private int n;
	public void setN(int value){this.n = value;}
	
	@Override
	public boolean match(ITrueAndFalse taf) {
		List<Boolean> result = taf.getResult();
		int pos = taf.getResultPos() - 1;
		//pos += taf.getOffset();
		
		boolean bMatch = false;
		//if(pos<this.n-1)
		//	return bMatch;
		if(pos < this.n+taf.getOffset()-1)
			return bMatch;
		
		for(int i=0;i<this.n&!bMatch;i++)
			bMatch = bMatch || result.get(pos - i);
		
		return !bMatch;
	}
}
