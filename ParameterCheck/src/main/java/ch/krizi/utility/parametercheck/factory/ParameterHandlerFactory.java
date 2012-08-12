/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import java.util.List;

import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;

/**
 * @author krizi
 * 
 */
public interface ParameterHandlerFactory {
	public List<AbstractParameterHandler<?, ?>> createParameterHandler(MethodParameter methodParameter);
}
