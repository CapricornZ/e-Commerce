package ecommerce.patterns.trueandfalse.gonext;

import java.util.List;

/***
 * match oxo
 * @author martin
 *
 */
public class Next4 implements INext {

	@Override
	public boolean go2First(List<Boolean> result, int length, int current) {
		
		if(length < 3)
			return false;
		if(result.get(length-1) && !result.get(length-2) && result.get(length-3))
			return true;
		return false;
	}

}
