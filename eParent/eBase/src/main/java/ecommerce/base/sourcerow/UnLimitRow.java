package ecommerce.base.sourcerow;

import ecommerce.base.ISourceRow;

/***
 * 不截断输入Source
 * @author martin
 *
 */
public class UnLimitRow implements ISubRow {

	@Override
	public String subRow(ISourceRow row) {
		return row.getSource();
	}
}
