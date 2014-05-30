package mu.codeoffice.enums;

public enum AnnouncementLevel {

	TIP("announcement.tip", "tip"),
	INFO("announcement.info", "info"),
	WARNING("announcement.warning", "warning"),
	ERROR("announcement.error", "error"),
	ALERT("announcement.alert", "alert");

	private final String key;
	
	private final String info;
	
	private AnnouncementLevel(String key, String info) {
		this.key = key;
		this.info = info;
	}
	
	public String getKey() {
		return key;
	}
	
	public String getInfo() {
		return info;
	}
	
}
