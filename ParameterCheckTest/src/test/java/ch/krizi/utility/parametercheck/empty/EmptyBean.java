/**
 * 
 */
package ch.krizi.utility.parametercheck.empty;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import ch.krizi.utility.parametercheck.handler.empty.NotEmpty;

/**
 * @author krizi
 * 
 */
@Component
public class EmptyBean {

	public void testEmptyListThrowException(@NotEmpty List<?> list) {

	}
	
	public void testEmptyMapThrowException(@NotEmpty Map<?, ?> map) {
		
	}
}
