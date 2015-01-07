package ecommerce.patterns.trueandfalse;

import ecommerce.base.ITrueAndFalse;
import ecommerce.patterns.trueandfalse.gonext.IGoNext;

public class GoNextOR implements IGoNext {

	public void setGoNext(IGoNext[] goNexts){
		GoNextOR.goNexts = goNexts;
	}
	private static IGoNext[] goNexts;
	
	@Override
	public int GetNext(ITrueAndFalse taf) {
		
		boolean bStop = false;
		int rtn = 0;
		for(int i=0; i<goNexts.length && !bStop; i++){
			rtn = goNexts[i].GetNext(taf);
			bStop = rtn==0;
		}
		return rtn;
	}
}
