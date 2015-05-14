package ecommerce.algorithm4.perterns;

public class Pattern1 {

	private static Pattern1[] PATTERNS;
	static {
		PATTERNS = new Pattern1[2];
		PATTERNS[0] = new Pattern1(new String[] { "AAAA", "AABB", "ABAB",
				"ABBA", "BAAB", "BABA", "BBAA", "BBBB" }, 2);
		PATTERNS[1] = new Pattern1(new String[] { "AAAB", "AABA", "ABAA",
				"ABBB", "BAAA", "BABB", "BBAB", "BBBA" }, 3);
	}

	public static int execute(String value) {
		int result = PATTERNS[0].pair(value);
		return result > 0 ? result : PATTERNS[1].pair(value);
	}

	private String[] patterns;
	private int result;

	public Pattern1(String[] patterns, int result) {
		this.patterns = patterns;
		this.result = result;
	}

	public int pair(String value) {

		int rtn = 0;
		boolean bFound = false;
		for (int i = 0; i < this.patterns.length && !bFound; i++)
			bFound = this.patterns[i].equals(value);
		if (bFound)
			rtn = this.result;
		return rtn;
	}

}
