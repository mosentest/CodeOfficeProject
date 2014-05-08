<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<jsp:include page="/WEB-INF/view/header.jsp" />
<div id="main">
	<div class="element">
		<div class="title"><spring:message code="vote.vote"/></div>
		<div class="content">
			<form action="vote" method="POST">
				<table id="vote-form" class="form-table">
					<tr class="title">
						<td><span class="title">${vote.title}</span></td>
					</tr>
					<c:forEach items="${vote.voteOptions}" var="voteOption" varStatus="status">
						<tr class="option">
							<td>
							<span>${status.index}.</span>
							<c:if test="${vote.enableMultipleSelection}">
								<input type="checkbox" name="option" value="${voteOption.id}"/>
							</c:if>
							<c:if test="${not vote.enableMultipleSelection}">
								<input type="radio" name="option" value="${voteOption.id}"/>
							</c:if>
							<span>${voteOption.option}</span>
							</td>
						</tr>
					</c:forEach>
					<tr class="separator-tr"><td> </td></tr>
					<tr>
						<td><input class="button largebutton" type="submit" value="<spring:message code="vote.submitvote" />"/></td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />