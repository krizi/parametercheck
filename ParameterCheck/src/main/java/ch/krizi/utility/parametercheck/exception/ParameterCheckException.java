/**
 * 
 */
package ch.krizi.utility.parametercheck.exception;

/**
 * @author krizi
 * 
 */
public class ParameterCheckException extends RuntimeException {

	private static final long serialVersionUID = -6464328885403898237L;

	/**
	 * @param arg0
	 */
	public ParameterCheckException(Throwable arg0) {
		super(arg0);
	}

}
