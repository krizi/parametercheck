/**
 * 
 */
package ch.krizi.utility.parametercheck;

/**
 * @author krizi
 * 
 */
public abstract class AbstractParameterHandler<A> {

	protected final Object object;
	protected final A annotation;
	protected final Class<?> objectClass;

	public AbstractParameterHandler(Object object, Class<?> clazz, A annotation) {
		this.object = object;
		this.annotation = annotation;
		this.objectClass = clazz;
	}

	public abstract Object check();
}
