package mu.codeoffice.enums;

public enum CaseStatus implements TextImageEnum {
	
	ASG("assigned"), DOC("document"), DOW("down"), EMA("email"), GEN("generic"),
	INF("information"), INV("invisible"), NEI("needinfo"), TRA("trash"), UNA("unassigned"), _UP("up"), VIS("visible"),
	OPE("open"), INP("inprogress"), 
	REO("reopened"), RES("resolved"), CLO("closed");

	private final String code;
	
	private CaseStatus(String code) {
		this.code = code;
	}

	@Override
	public String getCode() {
		return code;
	}
	
	@Override
	public String getImagePath() {
		return "office/status/" + code;
	}

}
