/**
 * 
 */
package ch.krizi.utility.parametercheck.handler;

import ch.krizi.utility.parametercheck.Parameter;

/**
 * @author krizi
 * 
 */
public abstract class AbstractParameterHandler<C, A> {

	protected final Parameter<C, A> parameter;

	public AbstractParameterHandler(Parameter<C, A> parameter) {
		this.parameter = parameter;
	}

	public abstract Object check();
}
