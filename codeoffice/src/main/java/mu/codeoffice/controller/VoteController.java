package mu.codeoffice.controller;

import java.util.Arrays;

import javax.servlet.http.HttpSession;

import mu.codeoffice.entity.Vote;
import mu.codeoffice.entity.VoteOption;
import mu.codeoffice.service.VoteService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("voteController")
@RequestMapping("/")
public class VoteController {

	private VoteService voteService;

	@RequestMapping(value = "vote", method = RequestMethod.GET)
	public ModelAndView requestCreate(HttpSession session) {
		Vote vote = new Vote();
		vote.setVoteOptions(Arrays.asList(new VoteOption(), new VoteOption(), new VoteOption()));
		return new ModelAndView("vote/voteform", "vote", vote);
	}
	
	@RequestMapping(value = "vote", method = RequestMethod.POST)
	public ModelAndView createVote(@ModelAttribute("vote") Vote vote, BindingResult bindintResult, HttpSession session) {
		String uniqueId = voteService.createVote(vote);
		if (uniqueId == null) {
			return new ModelAndView("vote/voteform", "vote", vote);
		} else {
			return new ModelAndView("redirect:v_" + uniqueId);
		}
	}
	
	@RequestMapping(value = "v_{uniqueId}", method = RequestMethod.GET)
	public ModelAndView getVote(@PathVariable("uniqueId") String uniqueId) {
		Vote vote = voteService.getVote(uniqueId);
		if (vote == null) {
			return new ModelAndView("error/404");
		}
		return new ModelAndView("vote/vote", "vote", vote);
	}
	
	@RequestMapping(value = "v_{uniqueId}", method = RequestMethod.POST)
	public ModelAndView vote(@PathVariable("uniqueId") String uniqueId) {
		return null;
	}
	
	@RequestMapping(value = "v_{uniqueId}/r", method = RequestMethod.GET)
	public ModelAndView getVoteResult(@PathVariable("uniqueId") String uniqueId) {
		Vote vote = voteService.getVoteResult(uniqueId);
		if (vote == null) {
			return new ModelAndView("error/404");
		}
		return new ModelAndView("vote/vote", "vote", vote);
	}
	
	@RequestMapping(value = "vhome", method = RequestMethod.GET)
	public ModelAndView home() {
		return null;
	}
	
	public VoteService getVoteService() {
		return voteService;
	}

	@Autowired
	public void setVoteService(VoteService voteService) {
		this.voteService = voteService;
	}
	
}
