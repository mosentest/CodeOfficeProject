<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="selectedClass" >class="selected"</c:set>
<ul class="horizontal-navigation">
<li ${param.menu eq 'system' ? selectedClass : ''}><a class="link" href="administration/system.html"><spring:message code="administration.system"/></a></li>
<li ${param.menu eq 'addons' ? selectedClass : ''}><a class="link" href="administration/add-ons.html"><spring:message code="administration.addons"/></a></li>
<li ${param.menu eq 'user' ? selectedClass : ''}><a class="link" href="administration/userManagement.html"><spring:message code="administration.userManagement"/></a></li>
<li ${param.menu eq 'project' ? selectedClass : ''}><a class="link" href="administration/project.html"><spring:message code="administration.project"/></a></li>
<li ${param.menu eq 'issue' ? selectedClass : ''}><a class="link" href="administration/issue.html"><spring:message code="administration.issue"/></a></li>
</ul>
<div class="clearfix"></div>