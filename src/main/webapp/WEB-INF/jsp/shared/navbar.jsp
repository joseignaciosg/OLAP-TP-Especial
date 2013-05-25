<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<c:if test="${ ! empty current_user }">
	<a class="brand" href='${ basePath }/profile/profile'> SozialNetz </a>
</c:if>
<c:if test="${ empty current_user }">
	<a class="brand" href="${ basePath }/index/index"> SozialNetz </a>
</c:if>
<ul class="nav pull-right">
	<c:if test="${ empty current_user }">
		<li><form:form class="navbar-form pull-right span7"
				action="${ basePath }/user/login" method="POST"
				commandName="loginForm">
				<div class="pull-right">
					<form:input type="text" name="username" class="span2" id="username"
						placeholder="Usuario" path="username" />
					<form:input type="password" name="password" id="password"
						placeholder="Contraseña" path="password" />
					<input type="submit" class="btn" value="Entrar" />
				</div>
			</form:form></li>
		<form:errors path="*" />
	</c:if>

	<c:if test="${ not empty current_user }">
		<form action="${ basePath }/user/search"
			class="navbar-search pull-left" method="GET">
			<button type="button"
				class="btn btn-mini btn-primar btn-success pull-left">
				<p style="color: white">
					<a href="${ basePath }/user/friendSuggest"> Sugerencias de
						Amigos</a>
				</p>
			</button>
			<button type="button"
				class="btn btn-mini btn-primar btn-success pull-left">
				<p style="color: white">
					<a href="${ basePath }/profile/notifications">Ver
						Notificationes</a>
				</p>
			</button>
			<button type="button"
				class="btn btn-mini btn-primar btn-success pull-left">
				<p style="color: white">
					<a href="${ basePath }/event/upcomingEvents"> Ver Eventos
						Futuros</a>
				</p>
			</button>
			<input type="text" name="pattern" class="search-query"
				placeholder="Search"> <input type="submit"
				class="btn btn-primary" value="Go!" /> <a class="btn btn-primary"
				href="${ basePath }/user/logout">Salir</a>
		</form>

	</c:if>
</ul>
