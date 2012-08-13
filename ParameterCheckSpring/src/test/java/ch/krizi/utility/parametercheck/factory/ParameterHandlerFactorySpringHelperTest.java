/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import mockit.Injectable;
import mockit.Mocked;
import mockit.NonStrictExpectations;
import mockit.Tested;
import mockit.integration.junit4.JMockit;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.context.ApplicationContext;

/**
 * @author krizi
 * 
 */
@RunWith(JMockit.class)
public class ParameterHandlerFactorySpringHelperTest {

	@Tested
	private ParameterHandlerFactorySpringHelper parameterHandlerFactorySpringHelper;

	@Mocked
	private TestHandler mockTestHandler;

	@Mocked
	@Injectable
	private ApplicationContext mockApplicationContext;

	@Test
	public void testCreateNewValidHandlerInstance() {
		new NonStrictExpectations() {
			{
				mockApplicationContext.getBean((Class) any);
				result = mockTestHandler;
			}
		};
		parameterHandlerFactorySpringHelper.createParameterHandler(TestHandler.class);
	}
}
