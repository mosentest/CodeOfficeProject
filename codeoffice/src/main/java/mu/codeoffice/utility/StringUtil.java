package mu.codeoffice.utility;

public class StringUtil {

	public static boolean isEmptyString(String string) {
		return string == null || string.trim().length() == 0;
	}
	
	public static String appendZero(long number, int count) {
		StringBuilder buffer = new StringBuilder(Long.toString(number));
		while (buffer.length() < count) {
			buffer.insert(0, '0');
		}
		return buffer.toString();
	}
	
}
