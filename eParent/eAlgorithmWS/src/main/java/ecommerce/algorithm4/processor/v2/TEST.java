package ecommerce.algorithm4.processor.v2;

import ecommerce.algorithm1.ITrueAndFalse;
import ecommerce.algorithm1.pairs.IPair;
import ecommerce.algorithm1.pairs.PairNegtive;
import ecommerce.algorithm1.pairs.PairPositive;
import ecommerce.algorithm4.Result;

public class TEST {

	public static void main(String[] args) {
		
		//IPair pairEngine = new PairPositive();
		IPair pairEngine = new PairNegtive();
		ecommerce.algorithm1.SourceRow sourceRow = new ecommerce.algorithm1.SourceRow(
				"BAAAAAAABAABBBABBBAABBBAABAABBAABABBAAAABAABAABBAABBBABBBBAA", pairEngine);
		ITrueAndFalse taf = sourceRow.execute();
		System.out.println(sourceRow.getFormatSource());
		System.out.println(taf.getFormated());
		taf.execute(6, "POSITIVE");
		
		ecommerce.algorithm1.SourceRow sourceRowN = new ecommerce.algorithm1.SourceRow(
				"BAAAAAAABAABBBABBBAABBBAABAABBAABABBAAAABAABAABBAABBBABBBBAA", pairEngine);
		int expect = 0;
		Result finalRes = null;
		String expectPattern = "+++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++";
		ecommerce.algorithm4.processor.v2.IProcessor processor = 
				ecommerce.algorithm4.processor.v2.Start.findProcessor(taf.getSource(), 6, expectPattern.toCharArray());
		if(null != processor){
			expect = processor.execute();
			finalRes = new Result(processor.getProcedure(), processor.getMaxStep(), expect, 'X');
		}
		System.out.println(finalRes.getFormated());
	}

}
