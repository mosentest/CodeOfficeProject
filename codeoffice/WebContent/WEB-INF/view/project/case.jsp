<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="code" uri="http://www.codeoffice.com/codelib"%>
<jsp:include page="/WEB-INF/view/header.jsp">
	<jsp:param name="navigation" value="project"/>
</jsp:include>
<link rel="stylesheet" type="text/css" href="css/project.css">
<div id="content">
	<div class="element">
		<div class="info"><jsp:include page="/WEB-INF/view/project/project_header.jsp"/></div>
		<div class="content">
			<div class="case-header imglink">
				<div class="fl-l">
					<img src="${empty project.iconPath ? 'img/office/project_icon.png' : project.iconPath}" width="50" height="50"/>
				</div>
				<div class="fl-l">
					<div class="fc-b"><span>${project.code}&nbsp;/&nbsp;${caseObject.code}</span></div>
					<div>${caseObject.summary}</div>
				</div>
				<div class="clearfix"></div>
			</div>
			<div class="case-body-main">
				<div class="mainelement">
					<div class="title"><spring:message code="project.c_details"/></div>
					<div class="content">
						<table class="info-table">
							<tr>
								<td class="case-info-title"><spring:message code="project.c_type"/>:</td>
								<td class="case-info-value"><code:enum value="${caseObject.type}"/></td>
								<td class="case-info-title"><spring:message code="project.c_status"/>:</td>
								<td class="case-info-value"><code:enum value="${caseObject.status}"/></td>
							</tr>
							<tr>
								<td class="case-info-title"><spring:message code="project.c_priority"/>:</td>
								<td class="case-info-value"><code:enum value="${caseObject.priority}"/></td>
								<td class="case-info-title"><spring:message code="project.c_resolution"/>:</td>
								<td class="case-info-value"><code:enum text="${caseObject.resolution}"/></td>
							</tr>
							<tr>
								<td class="case-info-title"><spring:message code="project.c_releaseversion"/>:</td>
								<td colspan="3">
									<c:if test="${empty caseObject.releaseVersion}"><spring:message code="project.c_undecided"/></c:if>
									<c:if test="${not empty caseObject.releaseVersion}"><a href="enterprise/pro_${project.code}/version/${caseObject.releaseVersion.code}">${caseObject.releaseVersion.code}</a></c:if>
								</td>
							</tr>
							<tr>
								<td class="case-info-title"><spring:message code="project.c_affectedversions"/>:</td>
								<td colspan="3">
									<c:forEach items="${caseObject.versions}" var="version" varStatus="status">
										<a href="enterprise/pro_${project.code}/version/${version.code}">${version.code}</a>
										<c:if test="${!status.last}">,&nbsp;</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td class="case-info-title"><spring:message code="project.c_components"/>:</td>
								<td colspan="3">
									<c:forEach items="${caseObject.components}" var="component" varStatus="status">
										<a href="enterprise/pro_${project.code}/component/${component.id}">${component.name}</a>
										<c:if test="${!status.last}">,&nbsp;</c:if>
									</c:forEach>
								</td>
							</tr>
							<tr>
								<td class="case-info-title"><spring:message code="project.c_labels"/>:</td>
								<td colspan="3">
									<c:forEach items="${caseObject.labels}" var="label" varStatus="status">
										<a href="enterprise/pro_${project.code}/label/${label.label}">${label.label}</a>
										<c:if test="${!status.last}">,&nbsp;</c:if>
									</c:forEach>
								</td>
							</tr>
						</table>
					</div>
				</div>
				<div class="sep-30"></div>
				<div class="mainelement">
					<div class="title"><spring:message code="project.c_description"/></div>
					<div class="content fs-ms">${caseObject.description}</div>
				</div>
				<c:if test="${not empty caseObject.attachments}">
					<div class="sep-30"></div>
					<div class="mainelement"></div>
				</c:if>
				</div>
				<c:if test="${not empty caseObject.caseLinks}">
					<div class="sep-30"></div>
					<div class="mainelement">
						<div class="title"><spring:message code="project.c_links"/></div>
						<div class="content fs-ms">
							<c:forEach items="${caseObject.caseLinks}" var="link">
								<div class="caselink">
									<div class="caselink-element"><spring:message code="${link.type.code}"/></div>
									<div class="caselink-element">
										<c:forEach items="${link.cases}" var="linkedCase">
											<div style="height: 26px;" class="imglink">
												<code:enum value="${linkedCase.type}"/>
												<div class="text">
													<a href="enterprise/pro_${project.code}/case/${linkedCase.code}" style="${linkedCase.removed ? 'removed-link' : ''">${linkedCase.caseHeader}</a>
												</div>
											</div>
										</c:forEach>
									</div>
									<div class="caselink-element">
										<c:forEach items="${link.cases}" var="linkedCase">
											<div style="height: 26px;"><spring:message code="${linkedCase.status.code}"/></div>
										</c:forEach>
									</div>
									<div class="clearfix"></div>
								</div>
							</c:forEach>
						</div>
					</div>
				</c:if>
				<div class="sep-30"></div>
				<div class="mainelement">
					<div class="title"><spring:message code="project.c_activity"/></div>
					<div class="content fs-ms">
						<ul class="horizontal-tab" style="margin-left: 30px;">
							<li class="${activity eq 'note' ? 'active' : ''}">
								<a href="casenote"><spring:message code="project.c_note"/></a>
							</li>
							<li class="${activity eq 'worklog' ? 'active' : ''}">
								<a href="casenote"><spring:message code="project.c_worklog"/></a>
							</li>
							<li class="${activity eq 'history' ? 'active' : ''}">
								<a href="casenote"><spring:message code="project.c_history"/></a>
							</li>
							<li class="${activity eq 'activity' ? 'active' : ''}">
								<a href="casenote"><spring:message code="project.c_activity"/></a>
							</li>
						</ul>
					</div>
					<div class="tab-content" id="tab-content">
						<c:if test="${activity eq 'note'}">
							<c:if test="${fn:length(notes) eq 0}"><code:info message="project.c_nonote"/></c:if>
							<c:if test="${fn:length(notes) gt 0}">
								<c:forEach items="${notes}" var="note">
								<div class="note-item">
									<div class="note-item-header">
										<code:user user="${note.creator}" width="24" height="24"/>&nbsp;<spring:message code="project.n_addednote"/>
										&nbsp;-&nbsp;<code:date date="${note.create}"/>
									</div>
									<div class="note-item-content activity-item-description">${note.content}</div>
								</div>
							</c:forEach>
							</c:if>
						</c:if>
						<c:if test="${activity eq 'worklog'}">
							<c:if test="${fn:length(worklogs) eq 0}"><code:info message="project.c_noworklog"/></c:if>
							<c:if test="${fn:length(worklogs) gt 0}">
							${fn:length(worklogs)}
							</c:if>
						</c:if>
						<c:if test="${activity eq 'history'}">
							<c:if test="${fn:length(histories) eq 0}"><code:info message="project.c_nohistory"/></c:if>
							<c:if test="${fn:length(histories) gt 0}">
							
							</c:if>
						</c:if>
						<c:if test="${activity eq 'activity'}">
							<c:if test="${fn:length(activities) eq 0}"><code:info message="project.c_noactivity"/></c:if>
							<c:if test="${fn:length(activities) gt 0}">
								<c:forEach items="${activities}" var="activity">
									<util:caseActivity caseObject="#{case}" activityObject="#{activity}"/>
								</c:forEach>
							</c:if>
						</c:if>
						<div layout="block" rendered="#{caseBean.activity eq 'history' and not empty case.histories}">
							<div class="history-element-header" layout="block">
								<div class="field" layout="block"><h:outputText value="#{office.case_historyfield}"/></div>
								<div class="old" layout="block"><h:outputText value="#{office.case_historyoldvalue}"/></div>
								<div class="new" layout="block"><h:outputText value="#{office.case_historynewvalue}"/></div>
								<div class="clearfix" layout="block"></div>
							</div>
							<h:form rendered="#{caseBean.activity eq 'history' and not empty case.histories}">
							<div layout="block">
								<ui:repeat value="#{case.histories}" var="history">
									<div class="history-item" layout="block">
											<div layout="block">
												<util:employee employee="#{history.creator}" link="true" width="24" height="24"/>#{' '}
												<h:outputText value="#{office.case_madechanges}"/>#{' - '}
												<code:date date="#{history.create}" recentForm="true" shortFormat="HH:mm" fullFormat="yy-MM-dd HH:mm"/>
											</div>
											<ui:repeat value="#{history.elements}" var="element">
												<div class="history-item-content" layout="block">
													<div class="field" layout="block"><h:outputText value="#{enum[element.type.code]}"/></div>
													<div class="old" layout="block"><h:outputText value="#{element.oldValue}"/></div>
													<div class="new" layout="block"><h:outputText value="#{element.newValue}"/></div>
													<div class="clearfix" layout="block"></div>
												</div>
											</ui:repeat>
										</div>
									</ui:repeat>
								</div>
								</h:form>
							</div>
							<h:form rendered="#{caseBean.activity eq 'activity' and not empty case.activities}">
							<div layout="block">
							</div>
							</h:form>
						</div>
						<div class="clearfix" layout="block"></div>
				</div>
			</div>
			<div class="case-body-right fl-l">
				<div class="mainelement" layout="block">
					<div class="title" layout="block"><h:outputText value="#{office.case_people}"/></div>
					<div class="content fs-ms" layout="block">
						<h:form>
						<h:panelGrid class="info-table" columns="2" columnClasses="case_finfo-title,case_finfo-value">
							<div><h:outputText value="#{office.case_assignee}"/>:</div>
							<div><util:employee employee="#{case.assignee}" link="true" space="true" width="30" height="30"/></div>
							
							<div><h:outputText value="#{office.case_reporter}"/>:</div>
							<div><util:employee employee="#{case.reporter}" link="true" space="true" width="30" height="30"/></div>
							
							<div><h:outputText value="#{office.case_participants}"/>:</div>
							<div>...</div>
							
							<div><h:outputText value="#{office.case_watchers}"/>:</div>
							<div>...</div>
						</h:panelGrid>
						</h:form>
					</div>
				</div>
				<div class="sep-30" layout="block"></div>
				<div class="mainelement" layout="block">
					<div class="title" layout="block"><h:outputText value="#{office.case_dates}"/></div>
					<div class="content fs-ms" layout="block">
						<h:panelGrid class="info-table" columns="2" columnClasses="case_finfo-title,case_finfo-value">
							<div><h:outputText value="#{office.case_create}"/>:</div>
							<div><code:date date="#{case.create}" shortFormat="HH:mm" fullFormat="yy-MM-dd HH:mm"/></div>
							
							<div><h:outputText value="#{office.case_update}"/>:</div>
							<div><code:date date="#{case.update}" shortFormat="HH:mm" fullFormat="yy-MM-dd HH:mm"/></div>
							
							<div rendered="#{not empty case.close}"><h:outputText value="#{office.case_close}"/>:</div>
							<div rendered="#{not empty case.close}"><code:date date="#{case.close}" shortFormat="HH:mm" fullFormat="yy-MM-dd HH:mm"/></div>
						</h:panelGrid>
					</div>
				</div>
				<div class="sep-30" layout="block"></div>
				<div class="mainelement" layout="block">
					<div class="title" layout="block"><h:outputText value="#{office.case_timeestimation}"/></div>
					<div class="content fs-ms" layout="block">
						<h:panelGrid class="info-table" columns="2" columnClasses="case-finfo-title,case-finfo-value">
						</h:panelGrid>
					</div>
				</div>
			</div>
			<div class="clearfix" layout="block"/>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />