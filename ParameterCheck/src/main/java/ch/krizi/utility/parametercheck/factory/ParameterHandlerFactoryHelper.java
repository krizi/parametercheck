/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerValue;

/**
 * @author krizi
 * 
 */
public interface ParameterHandlerFactoryHelper {
	public AbstractParameterHandler<?, ?> createParameterHandler(
			Class<? extends AbstractParameterHandler<?, ?>> parameterHandlerClass, ParameterHandlerValue<?, ?> parameter);
}
