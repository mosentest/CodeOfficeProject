package mu.codeoffice.entity;

import java.util.Date;
import java.util.List;

public class AskQuestion {

	public static final int DEFAULT_REPUTATION = 5;
	
	public static final int BASE_EXP = 5;
	public static final int BASE_COIN = 1;
	public static final int BASE_CONT = 5;
	
	private long id;
	private String title;
	private User creator;
	private String description;
	private Date create;
	private Date update;
	private Date expire;
	private int contribution;
	private int experience;
	private int coin;
	private int views;
	private int votes;
	private int answers;
	private boolean solved;
	private boolean expired;
	private List<Tag> tags;
	private List<AskSolution> solutions;
	private List<AskComment> comments;
	
	public AskQuestion() {}
	
	public void creationInit() {
		create = new Date();
		update = create;
		contribution = BASE_CONT;
		experience = BASE_EXP + experience;
		coin = BASE_COIN + coin;
		views = 0;
		votes = 0;
		answers = 0;
		solved = false;
		expired = false;
	}

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

	public User getCreator() {
		return creator;
	}

	public void setCreator(User creator) {
		this.creator = creator;
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

	public Date getUpdate() {
		return update;
	}

	public void setUpdate(Date update) {
		this.update = update;
	}

	public Date getExpire() {
		return expire;
	}

	public void setExpire(Date expire) {
		this.expire = expire;
	}

	public int getExperience() {
		return experience;
	}

	public void setExperience(int experience) {
		this.experience = experience;
	}

	public int getCoin() {
		return coin;
	}

	public void setCoin(int coin) {
		this.coin = coin;
	}

	public int getViews() {
		return views;
	}

	public void setViews(int views) {
		this.views = views;
	}

	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public int getAnswers() {
		return answers;
	}

	public void setAnswers(int answers) {
		this.answers = answers;
	}

	public boolean isSolved() {
		return solved;
	}

	public void setSolved(boolean solved) {
		this.solved = solved;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	public List<AskSolution> getSolutions() {
		return solutions;
	}

	public void setSolutions(List<AskSolution> solutions) {
		this.solutions = solutions;
	}

	public List<AskComment> getComments() {
		return comments;
	}

	public void setComments(List<AskComment> comments) {
		this.comments = comments;
	}

	public boolean isExpired() {
		return expired;
	}

	public void setExpired(boolean expired) {
		this.expired = expired;
	}

	public int getContribution() {
		return contribution;
	}

	public void setContribution(int contribution) {
		this.contribution = contribution;
	}
	
	
}
