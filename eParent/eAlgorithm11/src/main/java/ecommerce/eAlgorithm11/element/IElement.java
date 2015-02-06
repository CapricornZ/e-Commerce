package ecommerce.eAlgorithm11.element;

import java.util.List;

public interface IElement {
	List<Boolean> execute(IElement other);
	char[] getSource();
	boolean needSkip(int offSet);
	String getValue(int columnIndex);
	String getHtml(int columnIndex);
}
