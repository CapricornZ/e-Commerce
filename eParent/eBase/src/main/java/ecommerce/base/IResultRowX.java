package ecommerce.base;

import java.util.List;

public interface IResultRowX extends IRow {
	
	List<ITrueAndFalse> getResult();
	boolean isStopValid();
	ISourceRow getSource();
}
