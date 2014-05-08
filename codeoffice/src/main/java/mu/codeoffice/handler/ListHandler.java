package mu.codeoffice.handler;

import java.util.HashMap;
import java.util.Map;

public class ListHandler {

	private static final Map<Integer, String> SECURITY_QUESTIONS = new HashMap<Integer, String>();
	
	static {
		SECURITY_QUESTIONS.put(0, "q_1");
		SECURITY_QUESTIONS.put(1, "q_2");
		SECURITY_QUESTIONS.put(2, "q_3");
		SECURITY_QUESTIONS.put(3, "q_4");
		SECURITY_QUESTIONS.put(4, "q_5");
		SECURITY_QUESTIONS.put(5, "q_6");
		SECURITY_QUESTIONS.put(6, "q_7");
	}
	
	public static Map<Integer, String> getSecurityQuestions() {
		return SECURITY_QUESTIONS;
	}
	
}
