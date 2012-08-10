/**
 * 
 */
package ch.krizi.utility.parametercheck.empty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
public class EmptyAspectTest {

	@Autowired
	private EmptyBean bean;

	@Test(expected = IllegalArgumentException.class)
	public void testThrowExceptionWhenListEmpty() {
		bean.testEmptyListThrowException(new ArrayList());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThrowExceptionWhenListNull() {
		bean.testEmptyListThrowException(null);
	}

	@Test
	public void testNotThrowingExceptionWhenListHasValues() {
		List<String> list = new ArrayList<String>();
		list.add("one");
		bean.testEmptyListThrowException(list);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThrowExceptionWhenMapEmpty() {
		bean.testEmptyMapThrowException(new HashMap());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testThrowExceptionWhenMapNull() {
		bean.testEmptyMapThrowException(null);
	}

	@Test
	public void testNotThrowingExceptionWhenMapHasValues() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("key", "value");
		bean.testEmptyMapThrowException(map);
	}

}
