package mu.codeoffice.enums;

public enum CasePriority implements TextImageEnum {
	
	BLO("blocker"), CRI("critical"), MAJ("major"), 
	MIN("minor"), TRI("trivial");

	private final String code;
	
	private CasePriority(String code) {
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
