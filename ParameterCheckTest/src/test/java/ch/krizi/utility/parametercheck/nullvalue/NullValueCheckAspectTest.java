/**
 * 
 */
package ch.krizi.utility.parametercheck.nullvalue;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * @author krizi
 * 
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("/applicationContext-aspects.xml")
public class NullValueCheckAspectTest {

	@Autowired
	private NullBean bean;

	@Test(expected = IllegalArgumentException.class)
	public void testThrowExceptionWhenNull() {
		bean.throwExceptionWhenNull(null, null);
	}

	@Test
	public void testCreateInstanceWhenNull() {
		Object[] parameters = bean.createInstanceWhenNull(null, null);

		Assert.assertNotNull(parameters);
		Assert.assertNull(parameters[0]);
		Assert.assertNotNull(parameters[1]);
	}

	@Test
	public void testMethodWithTwoAnnotatedParameter() {
		Object[] parameters = bean.twoAnnotatedParameter(null, null);

		Assert.assertNotNull(parameters);
		Assert.assertNotNull(parameters[0]);
		Assert.assertNotNull(parameters[1]);
	}
}
