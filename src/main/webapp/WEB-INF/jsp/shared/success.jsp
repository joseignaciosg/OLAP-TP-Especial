<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page isELIgnored="false"%>

<br/>
<div class="row">
	<div class="offset3 span6">
		<c:if test="${ not empty success }">
			<c:forEach var="successes" items="${ success }">
				<div class="alert alert-success">${ successes }</div>
			</c:forEach>
		</c:if>
	</div>
</div>