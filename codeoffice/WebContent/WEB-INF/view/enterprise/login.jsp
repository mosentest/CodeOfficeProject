<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp" />
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
<div id="main">
	<div class="element">
		<div class="title"><spring:message code="page.login"/></div>
		<div class="content">
			<div id="login" class="border">
				<form action="<c:url value='j_spring_security_check'/>" method="POST">
					<table class="default-table">
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
								<div style="float: left; "><input type="checkbox" name="rememberme" /><span><spring:message code="page.rememberme"/></span></div>
								<div style="float: right; margin-right: 82px;"><input class="button" type="submit" value="<spring:message code="page.login"/>"/></div>
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />