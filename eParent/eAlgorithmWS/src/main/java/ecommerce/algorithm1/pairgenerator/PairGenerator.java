package ecommerce.algorithm1.pairgenerator;

import ecommerce.algorithm1.pairs.IPair;
import ecommerce.algorithm1.pairs.PairInstance;

public class PairGenerator implements IPairGenerator {

	public IPair generate(String source) {
		
		String[] array = source.split(",");
		return new PairInstance(new String[]{array[0]+array[1], array[1]+array[0]});
	}
}
