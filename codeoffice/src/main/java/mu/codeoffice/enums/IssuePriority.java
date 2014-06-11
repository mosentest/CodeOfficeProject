package mu.codeoffice.enums;

public enum IssuePriority {
	
	BLO("blocker"), CRI("critical"), MAJ("major"), 
	MIN("minor"), TRI("trivial");

	private final String code;
	
	private IssuePriority(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}

	public String getImagePath() {
		return "office/priority/" + code;
	}

}
