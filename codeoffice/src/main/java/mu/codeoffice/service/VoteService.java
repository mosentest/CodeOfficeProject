package mu.codeoffice.service;

import java.util.Date;
import java.util.stream.IntStream;

import javax.annotation.Resource;

import mu.codeoffice.entity.Vote;
import mu.codeoffice.entity.VoteOption;
import mu.codeoffice.repository.VoteOptionRepository;
import mu.codeoffice.repository.VoteRepository;
import mu.codeoffice.utility.StringUtil;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("voteService")
public class VoteService {
	
	@Resource
	private VoteRepository voteRepository;

	@Resource
	private VoteOptionRepository voteOptionRepository;

    @Transactional
	public String createVote(final Vote vote) {
		vote.setCreate(new Date());
		voteRepository.save(vote);
		if (vote == null || vote.getId() == 0l) {
			return null;
		}
		vote.setUniqueId(Integer.toHexString(StringUtil.appendZero(vote.getId(), 10).hashCode()));
		voteRepository.save(vote);
		IntStream.range(0, vote.getVoteOptions().size()).forEach(index -> {
			VoteOption voteOption = vote.getVoteOptions().get(index);
			if (!voteOption.getOption().isEmpty()) {
				voteOption.setVote(vote);
				voteOption.setOrder(index);
				voteOptionRepository.save(voteOption);
			}
		});
		return vote.getUniqueId();
	}

    @Transactional(readOnly = true)
	public Vote getVote(String uniqueId) {
    	Vote vote = voteRepository.getByUniqueId(uniqueId);
    	if (vote == null) {
    		return null;
    	}
    	vote.getVoteOptions().size();
		return vote;
	}

    @Transactional(readOnly = true)
	public Vote getVoteResult(String uniqueId) {
		Vote vote = voteRepository.getByUniqueId(uniqueId);
		if (vote == null) {
			return null;
		}
		vote.getVoteOptions().forEach(voteOption -> voteOption.setPercentage((double) voteOption.getVotes() / vote.getVotes()));
		return vote;
	}
	
}
