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
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/enterprise/project/project_header.jsp"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/version_menu.jsp">
				<jsp:param name="menu" value="summary"/>
			</jsp:include>
			<div class="maincontent">
				<div class="subelement">
					<div class="title"><spring:message code="project.p_info"/></div>
					<div class="content" style="padding: 15px;">
						<table class="info-table">
							<tr>
								<td class="info-title"><spring:message code="project.p_code"/></td>
								<td class="info-value"><span class="${project.removed ? 'removed-link' : ''}">${project.code}</span></td>
								<td class="info-title"><c:if test="${project.removed}"><spring:message code="project.projectremoved"/></c:if></td>
								<td class="info-value">&nbsp;</td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="project.p_name"/></td>
								<td class="info-value"><span>${project.name}</span></td>
								<td class="info-title"><spring:message code="project.p_lead"/></td>
								<td class="info-value"><code:user user="${project.lead}"/></td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="project.p_completed"/></td>
								<td class="info-value"><span class="imglink"><code:checkmark value="${project.completed}"/></span></td>
								<td class="info-title"><spring:message code="project.p_created"/></td>
								<td class="info-value"><code:date date="${project.create}"/></td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="project.p_nouser"/></td>
								<td class="info-value"><span>${project.noUser}</span></td>
								<td class="info-title"><spring:message code="project.p_updated"/></td>
								<td class="info-value"><code:date date="${project.update}"/></td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="project.p_nocase"/></td>
								<td class="info-value"><span>${project.noCase}</span></td>
								<td class="info-title"><spring:message code="project.p_estend"/></td>
								<td class="info-value"><code:date date="${project.end}"/></td>
							</tr>
							<tr>
								<td class="info-title"><spring:message code="project.p_visibility"/></td>
								<td class="info-value"><span>${project.visibilityType.name}</span></td>
								<td class="info-title">&nbsp;</td>
								<td class="info-value">&nbsp;</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="sep-30"></div>
				<div class="subelement">
					<div class="title"><spring:message code="project.p_description"/></div>
					<div class="content fs-ms" style="padding: 15px;">${project.description}</div>
				</div>
				<div class="sep-30"></div>
				<div class="subelement" style="height: 420px;">
					<div class="title"><spring:message code="project.p_summarymap"/></div>
					<div class="content">
						<c:if test="${fn:length(monthlySummary) eq 0}"><code:info message="project.nosummary"/></c:if>
						<c:if test="${fn:length(monthlySummary) gt 0}">
							<div id="monthly-summary-chart" class="tab-content"></div>
							<div class="tab-content">
								<span class="fc-bg"><spring:message code="project.p_cases"/>:</span>
								<span style="color: red; font-weight: bold;">${monthlySummary[fn:length(monthlySummary) - 1].noCount}</span>
								<span class="fc-bg">&nbsp;created and&nbsp;</span>
								<span style="color: green; font-weight: bold;">${monthlySummary[fn:length(monthlySummary) - 1].noResolved}</span>
								<span class="fc-bg">&nbsp;resolved</span>
							</div>
						</c:if>
					</div>
				</div>
			</div>
		</div>
		<div class="element-block">
			<div class="content">
				<div class="subelement">
					<div class="title"><spring:message code="project.activitystream"/></div>
					<div class="content" id="activity-stream">
						<c:if test="${empty activityStream}"><code:info message="project.noprojectactivity"/></c:if>
						<c:forEach var="activity" items="${activityStream}">
							...
						</c:forEach>
						<c:if test="${moreActivity}">
						<div class="show-more">
							<a href="javascript:loadActivity();"><spring:message code="project.showmoreactivity"/></a>
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