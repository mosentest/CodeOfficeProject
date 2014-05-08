package mu.codeoffice.entity;

import java.util.Date;

public class AskComment {
	
	public static final int HIDE_LIMIT = -10;
	public static final int QUESTION = 0;
	public static final int SOLUTION = 1;
	
	private long id;
	private int parentType;
	private long parentId;
	private String comment;
	private User creator;
	private Date create;
	private int votes;
	private boolean hide;
	
	public AskComment() {}
	
	public void creationInit() {
		if (parentType != QUESTION && parentType != SOLUTION) {
			parentType = 0;
		}
		create = new Date();
		votes = 0;
		hide = false;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public int getParentType() {
		return parentType;
	}

	public void setParentType(int parentType) {
		this.parentType = parentType;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}
	
	public boolean isHide() {
		return hide;
	}
	
	public void setHide(boolean hide) {
		this.hide = hide;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	
}
