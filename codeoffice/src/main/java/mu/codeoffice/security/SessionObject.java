package mu.codeoffice.security;

import java.util.Date;

import mu.codeoffice.entity.User;

public class SessionObject {
	
	private String sessionID;

	private Date create;
	
	private Date access;
	
	private int requests;
	
	private String ip;
	
	private User user;
	
	public SessionObject(String sessionID, String ip, User user) {
		this.sessionID = sessionID;
		this.ip = ip;
		this.user = user;
		create = new Date();
		access = new Date();
		requests = 0;
	}
	
	public synchronized void access() {
		requests++;
		access = new Date();
	}

	public String getSessionID() {
		return sessionID;
	}

	public Date getCreate() {
		return create;
	}

	public Date getAccess() {
		return access;
	}

	public int getRequests() {
		return requests;
	}

	public String getIp() {
		return ip;
	}

	public User getUser() {
		return user;
	}
	
}
