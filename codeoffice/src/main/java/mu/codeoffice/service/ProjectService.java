package mu.codeoffice.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;
import javax.transaction.Transactional;

import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectCategory;
import mu.codeoffice.entity.RoleGroup;
import mu.codeoffice.repository.ProjectCategoryRepository;
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.security.EnterpriseAuthentication;

import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

	@Resource
	private ProjectRepository projectRepository;
	
	@Resource
	private ProjectCategoryRepository projectCategoryRepository;
	
	public List<ProjectCategory> getProjectCategoryNames(User user) {
		EnterpriseAuthentication auth = (EnterpriseAuthentication) user;
		List<ProjectCategory> categories = projectCategoryRepository.getProjectCategories(auth.getEnterprise());
		categories.add(ProjectCategory.getEmptyCategory());
		return categories;
	}
	
	@Transactional
	public List<ProjectCategory> getProjectCategories(User user) {
		EnterpriseAuthentication auth = (EnterpriseAuthentication) user;
		List<ProjectCategory> categories = projectCategoryRepository.getProjectCategories(auth.getEnterprise());
		ProjectCategory emptyCategory = ProjectCategory.getEmptyCategory();
		emptyCategory.setProjects(projectRepository.getNoneCategorizedProjects(auth.getEnterprise()));
		categories.add(emptyCategory);
		categories.forEach(category -> category.getProjects().size());
		if (auth.hasProjectAuthority()) {
			return categories;
		}
		categories.forEach(category -> category.setProjects(category.getProjects()
				.stream()
				.filter(project -> isProjectVisible(project, auth))
				.collect(Collectors.toList())));
		return categories;
	}
	
	@Transactional
	public ProjectCategory getProjectCategory(Long category, User user) {
		EnterpriseAuthentication auth = (EnterpriseAuthentication) user;
		ProjectCategory projectCategory = null;
		if (category == 0l) {
			projectCategory = ProjectCategory.getEmptyCategory();
			projectCategory.setProjects(projectRepository.getNoneCategorizedProjects(auth.getEnterprise()));
		} else {
			projectCategory = projectCategoryRepository.getProjectCategory(category, auth.getEnterprise());
		}
		projectCategory.getProjects().size();
		if (auth.hasProjectAuthority()) {
			return projectCategory;
		}
		projectCategory.setProjects(projectCategory.getProjects()
				.stream()
				.filter(project -> isProjectVisible(project, auth))
				.collect(Collectors.toList()));
		return projectCategory;
	}
	
	private boolean isProjectVisible(Project project, EnterpriseAuthentication authentication) {
		project.getLead().getId();
		for (RoleGroup roleGroup : project.getRoleGroups()) {
			if (roleGroup.getUsers().contains(authentication.getEnterpriseUser())) {
				return true;
			}
		}
		return false;
	}
	
	
}