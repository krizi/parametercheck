/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.lang.annotation.Annotation;
import java.lang.annotation.Documented;
import java.util.ArrayList;
import java.util.List;

import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;
import ch.krizi.utility.testutils.LoggerTestUtils;

/**
 * @author krizi
 * 
 */
@RunWith(JMockit.class)
public class DefaultParameterHandlerFactoryTest {

	@Mocked
	@Injectable
	private ParameterHandlerFactoryHelper helper;

	@Mocked
	private Annotation annotation;

	@Mocked
	private Logger logger;

	private LoggerTestUtils loggerTestUtils;

	@Mocked
	private LoggerFactory loggerFactory;

	@Tested
	private DefaultParameterHandlerFactory factory;

	@Before
	public void prepare() {
		loggerTestUtils = new LoggerTestUtils(logger);

		new NonStrictExpectations() {
			{
				LoggerFactory.getLogger((Class) any);
				result = logger;
			}
		};
	}

	@Test
	public void testIgnoreAnnotationCreateParameterHandler() {
		loggerTestUtils.setLoggerTraceEnabled();
		new NonStrictExpectations() {
			{
				annotation.annotationType();
				result = Documented.class;
			}
		};

		List<AbstractParameterHandler<?, ?>> parameterHandler = factory.createParameterHandler(new ArrayList(),
				List.class, annotation);

		assertThat(parameterHandler, notNullValue());

		loggerTestUtils.verifyLoggerTraceWithParams("class={}, object={}, annotations={}");
		loggerTestUtils.verifyLoggerTrace("Annotation ignored...");
		loggerTestUtils.verifyLoggerTraceWithObject("Annotation [{}] will be ignored");
	}

	@Test
	public void testRekursivAnnotationsCreateParameterHandler() {
		loggerTestUtils.setLoggerTraceEnabled();
		loggerTestUtils.setLoggerDebugEnabled();
		loggerTestUtils.setLoggerWarnEnabled();
		new NonStrictExpectations() {
			{
				annotation.annotationType();
				result = TestAnnotation.class;
			}
		};

		try {
			factory.createParameterHandler(new ArrayList(), List.class, annotation);
		} catch (Throwable e) {
			e.printStackTrace();
		}

		loggerTestUtils.verifyLoggerTraceWithParams("class={}, object={}, annotations={}");
		loggerTestUtils.verifyLoggerTraceWithParams("Annotation={}, ParameterCheck={}");
		loggerTestUtils.verifyLoggerWarnWithParams("Parameter-Annotation is wrong "
				+ "[ParameterCheck={}, ParameterAnnotation={}]");
	}
}
