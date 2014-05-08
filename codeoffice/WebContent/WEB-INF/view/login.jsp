<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/view/header.jsp" />
<style type="text/css">
#login .infolink {
	font-size: 12px;
}
#login {
	width: 600px; 
	margin: 90px auto;
}
.title_column{
	width: 200px;
}
#button {
	text-align:right;
}
</style>
<div id="main">
	<div class="element">
		<div class="element_title"><spring:message code="page.login"/></div>
		<div class="element_content">
			<div id="login" class="border">
				<form action="login" method="POST">
					<table class="form_table">
						<tr>
							<td colspan="2" class="errorMessage">${error}</td>
						</tr>
						<tr>
							<td class="title_column"><label for="account">account</label></td>
							<td><input type="text" name="account" id="account"/></td>
						</tr>
						<tr>
							<td class="title_column"><label for="password">password</label></td>
							<td><input type="password" name="password" id="password"/></td>
						</tr>
						<tr>
							<td colspan="2" style="font-size: 12px;"><spring:message code="page.rememberme"/><input type="checkbox" name="rememberme" /></td>
						</tr>
						<tr>
							<td><a href="" class="infolink"><spring:message code="page.forgotpass"/></a></td>
							<td id="button"><input class="buttons" type="submit" value="<spring:message code="page.login"/>"/></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />