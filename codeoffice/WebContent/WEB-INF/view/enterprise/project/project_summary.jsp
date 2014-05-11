<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="co" uri="http://www.codeoffice.com/colib"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<link rel="stylesheet" type="text/css" href="css/project.css">
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<script type="text/javascript" src="https://www.google.com/jsapi"></script>
<script type="text/javascript">

	function loadActivity() {
		
	}
	
	google.load("visualization", "1", { packages:["corechart"] });
	google.setOnLoadCallback(drawChart);
	function drawChart() {
		var weeklyDataTable = new google.visualization.DataTable();
		weeklyDataTable.addColumn('string', 'Date');
		weeklyDataTable.addColumn({type: 'string', role: 'annotation'});
	    weeklyDataTable.addColumn('number', 'Total');
	    weeklyDataTable.addColumn('number', 'Resolved');
	    weeklyDataTable.addRows([
			<jsp:include page="/WEB-INF/view/enterprise/project/projectsummary_js.jsp">
				<jsp:param name="summary" value="${weeklySummary}"/>
			</jsp:include>
		]);
		
		var monthlyDataTable = new google.visualization.DataTable();
		monthlyDataTable.addColumn('string', 'Date');
		monthlyDataTable.addColumn({type: 'string', role: 'annotation'});
		monthlyDataTable.addColumn('number', 'Total');
		monthlyDataTable.addColumn('number', 'Resolved');
		monthlyDataTable.addRows([
			<jsp:include page="/WEB-INF/view/enterprise/project/projectsummary_js.jsp">
				<jsp:param name="summary" value="${monthlySummary}"/>
			</jsp:include>
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

		var weeklyChart = new google.visualization.AreaChart(document.getElementById("project-weekly-summary-chart"));
		weeklyChart.draw(weeklyDataTable, options);

		var monthlyChart = new google.visualization.AreaChart(document.getElementById("project-monthly-summary-chart"));
		monthlyChart.draw(monthlyDataTable, options);
	}
</script>
<div id="content">
	<div class="element">
		<div class="info"><spring:message code="project.home"/></div>
		<div class="content">
			<jsp:include page="/WEB-INF/view/enterprise/project/project_menu.jsp">
				<jsp:param name="menu" value="summary"/>
			</jsp:include>
			<div class="maincontent">
				<div class="element-block">
					<div class="content">
						<div class="subelement">
							<div class="title"><spring:message code="project.p_info"/></div>
							<div class="content">
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
										<td class="info-value"><co:user user="${project.lead}"/></td>
									</tr>
									<tr>
										<td class="info-title"><spring:message code="project.p_completed"/></td>
										<td class="info-value"><span class="imglink"><co:checkmark value="${project.completed}"/></span></td>
										<td class="info-title"><spring:message code="project.p_created"/></td>
										<td class="info-value"><co:date date="${project.create}"/></td>
									</tr>
									<tr>
										<td class="info-title"><spring:message code="project.p_nouser"/></td>
										<td class="info-value"><span>${project.noUser}</span></td>
										<td class="info-title"><spring:message code="project.p_updated"/></td>
										<td class="info-value"><co:date date="${project.update}"/></td>
									</tr>
									<tr>
										<td class="info-title"><spring:message code="project.p_nocase"/></td>
										<td class="info-value"><span>${project.noCase}</span></td>
										<td class="info-title"><spring:message code="project.p_estend"/></td>
										<td class="info-value"><co:date date="${project.end}"/></td>
									</tr>
									<tr>
										<td class="info-title"><spring:message code="project.p_visibility"/></td>
										<td class="info-value"><span>${project.visibilityType.name}</span></td>
										<td class="info-title">&nbsp;</td>
										<td class="info-value">&nbsp;</td>
									</tr>
								</table>
								<div class="sep-30"></div>
								<div class="fw-b fc-bg fs-s"><spring:message code="project.p_description"/></div>
								<div class="fs-ms">${project.description}</div>
							</div>
						</div>
						<div class="sep-30"></div>
						<div class="subelement">
							<div class="title"><spring:message code="project.p_summarymap"/></div>
							<div class="content">
								<div id="project-monthly-summary-chart"></div>
								<div id="project-weekly-summary-chart"></div>
								<div class="fs-ms" id="project-monthly-summary-info">
									<div class="fc-bg"><spring:message code="project.p_cases"/>:</div>
									<div style="color: red; font-weight: bold;">${monthlySummary[fn:length(monthlySummary) - 1].noCount}</div>
									<div class="fc-bg">&nbsp;created and&nbsp;</div>
									<div style="color: green; font-weight: bold;">${monthlySummary[fn:length(monthlySummary) - 1].noResolved}</div>
									<div class="fc-bg">&nbsp;resolved</div>
								</div>
								<div class="fs-ms" id="project-weekly-summary-info">
									<div class="fc-bg"><spring:message code="project.p_cases"/>:</div>
									<div style="color: red; font-weight: bold;">${weeklySummary[fn:length(weeklySummary) - 1].noCount}</div>
									<div class="fc-bg">&nbsp;created and&nbsp;</div>
									<div style="color: green; font-weight: bold;">${weeklySummary[fn:length(weeklySummary) - 1].noResolved}</div>
									<div class="fc-bg">&nbsp;resolved</div>
								</div>
							</div>
						</div>
						<div class="sep-30"></div>
						<div class="subelement">
							<div class="title"><spring:message code="project.p_unreleasedversions"/></div>
							<div class="content">
								<table class="default-table">
									<tr>
										<th class="fit-cell"><spring:message code="project.v_name"/></th>
										<th><spring:message code="project.v_release"/></th>
									</tr>
									<c:forEach items="${unreleasedVersions}" var="version">
									<tr>
										<td class="fit-cell"><a href="enterprise/version/${version.code}">${version.name}</a></td>
										<td>
										<c:if test="${empty version.delay}">
											<co:date date="${v.release}"/>
										</c:if>
										<c:if test="${not empty version.delay}">
											<span class="delayed-version"><spring:message code="project.v_delayedto"/>&nbsp;
											<co:date date="${v.delay}"/></span>
										</c:if>
										</td>
									</tr>
									</c:forEach>
								</table>
							</div>
						</div>
					</div>
				</div>
				<div class="element-block">
					<div class="content">
						<div class="subelement">
							<div class="title"><spring:message code="project.activitystream"/></div>
							<div class="content" id="activity-stream">
								<c:if test="${empty project.activities}">
									<div class="info-element imglink">
										<img src="img/info.png"/>
										<span><spring:message code="project.noprojectactivity"/></span>
									</div>
								</c:if>
								<c:forEach var="activity" items="${project.activities}">
									...
								</c:forEach>
								<c:if test="${moreActivity}">
								<div class="show-more">
									<a href="javascript:loadActivity();"><spring:message code="${project.showmoreactivity}"/></a>
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
	</div>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />