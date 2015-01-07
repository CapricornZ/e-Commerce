package ecommerce.base;

import java.util.List;

public interface IResultRow extends IRow {
	List<ITrueAndFalse> getResult();
}