/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.range;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.krizi.utility.parametercheck.annotation.ParameterCheck;
import ch.krizi.utility.parametercheck.handler.nullvalue.NotNull;

/**
 * @author krizi
 * 
 */
@ParameterCheck(RangeParameterHandler.class)
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@NotNull
public @interface Max {
	double value();

	String message() default "number is to high";
}
