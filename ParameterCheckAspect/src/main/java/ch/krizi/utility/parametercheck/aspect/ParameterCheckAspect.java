package ch.krizi.utility.parametercheck.aspect;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.aspect.utils.JoinPointUtils;
import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.factory.MethodParameter;
import ch.krizi.utility.parametercheck.factory.ParameterHandlerFactory;
import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;

/**
 * Matches all Annotation wich are annotated with
 * {@link ch.krizi.utility.parametercheck.annotation.ParameterCheck}
 * 
 * 
 * @author krizi
 * 
 */
@Aspect
public class ParameterCheckAspect {

	private static final Logger logger = LoggerFactory.getLogger(ParameterCheckAspect.class);

	private ParameterHandlerFactory parameterHandlerFactory;

	/**
	 * Matches all Annotation wich are annotated with
	 * {@link ch.krizi.utility.parametercheck.annotation.ParameterCheck}
	 */
	@Before("execution(public * *(.., @(@ch.krizi.utility.parametercheck.annotation.ParameterCheck *) (*), ..))")
	public void checkParams(JoinPoint joinPoint) throws Throwable {
		List<MethodParameter> methodParameter = JoinPointUtils.createMethodParameter(joinPoint);

		if (!CollectionUtils.isEmpty(methodParameter)) {
			for (MethodParameter mp : methodParameter) {
				if (logger.isTraceEnabled()) {
					logger.trace("MethodParameter {}", mp);
				}

				List<AbstractParameterHandler<?, ?>> parameterHandler = parameterHandlerFactory
						.createParameterHandler(mp);

				if (logger.isDebugEnabled()) {
					logger.debug("Parameter [{}] will be handled by these ParameterHandler [{}] ", new Object[] { mp,
							parameterHandler.toArray() });
				}

				for (AbstractParameterHandler<?, ?> aph : parameterHandler) {
					try {
						aph.check();

						// update parameter
					} catch (ParameterCheckException e) {
						throw e.getCause();
					} catch (Exception e) {
						if (logger.isErrorEnabled()) {
							logger.error("error while handling parameter", e);
						}
					}
				}
			}
		} else {
			if (logger.isWarnEnabled()) {
				logger.warn("there are no parameters... Aspect should not catch");
			}
		}
	}

	private void updateParameter(JoinPoint joinPoint, MethodParameter methodParameter, Object newInstance) {
		Object[] args = joinPoint.getArgs();

	}

	private Object getObject(MethodParameter methodParameter) {

		// methodParameter.getMethod().

		return null;
	}

	public void setParameterHandlerFactory(ParameterHandlerFactory parameterHandlerFactory) {
		this.parameterHandlerFactory = parameterHandlerFactory;
	}
}
