package ecommerce.base.sourcerow;

import ecommerce.base.ISourceRow;

/***
 * 根据Limit，截断输入Source
 * @author martin
 *
 */
public class LimitRow implements ISubRow {
	
	private int limit;
	public void setLimit(int val){
		this.limit = val;
	}
	
	public String subRow(ISourceRow row){
		
		String source = row.getSource();
		return source.length() > this.limit ? source.substring(0, this.limit):source;
	}
}
