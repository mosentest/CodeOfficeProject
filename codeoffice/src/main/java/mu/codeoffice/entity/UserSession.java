package mu.codeoffice.entity;

import java.util.Date;

public class UserSession {
	
	private String sessionID;
	
	private User user;
	
	private String type;
	
	private String IP;
	
	private int requests;
	
	private Date lastRequest;
	
	private Date sessionCreation;
	
	public UserSession() {
		
	}

	public String getSessionID() {
		return sessionID;
	}

	public void setSessionID(String sessionID) {
		this.sessionID = sessionID;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIP() {
		return IP;
	}

	public void setIP(String iP) {
		IP = iP;
	}

	public int getRequests() {
		return requests;
	}

	public void setRequests(int requests) {
		this.requests = requests;
	}

	public Date getLastRequest() {
		return lastRequest;
	}

	public void setLastRequest(Date lastRequest) {
		this.lastRequest = lastRequest;
	}

	public Date getSessionCreation() {
		return sessionCreation;
	}

	public void setSessionCreation(Date sessionCreation) {
		this.sessionCreation = sessionCreation;
	}
	
}
