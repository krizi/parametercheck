/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.empty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import mockit.UsingMocksAndStubs;
import mockit.integration.junit4.JMockit;
import mockit.integration.logging.Slf4jMocks;

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
public class EmptyParameterHandlerTest {
	@Mocked
	@Injectable
	private MethodParameter mockMethodParameter;

	@Tested
	private EmptyParameterHandler handler;

	@Mocked
	private NotEmpty mockNotEmpty;

	@Before
	public void prepare() {
		new NonStrictExpectations() {
			{
				mockNotEmpty.message();
				result = "parameter is empty";
			}
		};
		expectedParameterAnnotation(mockNotEmpty);
	}

	@Test(expected = ParameterHandlerException.class)
	public void testValidAnnotaion() {
		expectedParameterAnnotation(null);
		handler.check(mockMethodParameter);
	}

	@Test(expected = ParameterCheckException.class)
	public void testNullListObject() {
		expectedParameterClass(null, List.class);
		handler.check(mockMethodParameter);
	}

	@Test(expected = ParameterCheckException.class)
	public void testEmptyListObject() {
		expectedParameterClass(new ArrayList(), List.class);
		handler.check(mockMethodParameter);
	}

	@Test
	public void testNotEmptyListObject() {
		List<String> list = new ArrayList<String>();
		list.add("test");
		expectedParameterClass(list, List.class);
		handler.check(mockMethodParameter);
	}

	@Test(expected = ParameterCheckException.class)
	public void testNullMapObject() {
		expectedParameterClass(null, Map.class);
		handler.check(mockMethodParameter);
	}

	@Test(expected = ParameterCheckException.class)
	public void testEmptyMapObject() {
		expectedParameterClass(new HashMap(), Map.class);
		handler.check(mockMethodParameter);
	}

	@Test
	public void testNotEmptyMapObject() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		expectedParameterClass(map, Map.class);
		handler.check(mockMethodParameter);
	}

	@Test(expected = ParameterCheckException.class)
	public void testEmptyString() {
		expectedParameterClass("", String.class);
		handler.check(mockMethodParameter);
	}

	@Test(expected = ParameterHandlerException.class)
	public void testUnsupportedClassObject() {
		Integer integer = new Integer(5);
		expectedParameterClass(integer, Integer.class);
		handler.check(mockMethodParameter);
	}

	private void expectedParameterClass(final Object object, final Class<?> clazz) {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getType();
				result = clazz;

				mockMethodParameter.getObject();
				result = object;
			}
		};
	}

	private void expectedParameterAnnotation(final NotEmpty notEmpty) {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getAnnotation((Class<?>) any);
				result = notEmpty;
			}
		};
	}
}
