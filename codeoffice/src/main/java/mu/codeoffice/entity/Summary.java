package mu.codeoffice.entity;

import java.util.Date;

public class Summary {
	
	private Date date;
	private int noCount;
	private int noResolved;
	private boolean versionStart;
	private boolean versionRelease;
	private boolean versionDelay;
	private String versionCode;
	
	public Summary(Date date, int noCount, int noResolved) {
		this.date = date;
		this.noCount = noCount;
		this.noResolved = noResolved;
	}

	public Date getDate() {
		return date;
	}

	public int getNoCount() {
		return noCount;
	}

	public int getNoResolved() {
		return noResolved;
	}

	public boolean isVersionStart() {
		return versionStart;
	}

	public void setVersionStart(boolean versionStart) {
		this.versionStart = versionStart;
	}

	public boolean isVersionDelay() {
		return versionDelay;
	}

	public void setVersionDelay(boolean versionDelay) {
		this.versionDelay = versionDelay;
	}

	public boolean isVersionRelease() {
		return versionRelease;
	}

	public void setVersionRelease(boolean versionRelease) {
		this.versionRelease = versionRelease;
	}

	public String getVersionCode() {
		return versionCode;
	}

	public void setVersionCode(String versionCode) {
		this.versionCode = versionCode;
	}
}
