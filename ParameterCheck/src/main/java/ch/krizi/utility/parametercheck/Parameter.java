/**
 * 
 */
package ch.krizi.utility.parametercheck;

/**
 * @author krizi
 * 
 */
public class Parameter<C, A> {

	private final C object;
	private final A annotation;
	private final Class<C> objectClass;

	public Parameter(Class<C> objectClass, C object, A annotation) {
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
}
