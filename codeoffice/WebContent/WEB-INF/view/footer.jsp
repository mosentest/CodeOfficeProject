<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
</body>
<footer>
<div id="footer">
	<div id="copyright">
	<spring:message code="application.copyright"/>
	<br />
	<br />
	<spring:message code="application.mail"/> <a class="link" href="mailto:<spring:message code="application.email"/>">:<spring:message code="application.email"/></a>
	<br />
	<br />
	<br />
	<img src="assets/img/icon-enterprise.png"/>
	</div>
</div>
</footer>
</html>