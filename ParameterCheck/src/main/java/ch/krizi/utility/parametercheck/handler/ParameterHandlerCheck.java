/**
 * 
 */
package ch.krizi.utility.parametercheck.handler;

import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.factory.MethodParameter;

/**
 * @author krizi
 * 
 */
public interface ParameterHandlerCheck {
	/**
	 * checks if the parameter is valid.
	 * 
	 * @throws ParameterCheckException
	 */
	public void check(final MethodParameter methodParameter);
}
