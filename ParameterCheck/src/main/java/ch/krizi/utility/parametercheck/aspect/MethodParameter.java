/**
 * 
 */
package ch.krizi.utility.parametercheck.aspect;

import java.lang.annotation.Annotation;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * @author krizi
 * 
 */
public class MethodParameter {
	private final String name;
	private final Class<?> type;
	private final Object object;
	private final Annotation[] annotations;

	public MethodParameter(String name, Class<?> type, Object object, Annotation[] annotations) {
		this.name = name;
		this.type = type;
		this.object = object;
		this.annotations = annotations;
	}

	public String getName() {
		return name;
	}

	public Class<?> getType() {
		return type;
	}

	public Object getObject() {
		return object;
	}

	public Annotation[] getAnnotations() {
		return annotations;
	}

	@Override
	public String toString() {
		ToStringBuilder builder = new ToStringBuilder(this);
		builder.append("name", name);
		builder.append("class", type);
		builder.append("object", object);
		builder.append("isNull", object == null);
		builder.append("annotations", annotations);
		return builder.toString();
	}

}
