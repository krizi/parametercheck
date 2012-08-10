/**
 * 
 */
package ch.krizi.utility.parametercheck.handler;

import ch.krizi.utility.parametercheck.exception.ParameterCheckException;

/**
 * @author krizi
 * 
 */
public abstract class AbstractParameterHandler<C, A> {

	protected final ParameterHandlerValue<C, A> parameter;

	public AbstractParameterHandler(ParameterHandlerValue<C, A> parameter) {
		this.parameter = parameter;
	}

	public abstract Object check() throws ParameterCheckException;
}
