package mu.codeoffice.enums;

public enum AnnouncementLevel {

	TIP("announcement.tip"),
	INFO("announcement.info"),
	WARNING("announcement.warning"),
	ERROR("announcement.error"),
	ALERT("announcement.alert");

	private final String key;
	
	private AnnouncementLevel(String key) {
		this.key = key;
	}
	
	public String getKey() {
		return key;
	}
	
}
