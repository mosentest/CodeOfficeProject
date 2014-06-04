package mu.codeoffice.utility;

import java.util.HashSet;
import java.util.Set;

public class CodeUtil {
	
	public static <T> Set<T> toSet(T[] array) {
		Set<T> set = new HashSet<T>();
		toSet(set, array);
		return set;
	}
	
	public static <T> void toSet(Set<T> set, T[] array) {
		for (T t : array) {
			set.add(t);
		}
	}
	
}
