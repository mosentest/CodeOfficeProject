<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="co" uri="http://www.codeoffice.com/colib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/project_menu.jsp">
				<jsp:param name="menu" value="notes"/>
			</jsp:include>
			<div class="maincontent">
				<c:if test="${fn:length(notes) eq 0}">
					<div class="info-element imglink">
						<img src="img/info.png"/>
						<span><spring:message code="project.nonotes"/></span>
					</div>
				</c:if>
				<c:if test="${fn:length(notes) gt 0}">
				<c:forEach items="notes" var="note">
					<div class="note-item">
						<div class="note-item-header">
							<co:user user="${note.creator}" width="24" height="24"/>&nbsp;<spring:message code="project.n_addednote"/>
							&nbsp;-&nbsp;<co:date date="${note.create}"/>
						</div>
						<div class="note-item-content activity-item-description">${note.content}</div>
					</div>
				</c:forEach>
				</c:if>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />