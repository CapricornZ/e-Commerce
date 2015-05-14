package ecommerce.algorithm4.perterns.trueandfalse;

import java.util.List;

public class Next3 implements INext {

	@Override
	public boolean go2First(List<Boolean> result, int length, int current) {
		
		if(length < 3)
			return false;
		if(current > 8 && result.get(length-1) && !result.get(length-2) && result.get(length-3))
			return true;
		return false;
	}

}
