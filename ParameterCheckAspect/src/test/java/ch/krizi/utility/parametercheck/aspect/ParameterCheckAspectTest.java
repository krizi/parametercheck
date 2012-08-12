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
import ch.krizi.utility.parametercheck.handler.AbstractParameterHandler;
import ch.krizi.utility.parametercheck.handler.ParameterHandlerUpdater;

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
	private TestParameterHandler<?, ?> mockTestParameterHandler;

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
		List<AbstractParameterHandler<?, ?>> list1 = new ArrayList<AbstractParameterHandler<?, ?>>();
		list1.add(mockAbstractParameterHandler);
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

				mockAbstractParameterHandler.check();
				result = mockParameterCheckException;
			}
		};

		List<AbstractParameterHandler<?, ?>> list1 = new ArrayList<AbstractParameterHandler<?, ?>>();
		list1.add(mockAbstractParameterHandler);
		setParameterHandler(list1);

		ArrayList<MethodParameter> list = new ArrayList<MethodParameter>();
		list.add(new MethodParameter(0, "myMethod", List.class, null));
		setMethodParameter(list);

		parameterCheckAspect.checkParams(mockJoinPoint);

		new Verifications() {
			{
				mockAbstractParameterHandler.check();
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
				mockTestParameterHandler.getUpdatedParameter();
				result = new Object();
			}
		};

		List<AbstractParameterHandler<?, ?>> list1 = new ArrayList<AbstractParameterHandler<?, ?>>();
		list1.add(mockTestParameterHandler);
		list1.add(mockAbstractParameterHandler);
		setParameterHandler(list1);

		ArrayList<MethodParameter> list = new ArrayList<MethodParameter>();
		list.add(new MethodParameter(0, "myMethod", List.class, null));
		setMethodParameter(list);

		parameterCheckAspect.checkParams(mockJoinPoint);

//		new Verifications() {
//			{
//				mockTestParameterHandler.check();
//				times = 1;
//			}
//		};
		new Verifications() {
			{
				mockAbstractParameterHandler.check();
				times = 1;
			}
		};
		new Verifications() {
			{
				mockTestParameterHandler.getUpdatedParameter();
				times = 1;
			}
		};

	}
}
