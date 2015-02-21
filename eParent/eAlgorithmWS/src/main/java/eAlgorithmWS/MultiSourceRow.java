package eAlgorithmWS;

import java.util.ArrayList;
import java.util.List;

public class MultiSourceRow extends ISourceRow {
	
	public class SingleFormat {
		
		public boolean match(String row){
			int[] index4 = new int[]{2,3,6,7};
			int[] index5 = new int[]{3,4,8,9};
			int[] count4 = new int[]{0,0};
			int[] count5 = new int[]{0,0};
			
			char[] source = row.toCharArray();
			for(int index:index4)
				count4[source[index]=='A'?0:1]++;
			for(int index:index5)
				count5[source[index]=='A'?0:1]++;
			
			boolean bMatch4 = (count4[0]==3&&count4[1]==1) || (count4[1]==3&&count4[0]==1);
			boolean bMatch5 = (count5[0]==3&&count5[1]==1) || (count5[1]==3&&count5[0]==1);
			
			return !(bMatch4&&bMatch5);
		}
	}


	private String source;
	public MultiSourceRow(String source){
		this.source = source;
	}
	
	@Override public String getRealSource() { return this.source.substring(0, this.source.length()-1); }
	@Override public String getSource() { return this.source; }
	@Override public int getStartOff() { return 0; }
	
	public List<ISourceRow> generate(){
		SingleFormat pattern = new SingleFormat();
		List<ISourceRow> sources = new ArrayList<ISourceRow>();
		for(int i=0; i<source.length()-10; i++){
			String subString = source.substring(i,source.length());
			if(!pattern.match(subString))
				sources.add(new SingleSourceRow(this.source, i));
		}
		return sources;
	}
}
