/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.nullvalue;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import mockit.UsingMocksAndStubs;
import mockit.integration.junit4.JMockit;
import mockit.integration.logging.Slf4jMocks;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import ch.krizi.utility.parametercheck.exception.ParameterCheckException;
import ch.krizi.utility.parametercheck.exception.ParameterHandlerException;
import ch.krizi.utility.parametercheck.factory.MethodParameter;

/**
 * @author krizi
 * 
 */
@RunWith(JMockit.class)
@UsingMocksAndStubs(Slf4jMocks.class)
public class NullValueParameterHandlerTest {

	@Mocked
	@Injectable
	private MethodParameter mockMethodParameter;
	@Tested
	private NullValueParameterHandler handler;

	@Mocked
	private NotNull mockNotNull;

	@Before
	public void prepare() {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getAnnotation((Class<?>) any);
				result = mockNotNull;
			}
		};
	}

	@Test(expected = ParameterCheckException.class)
	public void throwsParameterCheckExceptionWhenInputNull() {
		expectHandleNull(HandleNull.ThrowException);

		handler.check();
	}

	@Test
	public void returnsNewInsanceWhenInputNull() {
		expectHandleNull(HandleNull.CreateInstance);
		expectNewInstanceClass(ArrayList.class);
		expectedClass(List.class);

		handler.check();

		Object object = handler.getUpdatedParameter();
		Assert.assertNotNull(object);
	}

	@Test(expected = ParameterHandlerException.class)
	public void throwsParameterHandlerExceptionWhenAnnotationIsWrong() {
		expectHandleNull(null);

		handler.check();
	}

	@Test(expected = ParameterHandlerException.class)
	public void throwsParameterHandlerExceptionWhenAnnotationIsNull() {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getAnnotation((Class) any);
				result = null;
			}
		};

		handler.check();
	}

	@Test(expected = ParameterHandlerException.class)
	public void throwsParameterHandlerExceptionWhenNoParameterAvialable() {
		handler = new NullValueParameterHandler(null);
		handler.check();
	}

	@Test(expected = ParameterHandlerException.class)
	public void testFaildToCreateInterface() {
		expectHandleNull(HandleNull.CreateInstance);
		expectNewInstanceClass(NotNull.class);
		Object instance = handler.createNewInstance(mockNotNull, null, List.class);

		Assert.assertNotNull(instance);
	}

	@Test
	public void testSuccessfulCreateNewInstance() {
		expectHandleNull(HandleNull.CreateInstance);
		expectNewInstanceClass(ArrayList.class);

		Object instance = handler.createNewInstance(mockNotNull, null, List.class);

		Assert.assertNotNull(instance);
		Assert.assertTrue(instance instanceof ArrayList);
	}

	@Test(expected = ParameterHandlerException.class)
	public void testWrongCombinationForAnnotation() {
		expectHandleNull(HandleNull.CreateInstance);
		expectNewInstanceClass(HashMap.class);
		expectedClass(List.class);

		handler.getUpdatedParameter();
	}

	@Test
	public void testCreateNewInstanceHandleNullThrowExceptions() {
		expectHandleNull(HandleNull.ThrowException);
		Object instance = handler.createNewInstance(mockNotNull, null, List.class);

		Assert.assertNull(instance);
	}

	@Test
	public void testUpdateedParameterInstanceHandleNullCreate() {
		expectHandleNull(HandleNull.CreateInstance);
		expectedClass(ArrayList.class);
		expectNewInstanceClass(NotNull.class);

		Object instance = handler.getUpdatedParameter();

		Assert.assertNotNull(instance);
	}

	private void expectHandleNull(final HandleNull handleNull) {
		new NonStrictExpectations() {
			{
				mockNotNull.handleNull();
				result = handleNull;
			}
		};
	}

	private void expectNewInstanceClass(final Class<?> clazz) {
		new NonStrictExpectations() {
			{
				mockNotNull.newInstance();
				result = clazz;
			}
		};
	}

	private void expectedClass(final Class clazz) {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getType();
				result = clazz;
			}
		};
	}

}
