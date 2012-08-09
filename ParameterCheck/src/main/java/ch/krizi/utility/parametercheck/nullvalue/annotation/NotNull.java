/**
 * 
 */
package ch.krizi.utility.parametercheck.nullvalue.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.krizi.utility.parametercheck.annotation.ParameterCheck;
import ch.krizi.utility.parametercheck.nullvalue.NullValueParameterHandler;

/**
 * @author krizi
 * 
 */
@ParameterCheck(NullValueParameterHandler.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NotNull {
	HandleNull handleNull() default HandleNull.ThrowException;

	String message() default "";
}
