<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="co" uri="http://www.codeoffice.com/colib"%>
<div class="imglink">
	<div class="fl-l">
		<img src="${empty project.iconPath ? 'img/office/project_icon.png' : project.iconPath}" width="50" height="50"/>
	</div>
	<div class="fl-l">
		<div class="fs-ml">${project.name}</div>
		<div class="imglink">
			<span class="info-span"><spring:message code="project.p_code"/>:&nbsp;<span class="fc-b">${project.code}</span></span>
			<span class="info-span"><spring:message code="project.p_category"/>:&nbsp;<a href="enterprise/pcategory/${project.category.id}">${project.category.name}</a></span>
			<span class="info-span"><spring:message code="project.p_lead"/>:&nbsp;<co:user user="${project.lead}" width="24" height="24"/></span>
		</div>
	</div>
	<div class="clearfix"></div>
</div>