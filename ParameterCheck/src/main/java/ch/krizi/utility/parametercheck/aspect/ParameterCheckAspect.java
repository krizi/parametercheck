/**
 * 
 */
package ch.krizi.utility.parametercheck.aspect;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.MethodParameter;

import ch.krizi.utility.parametercheck.AbstractParameterHandler;
import ch.krizi.utility.parametercheck.exception.ParameterHandlerException;
import ch.krizi.utility.parametercheck.factory.ParameterHandlerFactory;

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

	private static final Logger logger = LoggerFactory
			.getLogger(ParameterCheckAspect.class);

	public void setParameterHandlerFactory(
			ParameterHandlerFactory parameterHandlerFactory) {
		this.parameterHandlerFactory = parameterHandlerFactory;
	}

	private ParameterHandlerFactory parameterHandlerFactory;

	/**
	 * Matches all Annotation wich are annotated with
	 * {@link ch.krizi.utility.parametercheck.annotation.ParameterCheck}
	 */
	private static final String ANNOTATION = "@(@ch.krizi.utility.parametercheck.annotation.ParameterCheck *)";

	@Before("execution(public * *(.., " + ANNOTATION + " (*), ..))")
	public void checkParams(JoinPoint joinPoint) throws Throwable {
		List<MethodParameter> methodParameter = getMethodParameters(joinPoint);

		for (MethodParameter mp : methodParameter) {
			if (logger.isDebugEnabled()) {
				logger.debug("Type {}, Annotation {}", mp.getParameterType(),
						mp.getParameterAnnotations());
			}

			List<AbstractParameterHandler<?>> parameterHandler = parameterHandlerFactory
					.createParameterHandler(getObject(mp),
							mp.getParameterType(), mp.getParameterAnnotations());

			for (AbstractParameterHandler<?> aph : parameterHandler) {
				try {
					aph.check();
					
					// update parameter
					
				} catch (ParameterHandlerException e) {
					if (logger.isErrorEnabled()) {
						logger.error("error while handling parameter", e);
					}
				}
			}
		}
	}

	private void updateParameter(JoinPoint joinPoint,
			MethodParameter methodParameter, Object newInstance) {
		Object[] args = joinPoint.getArgs();

	}

	private Object getObject(MethodParameter methodParameter) {
		return null;
	}

	private List<MethodParameter> getMethodParameters(JoinPoint joinPoint)
			throws Exception {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String methodName = signature.getMethod().getName();
		Class<?>[] parameterTypes = signature.getMethod().getParameterTypes();
		Method method = joinPoint.getTarget().getClass()
				.getMethod(methodName, parameterTypes);

		int paramIndex = 0;
		List<MethodParameter> methodParameter = new ArrayList<MethodParameter>();
		for (Class<?> pt : parameterTypes) {
			methodParameter.add(new MethodParameter(method, paramIndex));
			paramIndex = paramIndex + 1;
		}

		return methodParameter;
	}
}
