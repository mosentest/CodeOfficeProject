package mu.codeoffice.entity;

import java.util.Date;

public class Tag {

	private long id;
	private String tag;
	private long countArticle;
	private long countAsk;
	private long countCode;
	private long countForum;
	private long count;
	private Date create;
	private Date update;
	
	public Tag() {}
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getTag() {
		return tag;
	}
	
	public void setTag(String tag) {
		this.tag = tag;
	}
	
	public long getCount() {
		return count;
	}
	
	public void setCount(long count) {
		this.count = count;
	}

	public long getCountArticle() {
		return countArticle;
	}

	public void setCountArticle(long countArticle) {
		this.countArticle = countArticle;
	}

	public long getCountAsk() {
		return countAsk;
	}

	public void setCountAsk(long countAsk) {
		this.countAsk = countAsk;
	}

	public long getCountCode() {
		return countCode;
	}

	public void setCountCode(long countCode) {
		this.countCode = countCode;
	}

	public long getCountForum() {
		return countForum;
	}

	public void setCountForum(long countForum) {
		this.countForum = countForum;
	}

	public Date getCreate() {
		return create;
	}

	public void setCreate(Date create) {
		this.create = create;
	}

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}
	
}
