package mu.codeoffice.enums;

public enum HistoryType {

	/* common */
	DES("description"), REM("removed"),
	/* project */
	NAM("name"), VIS("visibility"), ACE("accessibility"), LEA("lead"),
	/* case */
	STA("status"), ASS("assignee"), REP("reporter"), RES("resolution"),
	SUM("summary"), SUB("sub"), EST("estimation"), PRI("priority"),
	TYP("type"), 
	REL("release"), VER("version"), COM("component"), 
	LIN("link"), LAB("label");
	
	private final String code;
	
	private HistoryType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
}
