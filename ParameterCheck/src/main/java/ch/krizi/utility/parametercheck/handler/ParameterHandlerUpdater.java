/**
 * 
 */
package ch.krizi.utility.parametercheck.handler;

import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.factory.MethodParameter;

/**
 * 
 * @author krizi
 * 
 */
public interface ParameterHandlerUpdater {
	/**
	 * get the new value
	 * 
	 * @return
	 * @throws ParameterCheckException
	 *             throws exception if the paramater is invalid
	 * @throws ParameterHandlerCheck
	 *             throws exception if an error occurs in the handler
	 */
	public Object getUpdatedParameter(final MethodParameter methodParameter);
}
