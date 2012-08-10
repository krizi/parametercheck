/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import ch.krizi.utility.parametercheck.Parameter;
import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;

/**
 * @author krizi
 * 
 */
public interface ParameterHandlerFactoryHelper {
	public AbstractParameterHandler<?, ?> createParameterHandler(
			Class<? extends AbstractParameterHandler<?, ?>> parameterHandlerClass, Parameter<?, ?> parameter);
}
