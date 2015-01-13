package ecommerce.eAlgorithm11.element;

import java.util.List;

public interface IElementBuilder {
	List<IElement> createElement(String source);
	int getLength();
}
