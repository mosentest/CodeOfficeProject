package mu.codeoffice.enums;

public enum LinkType {

	SUF("sub_for"), REL("related"), SUO("sub_of"), DUP("duplicate"), BLO("blocked"), BLI("blocking");
	
	private final String code;
	
	private LinkType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
}
