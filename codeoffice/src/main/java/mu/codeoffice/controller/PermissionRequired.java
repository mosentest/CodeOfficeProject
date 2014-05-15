package mu.codeoffice.controller;

import mu.codeoffice.security.EnterpriseAuthentication;
import mu.codeoffice.security.EnterpriseAuthenticationException;
import mu.codeoffice.security.Permission;

public abstract class PermissionRequired {

	protected abstract void authorize(EnterpriseAuthentication auth, Object object, Permission...authorities) throws EnterpriseAuthenticationException;
	
}
