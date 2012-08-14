/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.range;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.factory.MethodParameter;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerCheck;

/**
 * @author p105324
 * 
 */
@Singleton
@Named
public class RangeParameterHandler implements ParameterHandlerCheck {
	private final Logger logger = LoggerFactory.getLogger(RangeParameterHandler.class);

	@Override
	public void check(MethodParameter methodParameter) {

		try {
			Number number = (Number) methodParameter.getObject();

			Range range = methodParameter.getAnnotation(Range.class);

			if (number.intValue() < range.min()) {
				throw new ParameterCheckException(new IllegalArgumentException("parameter is to low"));
			} else if (number.intValue() > range.max()) {
				throw new ParameterCheckException(new IllegalArgumentException("parameter is to high"));
			}

		} catch (ClassCastException e) {
			if (logger.isErrorEnabled()) {
				logger.error("MethodParameter [" + methodParameter + "] unsupported", e);
			}
		}
	}
}
