/**
 * 
 */
package ch.krizi.utility.testutils;

import org.junit.Ignore;
import org.slf4j.Logger;

import mockit.NonStrictExpectations;
import mockit.Verifications;

/**
 * @author krizi
 * 
 */
@Ignore
public class LoggerTestUtils {
	private final Logger logger;

	public LoggerTestUtils(Logger logger) {
		this.logger = logger;
	}

	public void setLoggerTraceEnabled() {
		new NonStrictExpectations() {
			{
				logger.isTraceEnabled();
				result = true;
			}
		};
	}

	public void setLoggerDebugEnabled() {
		new NonStrictExpectations() {
			{
				logger.isDebugEnabled();
				result = true;
			}
		};
	}

	public void setLoggerWarnEnabled() {
		new NonStrictExpectations() {
			{
				logger.isWarnEnabled();
				result = true;
			}
		};
	}

	public void verifyLoggerTraceWithParams(final String message) {
		new Verifications() {
			{
				logger.trace(message, (Object[]) any);
			}
		};
	}

	public void verifyLoggerTraceWithObject(final String message) {
		new Verifications() {
			{
				logger.trace(message, any);
			}
		};
	}

	public void verifyLoggerTrace(final String message) {
		new Verifications() {
			{
				logger.trace(message);
			}
		};
	}

	public void verifyLoggerWarnWithParams(final String message) {
		new Verifications() {
			{
				logger.warn(message, (Object[]) any);
			}
		};
	}

	public void verifyLoggerWarnWithParams(final String message, final Object[] params) {
		new Verifications() {
			{
				logger.warn(message, params);
			}
		};
	}

}
