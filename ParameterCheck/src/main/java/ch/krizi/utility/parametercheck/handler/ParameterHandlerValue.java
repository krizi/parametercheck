/**
 * 
 */
package ch.krizi.utility.parametercheck.handler;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author krizi
 * 
 */
public class ParameterHandlerValue<C, A> {

	private final C object;
	private final A annotation;
	private final Class<C> objectClass;

	public ParameterHandlerValue(Class<C> objectClass, C object, A annotation) {
		this.object = object;
		this.annotation = annotation;
		this.objectClass = objectClass;
	}

	public C getObject() {
		return object;
	}

	public A getAnnotation() {
		return annotation;
	}

	public Class<C> getObjectClass() {
		return objectClass;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("class", objectClass);
		builder.append("object", object);
		builder.append("isNull", object == null);
		builder.append("annotation", annotation);
		return builder.toString();
	}
}
