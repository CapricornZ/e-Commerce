package ecommerce.algorithm4.perterns.trueandfalse;

import java.util.List;

public class Next {

	private static INext[] nexts;
	static{
		nexts = new INext[]{new Next1(), new Next2(), new Next3()};
	}
	
	static public boolean go2First(List<Boolean> result, int length, int current) {
		
		boolean go2First = false;
		for(int i=0; i<nexts.length && !go2First; i++)
			go2First = nexts[i].go2First(result, length, current);
		return go2First;
	}

}
