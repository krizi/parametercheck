/**
 * 
 */
package ch.krizi.utility.parametercheck.nullvalue;

import ch.krizi.utility.parametercheck.handler.nullvalue.HandleNull;
import ch.krizi.utility.parametercheck.handler.nullvalue.NotNull;

/**
 * @author krizi
 * 
 */
public class NullBean {

	public Object[] throwExceptionWhenNull(Object arg0, @NotNull Object arg1) {
		Object[] objects = { arg0, arg1 };
		return objects;
	}

	public Object[] twoAnnotatedParameter(@NotNull(handleNull = HandleNull.CreateInstance) Object arg0,
			@NotNull(handleNull = HandleNull.CreateInstance) Object arg1) {
		Object[] objects = { arg0, arg1 };
		return objects;
	}

	public Object[] createInstanceWhenNull(Object arg0, @NotNull(handleNull = HandleNull.CreateInstance) Object arg1) {
		Object[] objects = { arg0, arg1 };
		return objects;
	}
}
