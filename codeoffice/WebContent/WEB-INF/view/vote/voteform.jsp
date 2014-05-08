<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<jsp:include page="/WEB-INF/view/header.jsp" />
<link rel="stylesheet" type="text/css" href="css/vote.css">
<script type="text/javascript">
	var rows = 3;
	$(document).ready(function() {
		$('#addoption').click(function() {
			if (rows > 12) {
				alert("Maximum 12 rows allowed");
				return;
			}
			rows++;
			var row = "<tr class=\"option\"><td><span>" + rows + ".</span><input id=\"voteOptions\" name=\"voteOptions[" + rows + "].option\" placeholder=\"Enter vote option...\" class=\"non-decorated-input option\" type=\"text\" size=\"60\"></td></tr>";
			$("#vote-form tr:nth-child(" + (rows + 1) +")").after(row);
		});
	});
</script>
<div id="main">
	<div class="element">
		<div class="title"><spring:message code="vote.createvote"/></div>
		<div class="content">
			<form:form action="vote" method="POST" modelAttribute="vote">
				<table id="vote-form" class="form-table">
					<tr>
						<td class="errorMessage"></td>
					</tr>
					<tr class="title">
						<td><form:input path="title" size="60" placeholder="Enter vote title..." class="non-decorated-input title"/></td>
					</tr>
					<tr class="option">
						<td><span>1.</span><form:input path="voteOptions[0].option" size="60" placeholder="Enter vote option..." class="non-decorated-input option"/></td>
					</tr>
					<tr class="option">
						<td><span>2.</span><form:input path="voteOptions[1].option" size="60" placeholder="Enter vote option..." class="non-decorated-input option"/></td>
					</tr>
					<tr class="option">
						<td><span>3.</span><form:input path="voteOptions[2].option" size="60" placeholder="Enter vote option..." class="non-decorated-input option"/></td>
					</tr>
					<tr>
						<td align="right"><input type="button" class="button" id="addoption" value="<spring:message code="vote.addoption"/>"/></td>
					</tr>
					<tr class="separator-tr"><td> </td></tr>
					<tr>
						<td><form:checkbox path="enableIpRestriction" value=""/><span><spring:message code="vote.enableiprestriction"/></span></td>
					</tr>
					<tr>
						<td><form:checkbox path="enableMultipleSelection" value=""/><span><spring:message code="vote.enablemultipleselection"/></span></td>
					</tr>
					<tr>
						<td><form:checkbox path="anonymous" value=""/><span><spring:message code="vote.anonymous"/></span></td>
					</tr>
					<tr class="separator-tr"><td> </td></tr>
					<tr>
						<td><input class="button largebutton" type="submit" value="<spring:message code="vote.create" />"/></td>
					</tr>
				</table>
			</form:form>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />