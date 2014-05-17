package mu.codeoffice.controller;

import mu.codeoffice.enums.ProjectPermission;
import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.Permission;
import mu.codeoffice.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

public abstract class ProjectPermissionRequired implements PermissionRequired {

	@Autowired
	protected ProjectService projectService;
	
	@Override
	public void authorize(EnterpriseAuthentication auth, Object object,
			Permission... authorities) throws EnterpriseAuthenticationException {
		if (auth.hasProjectAuthority()) {
			return;
		}
		if (object == null) {
			throw new EnterpriseAuthenticationException("You are not authorized.");
		}
		String project = (String) object;
		if (!auth.projectAuthenticate(projectService.getProjectAuthority(auth.getEnterpriseUser(), project), (ProjectPermission[]) authorities)) {
			throw new EnterpriseAuthenticationException("You are not authorized.");
		}
	}
	
	protected boolean hasAuthority(EnterpriseAuthentication auth, Object object, Permission...authorities) {
		try {
			authorize(auth, object, authorities);
			return true;
		} catch (AuthenticationException e) {
			return false;
		}
	}

}
