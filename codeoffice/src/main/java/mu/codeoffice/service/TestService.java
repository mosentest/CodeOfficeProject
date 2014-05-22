package mu.codeoffice.service;

import javax.annotation.Resource;

import mu.codeoffice.repository.ComponentRepository;
import mu.codeoffice.repository.EnterpriseUserRepository;
import mu.codeoffice.repository.IssueRepository;
import mu.codeoffice.repository.LabelRepository;
import mu.codeoffice.repository.ProjectCategoryRepository;
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.repository.VersionRepository;

import org.springframework.stereotype.Service;

@Service
public class TestService {

	@Resource
	private ProjectCategoryRepository projectCategoryRepository;
	
	@Resource
	private ProjectRepository projectRepository;

	@Resource
	private VersionRepository versionRepository;

	@Resource
	private ComponentRepository componentRepository;
	
	@Resource
	private LabelRepository labelRepository;
	
	@Resource
	private EnterpriseUserRepository enterpriseUserRepository;
	
	@Resource
	private IssueRepository caseRepository;
	
}
