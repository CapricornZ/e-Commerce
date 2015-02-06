package elements;

public interface IElement {
	
	int getLength();
	char nextItem(IElement other);	
	char getPositiveItem(int index);
	char getNegtiveItem(int index);
}
