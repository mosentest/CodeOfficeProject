d<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ch" uri="http://www.codeoffice.com/chlib" %>
<jsp:include page="/WEB-INF/view/header.jsp" />
<link rel="stylesheet" type="text/css" href="css/ask.css">
<script type="text/javascript">
	function search() {
		$('#search-dialog').dialog('open');
	}
	
	$(document).ready(function() {
		 $('#search-dialog').dialog({
		      autoOpen: false,
		      height: 300,
		      width: 350,
		      modal: true
		 });
	});
</script>
<div class="main_holder">
	<div class="element">
		<div class="element_title"><spring:message code="ask.featured"/></div>
		<div class="content">
			<c:forEach items="${featured.items}" var="question" varStatus="status">
				<div class="bb_lg_s p_10">
					<div>
						<span class="fl_l">
							<a href="ask/${question.id}">${question.title}</a>
							<c:if test="${question.solved}"><a class="aac" href="javascript:void(0);" title="<spring:message code="ask.solved"/>"></a></c:if>
						</span>
						<span class="fl_r fc_g fs_xs"><ch:date date="${question.create}"/></span>
					</div>
					<div class="fs_s clearfix">${question.description}</div>
					<div class="w_780">
						<div class="fl_l">
							<span class="fc_g fs_xs"><spring:message code="ask.views"/></span><span class="fc_b fs_ms fw_b">&nbsp;${question.views}</span>
							<span class="fc_g fs_xs"><spring:message code="ask.votes"/></span><span class="fc_b fs_ms fw_b">&nbsp;${question.votes}</span>
							<span class="fc_g fs_xs"><spring:message code="ask.solutions"/></span><span class="fc_b fs_ms fw_b">&nbsp;${question.answers}</span>
						</div>
						<div class="fl_r">
							<span class="fc_g fs_xs"><spring:message code="ask.contribution"/>&nbsp;</span><span class="fc_b fs_ms fw_b">+${question.contribution}</span>
							<span class="fc_g fs_xs"><spring:message code="ask.exp"/>&nbsp;</span><span class="fc_b fs_ms fw_b">+${question.experience}</span>
							<span class="fc_g fs_xs"><spring:message code="ask.coin"/>&nbsp;</span><span class="fc_b fs_ms fw_b">+${question.coin}</span>
						</div>
					</div>
					<div class="clearfix"></div>
					<div class="w_780">
						<div class="fl_l fs_s"><spring:message code="ask.tagged"/>:</div>
						<c:forEach items="${question.tags}" var="tag">
							<div class="tagholder_s fl_l">
								<a class="tag_s" href="ask/tag/${tag.tag}">${tag.tag}</a>
							</div>
						</c:forEach>
					</div>
					<div class="clearfix"></div>
				</div>
			</c:forEach>
			<table>
				<tr><td style="text-align: right;"><ch:page link="ask/list" limit="20" page="${featured}"/></td></tr>
			</table>
		</div>
	</div>
</div>
<div class="right_holder">
	<div class="element">
		<div id="ask_option">
			<input class="buttons" type="button" onclick="search();" value="   <spring:message code="ask.search"/>   " />
			<input class="buttons" type="button" value="      <spring:message code="ask.ask"/>      "/>
		</div>
		<div id="search-dialog">123</div>
	</div>
	<div class="sep_30"></div>
	<div class="element">
		<div id="tags">
			<div class="element_title"><spring:message code="ask.populartags"/></div>
			<div class="sep_30"></div>
			<table>
				<c:forEach items="${tags}" var="tag">
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
				<tr>
					<td colspan="2" class="right"><a href="ask/tags"><spring:message code="page.seeall"/></a></td>
				</tr>
			</table>
		</div>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />