package mu.codeoffice.utility;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import mu.codeoffice.security.SessionObject;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AccessInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		Object object = request.getSession().getAttribute("SESSION");
		if (object != null) {
			SessionObject sessionObject = (SessionObject) object;
			sessionObject.access();
		}
		return true;
	}
	
}
