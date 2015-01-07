package ecommerce.patterns.trueandfalse.stop;

import ecommerce.base.ITrueAndFalse;

public interface IStop {
	boolean match(ITrueAndFalse taf);
}
