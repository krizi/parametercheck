/**
 * 
 */
package ch.krizi.utility.parametercheck.handler.range;

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
import ch.krizi.utility.parametercheck.factory.MethodParameter;

/**
 * @author krizi
 * 
 */
@RunWith(JMockit.class)
@UsingMocksAndStubs(Slf4jMocks.class)
public class RangeParameterHandlerTest {

	@Mocked
	@Injectable
	private MethodParameter mockMethodParameter;

	@Tested
	private RangeParameterHandler handler;

	@Mocked
	private Range mockRange;

	@Before
	public void prepare() {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getAnnotation((Class<?>) Range.class);
				result = mockRange;
			}
		};
	}

	@Test
	public void testValidRange() {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getType();
				result = Integer.class;

				mockMethodParameter.getObject();
				result = 6;

				mockRange.min();
				result = 0;

				mockRange.max();
				result = 10;
			}
		};

		handler.check(mockMethodParameter);
	}

	@Test(expected = ParameterCheckException.class)
	public void testInvalidRange() {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getType();
				result = Integer.class;

				mockMethodParameter.getObject();
				result = 16;

				mockRange.min();
				result = 0;

				mockRange.max();
				result = 10;
			}
		};

		handler.check(mockMethodParameter);
	}
}
