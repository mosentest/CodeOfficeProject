package mu.codeoffice.service;

import javax.annotation.Resource;

import mu.codeoffice.repository.ProjectRepository;

import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Resource
	private ProjectRepository projectRepository;
	
	
}