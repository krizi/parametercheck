/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.empty;

import java.util.Collection;
import java.util.Map;

import javax.inject.Named;
import javax.inject.Singleton;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.exception.ParameterHandlerException;
import ch.krizi.utility.parametercheck.factory.MethodParameter;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerCheck;

/**
 * checks Collection & Map if is empty
 * 
 * @author krizi
 * 
 */
@Named
@Singleton
public class EmptyParameterHandler implements ParameterHandlerCheck {

	private final Logger logger = LoggerFactory.getLogger(EmptyParameterHandler.class);

	@Override
	public void check(final MethodParameter methodParameter) {
		if (logger.isDebugEnabled()) {
			logger.debug("Parameter: {}", methodParameter);
		}
		NotEmpty annotation = methodParameter.getAnnotation(NotEmpty.class);
		validateAnnotation(annotation);
		check(methodParameter.getType(), methodParameter.getObject(), annotation);
	}

	private void validateAnnotation(NotEmpty notEmpty) {
		if (notEmpty == null) {
			throw new ParameterHandlerException("annotation is null");
		}
	}

	protected void check(Class<?> clazz, Object object, NotEmpty notEmpty) {
		if (Collection.class.isAssignableFrom(clazz)) {
			Collection<?> collection = (Collection<?>) object;
			if (CollectionUtils.isEmpty(collection)) {
				throwException(notEmpty);
			}
		} else if (Map.class.isAssignableFrom(clazz)) {
			Map<?, ?> map = (Map<?, ?>) object;
			if (MapUtils.isEmpty(map)) {
				throwException(notEmpty);
			}
		} else if (String.class.isAssignableFrom(clazz)) {
			String string = (String) object;
			if (StringUtils.isEmpty(string)) {
				throwException(notEmpty);
			}
		} else {
			if (logger.isWarnEnabled()) {
				logger.warn("Class [{}] is not supported", clazz);
			}
			throw new ParameterHandlerException("Class [" + clazz + "] is not supported");
		}
	}

	private void throwException(NotEmpty notEmpty) {
		IllegalArgumentException e = new IllegalArgumentException(notEmpty.message());
		throw new ParameterCheckException(e);
	}

}
