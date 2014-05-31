package mu.codeoffice.service;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.IssueLinkType;
import mu.codeoffice.entity.settings.IssuePriority;
import mu.codeoffice.entity.settings.IssueResolution;
import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.IssueType;
import mu.codeoffice.entity.settings.IssueTypeScheme;
import mu.codeoffice.repository.settings.IssueLinkTypeRepository;
import mu.codeoffice.repository.settings.IssuePriorityRepository;
import mu.codeoffice.repository.settings.IssueResolutionRepository;
import mu.codeoffice.repository.settings.IssueStatusRepository;
import mu.codeoffice.repository.settings.IssueTypeRepository;
import mu.codeoffice.repository.settings.IssueTypeSchemeRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.utility.StringUtil;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

public class IssuePropertyConfigurationService {

	@Resource
	private IssueLinkTypeRepository issueLinkTypeRepository;

	@Resource
	private IssueTypeRepository issueTypeRepository;
	
	@Resource
	private IssueTypeSchemeRepository issueTypeSchemeRepository;

	@Resource
	private IssueStatusRepository issueStatusRepository;

	@Resource
	private IssueResolutionRepository issueResolutionRepository;
	
	@Resource
	private IssuePriorityRepository issuePriorityRepository;
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public void create(EnterpriseAuthentication auth, IssueType issueType, boolean isSubTask) throws AuthenticationException, InformationException {
		if (!issueLinkTypeRepository.isNameAvailable(auth.getEnterprise(), issueType.getName(), 0l)) {
			throw new InformationException("Name already exist.");
		}
		if (StringUtil.isEmptyString(issueType.getName())) {
			throw new InformationException("Name can not be empty.");
		}
		if (!IssueType.isValidIcon(issueType.getIcon())) {
			throw new InformationException("Icon is invalid.");
		}
		issueType.setEnterprise(auth.getEnterprise());
		issueType.setId(null);
		issueType.setStandard(isSubTask);
		issueTypeRepository.save(issueType);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public void update(EnterpriseAuthentication auth, IssueType issueType) throws AuthenticationException, InformationException {
		IssueType original = issueTypeRepository.getIssueType(auth.getEnterprise(), issueType.getName());
		if (original == null) {
			throw new InformationException("Issue Type not exist.");
		}
		if (!issueTypeRepository.isNameAvailable(auth.getEnterprise(), issueType.getName(), original.getId())) {
			throw new InformationException("Name already exist.");
		};
		if (!IssueType.isValidIcon(issueType.getIcon())) {
			throw new InformationException("Icon is invalid.");
		}
		original.setName(issueType.getName());
		original.setDescription(issueType.getDescription());
		original.setIcon(issueType.getIcon());
		issueTypeRepository.save(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public void deleteIssueType(EnterpriseAuthentication auth, String issueTypeName) throws AuthenticationException, InformationException {
		IssueType original = issueTypeRepository.getIssueType(auth.getEnterprise(), issueTypeName);
		if (original == null) {
			throw new InformationException("Issue Type not exist.");
		}
		//CHECKE FOR USAGE
		issueTypeRepository.delete(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public void create(EnterpriseAuthentication auth, IssueLinkType issueLinkType) throws AuthenticationException, InformationException {
		if (!issueLinkTypeRepository.isNameAvailable(auth.getEnterprise(), issueLinkType.getName(), 0l)) {
			throw new InformationException("Name already exist.");
		}
		if (StringUtil.isEmptyString(issueLinkType.getName()) || StringUtil.isEmptyString(issueLinkType.getInwardLink()) || 
				StringUtil.isEmptyString(issueLinkType.getOutwardLink())) {
			throw new InformationException("Fields can not be empty.");
		}
		issueLinkType.setEnterprise(auth.getEnterprise());
		issueLinkType.setId(null);
		issueLinkTypeRepository.save(issueLinkType);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public void update(EnterpriseAuthentication auth, IssueLinkType issueLinkType) throws AuthenticationException, InformationException {
		IssueLinkType original = issueLinkTypeRepository.getIssueLinkType(auth.getEnterprise(), issueLinkType.getName());
		if (original == null) {
			throw new InformationException("Issue Link Type not exist.");
		}
		if (!issueLinkTypeRepository.isNameAvailable(auth.getEnterprise(), issueLinkType.getName(), original.getId())) {
			throw new InformationException("Name already exist.");
		}
		if (StringUtil.isEmptyString(issueLinkType.getInwardLink()) || StringUtil.isEmptyString(issueLinkType.getOutwardLink())) {
			throw new InformationException("Fields can not be empty.");
		}
		original.setInwardLink(issueLinkType.getInwardLink());
		original.setOutwardLink(issueLinkType.getOutwardLink());
		original.setName(issueLinkType.getName());
		issueLinkTypeRepository.save(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public void deleteIssueLinkType(EnterpriseAuthentication auth, String issueLinkTypeName) throws AuthenticationException, InformationException {
		IssueLinkType original = issueLinkTypeRepository.getIssueLinkType(auth.getEnterprise(), issueLinkTypeName);
		if (original == null) {
			throw new InformationException("Issue Link Type not exist.");
		}
		//CHECKE FOR USAGE
		issueLinkTypeRepository.delete(original);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public List<IssueTypeScheme> getIssueTypeSchemes(EnterpriseAuthentication auth) {
		List<IssueTypeScheme> list = issueTypeSchemeRepository.getIssueTypeSchemes(auth.getEnterprise());
		for (IssueTypeScheme scheme : list) {
			scheme.getIssueTypes().size();
			scheme.getProjects().size();
		}
		return list;
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public List<IssueType> getSubTaskTypes(EnterpriseAuthentication auth) {
		return issueTypeRepository.getSubTaskTypes(auth.getEnterprise());
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public List<IssueType> getIssueTypes(EnterpriseAuthentication auth) {
		List<IssueType> list = issueTypeRepository.getIssueTypes(auth.getEnterprise());
		for (IssueType type : list) {
			type.getIssueTypeSchemes().size();
		}
		return list;
	}

	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public List<IssueLinkType> getIssueLinkTypes(EnterpriseAuthentication auth) {
		return issueLinkTypeRepository.getIssueLinkTypes(auth.getEnterprise());
	}

	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public List<IssueStatus> getIssueStatus(EnterpriseAuthentication auth) {
		return issueStatusRepository.getIssueStatus(auth.getEnterprise());
	}

	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public List<IssueResolution> getIssueResolutions(EnterpriseAuthentication auth) {
		return issueResolutionRepository.getIssueResolutions(auth.getEnterprise());
	}

	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN',)")
	public List<IssuePriority> getIssuePriorities(EnterpriseAuthentication auth) {
		return issuePriorityRepository.getIssuePriorities(auth.getEnterprise());
	}
	
}
