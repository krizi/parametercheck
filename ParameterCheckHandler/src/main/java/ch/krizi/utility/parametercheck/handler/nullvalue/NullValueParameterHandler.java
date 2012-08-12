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
import ch.krizi.utility.parametercheck.handler.ParameterHandlerUpdater;
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
public class NullValueParameterHandler extends AbstractParameterHandler<Object, NotNull> implements
		ParameterHandlerUpdater {

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
	public void check() throws ParameterCheckException {
		if (logger.isDebugEnabled()) {
			logger.debug("Parameter: {}", parameter);
		}
		validateParameter();

		if (parameter.getAnnotation() != null) {
			handleObject(parameter.getAnnotation(), parameter.getObject(), parameter.getObjectClass());
		}
	}

	@Override
	public Object getUpdatedParameter() {
		validateParameter();

		return createNewInstance(parameter.getAnnotation(), parameter.getObject(), parameter.getObjectClass());
	}

	/**
	 * checks if the parameter is acceptable with the annotation
	 */
	protected void validateParameter() {
		if (parameter == null) {
			throw new ParameterHandlerException("Parameter should not be null");
		}

		NotNull annotation = parameter.getAnnotation();
		if (annotation == null) {
			throw new ParameterHandlerException("Annotation should not be null");
		} else if (annotation.handleNull() == null) {
			throw new ParameterHandlerException("HandleNull should not be null");
		}

		if (HandleNull.CreateInstance.equals(annotation.handleNull())) {
			Class<Object> objectClass = parameter.getObjectClass();
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
