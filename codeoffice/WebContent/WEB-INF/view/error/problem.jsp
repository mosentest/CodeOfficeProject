<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<jsp:include page="/WEB-INF/view/header.jsp" />
<link rel="stylesheet" type="text/css" href="css/euler.css">
<script type="text/javascript">
	function cancelRating() {
		$('#rating_content').hide();
	}
	$(function() {
		$('#rating_slider').slider({
			range: "min",
			step: 0.1,
			value: 5,
			min: 0.1,
			max: 10,
			slide: function(event, ui) {
				$('#rating').val(parseFloat(ui.value).toFixed(1));
			}
		});
		$('#rating').val(parseFloat($('#rating_slider').slider("value")).toFixed(1));
	});
</script>
<div class="dashboard_element form">
	<div class="dashboard_element_title">
		${problem.title}&nbsp;
		<c:if test="${problem.solved}">
			<img src="img/euler/bock.png" title="<spring:message code="problem.hassolved"/>" width="25" height="25"/>
		</c:if>
	</div>
	<div class="dashboard_element_content">
		<div id="problem_information">
			<table id="problem_information_table">
				<tr>
					<td colspan="4"><c:if test="${problem.type == 0}"><spring:message code="problem.problem"/>&nbsp;<span class="info_span">${problem.defaultID}</span></c:if></td>
				</tr>
				<tr>
					<td class="title_col"><spring:message code="problem.experience"/></td>
					<td class="info_col"><span class="info_span">${problem.experience}</span></td>
					<td class="title_col"><spring:message code="problem.create_date"/></td>
					<td class="info_col"><span class="info_span"><fmt:formatDate value="${problem.create}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></td>
				</tr>
				<tr>
					<td class="title_col"><spring:message code="problem.coin"/></td>
					<td class="info_col"><span class="info_span">${problem.coin}</span></td>
					<td class="title_col"><spring:message code="problem.rating"/></td>
					<td class="info_col"><span class="info_span">${problem.rating}</span></td>
				</tr>
				<tr>
					<td class="title_col"><spring:message code="problem.attempts"/></td>
					<td class="info_col"><span class="info_span">${problem.attempts}</span></td>
					<td class="title_col"><spring:message code="problem.shares"/></td>
					<td class="info_col"><span class="info_span">${problem.shares}</span>&nbsp;<spring:message code="problem.times"/></td>
				</tr>
				<tr>
					<td class="title_col"><spring:message code="problem.successes"/></td>
					<td class="info_col"><span class="info_span">${problem.successes}</span></td>
					<td class="title_col"></td>
					<td class="info_col">
						<c:if test="${currentUser != null and !problem.shared}">
							<form action="problem/share" method="POST">
								<input type="hidden" name="id" value="${problem.id}"/>
								<input class="buttons" type="submit" value="share" />
							</form>	
						</c:if>
					</td>
				</tr>
			</table>
		</div>
		<div id="problem_content">${problem.content}</div>
		<c:if test="${currentUser != null and !problem.solved}">
			<div id="solution_content">
				<form action="problem/solve" method="POST">
				<spring:message code="problem.solutionlabel"/>:
				<input type="hidden" name="id" value="${problem.id}"/>
				<input type="text" name="solution" size="20"/>
				<input class="buttons" type="submit" value="<spring:message code="problem.submit"/>"/>
				</form>
			</div>
		</c:if>
		<c:if test="${currentUser != null and problem.solved and !problem.rated}">
			<div id="rating_content">
				<form action="problem/rate" method="POST">
				<label for="rating"><spring:message code="problem.rateproblem"/>: </label>
				<input type="text" name="rating" id="rating" style="border: 0; color: #f6931f; font-weight: bold;"/>
				<div id="rating_slider"></div>
				<input type="hidden" name="id" value="${problem.id}"/>
				<input class="buttons" type="submit" value="<spring:message code="problem.rate"/>"/>
				<input class="buttons" type="button" style="float: right;" value="<spring:message code="page.cancel"/>" onclick="cancelRating();"/>
				</form>
			</div>
		</c:if>
	</div>
</div>
<jsp:include page="/WEB-INF/view/footer.jsp" />