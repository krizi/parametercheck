/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.nullvalue;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.krizi.utility.parametercheck.annotation.ParameterCheck;

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

	String message() default "parameter is null";
}
