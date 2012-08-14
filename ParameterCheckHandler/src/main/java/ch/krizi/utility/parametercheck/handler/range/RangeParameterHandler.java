/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.range;

import java.math.BigDecimal;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.exception.ParameterHandlerException;
import ch.krizi.utility.parametercheck.factory.MethodParameter;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerCheck;

/**
 * @author krizi
 * 
 */
@Singleton
@Named
public class RangeParameterHandler implements ParameterHandlerCheck {
	private final Logger logger = LoggerFactory.getLogger(RangeParameterHandler.class);

	@Override
	public void check(MethodParameter methodParameter) {
		try {
			Min min = methodParameter.getAnnotation(Min.class);
			Max max = methodParameter.getAnnotation(Max.class);
			BigDecimal number = toBigDecimal((Number) methodParameter.getObject());

			if (min != null) {
				check(min, number);
			}
			if (max != null) {
				check(max, number);
			}
		} catch (ClassCastException e) {
			if (logger.isErrorEnabled()) {
				logger.error("MethodParameter [" + methodParameter + "] unsupported", e);
			}
			throw new ParameterHandlerException("class unsupported", e);
		}
	}

	private void check(Min min, BigDecimal number) {
		double minDouble = min.value();

		if (number.doubleValue() < minDouble) {
			IllegalArgumentException e = new IllegalArgumentException(min.message());
			throw new ParameterCheckException(e);
		}
	}

	private void check(Max max, BigDecimal number) {
		double maxDouble = max.value();

		if (number.doubleValue() > maxDouble) {
			IllegalArgumentException e = new IllegalArgumentException(max.message());
			throw new ParameterCheckException(e);
		}
	}

	private BigDecimal toBigDecimal(Number number) {
		return NumberUtils.createBigDecimal(number.toString());
	}
}
