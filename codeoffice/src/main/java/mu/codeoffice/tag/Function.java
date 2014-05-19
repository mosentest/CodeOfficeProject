package mu.codeoffice.tag;

import java.util.List;

public class Function {
	
	public static Integer bitwiseAnd(Integer value, Integer mask) {
		return (value & mask);
	}

	public static boolean listContains(List<Object> objects, Object object) {
		if (objects == null) {
			return false;
		}
		return objects.contains(object);
	}
	
	public static boolean arrayContains(Object[] objects, Object object) {
		if (objects == null) {
			return false;
		}
		for (Object o : objects) {
			if (o.equals(object)) {
				return true;
			}
		}
		return false;
	}
	
}
