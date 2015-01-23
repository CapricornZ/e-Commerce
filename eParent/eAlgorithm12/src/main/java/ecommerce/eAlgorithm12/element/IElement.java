package ecommerce.eAlgorithm12.element;

import java.util.List;

public interface IElement {
	List<Boolean> execute(IElement other, boolean result2this);
	char[] getSource();
	boolean needSkip(int offSet);
	String getValue(int columnIndex);
	
	void setResult(char[] result);
	char[] getResult();
	void append(char val);
	
}
