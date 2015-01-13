package ecommerce.patterns.trueandfalse.gonext;

import java.util.List;

import ecommerce.base.ITrueAndFalse;

/***
 * match oxo
 * @author martin
 *
 */
public class GoNext4 implements IGoNext {

	@Override
	public int GetNext(ITrueAndFalse taf) {
		
		List<Boolean> result = taf.getResult();
		int position = taf.getResultPos();
		
		if(position < 3)
			return taf.getMetaPos();
		if(result.get(position-1) && !result.get(position-2) && result.get(position-3))
			return 0;
		
		return taf.getMetaPos();
	}

}
