package mu.codeoffice.enums;

public enum CommentVisibility {

	PUBLIC("commentvisibility.public"), PROJECT("commentvisibility.project"),
	USER_ONLY("commentvisibility.user_only");
	
	private final String key;
	
	private CommentVisibility(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
	
}
