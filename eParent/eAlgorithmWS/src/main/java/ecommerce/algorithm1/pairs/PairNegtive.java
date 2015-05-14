package ecommerce.algorithm1.pairs;

public class PairNegtive implements IPair {
	
	private IPair[] pairs;
	public PairNegtive(){
		this.pairs = new IPair[4];
		this.pairs[0] = new PairNegtiveElement(new String[]{"A"});
		this.pairs[1] = new PairNegtiveElement(new String[]{"AA","BB"});
		this.pairs[2] = new PairNegtiveElement(new String[]{"AAA","BBB","ABA","BAB"});
		this.pairs[3] = new PairNegtiveElement(new String[]{"AAAA","BBBB","BBAA","BAAB","AABB","ABBA","ABAB","BABA"});
	}

	@Override
	public boolean pair(String first, String second) {

		return this.pairs[first.length()-1].pair(first, second);
	}

}
