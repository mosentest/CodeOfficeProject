package mu.codeoffice.enums;

public enum IssuePriority implements TextImageEnum {
	
	BLO("blocker"), CRI("critical"), MAJ("major"), 
	MIN("minor"), TRI("trivial");

	private final String code;
	
	private IssuePriority(String code) {
		this.code = code;
	}
	
	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getImagePath() {
		return "office/priority/" + code;
	}

}
