package ecommerce.patterns.trueandfalse.gonext;

import java.util.List;

/***
 * match oxo & current>8
 * @author martin
 *
 */
public class Next2 implements INext {

	@Override
	public boolean go2First(List<Boolean> result, int length, int current) {
		
		if(length < 3)
			return false;
		if(current > 8 && result.get(length-1) && !result.get(length-2) && result.get(length-3))
			return true;
		return false;
	}

}
