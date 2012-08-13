/**
 * 
 */
package ch.krizi.utility.parametercheck.aspect;

import java.util.ArrayList;
import java.util.List;

import mockit.Cascading;
import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;

import org.aspectj.lang.JoinPoint;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.krizi.utility.parametercheck.aspect.utils.JoinPointUtils;
import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.factory.MethodParameter;
import ch.krizi.utility.parametercheck.factory.ParameterHandlerFactory;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerCheck;

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
	private ParameterHandlerCheck mockParameterHandler;

	@Mocked
	private TestParameterHandler mockTestParameterHandler;

	@Mocked
	private ParameterCheckException mockParameterCheckException;

	@Mocked
	@Injectable
	private ParameterHandlerFactory mockParameterHandlerFactory;

	@Before
	public void prepare() throws Exception {

	}

	@SuppressWarnings("static-access")
	private void setMethodParameter(final List<?> list) throws Exception {
		new NonStrictExpectations() {
			{
				mockJoinPointUtils.createMethodParameter((JoinPoint) any);
				result = list;
			}
		};
	}

	private void setParameterHandler(final List<?> list) {
		new NonStrictExpectations() {
			{
				mockParameterHandlerFactory.createParameterHandler((MethodParameter) any);
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
		List<ParameterHandlerCheck> list1 = new ArrayList<ParameterHandlerCheck>();
		list1.add(mockParameterHandler);
		setParameterHandler(list1);

		ArrayList<MethodParameter> list = new ArrayList<MethodParameter>();
		list.add(new MethodParameter(0, "myMethod", List.class, null));
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

				mockParameterHandler.check((MethodParameter) any);
				result = mockParameterCheckException;
			}
		};

		List<ParameterHandlerCheck> list1 = new ArrayList<ParameterHandlerCheck>();
		list1.add(mockParameterHandler);
		setParameterHandler(list1);

		ArrayList<MethodParameter> list = new ArrayList<MethodParameter>();
		MethodParameter mp = new MethodParameter(0, "myMethod", List.class, null);
		list.add(mp);
		setMethodParameter(list);

		parameterCheckAspect.checkParams(mockJoinPoint);

		new Verifications() {
			{
				mockParameterHandler.check((MethodParameter) any);
				times = 1;

				mockJoinPointUtils.createMethodParameter((JoinPoint) any);
				times = 1;
			}
		};
	}

	@Test
	public void testParameterUpdater() throws Throwable {
		new NonStrictExpectations() {
			{
				mockTestParameterHandler.getUpdatedParameter((MethodParameter) any);
				result = new Object();
			}
		};

		List<ParameterHandlerCheck> list1 = new ArrayList<ParameterHandlerCheck>();
		list1.add(mockTestParameterHandler);
		list1.add(mockParameterHandler);
		setParameterHandler(list1);

		ArrayList<MethodParameter> list = new ArrayList<MethodParameter>();
		list.add(new MethodParameter(0, "myMethod", List.class, null));
		setMethodParameter(list);

		parameterCheckAspect.checkParams(mockJoinPoint);

		new Verifications() {
			{
				mockTestParameterHandler.getUpdatedParameter((MethodParameter) any);
				times = 1;
			}
		};

	}
}
