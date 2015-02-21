package eAlgorithmWS;

public class Item {
	
	private int index;
	private char charactor;
	private boolean value;
	private int count;

	@Override
	public String toString() {
		return String.format("{index:%d, charactor:%c*%d, value:%b}", this.index, this.charactor, this.count, this.value);
	};
	
	public Item(int index, char charactor, boolean value){
		this.index = index;
		this.charactor = charactor;
		this.value = value;
	}
	
	public void setIndex(int index){
		this.index = index;
	}
	public int getIndex() {
		return index;
	}
	public char getCharactor() {
		return charactor;
	}
	public boolean getValue() {
		return value;
	}
	
	public void setCount(int value){
		this.count = value;
	}
	public int getCount() {
		return count;
	}
}
