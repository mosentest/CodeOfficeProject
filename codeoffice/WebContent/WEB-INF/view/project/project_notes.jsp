<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/project_menu.jsp">
				<jsp:param name="menu" value="notes"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="assets/img/office/icon_notes.png"/><span class="titlespan"><spring:message code="project.notes"/></span></div>
					<div class="content">
						<c:if test="${fn:length(notes) eq 0}"><code:info title="project.no_notes"/></c:if>
						<c:if test="${fn:length(notes) gt 0}">
						<c:forEach items="notes" var="note">
							<div class="note-item">
								<div class="note-item-header">
									<code:user user="${note.creator}" width="24" height="24"/>&nbsp;<spring:message code="project.added_note"/>
									&nbsp;-&nbsp;<code:date date="${note.create}"/>
								</div>
								<div class="note-item-content activity-item-description">${note.content}</div>
							</div>
						</c:forEach>
						</c:if>
					</div>
				</div>
			</div>
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />