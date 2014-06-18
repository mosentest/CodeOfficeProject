package mu.codeoffice.security;

import java.util.Date;

public class SessionObject {
	
	private String sessionID;

	private Date create;
	
	private Date access;
	
	private int requests;
	
	private String ip;
	
	private EnterpriseAuthentication auth;
	
	public SessionObject(String sessionID, String ip, EnterpriseAuthentication auth) {
		this.sessionID = sessionID;
		this.ip = ip;
		this.auth = auth;
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

	public EnterpriseAuthentication getAuth() {
		return auth;
	}
	
}
