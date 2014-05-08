package mu.codeoffice.entity;

import java.util.Date;

public class Form {

	private long id;
	private String title;
	private String description;
	private Date create;
	private Date expire;
	private Date update;
	private boolean publicType;
	
	public Form() {}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public boolean isPublicType() {
		return publicType;
	}

	public void setPublicType(boolean publicType) {
		this.publicType = publicType;
	}
	
	
	
}
