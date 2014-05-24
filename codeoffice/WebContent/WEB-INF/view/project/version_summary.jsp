<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<script type="text/javascript" src="https://www.google.com/jsapi"></script>

<c:if test="${fn:length(monthlySummary) gt 0}">
<script type="text/javascript">
google.load("visualization", "1", { packages:["corechart"] });
google.setOnLoadCallback(drawChart);
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
<security:authorize access="hasAnyRole('ROLE_PROJECT_MANAGER', 'ROLE_MANAGER', 'ROLE_ADMIN')">
	<c:set var="VC_MANAGER_AUTH" value="true"/>
</security:authorize>
<spring:message var="text_edit" code="application.edit"/>
<spring:message var="text_delete" code="application.delete"/>
<spring:message var="text_start" code="version.start"/>
<spring:message var="text_stop" code="version.stop"/>
<spring:message var="text_release" code="version.release"/>
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/project/version_menu.jsp">
				<jsp:param name="menu" value="summary"/>
			</jsp:include>
			<div class="maincontent">
				<div class="subelement">
					<c:if test="${VC_MANAGER_AUTH}">
						<div class="action-field ">
							<c:if test="${not version.started}">
								<code:imagelink image="icon_start" text="${text_start}" link="enterprise/pro_${project.code}/v_${version.code}/start" width="90"/>
							</c:if>
							<c:if test="${version.started and not version.released}">
								<code:imagelink image="icon_stop" text="${text_stop}" link="enterprise/pro_${project.code}/v_${version.code}/stop" width="90"/>
								<code:imagelink image="icon_release" text="${text_release}" link="enterprise/pro_${project.code}/v_${version.code}/release" width="90"/>
							</c:if>
							<code:imagelink image="icon_edit" text="${text_edit}" link="enterprise/pro_${project.code}/v_${version.code}/edit" width="90"/>
							<code:imagelink image="icon_remove" text="${text_delete}" link="enterprise/pro_${project.code}/v_${version.code}/delete" width="90"/>
						</div>
					</c:if>
					<div class="title"><spring:message code="version.info"/></div>
					<div class="content" style="padding: 15px;">
						<table class="info-table">
							<tr>
								<td class="info-title"><spring:message code="version.code"/></td>
								<td class="info-value">${version.code}</td>
								<td class="info-title">&nbsp;</td>
								<td class="info-value">&nbsp;</td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="version.started"/></td>
								<td class="info-value"><code:checkmark value="${version.started}"/></td>
								<td class="info-title"><spring:message code="version.released"/></td>
								<td class="info-value"><code:checkmark value="${version.released}"/></td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="version.estimated_start_date"/></td>
								<td class="info-value"><fmt:formatDate value="${version.estimatedStart}" pattern="yy-MM-dd"/></td>
								<td class="info-title"><spring:message code="version.estimated_release_date"/></td>
								<td class="info-value"><fmt:formatDate value="${version.estimatedRelease}" pattern="yy-MM-dd"/></td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="version.start_date"/></td>
								<td class="info-value"><fmt:formatDate value="${version.start}" pattern="yy-MM-dd"/></td>
								<td class="info-title"><spring:message code="version.update_date"/></td>
								<td class="info-value"><fmt:formatDate value="${version.update}" pattern="yy-MM-dd HH:mm:ss"/></td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="version.release_date"/></td>
								<td class="info-value"><fmt:formatDate value="${version.release}" pattern="yy-MM-dd HH:mm:ss"/></td>
								<td class="info-title delayed-version"><c:if test="${not empty version.delay}"><spring:message code="version.delayed_to"/></c:if></td>
								<td class="info-value delayed-version"><c:if test="${not empty version.delay}"><fmt:formatDate value="${version.delay}" pattern="yy-MM-dd"/></c:if></td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="version.release_count"/></td>
								<td class="info-value">${version.noRelease}</td>
								<td class="info-title"><spring:message code="version.related_count"/></td>
								<td class="info-value">${version.noRelease}</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="sep-30"></div>
				<div class="subelement">
					<div class="title"><spring:message code="version.description"/></div>
					<div class="content fs-ms" style="padding: 15px;">${version.description}</div>
				</div>
				<div class="sep-30"></div>
				<div class="subelement" style="height: 300px;">
					<div class="title"><spring:message code="version.summary_map"/></div>
					<div class="content">
						<c:if test="${fn:length(monthlySummary) eq 0}"><code:info title="version.no_summary"/></c:if>
						<c:if test="${fn:length(monthlySummary) gt 0}">
							<div id="monthly-summary-chart" class="tab-content"></div>
							<div class="tab-content">
								<span class="fc-bg"><spring:message code="version.cases"/>:</span>
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
					<div class="title"><spring:message code="version.activity_stream"/></div>
					<div class="content" id="activity-stream">
						<c:if test="${empty activityStream}"><code:info title="version.no_activity_stream"/></c:if>
						<c:forEach var="activity" items="${activityStream}">
							...
						</c:forEach>
						<c:if test="${moreActivity}">
						<div class="show-more">
							<a href="javascript:loadActivity();"><spring:message code="version.show_more_activity"/></a>
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
<jsp:include page="/WEB-INF/view/footer.jsp" />