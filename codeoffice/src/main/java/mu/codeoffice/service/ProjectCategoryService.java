package mu.codeoffice.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import mu.codeoffice.common.InformationException;
import mu.codeoffice.entity.Project;
import mu.codeoffice.entity.ProjectCategory;
import mu.codeoffice.entity.RoleGroup;
import mu.codeoffice.repository.ProjectCategoryRepository;
import mu.codeoffice.repository.ProjectRepository;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProjectCategoryService {

	@Resource
	private ProjectRepository projectRepository;
	
	@Resource
	private ProjectCategoryRepository projectCategoryRepository;
	
	public boolean isNameAvailable(String name, EnterpriseAuthentication auth) {
		return projectCategoryRepository.isNameAvailable(name, auth.getEnterprise());
	}

	@Transactional
	@CacheEvict(value = {"projectCategoryCache", "projectCategoryNameCache"}, allEntries = true, beforeInvocation = false)
	public ProjectCategory update(ProjectCategory category, EnterpriseAuthentication auth) throws InformationException {
		if (!projectCategoryRepository.isNameAvailable(category.getName(), auth.getEnterprise())) {
			throw new InformationException("Category with same name already exist.");
		}
		category.setEnterprise(auth.getEnterprise());
		projectCategoryRepository.save(category);
		return category;
	}
	
	@Transactional
	@CacheEvict(value = {"projectCategoryCache", "projectCategoryNameCache"}, allEntries = true, beforeInvocation = false)
	public void remove(Long category, EnterpriseAuthentication auth) throws EnterpriseAuthenticationException, InformationException {
		ProjectCategory projectCategory = projectCategoryRepository.getProjectCategory(category, auth.getEnterprise());
		if (projectCategory == null) {
			throw new EnterpriseAuthenticationException("You have no permission to delete.");
		}
		if (projectCategory.getProjects().size() > 0) {
			throw new InformationException("Unable to delete: several projects are related to this category");
		}
		projectCategoryRepository.delete(projectCategory);
	}
	
	@Cacheable(value = "projectCategoryNameCache", key = "#auth.enterprise.id")
	public List<ProjectCategory> getProjectCategoryNames(EnterpriseAuthentication auth) {
		List<ProjectCategory> categories = projectCategoryRepository.getProjectCategories(auth.getEnterprise());
		categories.add(ProjectCategory.getEmptyCategory());
		return categories;
	}
	
	@Transactional(readOnly = true)
	public List<ProjectCategory> getProjectCategories(EnterpriseAuthentication auth) {
		List<ProjectCategory> categories = projectCategoryRepository.getProjectCategories(auth.getEnterprise());
		ProjectCategory emptyCategory = ProjectCategory.getEmptyCategory();
		emptyCategory.setProjects(projectRepository.getNoneCategorizedProjects(auth.getEnterprise()));
		categories.add(emptyCategory);
		categories.forEach(category -> category.getProjects().size());
		if (auth.hasProjectAuthority()) {
			categories.forEach(category -> category.getProjects().forEach(project -> project.getLead().getId()));
			return categories;
		}
		categories.forEach(category -> category.setProjects(category.getProjects()
				.stream()
				.filter(project -> isProjectVisible(project, auth))
				.collect(Collectors.toList())));
		return categories;
	}

	@Transactional(readOnly = true)
	@Cacheable(value = "projectCategoryCache", key = "#category + #auth.enterprise.code")
	public ProjectCategory getProjectCategory(Long category, EnterpriseAuthentication auth) {
		ProjectCategory projectCategory = null;
		if (category == 0l) {
			projectCategory = ProjectCategory.getEmptyCategory();
			projectCategory.setProjects(projectRepository.getNoneCategorizedProjects(auth.getEnterprise()));
		} else {
			projectCategory = projectCategoryRepository.getProjectCategory(category, auth.getEnterprise());
		}
		if (projectCategory == null) {
			return null;
		}
		projectCategory.getProjects().forEach(project -> project.getLead().getId());
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
