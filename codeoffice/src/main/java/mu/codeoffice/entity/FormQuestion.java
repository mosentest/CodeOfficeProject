package mu.codeoffice.entity;

import java.util.List;

public class FormQuestion {

	private long id;
	private long parentId;
	private int score;
	private long timeLimit;
	private int type;
	private int maxSelection;
	private int minSelection;
	private int minScore;
	private int maxScore;
	private int corectScore;
	private int incorrectScore;
	private String solution;
	
	private List<FormQuestionOption> options;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getParentId() {
		return parentId;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public long getTimeLimit() {
		return timeLimit;
	}

	public void setTimeLimit(long timeLimit) {
		this.timeLimit = timeLimit;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getMaxSelection() {
		return maxSelection;
	}

	public void setMaxSelection(int maxSelection) {
		this.maxSelection = maxSelection;
	}

	public int getMinSelection() {
		return minSelection;
	}

	public void setMinSelection(int minSelection) {
		this.minSelection = minSelection;
	}

	public int getMinScore() {
		return minScore;
	}

	public void setMinScore(int minScore) {
		this.minScore = minScore;
	}

	public int getMaxScore() {
		return maxScore;
	}

	public void setMaxScore(int maxScore) {
		this.maxScore = maxScore;
	}

	public int getCorectScore() {
		return corectScore;
	}

	public void setCorectScore(int corectScore) {
		this.corectScore = corectScore;
	}

	public int getIncorrectScore() {
		return incorrectScore;
	}

	public void setIncorrectScore(int incorrectScore) {
		this.incorrectScore = incorrectScore;
	}

	public String getSolution() {
		return solution;
	}

	public void setSolution(String solution) {
		this.solution = solution;
	}

	public List<FormQuestionOption> getOptions() {
		return options;
	}

	public void setOptions(List<FormQuestionOption> options) {
		this.options = options;
	}
	
	
	
}
