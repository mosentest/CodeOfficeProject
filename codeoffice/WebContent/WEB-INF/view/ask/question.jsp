<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ch" uri="http://www.codeoffice.com/chlib" %>
<jsp:include page="/WEB-INF/view/header.jsp" />
<link rel="stylesheet" type="text/css" href="css/ask.css"/>
<link rel="stylesheet" type="text/css" href="css/sh/shCore.css" />
<link rel="stylesheet" type="text/css" href="css/sh/shThemeDefault.css" />
<script type="text/javascript" src="js/sh/shCore.js"></script>
<script type="text/javascript" src="js/jquery.form.min.js"></script>
<script type="text/javascript" src="js/sh/shBrushJScript.js"></script>
<script type="text/javascript" src="js/sh/shBrushJava.js"></script>
<script type="text/javascript">
	SyntaxHighlighter.all();
	
	function accept(id) {
		clearMessages();
		$.ajax({
			url: 'ask/' + id + '/accept',
			type: 'POST',
			success: function(data) {
				if (data == '') {
					showNoticeMessage('<spring:message code="ask.solutionaccepted"/>');
					setTimeout(function() { location.reload(); }, 3000);
				} else {
					showErrorMessage(data);
				}
			},
			error: function(data) {
				showErrorMessage(data);
			}
		});
		$('#s_' + id).submit();
	}
	
	function vote(type, positive, id) {
		clearMessages();
		var v = positive ? 'u' : 'd';
		var url = "ask/" + id + "/vote_" + v + "_" + type;
		$.ajax({
			url: url,
			type: 'POST',
			success: function(data) {
				if(!$.isNumeric(data)) {
					showErrorMessage(data);
				} else {
					if (type == 'q') {
						$('#q_vote_' + id).html(data);
					} else if (type == 's') {
						$('#s_vote_' + id).html(data);
					} else if (type == 'c') {
						data = "+" + data;
						$('#c_vote_' + id).html(data);
					}
				} 
			},
			error: function(data) {
				showErrorMessage(data);
			}
		});
		return false;
	}
	
	function openDialog(id) {
		clearMessages();
		$('#' + id).dialog({
			autoOpen: true,
			height: 160,
			width: 500,
			modal: true,
			title: '<spring:message code="ask.comment"/>',
			buttons: {
				'<spring:message code="ask.comment"/>': function() {
					var form = $('#' + id).find('form').first();
					$.ajax({
						url: form.attr('action'),
						type: 'POST',
						data: form.serialize(),
						success: function(data) {
							$('#' + id).dialog('close');
							if (data == null || data == '') {
								showErrorMessage('<spring:message code="login_required"/>');
								setTimeout(function() { window.location.href='login'; }, 3000);
							} else if (data.id == 0) {
								showErrorMessage('<spring:message code="ask.couldnotcomment"/>');
							} else {
								showNoticeMessage('<spring:message code="ask.createdcomment"/>');
							}
							setTimeout(function() { location.reload(); }, 3000);
						}
					});
				},
				Cancel: function() {
					$(this).dialog('close');
				}
			}
		});
	}
	
	$(document).ready(function() {
		$("input[name='comment']").keyup(function(e) {
			if (e.keyCode == 13) {
				$('.ui-dialog-buttonpane button:first').click();
				e.preventDefault();
				return false;
			}
		});
	});
	
</script>
<div class="hidden message_div" id="message"></div>
<div class="main_holder">
	<div class="element">
		<div class="element_title">${question.title}</div>
		<div class="content">
			<div class="q">
				<div class="votesec">
					<ul>
						<li class="lvu"><a class="avu" href="
							<c:if test="${currentUser != null}">javascript:vote('q', true, ${question.id});</c:if>
							<c:if test="${currentUser == null}">javascript:void(0);</c:if>" title="<spring:message code="ask.qvu"/>">&nbsp;</a></li>
						<li class="lvn" id="q_vote_${question.id}">${question.votes}</li>
						<li class="lvd"><a class="avd" href="
							<c:if test="${currentUser != null}">javascript:vote('q', false, ${question.id});</c:if>
							<c:if test="${currentUser == null}">javascript:void(0);</c:if>" title="<spring:message code="ask.qvd"/>">&nbsp;</a></li>
					</ul>
				</div>
				<div class="contentsec">${question.description}
					<div class="codewrapper"><pre class="brush: js">
						function foo()
						{
						}
					</pre>
					</div>
				</div>
				<div class="clearfix sep_30"></div>
				<div class="commentsec">
					<div class="fc_lg fs_ms">
						<ul id="q_c">
							<c:forEach items="${question.comments}" var="comment" varStatus="status">
								<li class="li_sep <c:if test="${status.index == 0}">li_first</c:if>">
									<span id="c_vote_${comment.id}" class="fc_b fw_b"><c:if test="${comment.votes > 0}">+${comment.votes}</c:if></span>
									${comment.comment}&nbsp;---
									<a href="#">${comment.creator.username}</a>
									<span class="fc_g"><ch:date date="${comment.create}" format="yy-MM-dd"/></span>
									<c:if test="${currentUser != null}">
										<span><a href="javascript:vote('c', true, ${comment.id})" title="<spring:message code="ask.like"/>">+</a></span>
									</c:if>
								</li>
							</c:forEach>
						</ul>
					</div>
				</div>
				<div class="clearfix sep_30"></div>
				<c:if test="${currentUser != null}">
					<div><input class="buttons" type="button" onClick="openDialog('c_q_${question.id}');" value="<spring:message code="ask.comment"/>"/></div>
					<div id="c_q_${question.id}" class="hidden">
						<form onkeypress="return event.keyCode != 13;" action="ask/${question.id}/comment_q" method="POST">
							<fieldset>
								<input type="text" name="comment" size="36" />
							</fieldset>
						</form>
					</div>
				</c:if>
			</div>
			<div class="sh">
				<c:if test="${fn:length(question.solutions) > 0}">
					${fn:length(question.solutions)}&nbsp;<spring:message code="ask.solutions"/>
				</c:if>
				<c:if test="${fn:length(question.solutions) == 0}">
					<spring:message code="ask.nosol"/>
				</c:if>
			</div>
			<c:if test="${fn:length(question.solutions) > 0}">
				<c:forEach items="${question.solutions}" var="solution">
					<div class="s">
						<div class="votesec">
							<ul>
								<li class="lvu"><a class="avu" href="
									<c:if test="${currentUser != null}">javascript:vote('s', true, ${solution.id});</c:if>
									<c:if test="${currentUser == null}">javascript:void(0);</c:if>" title="<spring:message code="ask.svu"/>">&nbsp;</a></li>
								<li class="lvn" id="s_vote_${solution.id}">${solution.votes}</li>
								<li class="lvd"><a class="avd" href="
									<c:if test="${currentUser != null}">javascript:vote('s', false, ${solution.id});</c:if>
									<c:if test="${currentUser == null}">javascript:void(0);</c:if>" title="<spring:message code="ask.svd"/>">&nbsp;</a></li>
								<c:if test="${solution.accepted}">
									<li class="lac"><a class="aac" href="javscript:void(0);" title="<spring:message code="ask.accepted"/>">&nbsp;</a></li>
								</c:if>
								<c:if test="${!question.solved and question.creator.id == currentUser.id}">
									<li class="lua"><a class="aua" href="javascript:accept(${solution.id});" title="<spring:message code="ask.accept"/>"></a></li>
								</c:if>
							</ul>
						</div>
						<div class="contentsec">${solution.solution}</div>
						<div class="clearfix sep_30"></div>
						<div class="usersec">
							<span class="fs_s fl_l clear_fix"><ch:date date="${solution.create}" format="yy-MM-dd"/></span>
							<span class="clearfix fl_l">
								<a href="#"><img src="img/profile/${solution.creator.profilePath}" class="g_imglink"/></a>
								<a href="#" class="g_namelink">${solution.creator.username}</a>
							</span>
							<span class="clearfix fl_l">
								<span class="fs_xs fc_g" title="<spring:message code="page.reputation"/>"><spring:message code="page.r"/>:</span>
								<span class="fs_xs fc_b">${solution.creator.reputation}</span>
								<span class="fs_xs fc_g" title="<spring:message code="page.contribution"/>"><spring:message code="page.c"/>:</span>
								<span class="fs_xs fc_b">${solution.creator.contribution}</span>
								<span class="fs_xs fc_g" title="<spring:message code="page.experience"/>"><spring:message code="page.e"/>:</span>
								<span class="fs_xs fc_b">${solution.creator.experience}</span>
							</span>
						</div>
						<div class="clearfix sep_30"></div>
						<div class="commentsec">
							<div class="fc_lg fs_ms">
								<ul id="s_c_${solution.id}">
									<c:forEach items="${solution.comments}" var="comment" varStatus="status">
										<li class="li_sep  <c:if test="${status.index == 0}">li_first</c:if>">
											<span id="c_vote_${comment.id}" class="fc_b fw_b"><c:if test="${comment.votes > 0}">+${comment.votes}</c:if></span>
											${comment.comment}&nbsp;---
											<a href="#">${comment.creator.username}</a>
											<span class="fc_g"><ch:date date="${comment.create}" format="yy-MM-dd"/></span>
											<c:if test="${currentUser != null}">
												<span><a href="javascript:vote('c', true, ${comment.id})" title="<spring:message code="ask.like"/>">+</a></span>
											</c:if>
										</li>
									</c:forEach>
								</ul>
							</div>
						</div>
						<div class="clearfix sep_30"></div>
						<c:if test="${currentUser != null}">
							<div><input class="buttons" type="button" onClick="openDialog('c_s_${solution.id}');" value="<spring:message code="ask.comment"/>"/></div>
							<div id="c_s_${solution.id}" class="hidden">
								<form onkeypress="return event.keyCode != 13;" action="ask/${solution.id}/comment_s" method="POST">
									<fieldset>
										<input type="text" name="comment" size="36" />
									</fieldset>
								</form>
							</div>
							<div class="clearfix sep_30"></div>
						</c:if>
					</div>
				</c:forEach>
			</c:if>
		</div>
	</div>
</div>
<div class="right_holder">
	<div class="element">
		<div id="question_info">
			<table class="w_100p">
				<c:if test="${question.solved}">
					<tr>
						<td colspan="2"><a class="aac" href="javascript:void(0);" title="<spring:message code="ask.solved"/>"></a></td>
					</tr>
				</c:if>
				<tr>
					<td colspan="2"><spring:message code="ask.askedby"/></td>
				</tr>
				<tr>
					<td colspan="2">
						<a href="#"><img src="img/profile/${question.creator.profilePath}" class="g_imglink"/></a>
						<a href="#" class="g_namelink">${question.creator.username}</a>
					</td>
				</tr>
				<tr>
					<td class="w_50p fs_xs"><spring:message code="ask.created"/></td>
					<td class="w_50p fs_s fc_hb"><ch:date date="${question.create}" format="yy-MM-dd"/></td>
				</tr>
				<tr>
					<td class="w_50p fs_xs"><spring:message code="ask.updated"/></td>
					<td class="w_50p fs_s fc_hb"><ch:date date="${question.update}" format="yy-MM-dd"/></td>
				</tr>
				<tr>
					<td class="w_50p fs_xs"><spring:message code="ask.expirein"/></td>
					<td class="w_50p fs_s fc_hb"><fmt:formatDate value="${question.expire}" type="both" pattern="yyyy-MM-dd"/></td>
				</tr>
				<tr><td colspan="2" class="spacetr"></td></tr>
				<tr>
					<td><span class="w_75 fl_l fc_g fs_xs"><spring:message code="ask.views"/></span><span class="fs_s fc_hb fw_b">&nbsp;${question.views}</span></td>
					<td><span class="w_75 fl_l fc_g fs_xs"><spring:message code="ask.contribution"/>&nbsp;</span><span class="fs_s fc_hb fw_b">+${question.contribution}</span></td>
				</tr>
				<tr>
					<td><span class="w_75 fl_l fc_g fs_xs"><spring:message code="ask.votes"/></span><span class="fs_s fc_hb fw_b">&nbsp;${question.votes}</span></td>
					<td><span class="w_75 fl_l fc_g fs_xs"><spring:message code="ask.exp"/>&nbsp;</span><span class="fs_s fc_hb fw_b">+${question.experience}</span></td>
				</tr>
				<tr>
					<td><span class="w_75 fl_l fc_g fs_xs"><spring:message code="ask.solutions"/></span><span class="fs_s fc_hb fw_b">&nbsp;${question.answers}</span></td>
					<td><span class="w_75 fl_l fc_g fs_xs"><spring:message code="ask.coin"/>&nbsp;</span><span class="fs_s fc_hb fw_b">+${question.coin}</span></td>
				</tr>
				<tr><td colspan="2" class="spacetr"></td></tr>
				<tr>
					<td colspan="2"><spring:message code="ask.tags"/></td>
				</tr>
				<c:forEach items="${question.tags}" var="tag">
					<tr>
						<td colspan="2">
							<div class="tagholder">
								<span class="tag"><a href="tag/${tag.tag}">${tag.tag}</a></span>
								<span class="tag_x">x</span>
								<span class="tag_c">${tag.count}</span>
							</div>
						</td>
					</tr>
				</c:forEach>
			</table>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />