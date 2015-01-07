package ecommerce.patterns.trueandfalse.alg8;

import java.util.List;

import ecommerce.patterns.trueandfalse.gonext.INext;

public class Next implements INext {

	private static INext[] nexts;
	public void setNexts(INext[] vals){
		nexts = vals;
	}
	
	@Override
	public boolean go2First(List<Boolean> result, int length, int current) {
		
		boolean go2First = false;
		for(int i=0; i<nexts.length && !go2First; i++){
			go2First = nexts[i].go2First(result, length, current);
		}
		return go2First;
	}
}
