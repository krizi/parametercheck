/**
 * 
 */
package ch.krizi.utility.parametercheck.aspect;

import org.junit.Ignore;

import ch.krizi.utility.parametercheck.factory.MethodParameter;
import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerUpdater;

/**
 * @author krizi
 * 
 */
@Ignore
public abstract class TestParameterHandler<C, A> extends AbstractParameterHandler<C, A> implements
		ParameterHandlerUpdater {

	public TestParameterHandler(MethodParameter methodParameter) {
		super(methodParameter);
	}

}
