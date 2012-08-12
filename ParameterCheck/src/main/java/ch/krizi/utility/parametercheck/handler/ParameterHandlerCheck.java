/**
 * 
 */
package ch.krizi.utility.parametercheck.handler;

import ch.krizi.utility.parametercheck.exception.ParameterCheckException;

/**
 * @author krizi
 * 
 */
public interface ParameterHandlerCheck {
	public void check() throws ParameterCheckException;
}
