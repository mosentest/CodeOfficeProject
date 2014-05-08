package mu.codeoffice.mapper;

import java.util.List;

import mu.codeoffice.entity.Vote;
import mu.codeoffice.entity.VoteOption;

public interface VoteMapper {

	public long createVote(Vote vote);
	
	public String getUniqueId(long vote);
	
	public Vote getVote(String uniqueId);
	
	public Vote getVoteOptions(long vote);
	
	public List<Vote> getVotes(int offset, int size);
	
	public int vote(long vote, long option);
	
	public int vote(long vote, long option, String ip);
	
	public int deleteVote(long vote);
	
	public int createVoteOption(VoteOption voteOption);
	
	public int deleteVoteOption(long option);
	
}
