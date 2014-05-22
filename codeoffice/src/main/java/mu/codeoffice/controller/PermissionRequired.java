package mu.codeoffice.controller;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.Permission;

import org.springframework.security.core.AuthenticationException;

public interface PermissionRequired {

	public void authorize(EnterpriseAuthentication auth, Object object, Permission...permissions) throws AuthenticationException;
	
}
