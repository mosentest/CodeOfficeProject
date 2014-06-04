package mu.codeoffice.entity.settings;

import java.util.List;

import mu.codeoffice.enums.NotificationEvent;

public class Notification {

	public NotificationEvent event;
	
	public List<Object /* notification types*/> notifies;
	
}
