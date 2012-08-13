/**
 * 
 */
package ch.krizi.utility.parametercheck.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import ch.krizi.utility.parametercheck.handler.ParameterHandlerCheck;

/**
 * Marker-Annotation for other Annotation, wich should be handled.
 * 
 * @author krizi
 * 
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.ANNOTATION_TYPE)
public @interface ParameterCheck {
	Class<? extends ParameterHandlerCheck> value();
}
