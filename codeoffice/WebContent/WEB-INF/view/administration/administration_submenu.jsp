<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<div class="sub-menu-link"><a class="link full-link ${param.menu eq 'system' ? 'active' : ''}" href="administration/system.html"><spring:message code="administration.system"/></a></div>
<div class="sub-menu-link"><a class="link full-link ${param.menu eq 'addons' ? 'active' : ''}" href="administration/add-ons.html"><spring:message code="administration.addons"/></a></div>
<div class="sub-menu-link"><a class="link full-link ${param.menu eq 'user' ? 'active' : ''}" href="administration/userManagement.html"><spring:message code="administration.user_management"/></a></div>
<div class="sub-menu-link"><a class="link full-link ${param.menu eq 'project' ? 'active' : ''}" href="administration/project.html"><spring:message code="administration.project"/></a></div>
<div class="sub-menu-link"><a class="link full-link ${param.menu eq 'issue' ? 'active' : ''}" href="administration/issueType.html"><spring:message code="administration.issue"/></a></div>
<div class="clearfix"></div>