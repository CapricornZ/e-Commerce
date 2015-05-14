package ecommerce.algorithm4.perterns.trueandfalse;

import java.util.List;

public class Next1 implements INext {

	@Override
	public boolean go2First(List<Boolean> result, int length, int current) {

		if(length < 2)
			return false;
		if(result.get(length-1) && result.get(length-2))
			return true;
		return false;
	}

}
