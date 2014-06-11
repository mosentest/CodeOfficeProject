package mu.codeoffice.enums;

public enum CaseResolution {
	
	FIX("fixed"), WNF("will_not_fix"), DUP("duplicate"), 
	INC("incomplete"), IMP("implemented"), RES("responded"), 
	BYD("by_design"), WNI("will_not_implement"), 
	ALE("already_exist"), WFI("waiting_for_info"), SPA("spam"), UNR("unresolved");

	private final String code;
	
	private CaseResolution(String code) {
		this.code = code;
	}

	public String getCode() {
		return code;
	}

}
