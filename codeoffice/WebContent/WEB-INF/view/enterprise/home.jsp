<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<jsp:include page="/WEB-INF/view/enterprise/header.jsp">
	<jsp:param name="navigation" value="home"/>
</jsp:include>
<div id="content">
<table>
    <thead><tr><th>Code</th><th>Price</th><th>Time</th></tr></thead>
    <tbody id="price"></tbody>
  </table>

  <p class="new">
    Code: <input type="text" class="code"/>
    Price: <input type="text" class="price"/>
    <button class="add">Add</button>
    <button class="remove-all">Remove All</button>
  </p>
</div>
<jsp:include page="/WEB-INF/view/enterprise/footer.jsp" />