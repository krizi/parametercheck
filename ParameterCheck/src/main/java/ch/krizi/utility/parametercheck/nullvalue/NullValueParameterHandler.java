/**
 * 
 */
package ch.krizi.utility.parametercheck.nullvalue;

import ch.krizi.utility.parametercheck.AbstractParameterHandler;
import ch.krizi.utility.parametercheck.annotation.ParameterHandler;
import ch.krizi.utility.parametercheck.exception.ParameterHandlerException;
import ch.krizi.utility.parametercheck.nullvalue.annotation.NotNull;

/**
 * checks if the object is null. if it is null, it will be handled by the
 * annotation-config
 * 
 * 
 * @author krizi
 * 
 */
@ParameterHandler
public class NullValueParameterHandler extends AbstractParameterHandler<NotNull> {

	public NullValueParameterHandler(Object object, Class<?> clazz,
			NotNull notNull) {
		super(object, clazz, notNull);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see ch.krizi.utility.parametercheck.ParameterHandler#check()
	 */
	@Override
	public Object check() {
		Object newInstance = object;
		if (annotation != null) {
			newInstance = handleObject(annotation, object, objectClass);
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
					throw new ParameterHandlerException(
							"failed to create new instance", e);
				}
				break;

			case ThrowException:
				throw new IllegalArgumentException(notNull.message());
			}
		}
		return instance;
	}

}
