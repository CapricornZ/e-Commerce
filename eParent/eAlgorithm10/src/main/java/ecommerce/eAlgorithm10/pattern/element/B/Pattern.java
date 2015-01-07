package ecommerce.eAlgorithm10.pattern.element.B;

import ecommerce.eAlgorithm10.IExpect;

public class Pattern implements IExpect {
	
	static char[] result0 = new char[]{'-','-'};
	static char[] result1 = new char[]{'+','+'};

	/***
	 * match
	 * [oo+] expects [++]
	 * [oo-] expects [--]
	 * [xo+] expects [--]
	 * [xo-] expects [++]
	 * [ox+] expects [--]
	 * [ox-] expects [++]
	 * [xx+] expects [--]
	 * [xx-] expects [++]
	 * @param data
	 * @return
	 */
	@Override
	public char[] expects(String data) {
		
		if(data.equals("oo+") || data.equals("xo-") || data.equals("ox-") || data.equals("xx-"))
			return result1;
		else if(data.equals("oo-") || data.equals("xo+") || data.equals("ox+") || data.equals("xx+"))
			return result0;
		
		return null;
	}

}
