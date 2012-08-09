/**
 * 
 */
package ch.krizi.utility.parametercheck.annotation;

import java.lang.annotation.Documented;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

/**
 * All ParameterHandler should be marked with this Annotation
 * 
 * @author krizi
 * 
 */
@Documented
@Scope("prototype")
@Component
public @interface ParameterHandler {

}
