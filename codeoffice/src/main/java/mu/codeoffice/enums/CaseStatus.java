package mu.codeoffice.enums;

public enum CaseStatus {
	
	ASG("assigned"), DOC("document"), DOW("down"), EMA("email"), GEN("generic"),
	INF("information"), INV("invisible"), NEI("needinfo"), TRA("trash"), UNA("unassigned"), _UP("up"), VIS("visible"),
	OPE("open"), INP("inprogress"), 
	REO("reopened"), RES("resolved"), CLO("closed");

	private final String code;
	
	private CaseStatus(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getImagePath() {
		return "status/" + code;
	}

}
