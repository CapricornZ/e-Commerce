package ecommerce.patterns.trueandfalse.gonext;

import java.util.List;

/***
 * match oo
 * @author martin
 *
 */
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
