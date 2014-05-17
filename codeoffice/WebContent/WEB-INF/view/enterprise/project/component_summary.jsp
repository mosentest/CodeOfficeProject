<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<style type="text/css">
	#merge-form {
		display: inline;
	}
</style>
<c:if test="${fn:length(monthlySummary) gt 0}">
<script type="text/javascript">
google.load("visualization", "1", { packages:["corechart"] });
google.setOnLoadCallback(drawChart);
function submitMerge() {
	$('#merge-form').submit();
}
function drawChart() {
	
	var monthlyDataTable = new google.visualization.DataTable();
	monthlyDataTable.addColumn('string', 'Date');
	monthlyDataTable.addColumn({type: 'string', role: 'annotation'});
	monthlyDataTable.addColumn('number', 'Total');
	monthlyDataTable.addColumn('number', 'Resolved');
	monthlyDataTable.addRows([<code:jssummary summary="${monthlySummary}"/>
	]);
	
	var options = { 
		annotations : {
			style : 'line'
		},
		vAxis: {
			minValue : 0,
			format: '#'
		}
	};

	var monthlyChart = new google.visualization.AreaChart(document.getElementById("monthly-summary-chart"));
	monthlyChart.draw(monthlyDataTable, options);
}
</script>
</c:if>
<spring:message var="text_edit" code="application.edit"/>
<spring:message var="text_delete" code="application.delete"/>
<spring:message var="text_merge" code="application.merge"/>
<security:authorize access="hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_MANAGER', 'ROLE_ADMIN')">
	<c:set var="VC_MANAGER_AUTH" value="true"/>
</security:authorize>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/component_menu.jsp">
				<jsp:param name="menu" value="summary"/>
			</jsp:include>
			<div class="maincontent">
				<div class="subelement">
					<div class="title imglink">
						<span><spring:message code="component.info"/></span>
						<c:if test="${VC_MANAGER_AUTH}">
							<span class="minorspace"></span>
							<a href="enterprise/pro_${project.code}/m_${component.code}/edit"><img src="img/icon_edit.png" title="${text_edit}"/></a>
							<span class="minorspace"></span>
							<form:form id="merge-form" action="enterprise/pro_${project.code}/m_merge" modelAttribute="mergeComponent" method="POST">
								<form:hidden path="project" value="${project.code}"/>
								<input type="hidden" name="targetComponent" value="${component.code}"/>
								<a href="javascript:submitMerge();"><img src="img/icon_merge.png" title="${text_merge}"/></a>
							</form:form>
							<span class="minorspace"></span>
							<a href="enterprise/pro_${project.code}/m_${component.code}/delete"><img src="img/icon_remove.png" title="${text_delete}"/></a>
						</c:if>
					</div>
					<div class="content" style="padding: 15px;">
						<table class="info-table">
							<tr>
								<td class="info-title"><spring:message code="component.code"/></td>
								<td class="info-value">${component.code}</td>
								<td class="info-title"><spring:message code="component.name"/></td>
								<td class="info-value">${component.name}</td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="component.lead"/></td>
								<td class="info-value"><code:user user="${component.lead}" showImage="false" showSpace="false"/></td>
								<td class="info-title"><spring:message code="component.case_count"/></td>
								<td class="info-value">${component.noCase}</td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="component.default_reporter"/></td>
								<td class="info-value"><code:user user="${component.defaultReporter}" showImage="false" showSpace="false"/></td>
								<td class="info-title"><spring:message code="component.default_assignee"/></td>
								<td class="info-value"><code:user user="${component.defaultAssignee}" showImage="false" showSpace="false"/></td>
							</tr>
						</table>
					</div>
				</div>
				<div class="sep-30"></div>
				<div class="subelement">
					<div class="title"><spring:message code="component.description"/></div>
					<div class="content fs-ms" style="padding: 15px;">${component.description}</div>
				</div>
				<div class="sep-30"></div>
				<div class="subelement" style="height: 300px;">
					<div class="title"><spring:message code="component.summary_map"/></div>
					<div class="content">
						<c:if test="${fn:length(monthlySummary) eq 0}"><code:info message="component.no_summary"/></c:if>
						<c:if test="${fn:length(monthlySummary) gt 0}">
							<div id="monthly-summary-chart" class="tab-content"></div>
							<div class="tab-content">
								<span class="fc-bg"><spring:message code="component.cases"/>:</span>
								<span style="color: red; font-weight: bold;">${monthlySummary[fn:length(monthlySummary) - 1].noCount}</span>
								<span class="fc-bg">&nbsp;created and&nbsp;</span>
								<span style="color: green; font-weight: bold;">${monthlySummary[fn:length(monthlySummary) - 1].noResolved}</span>
								<span class="fc-bg">&nbsp;resolved</span>
							</div>
						</c:if>
					</div>
				</div>
				<div class="sep-30"></div>
				<div class="subelement">
					<div class="title"><spring:message code="component.activity_stream"/></div>
					<div class="content" id="activity-stream">
						<c:if test="${empty activityStream}"><code:info message="component.no_activity_stream"/></c:if>
						<c:forEach var="activity" items="${activityStream}">
							...
						</c:forEach>
						<c:if test="${moreActivity}">
						<div class="show-more">
							<a href="javascript:loadActivity();"><spring:message code="component.show_more_activity"/></a>
						</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<div class="clearfix"></div>
	</div>
	<div class="clearfix"></div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />