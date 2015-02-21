package elements.patterns;

import java.util.List;

import eAlgorithmWS.ITrueAndFalse;
import eAlgorithmWS.Item;

public class StopWhileTrue implements IStop {

	@Override
	public boolean match(ITrueAndFalse taf) {
		
		List<Item> source = taf.getSource();
		int pos = taf.getSourcePos() - 1;
		return source.get(pos).getValue();
	}

}
