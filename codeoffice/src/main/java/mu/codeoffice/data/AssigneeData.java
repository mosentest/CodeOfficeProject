package mu.codeoffice.data;

import mu.codeoffice.entity.User;

public class AssigneeData {

	private User user;
	
	private int inProgress;
	
	private int closed;
	
	private int resolved;
	
	private int total;

	public AssigneeData() {}
	
	public AssigneeData(User user) {
		this.user = user;
	}
	
	public void setData(int inProgress, int closed, int resolved, int total) {
		this.inProgress = inProgress;
		this.closed = closed;
		this.resolved = resolved;
		this.total = total;
	}

	public User getUser() {
		return user;
	}

	public int getInProgress() {
		return inProgress;
	}

	public int getClosed() {
		return closed;
	}

	public int getResolved() {
		return resolved;
	}

	public int getTotal() {
		return total;
	}
	
}
