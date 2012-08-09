/**
 * 
 */
package ch.krizi.utility.parametercheck.exception;

/**
 * @author krizi
 * 
 */
public class ParameterHandlerException extends RuntimeException {

	private static final long serialVersionUID = 722715686377327418L;

	/**
	 * @param arg0
	 */
	public ParameterHandlerException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 */
	public ParameterHandlerException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	/**
	 * @param arg0
	 * @param arg1
	 */
	public ParameterHandlerException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

}
