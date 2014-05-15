<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
</body>
<footer>
<div id="footer">
	<div id="copyright">
	<spring:message code="page.copyright"/>
	<br />
	<spring:message code="page.mail"/> <a href="mailto:<spring:message code="page.email"/>">:<spring:message code="page.email"/></a>
	</div>
</div>
</footer>
</html>