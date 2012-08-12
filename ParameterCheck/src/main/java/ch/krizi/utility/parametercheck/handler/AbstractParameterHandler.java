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
public abstract class AbstractParameterHandler<C, A> implements ParameterHandlerCheck {

	protected final MethodParameter methodParameter;

	public AbstractParameterHandler(MethodParameter methodParameter) {
		this.methodParameter = methodParameter;
	}

	public abstract void check() throws ParameterCheckException;
}
