/**
 * 
 */
package ch.krizi.utility.parametercheck.aspect.utils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.factory.MethodParameter;

/**
 * @author krizi
 * 
 */
public class JoinPointUtils {
	private static final Logger logger = LoggerFactory.getLogger(JoinPointUtils.class);

	/**
	 * create MethodParameter for all parameter for the method
	 * 
	 * @param joinPoint
	 * @return
	 * @throws Exception
	 */
	public static List<MethodParameter> createMethodParameter(JoinPoint joinPoint) throws Exception {
		MethodSignature signature = (MethodSignature) joinPoint.getSignature();
		String[] parameterNames = signature.getParameterNames();
		Class<?>[] parameterTypes = signature.getParameterTypes();
		Object[] args = joinPoint.getArgs();
		Method method = signature.getMethod();
		Annotation[][] annotations = method.getParameterAnnotations();

		List<MethodParameter> methodParameterList = new ArrayList<MethodParameter>();
		for (int i = 0; i < parameterNames.length; i++) {
			MethodParameter methodParameter = new MethodParameter(i, parameterNames[i], parameterTypes[i], args[i],
					annotations[i]);
			methodParameterList.add(methodParameter);
		}

		return methodParameterList;
	}

	/**
	 * update a MethodParameter
	 * 
	 * @param joinPoint
	 * @param methodParameter
	 * @param updatedObject
	 */
	public static void updateMethodParameter(JoinPoint joinPoint, MethodParameter methodParameter, Object updatedObject) {
		if (logger.isTraceEnabled()) {
			logger.trace("Arguments before update: {}", Arrays.toString(joinPoint.getArgs()));
		}
		Object[] arguments = joinPoint.getArgs();
		arguments[methodParameter.getParameterIndex()] = updatedObject;
		if (logger.isDebugEnabled()) {
			logger.debug("update Parameter [{}] with this new Object [{}]", methodParameter.getParameterIndex(),
					updatedObject);
		}
		if (logger.isTraceEnabled()) {
			logger.trace("Arguments after update: {}", Arrays.toString(joinPoint.getArgs()));
		}
	}
}
