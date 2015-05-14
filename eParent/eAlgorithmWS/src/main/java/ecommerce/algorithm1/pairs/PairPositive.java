package ecommerce.algorithm1.pairs;

public class PairPositive implements IPair {
	
	private IPair[] pairs;
	public PairPositive(){
		this.pairs = new IPair[4];
		this.pairs[0] = new PairPositiveElement(new String[]{"A"});
		this.pairs[1] = new PairPositiveElement(new String[]{"AA","BB"});
		this.pairs[2] = new PairPositiveElement(new String[]{"AAA","BBB","ABA","BAB"});
		this.pairs[3] = new PairPositiveElement(new String[]{"AAAA","BBBB","BBAA","BAAB","AABB","ABBA","ABAB","BABA"});
	}

	@Override
	public boolean pair(String first, String second) {

		return this.pairs[first.length()-1].pair(first, second);
	}

}
