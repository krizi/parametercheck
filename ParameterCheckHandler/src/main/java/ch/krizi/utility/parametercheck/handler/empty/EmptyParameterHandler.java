/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.empty;

import java.util.Collection;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.annotation.ParameterHandler;
import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerValue;

/**
 * checks Collection & Map if is empty
 * 
 * @author krizi
 * 
 */
@ParameterHandler
public class EmptyParameterHandler extends AbstractParameterHandler<Object, NotEmpty> {

	private static final Logger logger = LoggerFactory.getLogger(EmptyParameterHandler.class);

	public EmptyParameterHandler(ParameterHandlerValue<Object, NotEmpty> parameter) {
		super(parameter);
	}

	@Override
	public void check() throws ParameterCheckException {
		if (logger.isDebugEnabled()) {
			logger.debug("Parameter: {}", parameter);
		}

		check(parameter.getObjectClass(), parameter.getObject(), parameter.getAnnotation());
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
		} else {
			if (logger.isWarnEnabled()) {
				logger.warn("Class [{}] is not supported", clazz);
			}
		}
	}

	private void throwException(NotEmpty notEmpty) {
		IllegalArgumentException e = new IllegalArgumentException(notEmpty.message());
		throw new ParameterCheckException(e);
	}

}
