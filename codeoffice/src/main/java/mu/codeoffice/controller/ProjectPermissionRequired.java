package mu.codeoffice.controller;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.Permission;
import mu.codeoffice.service.ProjectService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;

public abstract class ProjectPermissionRequired implements PermissionRequired {

	@Autowired
	protected ProjectService projectService;
	
	@Override
	public void authorize(EnterpriseAuthentication auth, Object object,
			Permission... permissions) throws AuthenticationException {
	}
	
	protected boolean hasAuthority(EnterpriseAuthentication auth, Object object, Permission...permissions) {
		try {
			authorize(auth, object, permissions);
			return true;
		} catch (AuthenticationException e) {
			return false;
		}
	}

}
