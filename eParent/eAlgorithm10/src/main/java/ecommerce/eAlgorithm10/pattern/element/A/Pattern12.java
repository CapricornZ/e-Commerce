package ecommerce.eAlgorithm10.pattern.element.A;

import ecommerce.eAlgorithm10.IExpect;

/***
 * 匹配element的第1,2列
 * @author martin
 *
 */
public class Pattern12 implements IExpect {
	
	static char[] result0 = new char[]{'-','-'};
	static char[] result1 = new char[]{'+','+'};
	static char[] result2 = new char[]{'+','-'};
	static char[] result3 = new char[]{'-','+'};
	
	/***
	 * match
	 * [++] expects [--]
	 * [--] expects [++]
	 * [+-] expects [+-]
	 * [-+] expects [-+]
	 * @param data
	 * @return
	 */
	@Override
	public char[] expects(String data){
		if(data.equals("++"))
			return result0;
		else if(data.equals("--"))
			return result1;
		else if(data.equals("+-"))
			return result2;
		else if(data.equals("-+"))
			return result3;
		else
			return null;
	}
}
