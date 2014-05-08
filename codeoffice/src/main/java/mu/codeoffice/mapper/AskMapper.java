package mu.codeoffice.mapper;

import java.util.List;

import mu.codeoffice.common.query.Order;
import mu.codeoffice.common.query.QueryModel;
import mu.codeoffice.entity.AskComment;
import mu.codeoffice.entity.AskQuestion;
import mu.codeoffice.entity.AskSolution;
import mu.codeoffice.entity.Tag;

public interface AskMapper {
	
	public int countQuestion();
	
	public List<AskQuestion> getQuestions(QueryModel queryModel, int offset, int size);
	
	public List<AskComment> getSolutionComments(long id);
	
	public List<AskComment> getQuestionComments(long id);
	
	public List<AskSolution> getSolutions(long id);
	
	public List<Tag> getTags(Order order, int offset, int size);
	
	public List<Tag> getQuestionTags(long id);
	
	public AskQuestion getQuestion(long id);
	
	public AskSolution getSolution(long id);
	
	public AskComment getComment(long id);
	
	public int updateQuestionStatus(AskQuestion question);
	
	public int updateSolutionStatus(AskSolution solution);
	
	public int updateCommentStatus(AskComment comment);
	
	public boolean hasVotedQuestion(long id, long uid);
	
	public boolean hasVotedSolution(long id, long uid);
	
	public boolean hasVotedComment(long id, long uid);
	
	public int voteQuestion(long id, long uid);
	
	public int voteSolution(long id, long uid);
	
	public int voteComment(long id, long uid);
	
	public long createComment(AskComment comment);
	
	public int createQuestion(AskQuestion question);
	
	public int createSolution(AskSolution solution);
	
}
