/**
 * 
 */
package ch.krizi.utility.parametercheck.nullvalue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.handler.nullvalue.HandleNull;
import ch.krizi.utility.parametercheck.handler.nullvalue.NotNull;

/**
 * @author krizi
 * 
 */
public class NullBean {
	private final Logger logger = LoggerFactory.getLogger(NullBean.class);

	public Object[] throwExceptionWhenNull(Object arg0, @NotNull Object arg1) {
		System.out.println("arg0: " + arg0 + ", arg1:" + arg1);
		Object[] objects = { arg0, arg1 };
		return objects;
	}

	public Object[] twoAnnotatedParameter(@NotNull(handleNull = HandleNull.CreateInstance) Object arg0,
			@NotNull(handleNull = HandleNull.CreateInstance) Object arg1) {
		if (logger.isDebugEnabled()) {
			logger.debug("twoAnnotatedParameter: arg0={}, arg1={}", arg0, arg1);
		}

		return new Object[] { arg0, arg1 };
	}

	public Object[] createInstanceWhenNull(Object arg0, @NotNull(handleNull = HandleNull.CreateInstance) Object arg1) {
		if (logger.isDebugEnabled()) {
			logger.debug("createInstanceWhenNull: arg0={}, arg1={}", arg0, arg1);
		}

		return new Object[] { arg0, arg1 };
	}
}
