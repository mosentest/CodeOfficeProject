<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
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
				<jsp:param name="menu" value="roadmap"/>
			</jsp:include>
			<div class="maincontent">
				<div class="mainelement">
					<div class="title imglink"><img src="img/office/icon_roadmap.png"/><span class="titlespan"><spring:message code="project.p_roadmap"/></span></div>
					<div class="content">
						<c:forEach items="${roadMap}" var="version">
							<div class="roadmap-item">
								<div class="fl-l p_3">
									<div class="fw-b imglink p_3">
										<c:if test="${version.key.released}"><img src="img/office/icon_version_released.png"/></c:if>
										<c:if test="${not version.key.released}"><img src="img/office/icon_version_unreleased.png"/></c:if>
										<a href="enterprise/pro_${project.code}/v_${version.key.code}">${version.key.code}</a>
									</div>
									<div class="fs-ms p_3">${version.key.description}</div>
									<div class="fc-g p_3">
										<spring:message code="project.v_release"/>:&nbsp;<code:date date="${version.key.release}"/>
									</div>
									<c:if test="${not empty version.key.delay}">
										<div class="delayed-version p_3">
											<spring:message code="project.v_delayedto"/>:&nbsp;<code:date date="${version.key.delay}"/>
										</div>
									</c:if>
									<div class="p_3"><a href="enterprise/pro_${project.code}/r_${version.key.code}"><spring:message code="project.v_releasenote"/></a>
									</div>
								</div>
								<div class="fl-r p_3">
									<code:percent number="${version.value}" total="${version.key.noRelease}" background="red" color="green" width="500" showPercentage="false"/>
									<div class="fs-ms fc-bg" style="margin-left: 10px;">
										<a href="enterprise/pro_${project.code}/v_${version.key.code}">${version.value}</a>
										&nbsp;of&nbsp;
										<a href="enterprise/pro_${project.code}/v_${version.key.code}">${version.key.noRelease}</a>
										&nbsp;<spring:message code="project.v_casesresolved"/>
									</div>
								</div>
								<div class="clearfix"></div>
							</div>
						</c:forEach>
					</div>
				</div></div>
			
			<div class="clearfix"></div>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />