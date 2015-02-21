package elements.patterns;

import eAlgorithmWS.ITrueAndFalse;

public class GoNext implements IGoNext {

	@Override
	public int GetNext(ITrueAndFalse taf) {
		return taf.getMetaPos();
	}
}
