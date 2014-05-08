<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/view/header.jsp" />
<link rel="stylesheet" type="text/css" href="css/euler.css">
<div class="dashboard_element form">
	<div class="dashboard_element_title"><spring:message code="problemlist.problems"/></div>
	<div class="dashboard_element_content">
		<div class="dashboart_element_description">
			<img src="img/euler/bock.png" width="20" height="20"/> = <spring:message code="problem.solved"/>
		</div>
		<table id="problemlist_table">
			<thead>
				<tr>
					<th class="standardHeader"><spring:message code="problemlist.id"/></th>
					<th class="standardHeader"><spring:message code="problemlist.title"/></th>
					<th class="standardHeader"><spring:message code="problemlist.rating"/></th>
					<th class="standardHeader"><spring:message code="problemlist.success"/></th>
				</tr>
			</thead>
			<tbody>
			<c:forEach items="${problems.items}" var="problem" varStatus="status">
				<tr class="<c:if test="${status.index % 2 == 0}">odd</c:if><c:if test="${status.index % 2 == 1}">even</c:if>">
					<td style="text-align: center;">
						<c:if test="${problem.solved}"><img src="img/euler/bock.png" title="<spring:message code="problem.hassolved"/>" width="20" height="20"/></c:if>
						<c:if test="${problem.type eq 0}">${problem.defaultID}</c:if>
						<c:if test="${problem.type eq 1}">${problem.id}</c:if>
					</td>
					<td><a href="problem/${problem.id}">${problem.title}</a></td>
					<td style="text-align: center;">${problem.rating}</td>
					<td style="text-align: center;">${problem.successes}</td>
				</tr>
			</c:forEach>
			</tbody>
			<tfoot>
				<tr>
					<td colspan="4" style="text-align: right;">
						<div class="page_holder">
							<c:if test="${problems.totalPages le 20}">
								<c:forEach begin="1" end="${problems.totalPages}" var="i">
									<div class="page_div <c:if test="${i eq problems.currentPage}">current_page</c:if>"><a class="page_link" href="problems/${i - 1}">${i}</a></div>
								</c:forEach>
							</c:if>
							<c:if test="${problems.totalPages gt 20}">
								<c:forEach items="${problems.pages}" var="page">
									<div class="page_div"><a class="page_link" href="#">${page}</a></div>
								</c:forEach>
							</c:if>
						</div>
					</td>
				</tr>
			</tfoot>
		</table>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />