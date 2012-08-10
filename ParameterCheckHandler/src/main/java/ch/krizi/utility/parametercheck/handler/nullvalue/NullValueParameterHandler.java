/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.nullvalue;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.annotation.ParameterHandler;
import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.exception.ParameterHandlerException;
import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerValue;

/**
 * checks if the object is null. if it is null, it will be handled by the
 * annotation-config
 * 
 * 
 * @author krizi
 * 
 */
@ParameterHandler
public class NullValueParameterHandler extends AbstractParameterHandler<Object, NotNull> {

	private static final Logger logger = LoggerFactory.getLogger(NullValueParameterHandler.class);

	public NullValueParameterHandler(ParameterHandlerValue<Object, NotNull> parameter) {
		super(parameter);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.krizi.utility.parametercheck.ParameterHandler#check()
	 */
	@Override
	public Object check() {
		if (logger.isDebugEnabled()) {
			logger.debug("Parameter: {}", parameter);
		}
		Object newInstance = parameter.getObject();
		if (parameter.getAnnotation() != null) {
			newInstance = handleObject(parameter.getAnnotation(), parameter.getObject(), parameter.getObjectClass());
		}
		return newInstance;
	}

	@SuppressWarnings("unchecked")
	protected <T> T handleObject(NotNull notNull, Object object, Class<T> clazz) {
		T instance = (T) object;

		if (instance == null) {
			switch (notNull.handleNull()) {
			case CreateInstance:
				try {
					instance = clazz.newInstance();
				} catch (Exception e) {
					throw new ParameterHandlerException("failed to create new instance", e);
				}
				break;

			case ThrowException:
				throw new ParameterCheckException(new IllegalArgumentException(notNull.message()));
			}
		}
		return instance;
	}

}