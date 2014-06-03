package mu.codeoffice.tag;

import java.util.Date;
import java.util.List;
import java.util.TimeZone;

public class Function {
	
	public static String maskURL(String string) {
		return string.replace(' ', '-');
	}
	
	public static int bitwiseAnd(Integer value, Integer mask) {
		return (value & mask);
	}
	
	public static boolean bitOn(Integer value, Integer mask) {
		return bitwiseAnd(value, mask) == mask;
	}

	public static boolean listContains(List<Object> objects, Object object) {
		if (objects == null) {
			return false;
		}
		return objects.contains(object);
	}
	
	public static String timeOffsetString(TimeZone timeZone) {
		long halfHourOffset = timeZone.getOffset(new Date().getTime()) / 1800000;
		return halfHourOffset / 2 + ":" + halfHourOffset % 2 + "0";
	}
	
	public static String formatString(String string, Object[] arguments) {
		return String.format(string, arguments);
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
