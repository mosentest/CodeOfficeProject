package mu.codeoffice.enums;

public enum AttachmentType {
	
	TXT("text"), FIL("file"), IMG("image"), DOC("word"), PDF("pdf"), EXL("excel"), HTM("html"),
	JAV("java"), PPT("powerpoint"), ZIP("zip"), XML("xml");
	
	private final String code;
	
	private AttachmentType(String code) {
		this.code = code;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getImagePath() {
		return "attachment/" + code;
	}

}
