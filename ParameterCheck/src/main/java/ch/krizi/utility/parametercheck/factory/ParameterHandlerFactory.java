/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.AbstractParameterHandler;
import ch.krizi.utility.parametercheck.annotation.ParameterCheck;

/**
 * @author krizi
 * 
 */
public class ParameterHandlerFactory {

	private static final Logger logger = LoggerFactory
			.getLogger(ParameterHandlerFactory.class);

	/**
	 * Use the helper for creating new instances of
	 * {@link AbstractParameterHandler}
	 */
	private ParameterHandlerFactoryHelper parameterHandlerFactoryHelper;

	public List<AbstractParameterHandler<?>> createParameterHandler(
			Object object, Class<?> objectClass, Annotation... annotations) {
		List<AbstractParameterHandler<?>> handlerList = new ArrayList<AbstractParameterHandler<?>>();

		for (Annotation anno : annotations) {
			Class<? extends Annotation> annotationType = anno.annotationType();
			ParameterCheck parameterCheck = annotationType
					.getAnnotation(ParameterCheck.class);

			if (logger.isTraceEnabled()) {
				logger.trace("Annotation={}, Annotations={}", anno,
						parameterCheck);
			}

			if (annotationType.isAnnotationPresent(ParameterCheck.class)) {
				if (logger.isDebugEnabled()) {
					logger.debug("ParameterCheck={}, ParameterAnnotation={}",
							parameterCheck, anno);
				}
				Class<? extends AbstractParameterHandler<?>> handlerClass = parameterCheck
						.value();
				AbstractParameterHandler<?> parameterHandlerInstance = parameterHandlerFactoryHelper
						.createParameterHandler(handlerClass, object,
								objectClass, anno);

				if (logger.isDebugEnabled()) {
					logger.debug("create new ParameterHandler {}",
							parameterHandlerInstance.getClass());
				}
				handlerList.add(parameterHandlerInstance);
			} else {
				if (logger.isWarnEnabled()) {
					logger.warn(
							"Parameter-Annotation is wrong [ParameterCheck={}, ParameterAnnotation={}]",
							parameterCheck, anno);
				}
			}
		}

		return handlerList;
	}

	public void setParameterHandlerFactoryHelper(
			ParameterHandlerFactoryHelper parameterHandlerFactoryHelper) {
		this.parameterHandlerFactoryHelper = parameterHandlerFactoryHelper;
	}

	/**
	 * checks if an Annotation is available
	 * 
	 * @param searchedAnnotation
	 * @param allParameterAnnotations
	 * @return
	 */
	public boolean isAnnotationAvailable(Class<?> searchedAnnotation,
			Annotation[] allParameterAnnotations) {
		return getAnnotation(searchedAnnotation, allParameterAnnotations) != null;
	}

	/**
	 * 
	 * @param searchedAnnotation
	 * @param allParameterAnnotations
	 * @return
	 */
	public <A> A getAnnotation(Class<A> searchedAnnotation,
			Annotation[] allParameterAnnotations) {
		return null;
	}

}
