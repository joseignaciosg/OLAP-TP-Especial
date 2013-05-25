<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<jsp:include page="../shared/header.jsp"></jsp:include>




<c:if test="${empty notInvited}">
	<p>Ya invito a todos sus amigos</p>
</c:if>
<c:forEach var="user" items="${notInvited}">
	<div class="row">
		<div class="span8">
			<div class="hero-unit searchItem">
				<div class="row">
					<li class="span2"><a href='<c:url value="#"/>'
						class="thumbnail"><c:if test="${!user.hasPhoto}">
								<img height="120" width="160" class="photo"
									src="${ basePath }/../img/default.jpg" alt="default picture" />

							</c:if> <c:if test="${user.hasPhoto}">
								<img height="120" width="160" class="photo"
									src="${ basePath }/user/photo?nickname=${user.username}" />
							</c:if> </a></li> <br />
					
						<br /> <span class="infoTitle"> Nombre: </span>
						<c:out value="${user.name}" />
						<c:out value="${user.surname}" />

						<form action="invite" method="POST">
							<input type="hidden" name="nickname" value="${user.username}" />
							<input type="hidden" name="event" value="${event.id}" />
							<input type="submit" class="btn" value="Invitar al Usuario" />
						</form>
					

				</div>

			</div>
		</div>
	</div>
</c:forEach>



<jsp:include page="../shared/footer.jsp"></jsp:include>
