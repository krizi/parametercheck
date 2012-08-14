/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.nullvalue;

import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.exception.ParameterHandlerException;
import ch.krizi.utility.parametercheck.factory.MethodParameter;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerCheck;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerUpdater;

/**
 * checks if the object is null. if it is null, it will be handled by the
 * annotation-config
 * 
 * 
 * @author krizi
 * 
 */
@Singleton
@Named
public class NullValueParameterHandler implements ParameterHandlerCheck, ParameterHandlerUpdater<Object> {

	private final Logger logger = LoggerFactory.getLogger(NullValueParameterHandler.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.krizi.utility.parametercheck.ParameterHandler#check()
	 */
	@Override
	public void check(final MethodParameter methodParameter) {
		if (logger.isDebugEnabled()) {
			logger.debug("Parameter: {}", methodParameter);
		}
		validateParameter(methodParameter);

		if (methodParameter.getAnnotation(NotNull.class) != null) {
			handleObject(methodParameter.getAnnotation(NotNull.class), methodParameter.getObject(),
					methodParameter.getType());
		}
	}

	@Override
	public Object getUpdatedParameter(final MethodParameter methodParameter) {
		validateParameter(methodParameter);

		return createNewInstance(methodParameter.getAnnotation(NotNull.class), methodParameter.getObject(),
				methodParameter.getType());
	}

	/**
	 * checks if the parameter is acceptable with the annotation
	 */
	protected void validateParameter(final MethodParameter methodParameter) {
		if (methodParameter == null) {
			throw new ParameterHandlerException("Parameter should not be null");
		}

		NotNull annotation = methodParameter.getAnnotation(NotNull.class);
		if (annotation == null) {
			throw new ParameterHandlerException("Annotation should not be null");
		} else if (annotation.handleNull() == null) {
			throw new ParameterHandlerException("HandleNull should not be null");
		}

		if (HandleNull.CreateInstance.equals(annotation.handleNull())) {
			Class<?> objectClass = methodParameter.getType();
			Class<?> newInstance = annotation.newInstance();
			if (objectClass.isInterface()) {
				if (isDefaultAnnotationNewInstance(annotation)) {
					throw new ParameterHandlerException("parameter [" + objectClass
							+ "] is a Interface, but there is no explicit class. "
							+ "see: NotNull(newInstance=ExplicitClass.class)");
				} else if (!objectClass.isAssignableFrom(newInstance)) {
					throw new ParameterHandlerException("combination [parameter=" + objectClass + ", newInstance="
							+ newInstance + "] didnt work");
				}
			}
		}
	}

	private boolean isDefaultAnnotationNewInstance(NotNull notNull) {
		return NotNull.class.equals(notNull.newInstance());
	}

	protected void handleObject(NotNull notNull, Object object, Class<?> clazz) {
		if (object == null) {
			if (HandleNull.ThrowException.equals(notNull.handleNull())) {
				throw new ParameterCheckException(new IllegalArgumentException(notNull.message()));
			}
		}
	}

	protected Object createNewInstance(NotNull notNull, Object object, Class<?> clazz) {
		if (HandleNull.CreateInstance.equals(notNull.handleNull())) {
			try {
				if (isDefaultAnnotationNewInstance(notNull)) {
					return clazz.newInstance();
				} else {
					return notNull.newInstance().newInstance();
				}
			} catch (Exception e) {
				throw new ParameterHandlerException("failed to create new instance", e);
			}
		}
		return null;
	}

}
