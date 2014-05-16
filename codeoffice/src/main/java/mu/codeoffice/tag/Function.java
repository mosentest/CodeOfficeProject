package mu.codeoffice.tag;

import java.util.List;

public class Function {

	public static boolean listContains(List<Object> objects, Object object) {
		return objects.contains(object);
	}
	
	public static boolean arrayContains(Object[] objects, Object object) {
		for (Object o : objects) {
			if (o.equals(object)) {
				return true;
			}
		}
		return false;
	}
	
}
