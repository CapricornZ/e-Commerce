package elements;

import java.util.List;

public interface IElement {
	
	int getLength();
	void setSkipFlag();
	char nextItem(IElement other);
	char getPositiveItem(int index);
	char getNegtiveItem(int index);
	
	List<eAlgorithmWS.Item> execute(IElement other);
	char[] getSource();
	void append(char value);
}
