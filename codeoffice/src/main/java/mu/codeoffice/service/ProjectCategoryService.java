package mu.codeoffice.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import mu.codeoffice.common.ServiceResponse;
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

	@Transactional
	@CacheEvict(value = {"projectCategoryCache", "projectCategoryNameCache"}, allEntries = true, beforeInvocation = false)
	public ServiceResponse deleteProjectCategory(Long category, EnterpriseAuthentication auth) throws Exception {
		ProjectCategory projectCategory = projectCategoryRepository.getProjectCategory(category, auth.getEnterprise());
		if (!auth.hasEnterpriseAuthority(projectCategory.getEnterprise())) {
			throw new EnterpriseAuthenticationException("You have no access to this project category.");
		}
		projectCategoryRepository.delete(category);
		return ServiceResponse.SUCCESS;
	}
	
	@Transactional
	@CacheEvict(value = {"projectCategoryCache", "projectCategoryNameCache"}, allEntries = true, beforeInvocation = false)
	public ServiceResponse updateProjectCategory(Long category, String name, EnterpriseAuthentication auth) throws Exception {
		ProjectCategory projectCategory = projectCategoryRepository.getProjectCategory(category, auth.getEnterprise());
		if (!auth.hasEnterpriseAuthority(projectCategory.getEnterprise())) {
			throw new EnterpriseAuthenticationException("You have no access to this project category.");
		}
		if (projectCategoryRepository.doesCategoryNameExist(name, auth.getEnterprise())) {
			throw new Exception("Project category with same name already exist.");
		}
		projectCategory.setName(name);
		projectCategoryRepository.save(projectCategory);
		return ServiceResponse.SUCCESS;
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
