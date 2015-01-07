package ecommerce.base;

public enum Decide{
	FALSE('x'),
	TRUE('o'),
	INVALID('_'),
	SKIP(' ');
	
	public char getVal(){
		return this.val;
	}
	
	private char val;
	private Decide(char val){
		this.val = val;
	}
}