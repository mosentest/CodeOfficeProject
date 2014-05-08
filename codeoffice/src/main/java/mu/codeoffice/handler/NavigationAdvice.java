package mu.codeoffice.handler;

import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

@Aspect
public class NavigationAdvice {

	@Before("execution(* mu.codeoffice.common.controller.*.*(..))")
	public void home(JoinPoint joinPoint) {
		setNavigation(joinPoint, "home");
	}

	@Before("execution(* mu.codeoffice.ask.controller.*.*(..))")
	public void ask(JoinPoint joinPoint) {
		setNavigation(joinPoint, "ask");
	}

	@Before("execution(* mu.codeoffice.euler.controller.*.*(..))")
	public void projectEuler(JoinPoint joinPoint) {
		setNavigation(joinPoint, "euler");
	}

	@Before("execution(* mu.codeoffice.code.controller.*.*(..))")
	public void codeShare(JoinPoint joinPoint) {
		setNavigation(joinPoint, "code");
	}

	@Before("execution(* mu.codeoffice.article.controller.*.*(..))")
	public void article(JoinPoint joinPoint) {
		setNavigation(joinPoint, "article");
	}

	@Before("execution(* mu.codeoffice.forum.controller.*.*(..))")
	public void forum(JoinPoint joinPoint) {
		setNavigation(joinPoint, "forum");
	}
	
	private void setNavigation(JoinPoint joinPoint, String value) {
		setSessionVariable(joinPoint, "navigation", value);
	}
	
	private void setSessionVariable(JoinPoint joinPoint, String attr, String value) {
		for (Object arg : joinPoint.getArgs()) {
			if (arg instanceof HttpSession) {
				HttpSession session = (HttpSession) arg;
				session.setAttribute(attr, value);
				break;
			}
		}
	}
	
}
