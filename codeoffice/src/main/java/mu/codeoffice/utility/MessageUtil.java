package mu.codeoffice.utility;

import java.util.ArrayList;
import java.util.List;

public class MessageUtil {

	public static List<String> getMessages(String... messages)
	{
		List<String> list = new ArrayList<String>();
		if (messages != null) {
			for (String message : messages) {
				list.add(message);
			}
		}
		return list;
	}
	
}
