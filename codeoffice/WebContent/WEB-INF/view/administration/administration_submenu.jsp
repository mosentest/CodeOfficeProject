<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="navigation" value="selected"/>
<ul class="horizontal-navigation">
<li class="${param.menu eq 'system' ? navigation : ''}"><a class="link" href="administration/system.html"><spring:message code="administration.system"/></a></li>
<li class="${param.menu eq 'addons' ? navigation : ''}"><a class="link" href="administration/add-ons.html"><spring:message code="administration.addons"/></a></li>
<li class="${param.menu eq 'user' ? navigation : ''}"><a class="link" href="administration/userManagement.html"><spring:message code="administration.user_management"/></a></li>
<li class="${param.menu eq 'project' ? navigation : ''}"><a class="link" href="administration/project.html"><spring:message code="administration.project"/></a></li>
<li class="${param.menu eq 'issue' ? navigation : ''}"><a class="link" href="administration/issueType.html"><spring:message code="administration.issue"/></a></li>
</ul>
<div class="clearfix"></div>