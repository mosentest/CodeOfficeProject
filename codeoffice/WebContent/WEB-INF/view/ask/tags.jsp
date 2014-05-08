<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="ch" uri="http://www.codeoffice.com/chlib" %>
<jsp:include page="/WEB-INF/view/header.jsp" />
<link rel="stylesheet" type="text/css" href="css/ask.css">
<style type="text/css">
#tag_table {
	width: 1000px;
	margin: 30px auto 0px auto;	
}
.tag_fact {
	margin: 5px;
}
.tagcontainer {
	height: 100px;
}
.current {
	color: white;
	background: #0551A7;
}
.current .ui-icon-triangle-1-s {
	width: 16px;
	height: 16px;
	background-image: url(/css/custom-theme/images/ui-icons_ffffff_256x240.png);
	background-repeat: repeat-x;
	background-position: -64px -16px;
}
.current .ui-icon-triangle-1-n {
	width: 16px;
	height: 16px;
	background-image: url(/css/custom-theme/images/ui-icons_ffffff_256x240.png);
	background-repeat: repeat-x;
	background-position: 0px -16px;
}
.newtag {
	background-color: #E0E0B9;
}
</style>
<script type="text/javascript">

	var page = parseInt('<c:if test="${page gt 0}">${page}</c:if><c:if test="${empty page}">0</c:if>');
	var order = '<c:if test="${empty orderString}">D</c:if><c:if test="${not empty orderString}">${orderString}</c:if>';
	
	var TAG_VIEW_SIZE = 32;
	
	function showMoreTags() {
		page = page + 1;
		var prio = $('#tagform').find("input[name='orderPrio']").val();
		console.log('ask/tags/' + page + "/" + order + "/" + prio);
		$.ajax({
			url: 'ask/tags/' + page + "/" + order + "/" + prio,
			type: 'GET',
			success: function(data) {
				var builder = "</tr>";
				var link = $('#tag_table tr:last');
				for (var i = 0; i < data.length;) {
					builder += "<tr>";
					for (var j = 0; j < 4 && i < data.length; j++, i++) {
						builder += createTagColumn(data[i]);
					}
					builder += "</tr>";
				}
				$('#tag_table').append(builder);
				$('#tag_table').append(link);
				if (data.length < TAG_VIEW_SIZE) {
					$('#showMore').attr("href", "javacsript:void(0);");
					$('#showMore').html('<spring:message code="ask.nomoretags"/>');
				}
				$('.newtag').animate({backgroundColor: '#FFFFFF'}, 1500);
				setTimeout(function() {
					$('#tag_table .tagcontainer').removeClass(".newtag");
				}, 2000);
			},
			error: function(data) {
				console.log(error);
				console.log(data);
			}
		});
	}
	
	function createTagColumn(tag) {
		var builder = "<td class=\"tagcontainer bb_lg_s newtag\">";
		builder += "<div class=\"tag_main\">";
		builder = builder + "<span class=\"tag\"><a href=\"ask/tag/" + tag.tag + "\">" + tag.tag + "</a></span>";
		builder = builder + "<span class=\"fl_r fs_m\" title=\"" + $('#title_M').html() + "\">";
		if (order == 'T') {
			builder += tag.count;
		} else if (order == 'C') {
			builder += dateFormat(tag.create);
		} else if (order == 'U') {
			builder += dateFormat(tag.update);
		} else if (order == 'A') {
			<%-- alphabetically order doesn't need information --%>
		} else {
			builder += tag.countAsk;
		}
		builder += "</span>";
		builder += "</div>";
		
		builder += "<div class=\"tag_fact\">";
		if (order != 'D') {
			builder = builder + "<div><span class=\"fc_g fs_xs\">" + $('#title_D').html() + "&nbsp;</span><span class=\"fc_b fs_s fw_b\">&nbsp;"  + tag.countAsk + "</span></div>";
		}
		if (order != 'T') {
			builder = builder + "<div><span class=\"fc_g fs_xs\">" + $('#title_T').html() + "&nbsp;</span><span class=\"fc_b fs_s fw_b\">&nbsp;"  + tag.count + "</span></div>";
		}
		if (order != 'C') {
			builder = builder + "<div><span class=\"fc_g fs_xs\">" + $('#title_C').html() + "&nbsp;</span><span class=\"fc_b fs_s fw_b\">&nbsp;"  + dateFormat(tag.create) + "</span></div>";
		}
		if (order != 'U') {
			builder = builder + "<div><span class=\"fc_g fs_xs\">" + $('#title_U').html() + "&nbsp;</span><span class=\"fc_b fs_s fw_b\">&nbsp;"  + dateFormat(tag.update) + "</span></div>";
		}
		builder += "</div>";
		builder += "</td>";
		return builder;
	}
	
	function searchTag(orderString) {
		var tagForm = $('#tagform');
		var oldValue = tagForm.find("input[name='orderString']").val();
		tagForm.find("input[name='orderString']").val(orderString);
		console.log();
		if (oldValue != orderString) {
			if (orderString == 'A') {
				tagForm.find("input[name='orderPrio']").val(true);
			} else {
				tagForm.find("input[name='orderPrio']").val(false);
			}
		} else {
			if (tagForm.find("input[name='orderPrio']").val() == 'true') {
				tagForm.find("input[name='orderPrio']").val(false);
			} else {
				tagForm.find("input[name='orderPrio']").val(true);
			}
		}
		tagForm.submit();
	}

	var currentStyle = '<c:if test="${orderPrio}">ui-icon-triangle-1-n</c:if><c:if test="${!orderPrio}">ui-icon-triangle-1-s</c:if>';
	
	$(document).ready(function() {
		$('button:not(.current)').button({
			icons: {
				secondary: 'ui-icon-triangle-1-s'
			}
		});
		$('.current').button({
			icons: {
				secondary: currentStyle
			}
		});
	});
</script>
<!-- @RequestMapping(value = "list/tags/{page}/{orderString}/{orderPrio}", produces = MediaType.APPLICATION_JSON_VALUE)
	public @ResponseBody List<Tag> ajaxTags(@PathVariable("page")int page, @PathVariable("orderString")String orderString, @PathVariable("orderPrio")boolean orderPrio, 
			HttpSession session) {
		Order order = null;
		if (page <= 0) {
			page = 0;
		}
		String O = orderPrio ? Order.ASCENDING : Order.DESCENDING;
		
		if ("T".equals(orderString)) {//total count
			order = new Order("count", O);
		} else if ("C".equals(orderString)) {//create date
			order = new Order("create_date", O);
		} else if ("U".equals(orderString)) {//update date
			order = new Order("update_date", O);
		} else { // default ask count
			order = new Order("count_ask", O);
		}
		return askService.getTags(order, page, TAG_VIEW_SIZE);
	} -->
	
<c:choose>
	<c:when test="${orderString == 'T'}"><c:set var="mainTitle" value="ask.counttotal" scope="request"/></c:when>
	<c:when test="${orderString == 'C'}"><c:set var="mainTitle" value="ask.created" scope="request"/></c:when>
	<c:when test="${orderString == 'U'}"><c:set var="mainTitle" value="ask.latestupdate" scope="request"/></c:when>
	<c:when test="${orderString == 'A'}"><c:set var="mainTitle" value="ask.emptystring" scope="request"/></c:when>
	<c:otherwise><c:set var="mainTitle" value="ask.countask" scope="request"/></c:otherwise>
