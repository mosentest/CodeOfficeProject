<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<script type="text/javascript">
	function submitGoto() {
		var value = $('#goto').val();
		if (isNaN(value)) {
			alert("Invalid page");
			return;
		}
		if (value <= 1) {
			window.location = 'enterprise/pro_${project.code}/case';
		} else {
			window.location = 'enterprise/pro_${project.code}/case/' + value;
		}
	}
</script>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/version_menu.jsp">
				<jsp:param name="menu" value="releasenote"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="assets/img/office/icon_notes.png"/><span class="titlespan"><spring:message code="version.releasenote"/></span></div>
					<div class="content">
						<c:if test="${empty releaseNote}"><code:info title="version.no_releasenote"/></c:if>
						<c:if test="${not empty releaseNote}">
							<c:forEach items="${releaseNote}" var="note">
								<div class="releasenote">
									<div class="title"><code:enum value="${note.key}"/></div>
									<div class="content">
										<ul>
											<c:forEach items="${note.value}" var="c">
												<li class="fs-ms">[<a href="enterprise/pro_${project.code}/${c.code}">${c.code}</a>]&nbsp;-&nbsp;${c.summary}</li>
											</c:forEach>
										</ul>
									</div>
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