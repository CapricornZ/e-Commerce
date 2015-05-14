package ecommerce.algorithm1.pairs;

public class PairPositiveElement implements IPair {

	String[] group;
	public PairPositiveElement(String[] group){
		this.group = group;
	}

	@Override
	public boolean pair(String first, String second) {
		
		boolean bFoundFirst = this.foundInGroup(first);
		boolean bFoundSecond = this.foundInGroup(second);
		return (bFoundFirst && bFoundSecond) || !(bFoundFirst || bFoundSecond);
	}
	
	private boolean foundInGroup(String element){
		
		boolean bFound = false;
		for(int i=0; !bFound && i<group.length; i++)
			bFound = element.equals(this.group[i]);
		
		return bFound;
	}

}
