package mu.codeoffice.enums;

public enum CaseType {
	
	BLA("blank"), BUG("bug"), DEF("defect"), DOC("documentation"), EPI("epic"), GEN("generic"), 
	HEA("health"), REM("removefeature"), REQ("requirement"), SAL("sales"), STO("story"), 
	TAA("taskagile"), UND("undefined"),  
	IPR("improvement"), IPL("implementation"), TAS("task"), 
	NEF("newfeature"), SUT("subtask");

	private final String code;
	
	private CaseType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getImagePath() {
		return "type/" + code;
	}

}
