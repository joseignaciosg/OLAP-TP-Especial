<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib prefix="kc" tagdir="/WEB-INF/tags"%>

<jsp:include page="../shared/header.jsp"></jsp:include>


<c:if test="${empty events}">
	<p>No hay eventos disponibles</p>
</c:if>
<c:forEach var="event" items="${events}">
	<div class="row">
		<div class="span8">
			<div class="hero-unit searchItem">
				<div class="row">
					<li class="span2"><a href='<c:url value="#"/>'
						class="thumbnail"><c:if test="${!event.hasPhoto}">
								<img height="120" width="160" class="photo"
									src="${ basePath }/../img/default.jpg" alt="default picture" />

							</c:if> <c:if test="${event.hasPhoto}">
								<img height="120" width="160" class="photo"
									src="${ basePath }/event/photo?event=${event.id}" />
							</c:if> </a></li> <br />
					<h3>
						<span class="infoTitle"> Nombre: </span>
						<c:out value="${event.name}" />

						<br /> <span class="infoTitle"> Fecha: </span> <kc:prettytime date="${event.date}"></kc:prettytime>
						<br /> <a href="${ basePath }/event/show?event=${ event.id}">Ir
							al evento</a>
					</h3>

				</div>

			</div>
		</div>
	</div>
</c:forEach>

<jsp:include page="../shared/footer.jsp"></jsp:include>