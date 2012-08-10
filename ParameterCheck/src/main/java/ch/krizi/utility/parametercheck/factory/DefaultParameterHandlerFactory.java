/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.annotation.ParameterCheck;
import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerValue;

/**
 * @author krizi
 * 
 */
public class DefaultParameterHandlerFactory implements ParameterHandlerFactory {

	private static final Logger logger = LoggerFactory.getLogger(DefaultParameterHandlerFactory.class);

	@SuppressWarnings("unchecked")
	private static final Class<? extends Annotation>[] IGNORED_ANNOTATIONS = new Class[] { Target.class,
			Retention.class, Documented.class, ParameterCheck.class };

	/**
	 * Use the helper for creating new instances of
	 * {@link AbstractParameterHandler}
	 */
	protected final ParameterHandlerFactoryHelper parameterHandlerFactoryHelper;

	public DefaultParameterHandlerFactory(ParameterHandlerFactoryHelper parameterHandlerFactoryHelper) {
		this.parameterHandlerFactoryHelper = parameterHandlerFactoryHelper;
	}

	@Override
	public List<AbstractParameterHandler<?, ?>> createParameterHandler(Object object, Class<?> objectClass,
			Annotation... annotations) {
		if (logger.isTraceEnabled()) {
			logger.trace("class={}, object={}, annotations={}", new Object[] { objectClass, object, annotations });
		}

		List<AbstractParameterHandler<?, ?>> handlerList = new ArrayList<AbstractParameterHandler<?, ?>>();

		addAnnotationsRekursiv(object, objectClass, handlerList, annotations);

		return handlerList;
	}

	/**
	 * checks if the Annotation should be ignored
	 * 
	 * @param annotationType
	 * @return
	 */
	protected boolean isIgnored(Class<? extends Annotation> annotationType) {
		for (Class<? extends Annotation> a : IGNORED_ANNOTATIONS) {
			if (a.equals(annotationType)) {
				return true;
			}
		}
		return false;
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	protected void addAnnotationsRekursiv(Object object, Class<?> objectClass,
			List<AbstractParameterHandler<?, ?>> handlerList, Annotation... annotations) {
		for (Annotation anno : annotations) {
			Class<? extends Annotation> annotationType = anno.annotationType();
			if (!isIgnored(annotationType)) {
				ParameterCheck parameterCheck = annotationType.getAnnotation(ParameterCheck.class);

				if (logger.isTraceEnabled()) {
					logger.trace("Annotation={}, Annotations={}", anno, parameterCheck);
				}

				if (hasParameterCheckAnnotations(anno)) {
					if (logger.isDebugEnabled()) {
						logger.debug("Annotation has other ParameterCheck Annotation");
					}
					addAnnotationsRekursiv(object, objectClass, handlerList, annotationType.getAnnotations());
				}

				if (annotationType.isAnnotationPresent(ParameterCheck.class)) {
					if (logger.isDebugEnabled()) {
						logger.debug("ParameterCheck={}, ParameterAnnotation={}", parameterCheck, anno);
					}
					Class<? extends AbstractParameterHandler<?, ?>> handlerClass = parameterCheck.value();

					ParameterHandlerValue<?, ?> parameter = new ParameterHandlerValue(objectClass, object, anno);
					try {
						AbstractParameterHandler<?, ?> parameterHandlerInstance = parameterHandlerFactoryHelper
								.createParameterHandler(handlerClass, parameter);

						if (logger.isDebugEnabled()) {
							logger.debug("create new ParameterHandler {}", parameterHandlerInstance.getClass());
						}
						handlerList.add(parameterHandlerInstance);
					} catch (Exception e) {
						if (logger.isErrorEnabled()) {
							logger.error(
									"cant create new instance of {}, ParameterCheck {}, Annotation {}, Exception {}",
									new Object[] { handlerClass, parameterCheck, anno, e });
						}
					}
				} else {
					if (logger.isWarnEnabled()) {
						logger.warn("Parameter-Annotation is wrong [ParameterCheck={}, ParameterAnnotation={}]",
								parameterCheck, anno);
					}
				}
			} else {
				if (logger.isTraceEnabled()) {
					logger.trace("Annotation [{}] will be ignored", annotationType);
				}
			}
		}
	}

	/**
	 * checks if the annotation has other parameterchecks
	 * 
	 * @param annotation
	 * @return
	 */
	private boolean hasParameterCheckAnnotations(Annotation annotation) {
		for (Annotation anno : annotation.annotationType().getAnnotations()) {
			Class<? extends Annotation> annotationType = anno.annotationType();
			if (annotationType.isAnnotationPresent(ParameterCheck.class)) {
				return true;
			}
		}

		return false;
	}
}
