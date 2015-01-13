package ecommerce.eAlgorithm11;

public class RelayPosition {
	private int pos0, pos1;
	public RelayPosition(int pos0, int pos1){
		this.pos0 = pos0;
		this.pos1 = pos1;
	}
	public String getIdentity(){
		return String.format("(%d,%d)", this.pos0+1, this.pos1+1);
	}
	
	public int getPos0(){return pos0;}
	public int getPos1(){return pos1;}
}
