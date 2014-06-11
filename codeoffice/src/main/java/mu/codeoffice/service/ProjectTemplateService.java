package mu.codeoffice.service;

import java.util.List;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.settings.NotificationScheme;
import mu.codeoffice.entity.settings.ProjectPermissionScheme;
import mu.codeoffice.entity.settings.ProjectTemplate;
import mu.codeoffice.entity.settings.WorkFlow;
import mu.codeoffice.repository.settings.NotificationSchemeRepository;
import mu.codeoffice.repository.settings.ProjectPermissionSchemeRepository;
import mu.codeoffice.repository.settings.ProjectTemplateRepository;
import mu.codeoffice.repository.settings.WorkFlowRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectTemplateService {

	@Resource
	private ProjectTemplateRepository projectTemplateRepository;

	@Resource
	private ProjectPermissionSchemeRepository projectPermissionSchemeRepository;

	@Resource
	private WorkFlowRepository workFlowRepository;
	
	@Resource
	private NotificationSchemeRepository notificationSchemeRepository;
	
	@Transactional(readOnly = true)
	public List<ProjectTemplate> getProjectTemplates(EnterpriseAuthentication auth) {
		return projectTemplateRepository.getProjectTemplates(auth.getEnterprise());
	}

	@Transactional(readOnly = true)
	public ProjectTemplate getProjectTemplate(EnterpriseAuthentication auth, String template) {
		return projectTemplateRepository.getProjectTemplate(auth.getEnterprise(), template);
	}
	
	@Transactional
	public void create(EnterpriseAuthentication auth, ProjectTemplate projectTemplate) throws InformationException {
		if (!projectTemplateRepository.isNameAvailable(auth.getEnterprise(), projectTemplate.getName(), 0l)) {
			throw new InformationException("Project Template Name is not available.");
		}
		if (projectTemplate.getWorkFlow() != null && projectTemplate.getWorkFlow().getId() != null) {
			WorkFlow workFlow = workFlowRepository.getWorkFlow(auth.getEnterprise(), projectTemplate.getWorkFlow().getId());
			if (workFlow == null) {
				throw new InformationException("WorkFlow doens't exist.");
			}
			projectTemplate.setWorkFlow(workFlow);
		} else {
			projectTemplate.setWorkFlow(null);
		}
		if (projectTemplate.getProjectPermissionScheme() != null && projectTemplate.getProjectPermissionScheme().getId() != null) {
			ProjectPermissionScheme projectPermissionScheme = 
					projectPermissionSchemeRepository.getProjectPermissionScheme(auth.getEnterprise(), projectTemplate.getProjectPermissionScheme().getId());
			if (projectPermissionScheme == null) {
				throw new InformationException("Project Permission Scheme doens't exist.");
			}
			projectTemplate.setProjectPermissionScheme(projectPermissionScheme);
		} else {
			projectTemplate.setProjectPermissionScheme(null);
		}
		if (projectTemplate.getNotificationScheme() != null && projectTemplate.getNotificationScheme().getId() != null) {
			NotificationScheme notificationScheme = 
					notificationSchemeRepository.getNotificationScheme(auth.getEnterprise(), projectTemplate.getNotificationScheme().getId());
			if (notificationScheme == null) {
				throw new InformationException("Notification Scheme doens't exist.");
			}
			projectTemplate.setNotificationScheme(notificationScheme);
		} else {
			projectTemplate.setNotificationScheme(null);
		}
		projectTemplate.setId(null);
		projectTemplate.setEnterprise(auth.getEnterprise());
		projectTemplateRepository.save(projectTemplate);
	}
	
	@Transactional
	public void update(EnterpriseAuthentication auth, ProjectTemplate projectTemplate) throws InformationException {
		ProjectTemplate original = projectTemplateRepository.getProjectTemplate(auth.getEnterprise(), projectTemplate.getId());
		if (original == null) {
			throw new InformationException("Project Template not found.");
		}
		if (!projectTemplateRepository.isNameAvailable(auth.getEnterprise(), projectTemplate.getName(), original.getId())) {
			throw new InformationException("Project Template Name is not available.");
		}
		original.setName(projectTemplate.getName());
		original.setDescription(projectTemplate.getDescription());
		if (projectTemplate.getWorkFlow() != null && projectTemplate.getWorkFlow().getId() != null) {
			WorkFlow workFlow = workFlowRepository.getWorkFlow(auth.getEnterprise(), projectTemplate.getWorkFlow().getId());
			if (workFlow == null) {
				throw new InformationException("WorkFlow doens't exist.");
			}
			original.setWorkFlow(workFlow);
		} else {
			original.setWorkFlow(null);
		}
		if (projectTemplate.getProjectPermissionScheme() != null && projectTemplate.getProjectPermissionScheme().getId() != null) {
			ProjectPermissionScheme projectPermissionScheme = 
					projectPermissionSchemeRepository.getProjectPermissionScheme(auth.getEnterprise(), projectTemplate.getProjectPermissionScheme().getId());
			if (projectPermissionScheme == null) {
				throw new InformationException("Project Permission Scheme doens't exist.");
			}
			original.setProjectPermissionScheme(projectPermissionScheme);
		} else {
			original.setProjectPermissionScheme(null);
		}
		if (projectTemplate.getNotificationScheme() != null && projectTemplate.getNotificationScheme().getId() != null) {
			NotificationScheme notificationScheme = 
					notificationSchemeRepository.getNotificationScheme(auth.getEnterprise(), projectTemplate.getNotificationScheme().getId());
			if (notificationScheme == null) {
				throw new InformationException("Notification Scheme doens't exist.");
			}
			original.setNotificationScheme(notificationScheme);
		} else {
			original.setNotificationScheme(null);
		}
		projectTemplateRepository.save(original);
	}
	
	@Transactional
	public void delete(EnterpriseAuthentication auth, String template) throws InformationException {
		ProjectTemplate original = projectTemplateRepository.getProjectTemplate(auth.getEnterprise(), template);
		if (original == null) {
			throw new InformationException("Project Template not found.");
		}
		projectTemplateRepository.delete(original);
	}
	
}
