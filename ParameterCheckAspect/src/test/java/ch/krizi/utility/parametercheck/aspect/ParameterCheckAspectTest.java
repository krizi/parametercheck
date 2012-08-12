/**
 * 
 */
package ch.krizi.utility.parametercheck.aspect;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import mockit.Cascading;
import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.krizi.utility.JoinPointUtils;
import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.factory.ParameterHandlerFactory;
import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;

/**
 * @author krizi
 * 
 */
@RunWith(JMockit.class)
public class ParameterCheckAspectTest {

	@Tested
	private ParameterCheckAspect parameterCheckAspect;

	@Mocked
	@Cascading
	private JoinPoint mockJoinPoint;

	@Mocked
	private JoinPointUtils mockJoinPointUtils;

	@Mocked
	private AbstractParameterHandler<?, ?> mockAbstractParameterHandler;

	@Mocked
	private ParameterCheckException mockParameterCheckException;

	@Mocked
	@Injectable
	private ParameterHandlerFactory mockParameterHandlerFactory;

	@Before
	public void prepare() throws Exception {
	}

	private void setMethodParameter(final List list) throws Exception {
		new NonStrictExpectations() {
			{
				mockJoinPointUtils.createMethodParameter((JoinPoint) any);
				result = list;
			}
		};
	}

	private void setParameterHandler(final List list) {
		new NonStrictExpectations() {
			{
				mockParameterHandlerFactory.createParameterHandler(any, (Class) any, (Annotation[]) any);
				result = list;
			}
		};
	}

	@Test
	public void testWithoutMethodParameters() throws Throwable {
		ArrayList<MethodParameter> list = new ArrayList<MethodParameter>();
		setMethodParameter(list);

		parameterCheckAspect.checkParams(mockJoinPoint);
	}

	@Test
	public void testWithOneValidParameter() throws Throwable {
		List list1 = new ArrayList();
		list1.add(mockAbstractParameterHandler);
		setParameterHandler(list1);

		ArrayList<MethodParameter> list = new ArrayList<MethodParameter>();
		list.add(new MethodParameter("myMethod", List.class, null, null));
		setMethodParameter(list);

		parameterCheckAspect.checkParams(mockJoinPoint);

		// verify logger
	}

	@Test(expected = IllegalArgumentException.class)
	public void testHandlerThrowsParameterException() throws Throwable {
		new NonStrictExpectations() {
			{
				mockParameterCheckException.getCause();
				result = new IllegalArgumentException();

				mockAbstractParameterHandler.check();
				result = mockParameterCheckException;
			}
		};

		List list1 = new ArrayList();
		list1.add(mockAbstractParameterHandler);
		setParameterHandler(list1);

		ArrayList<MethodParameter> list = new ArrayList<MethodParameter>();
		list.add(new MethodParameter("myMethod", List.class, null, null));
		setMethodParameter(list);

		parameterCheckAspect.checkParams(mockJoinPoint);

		// verify logger
	}

}
