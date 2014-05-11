package mu.codeoffice.security;

import org.springframework.security.core.AuthenticationException;

public class EnterpriseAuthenticationException extends AuthenticationException {

	private static final long serialVersionUID = -6335330927327116863L;
	
	public EnterpriseAuthenticationException(String message) {
		super(message);
	}

}
