package mu.codeoffice.controller;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.Permission;

public interface PermissionRequired {

	public void authorize(EnterpriseAuthentication auth, Object object, Permission...authorities) throws EnterpriseAuthenticationException;
	
}
