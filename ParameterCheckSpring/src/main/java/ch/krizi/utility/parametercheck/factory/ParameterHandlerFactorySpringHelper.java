/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;

/**
 * @author krizi
 * 
 */
public class ParameterHandlerFactorySpringHelper implements ParameterHandlerFactoryHelper, ApplicationContextAware {

	private ApplicationContext applicationContext;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * ch.krizi.utility.parametercheck.factory.ParameterHandlerFactoryHelper
	 * #createParameterHandler(java.lang.Class)
	 */
	@Override
	public AbstractParameterHandler<?, ?> createParameterHandler(
			Class<? extends AbstractParameterHandler<?, ?>> parameterHandlerClass, MethodParameter methodParameter) {
		return (AbstractParameterHandler<?, ?>) applicationContext.getBean(toSpringBean(parameterHandlerClass),
				methodParameter);
	}

	private String toSpringBean(Class<?> clazz) {
		String simpleName = clazz.getSimpleName();
		return simpleName.substring(0, 1).toLowerCase() + simpleName.substring(1, simpleName.length());
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
