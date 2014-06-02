package mu.codeoffice.service;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.IssueLink;
import mu.codeoffice.entity.settings.IssuePriority;
import mu.codeoffice.entity.settings.IssueResolution;
import mu.codeoffice.entity.settings.IssueStatus;
import mu.codeoffice.entity.settings.IssueType;
import mu.codeoffice.entity.settings.IssueTypeScheme;
import mu.codeoffice.repository.settings.IssueLinkRepository;
import mu.codeoffice.repository.settings.IssuePriorityRepository;
import mu.codeoffice.repository.settings.IssueResolutionRepository;
import mu.codeoffice.repository.settings.IssueStatusRepository;
import mu.codeoffice.repository.settings.IssueTypeRepository;
import mu.codeoffice.repository.settings.IssueTypeSchemeRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

public class IssuePropertyConfigurationService {

	@Resource
	private IssueLinkRepository issueLinkRepository;

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
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void update(EnterpriseAuthentication auth, IssueStatus issueStatus) throws AuthenticationException, InformationException {
		IssueStatus original = issueStatusRepository.getIssueStatus(auth.getEnterprise(), issueStatus.getId());
		if (original == null) {
			throw new InformationException("Issue Status not exist.");
		}
		if (!issueStatusRepository.isNameAvailable(auth.getEnterprise(), issueStatus.getName(), original.getId())) {
			throw new InformationException("Name already exist.");
		};
		if (!IssueStatus.isValidIcon(issueStatus.getIcon())) {
			throw new InformationException("Icon is invalid.");
		}
		original.setName(issueStatus.getName());
		original.setDescription(issueStatus.getDescription());
		original.setIcon(issueStatus.getIcon());
		original.setColor(issueStatus.getColor());
		issueStatusRepository.save(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void update(EnterpriseAuthentication auth, IssueResolution issueResolution) throws AuthenticationException, InformationException {
		IssueResolution original = issueResolutionRepository.getIssueResolution(auth.getEnterprise(), issueResolution.getId());
		if (original == null) {
			throw new InformationException("Issue Resolution not exist.");
		}
		if (!issueResolutionRepository.isNameAvailable(auth.getEnterprise(), issueResolution.getName(), original.getId())) {
			throw new InformationException("Name already exist.");
		};
		original.setName(issueResolution.getName());
		original.setDescription(issueResolution.getDescription());
		original.setColor(issueResolution.getColor());
		issueResolutionRepository.save(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void update(EnterpriseAuthentication auth, IssuePriority issuePriority) throws AuthenticationException, InformationException {
		IssuePriority original = issuePriorityRepository.getIssuePriority(auth.getEnterprise(), issuePriority.getId());
		if (original == null) {
			throw new InformationException("Issue Priority not exist.");
		}
		if (!issuePriorityRepository.isNameAvailable(auth.getEnterprise(), issuePriority.getName(), original.getId())) {
			throw new InformationException("Name already exist.");
		};
		if (!IssuePriority.isValidIcon(issuePriority.getIcon())) {
			throw new InformationException("Icon is invalid.");
		}
		original.setName(issuePriority.getName());
		original.setDescription(issuePriority.getDescription());
		original.setIcon(issuePriority.getIcon());
		original.setColor(issuePriority.getColor());
		issuePriorityRepository.save(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void update(EnterpriseAuthentication auth, IssueLink issueLink) throws AuthenticationException, InformationException {
		IssueLink original = issueLinkRepository.getIssueLink(auth.getEnterprise(), issueLink.getId());
		if (original == null) {
			throw new InformationException("Issue Link not exist.");
		}
		if (!issueLinkRepository.isNameAvailable(auth.getEnterprise(), issueLink.getName(), original.getId())) {
			throw new InformationException("Name already exist.");
		};
		original.setName(issueLink.getName());
		original.setInwardLink(issueLink.getInwardLink());
		original.setOutwardLink(issueLink.getOutwardLink());
		issueLinkRepository.save(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void update(EnterpriseAuthentication auth, IssueType issueType, boolean standard) throws AuthenticationException, InformationException {
		IssueType original = issueTypeRepository.getIssueType(auth.getEnterprise(), issueType.getId(), standard);
		if (original == null) {
			throw new InformationException("Issue Type not exist.");
		}
		if (!IssueType.isValidIcon(issueType.getIcon())) {
			throw new InformationException("Icon is invalid.");
		}
		if (!issueTypeRepository.isNameAvailable(auth.getEnterprise(), issueType.getName(), original.getId())) {
			throw new InformationException("Name already exist.");
		};
		original.setName(issueType.getName());
		original.setDescription(issueType.getDescription());
		original.setIcon(issueType.getIcon());
		issueTypeRepository.save(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void create(EnterpriseAuthentication auth, IssueLink issueLink) throws AuthenticationException, InformationException {
		if (!issueLinkRepository.isNameAvailable(auth.getEnterprise(), issueLink.getName().toLowerCase(), 0l)) {
			throw new InformationException("Issue Link Name is not available");
		}
		issueLink.setId(null);
		issueLink.setEnterprise(auth.getEnterprise());
		issueLinkRepository.save(issueLink);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void create(EnterpriseAuthentication auth, IssueStatus issueStatus) throws AuthenticationException, InformationException {
		if (!issueStatusRepository.isNameAvailable(auth.getEnterprise(), issueStatus.getName().toLowerCase(), 0l)) {
			throw new InformationException("Issue Status Name is not available");
		}
		if (!IssueStatus.isValidIcon(issueStatus.getIcon())) {
			throw new InformationException("Icon is invalid.");
		}
		issueStatus.setOrder(issueStatusRepository.getOrder(auth.getEnterprise()));
		issueStatus.setId(null);
		issueStatus.setEnterprise(auth.getEnterprise());
		issueStatusRepository.save(issueStatus);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void create(EnterpriseAuthentication auth, IssuePriority issuePriority) throws AuthenticationException, InformationException {
		if (!issuePriorityRepository.isNameAvailable(auth.getEnterprise(), issuePriority.getName().toLowerCase(), 0l)) {
			throw new InformationException("Issue Priority Name is not available");
		}
		if (!IssuePriority.isValidIcon(issuePriority.getIcon())) {
			throw new InformationException("Icon is invalid.");
		}
		issuePriority.setOrder(issuePriorityRepository.getOrder(auth.getEnterprise()));
		issuePriority.setId(null);
		issuePriority.setEnterprise(auth.getEnterprise());
		issuePriorityRepository.save(issuePriority);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void create(EnterpriseAuthentication auth, IssueResolution issueResolution) throws AuthenticationException, InformationException {
		if (!issueResolutionRepository.isNameAvailable(auth.getEnterprise(), issueResolution.getName().toLowerCase(), 0l)) {
			throw new InformationException("Issue Resolution Name is not available");
		}
		issueResolution.setOrder(issueResolutionRepository.getOrder(auth.getEnterprise()));
		issueResolution.setId(null);
		issueResolution.setEnterprise(auth.getEnterprise());
		issueResolutionRepository.save(issueResolution);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void create(EnterpriseAuthentication auth, IssueType issueType) throws AuthenticationException, InformationException {
		if (!issueLinkRepository.isNameAvailable(auth.getEnterprise(), issueType.getName().toLowerCase(), 0l)) {
			throw new InformationException("Name already exist.");
		}
		if (!IssueType.isValidIcon(issueType.getIcon())) {
			throw new InformationException("Icon is invalid.");
		}
		issueType.setEnterprise(auth.getEnterprise());
		issueType.setId(null);
		issueTypeRepository.save(issueType);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void deleteIssueType(EnterpriseAuthentication auth, String issueTypeName, boolean standard) throws AuthenticationException, InformationException {
		IssueType original = issueTypeRepository.getIssueType(auth.getEnterprise(), issueTypeName, standard);
		if (original == null) {
			throw new InformationException("Issue Type not exist.");
		}
		//CHECKE FOR USAGE
		issueTypeRepository.delete(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void deleteIssueStatus(EnterpriseAuthentication auth, String issueStatus) throws AuthenticationException, InformationException {
		IssueStatus original = issueStatusRepository.getIssueStatus(auth.getEnterprise(), issueStatus);
		if (original == null) {
			throw new InformationException("Issue Status not exist.");
		}
		//CHECKE FOR USAGE
		issueStatusRepository.resetOrder(auth.getEnterprise(), original.getOrder());
		issueStatusRepository.delete(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void deleteIssueResolution(EnterpriseAuthentication auth, String issueResolution) throws AuthenticationException, InformationException {
		IssueResolution original = issueResolutionRepository.getIssueResolution(auth.getEnterprise(), issueResolution);
		if (original == null) {
			throw new InformationException("Issue Resolution not exist.");
		}
		//CHECKE FOR USAGE
		issueResolutionRepository.resetOrder(auth.getEnterprise(), original.getOrder());
		issueResolutionRepository.delete(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void deleteIssuePriority(EnterpriseAuthentication auth, String issuePriority) throws AuthenticationException, InformationException {
		IssuePriority original = issuePriorityRepository.getIssuePriority(auth.getEnterprise(), issuePriority);
		if (original == null) {
			throw new InformationException("Issue Priority not exist.");
		}
		//CHECKE FOR USAGE
		issuePriorityRepository.resetOrder(auth.getEnterprise(), original.getOrder());
		issuePriorityRepository.delete(original);
	}
	
	@Transactional
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public void deleteIssueLink(EnterpriseAuthentication auth, String issueLink) throws AuthenticationException, InformationException {
		IssueLink original = issueLinkRepository.getIssueLink(auth.getEnterprise(), issueLink);
		if (original == null) {
			throw new InformationException("Issue Link not exist.");
		}
		//CHECKE FOR USAGE
		issueLinkRepository.delete(original);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<IssueTypeScheme> getIssueTypeSchemes(EnterpriseAuthentication auth) {
		List<IssueTypeScheme> list = issueTypeSchemeRepository.getIssueTypeSchemes(auth.getEnterprise());
		for (IssueTypeScheme scheme : list) {
			scheme.getIssueTypes().size();
			scheme.getProjects().size();
		}
		return list;
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public IssueType getIssueType(EnterpriseAuthentication auth, String name, boolean standard) {
		return issueTypeRepository.getIssueType(auth.getEnterprise(), name, standard);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public IssueLink getIssueLink(EnterpriseAuthentication auth, String name) {
		return issueLinkRepository.getIssueLink(auth.getEnterprise(), name);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public IssueStatus getIssueStatus(EnterpriseAuthentication auth, String name) {
		return issueStatusRepository.getIssueStatus(auth.getEnterprise(), name);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public IssueResolution getIssueResolution(EnterpriseAuthentication auth, String name) {
		return issueResolutionRepository.getIssueResolution(auth.getEnterprise(), name);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public IssuePriority getIssuePriority(EnterpriseAuthentication auth, String name) {
		return issuePriorityRepository.getIssuePriority(auth.getEnterprise(), name);
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<IssueType> getSubTaskTypes(EnterpriseAuthentication auth) {
		return issueTypeRepository.getSubTaskTypes(auth.getEnterprise());
	}

	@Transactional(readOnly = true)
	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<IssueType> getIssueTypes(EnterpriseAuthentication auth) {
		List<IssueType> list = issueTypeRepository.getIssueTypes(auth.getEnterprise());
		for (IssueType type : list) {
			type.getIssueTypeSchemes().size();
		}
		return list;
	}

	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<IssueLink> getIssueLinks(EnterpriseAuthentication auth) {
		return issueLinkRepository.getIssueLinks(auth.getEnterprise());
	}

	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<IssueStatus> getIssueStatus(EnterpriseAuthentication auth) {
		return issueStatusRepository.getIssueStatus(auth.getEnterprise());
	}

	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<IssueResolution> getIssueResolutions(EnterpriseAuthentication auth) {
		return issueResolutionRepository.getIssueResolutions(auth.getEnterprise());
	}

	@PreAuthorize("hasAnyRole('ROLE_GLOBAL_SYSTEM_ADMIN','ROLE_GLOBAL_ADMIN','ROLE_GLOBAL_PROJECT_ADMIN')")
	public List<IssuePriority> getIssuePriorities(EnterpriseAuthentication auth) {
		return issuePriorityRepository.getIssuePriorities(auth.getEnterprise());
	}
	
}
