package mu.codeoffice.enums;

public enum ActivityType {

	/* general */
	C_N("create_note"), U_N("update_note"), D_N("delete_note"), 
	C_A("create_attachment"), U_A("update_attachment"), D_A("delete_attachment"),
	/* project */
	C_P("create_project"), D_P("delete_project"),
	C_M("create_member"), U_L("update_lead"), D_M("delete_member"),
	U_V("update_version"), U_C("update_component"),
	C_C("create_case"), D_C("delete_case"), 
	/* case */
	STA("start_progress"), STO("stop_progress"),
	RES("resolve"), CLO("close"), ASS("change_assignee"), REP("change_reporter"),
	C_V("change_release"), A_V("add_version"), R_V("remove_version"), A_C("add_component"), R_C("remove_component"),
	A_A("add_label"), R_A("remove_label"), A_L("add_link"), R_L("remove_link");
	
	private final String code;
	
	private ActivityType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
}
