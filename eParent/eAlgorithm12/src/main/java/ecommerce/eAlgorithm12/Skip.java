package ecommerce.eAlgorithm12;

import java.util.List;

import ecommerce.base.ISourceRow;

public class Skip {
	
	static private int lengthLimit;
	static public void setLengthLimit(int limit){
		lengthLimit = limit;
	}
	
	/***
	 * 1 长度检查
	 * 2 3:1检查
	 * @param row
	 * @return
	 */
	static public boolean exam(ISourceRow row){
		
		//if(row.getSource().length()<lengthLimit)
		//	return true;
		
		int[] index4 = new int[]{2,3,6,7};
		int[] index5 = new int[]{3,4,8,9};
		int[] count4 = new int[]{0,0};
		int[] count5 = new int[]{0,0};
		
		char[] source = row.getSource().toCharArray();
		for(int index:index4)
			count4[source[index]=='A'?0:1]++;
		for(int index:index5)
			count5[source[index]=='A'?0:1]++;
		
		boolean bMatch4 = (count4[0]==3&&count4[1]==1) || (count4[1]==3&&count4[0]==1);
		boolean bMatch5 = (count5[0]==3&&count5[1]==1) || (count5[1]==3&&count5[0]==1);
		
		return !(bMatch4&&bMatch5);
	}
	
	static public boolean exam(TrueAndFalseEx taf){

		List<List<Boolean>> resultDetail = taf.getResultDetail();
		List<Boolean> c0 = resultDetail.get(0);
		List<Boolean> c1 = resultDetail.get(1);
		
		int[] count = new int[]{0,0};
		for(Boolean val:c0)
			count[val?0:1]++;
		for(Boolean val:c1)
			count[val?0:1]++;
		
		if((count[0]==1&&count[1]==3) || (count[0]==1&&count[1]==3))
			return false;
		return true;
	}
}
