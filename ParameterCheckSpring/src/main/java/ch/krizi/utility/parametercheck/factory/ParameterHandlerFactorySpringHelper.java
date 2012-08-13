/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import ch.krizi.utility.parametercheck.handler.ParameterHandlerCheck;

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
	public ParameterHandlerCheck createParameterHandler(Class<? extends ParameterHandlerCheck> parameterHandlerClass) {
		return applicationContext.getBean(parameterHandlerClass);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) {
		this.applicationContext = applicationContext;
	}
}
