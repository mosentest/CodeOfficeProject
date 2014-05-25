package mu.codeoffice.enums;

public enum EmailVisibility {

	PUBLIC("emailvisibility.public"), HIDDEN("emailvisibility.hidden"),
	MASKED("emailvisibility.masked"), USER_ONLY("emailvisibility.user_only");
	
	private final String key;
	
	private EmailVisibility(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
}
