/**
 * 
 */
package ch.krizi.utility.parametercheck.factory;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import mockit.Mocked;
import mockit.integration.junit4.JMockit;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * @author krizi
 * 
 */
@RunWith(JMockit.class)
public class MethodParameterTest {

	private MethodParameter methodParameter;

	@Mocked(methods = "", inverse = true)
	private Documented mockDocumented;

	@Mocked(methods = "", inverse = true)
	private Target mockTarget;

	@Before
	public void prepare() {
		methodParameter = new MethodParameter(0, "test", Documented.class, new Object(), mockDocumented, mockTarget);
	}

	@Test
	public void testGetAnnotationsNotNull() {
		Documented annotation = methodParameter.getAnnotation(Documented.class);
		Assert.assertNotNull(annotation);
	}

	@Test
	public void testGetAnnotationsNull() {
		Retention annotation = methodParameter.getAnnotation(Retention.class);
		Assert.assertNull(annotation);
	}

}
