package mu.codeoffice.enums;

public enum AttachmentType {
	
	TXT("attachment.text"), FILE("attachment.file"), IMG("attachment.image"), DOC("attachment.word"), PDF("attachment.pdf"), EXL("attachment.excel"), 
	HTML("attachment.html"), JAVA("attachment.java"), PPT("attachment.powerpoint"), ZIP("attachment.zip"), XML("attachment.xml");
	
	private final String key;
	
	private final String icon;
	
	private AttachmentType(String key) {
		this.key = key;
		this.icon = key.split(".")[1];
	}
	
	public String getKey() {
		return key;
	}
	
	public String getIcon() {
		return icon;
	}

}
