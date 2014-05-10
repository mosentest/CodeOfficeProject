package mu.codeoffice.enums;

public enum CasePriority {
	
	BLO("blocker"), CRI("critical"), MAJ("major"), 
	MIN("minor"), TRI("trivial");

	private final String code;
	
	private CasePriority(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getImagePath() {
		return "priority/" + code;
	}

}
