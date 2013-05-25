<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:if test="${empty users}">
	<p>No se encontraron resultados.</p>
</c:if>
<c:forEach var="user" items="${users}">
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
					<h3>
						<br /> <span class="infoTitle"> Nombre: </span>
						<c:out value="${user.name}" />
						<c:out value="${user.surname}" />
						<br /> <a
							href="${ basePath }/profile/profile?nickname=${ user.username}">Ir
							a perfil de usuario</a> <br />
					</h3>

				</div>

			</div>
		</div>
	</div>
</c:forEach>