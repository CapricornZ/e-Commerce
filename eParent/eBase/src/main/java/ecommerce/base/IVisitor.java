package ecommerce.base;

import ecommerce.base.ISourceRow;

public interface IVisitor {
	boolean filter(ISourceRow row);
}
