/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import ch.krizi.utility.parametercheck.handler.ParameterHandlerCheck;

/**
 * @author krizi
 * 
 */
public interface ParameterHandlerFactoryHelper {
	public ParameterHandlerCheck createParameterHandler(Class<? extends ParameterHandlerCheck> parameterHandlerClass);
}
