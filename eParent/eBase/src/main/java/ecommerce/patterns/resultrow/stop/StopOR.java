package ecommerce.patterns.resultrow.stop;

import java.util.List;

public class StopOR implements IStop {
	
	private static IStop[] stops;
	public void setStops(IStop[] vals){
		stops = vals;
	}

	@Override
	public boolean match(List<List<Boolean>> taf) {
		
		boolean bStop = false;
		for(int i=0; !bStop && i<stops.length; i++)
			bStop = stops[i].match(taf);
		return bStop;
	}
}
