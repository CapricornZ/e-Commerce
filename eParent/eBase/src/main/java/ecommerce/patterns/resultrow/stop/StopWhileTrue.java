package ecommerce.patterns.resultrow.stop;

import java.util.List;

/***
 * match whether last Element is TRUE
 * @author martin
 *
 */
public class StopWhileTrue implements IStop {

	@Override
	public boolean match(List<List<Boolean>> result) {
		if(result.size()<2)
			return false;
		
		List<Boolean> last = result.get(result.size()-1);
		return last.size() == 0?false:last.get(last.size()-1);
	}
}
