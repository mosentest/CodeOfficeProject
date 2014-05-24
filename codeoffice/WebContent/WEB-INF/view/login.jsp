<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="/WEB-INF/view/header.jsp" />
<style type="text/css">
#login {
	width: 800px;
	margin: 20px auto;
}
#login .default-table {
	width: 400px;
	margin: 0px auto;
}
</style>
<div id="content">
	<div class="sub-element">
		<div class="sub-element-info">
		<div class="sub-element-title"><spring:message code="application.login"/></div>
		</div>
		<div class="sub-element-content">
			<div id="login" class="border">
				<form action="<c:url value='j_spring_security_check'/>" method="POST">
					<table class="default-table form-table">
						<tr>
							<td colspan="2" class="errorMessage">${error}</td>
						</tr>
						<tr>
							<td><label for="account">Account/Email</label></td>
							<td><input type="text" name="j_username" id="account"/></td>
						</tr>
						<tr>
							<td><label for="password">Password</label></td>
							<td><input type="password" name="j_password" id="password"/></td>
						</tr>
						<tr>
							<td colspan="2" style="vertical-align: middle;">
								<div style="float: left; "><input type="checkbox" name="rememberme" /><span><spring:message code="application.rememberme"/></span></div>
								<div style="float: right; margin-right: 82px;"><input class="button" type="submit" value="<spring:message code="application.login"/>"/></div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />