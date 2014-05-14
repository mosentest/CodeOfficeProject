<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<div class="imglink">
	<c:if test="${not empty project}">
	<div class="fl-l">
		<img src="${empty project.iconPath ? 'img/office/project_icon.png' : project.iconPath}" width="50" height="50"/>
	</div>
	<div class="fl-l">
		<div class="fs-ml">${project.name}</div>
		<div class="imglink">
			<span class="info-span"><spring:message code="project.p_code"/>:&nbsp;</span><a href="enterprise/pro_${project.code}">${project.code}</a><span class="minorspace"></span>
			<span class="info-span"><spring:message code="project.p_category"/>:&nbsp;</span><a href="enterprise/pcategory/${project.category.id}">${project.category.name}</a><span class="minorspace"></span>
			<span class="info-span"><spring:message code="project.p_lead"/>:&nbsp;</span><code:user user="${project.lead}" showImage="false" showSpace="false" width="24" height="24"/>
		</div>
	</div>
	<div class="clearfix"></div>
	</c:if>
</div>