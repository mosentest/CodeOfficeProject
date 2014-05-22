<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
</body>
<footer>
<div id="footer">
	<div id="copyright">
	<spring:message code="application.copyright"/>
	<br />
	<spring:message code="application.mail"/> <a href="mailto:<spring:message code="application.email"/>">:<spring:message code="application.email"/></a>
	</div>
</div>
</footer>
</html>