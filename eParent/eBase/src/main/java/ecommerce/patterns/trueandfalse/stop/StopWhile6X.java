package ecommerce.patterns.trueandfalse.stop;

import java.util.List;

import ecommerce.base.ITrueAndFalse;

public class StopWhile6X implements IStop {

	@Override
	public boolean match(ITrueAndFalse taf) {
		List<Boolean> result = taf.getResult();
		int pos = taf.getResultPos() - 1;
		
		boolean bMatch = false;
		if(pos<5)
			return bMatch;
		
		for(int i=0;i<6&!bMatch;i++)
			bMatch = bMatch || result.get(pos-i);
		
		return !bMatch;
	}

}