</c:choose>
<div class="element">
	<div class="element_title"><spring:message code="ask.tags"/></div>
	<div class="element_content">
		<div class="element_description clearfix">
			<spring:message code="ask.sorttagsby"/>
			<button onclick="javascript:searchTag('T');" class="<c:if test="${orderString == 'T'}">current</c:if>"><spring:message code="ask.counttotal"/></button>
			<button onclick="javascript:searchTag('');" class="<c:if test="${empty orderString}">current</c:if>"><spring:message code="ask.countask"/></button>
			<button onclick="javascript:searchTag('C');" class="<c:if test="${orderString == 'C'}">current</c:if>"><spring:message code="ask.created"/></button>
			<button onclick="javascript:searchTag('U');" class="<c:if test="${orderString == 'U'}">current</c:if>"><spring:message code="ask.updated"/></button>
			<button onclick="javascript:searchTag('A');" class="<c:if test="${orderString == 'A'}">current</c:if>"><spring:message code="ask.tagname"/></button>
		</div>
		<div class="hidden">
			<form id="tagform" action="" method="POST">
				<input type="hidden" name="orderString" value="${orderString}"/>
				<input type="hidden" name="orderPrio" value="${orderPrio}"/>
			</form>
			<span id="title_M"><spring:message code="${mainTitle}"/></span>
			<span id="title_T"><spring:message code="ask.counttotal"/></span>
			<span id="title_C"><spring:message code="ask.created"/></span>
			<span id="title_U"><spring:message code="ask.latestupdate"/></span>
			<span id="title_D"><spring:message code="ask.countask"/></span>
		</div>
		<div class="clearfix"></div>
		<table id="tag_table">
			<c:forEach items="${tags}" var="tag" varStatus="status">
				<c:if test="${!status.first and status.index % 4 == 0}">
					</tr>
					<tr>
				</c:if>
				<c:if test="${status.first}">
					<tr>
				</c:if>
					<td class="tagcontainer bb_lg_s">
						<div class="tag_main">
							<span class="tag"><a href="tag/${tag.tag}">${tag.tag}</a></span>
							<span class="fl_r fs_m" title="<spring:message code="${mainTitle}"/>">
								<c:choose>
									<c:when test="${orderString == 'T'}">${tag.count}</c:when>
									<c:when test="${orderString == 'C'}"><ch:date date="${tag.create}" format="yy-MM-dd"/></c:when>
									<c:when test="${orderString == 'U'}"><ch:date date="${tag.update}" format="yy-MM-dd"/></c:when>
									<c:when test="${orderString == 'A'}"></c:when>
									<c:otherwise>${tag.countAsk}</c:otherwise>
								</c:choose>
							</span>
						</div>
						<div class="tag_fact">
							<c:if test="${not empty orderString}">
								<div><span class="fc_g fs_xs"><spring:message code="ask.countask"/>&nbsp;</span><span class="fc_b fs_s fw_b">&nbsp;${tag.countAsk}</span></div>
							</c:if>
							<c:if test="${orderString != 'T'}">
								<div><span class="fc_g fs_xs"><spring:message code="ask.counttotal"/>&nbsp;</span><span class="fc_b fs_s fw_b">&nbsp;${tag.count}</span></div>
							</c:if>
							<c:if test="${orderString != 'C'}">
								<div><span class="fc_g fs_xs"><spring:message code="ask.created"/>&nbsp;</span><span class="fc_b fs_s fw_b">&nbsp;<ch:date date="${tag.create}" format="yy-MM-dd"/></span></div>
							</c:if>
							<c:if test="${orderString != 'U'}">
								<div><span class="fc_g fs_xs"><spring:message code="ask.latestupdate"/>&nbsp;</span><span class="fc_b fs_s fw_b">&nbsp;<ch:date date="${tag.update}" format="yy-MM-dd"/></span></div>
							</c:if>
						</div>
					</td>
				<c:if test="${status.last}">
					</tr>
				</c:if>
			</c:forEach>
			<tr>
				<td colspan="4" class="center fc_b"><a id="showMore" href="javascript:showMoreTags();"><spring:message code="ask.seemoretags"/></a></td>
			</tr>
		</table>
	</div>
</div>

<jsp:include page="/WEB-INF/view/footer.jsp" />