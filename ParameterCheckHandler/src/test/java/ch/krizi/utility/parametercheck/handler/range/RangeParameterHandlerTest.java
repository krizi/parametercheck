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
	private Min mockMin;

	@Mocked
	private Max mockMax;

	@Test
	public void testValidRange() {
		setParameter(Integer.class, 6);
		setMin(0);
		setMax(10);

		handler.check(mockMethodParameter);
	}

	@Test(expected = ParameterCheckException.class)
	public void testInvalidRange() {
		setParameter(Integer.class, 16);
		setMin(0);
		setMax(10);

		handler.check(mockMethodParameter);
	}

	@Test
	public void testValidFloatRange() {
		setParameter(Float.class, 164.345);
		setMin(99.242);
		setMax(164.346);

		handler.check(mockMethodParameter);
	}

	@Test
	public void testValidMax() {
		setParameter(Float.class, 9.999099);
		setMax(10);

		handler.check(mockMethodParameter);
	}

	@Test
	public void testValidMaxMinusNumber() {
		setParameter(Float.class, -9.999099);
		setMax(10);

		handler.check(mockMethodParameter);
	}

	private void setParameter(final Class<?> type, final Object number) {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getType();
				result = type;

				mockMethodParameter.getObject();
				result = number;
			}
		};
	}

	private void setMin(final double value) {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getAnnotation((Class<?>) Min.class);
				result = mockMin;

				mockMin.value();
				result = value;
			}
		};
	}

	private void setMax(final double value) {
		new NonStrictExpectations() {
			{
				mockMethodParameter.getAnnotation((Class<?>) Max.class);
				result = mockMax;

				mockMax.value();
				result = value;
			}
		};
	}
}
