package ecommerce.patterns.trueandfalse.gonext;

import java.util.List;

/***
 * match oxoxo
 * @author martin
 *
 */
public class Next3 implements INext {

	@Override
	public boolean go2First(List<Boolean> result, int length, int current) {
		
		if(length < 5)
			return false;
		if(result.get(length-1) && !result.get(length-2) && result.get(length-3) && !result.get(length-4) && result.get(length-5))
			return true;
		return false;
	}

}
