<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<jsp:include page="header.jsp"></jsp:include>

<div class="row" id="content">
	<div class="span4 offset2">
		<ul class="thumbnails">
			<li class="span7"><c:if test="${errorType == 404}">
					<img class="errorImg" src="${ basePath }/../img/404error.jpg">
				</c:if> <c:if test="${errorType == 500}">
					<img class="errorImg" src="${ basePath }/img/500error.png">
				</c:if></li>
		</ul>
	</div>
</div>

<jsp:include page="footer.jsp"></jsp:include>
