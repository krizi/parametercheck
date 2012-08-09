/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import java.lang.annotation.Annotation;

import ch.krizi.utility.parametercheck.AbstractParameterHandler;

/**
 * @author krizi
 * 
 */
public interface ParameterHandlerFactoryHelper {
	public AbstractParameterHandler<?> createParameterHandler(
			Class<? extends AbstractParameterHandler<?>> parameterHandlerClass,
			Object parameter, Class<?> parameterClass, Annotation annotation);
}
