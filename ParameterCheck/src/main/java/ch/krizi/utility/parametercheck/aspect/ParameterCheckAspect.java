package ch.krizi.utility.parametercheck.aspect;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
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

	public void setParameterHandlerFactory(ParameterHandlerFactory parameterHandlerFactory) {
		this.parameterHandlerFactory = parameterHandlerFactory;
	}

	private ParameterHandlerFactory parameterHandlerFactory;

	/**
	 * Matches all Annotation wich are annotated with
	 * {@link ch.krizi.utility.parametercheck.annotation.ParameterCheck}
	 */
	@Before("execution(public * *(.., @(@ch.krizi.utility.parametercheck.annotation.ParameterCheck *) (*), ..))")
	public void checkParams(JoinPoint joinPoint) throws Throwable {
		List<MethodParameter> methodParameter = createMethodParameter(joinPoint);

		for (MethodParameter mp : methodParameter) {
			if (logger.isTraceEnabled()) {
				logger.trace("MethodParameter {}", mp);
			}

			List<AbstractParameterHandler<?, ?>> parameterHandler = parameterHandlerFactory.createParameterHandler(
					mp.getObject(), mp.getType(), mp.getAnnotations());

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
	}

	private void updateParameter(JoinPoint joinPoint, MethodParameter methodParameter, Object newInstance) {
		Object[] args = joinPoint.getArgs();

	}

	private Object getObject(MethodParameter methodParameter) {

		// methodParameter.getMethod().

		return null;
	}

	private List<MethodParameter> createMethodParameter(JoinPoint joinPoint) throws Exception {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String[] parameterNames = signature.getParameterNames();
		Class<?>[] parameterTypes = signature.getParameterTypes();
		Object[] args = joinPoint.getArgs();
		String methodName = signature.getMethod().getName();
		Method method = joinPoint.getTarget().getClass().getMethod(methodName, parameterTypes);
		Annotation[][] annotations = method.getParameterAnnotations();

		List<MethodParameter> methodParameterList = new ArrayList<MethodParameter>();
		for (int i = 0; i < parameterNames.length; i++) {
			MethodParameter methodParameter = new MethodParameter(parameterNames[i], parameterTypes[i], args[i],
					annotations[i]);
			methodParameterList.add(methodParameter);
		}

		return methodParameterList;
	}
}
