/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import java.util.List;

import ch.krizi.utility.parametercheck.handler.ParameterHandlerCheck;

/**
 * @author krizi
 * 
 */
public interface ParameterHandlerFactory {
	public List<ParameterHandlerCheck> createParameterHandler(MethodParameter methodParameter);
}
