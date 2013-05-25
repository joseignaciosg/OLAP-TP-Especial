<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kc" tagdir="/WEB-INF/tags"%>

<jsp:include page="../shared/header.jsp"></jsp:include>

<c:if test="${!event.hasPhoto}">
	<img height="120" width="160" class="photo"
		src="${ basePath }/../img/default.jpg" alt="default picture" />

</c:if>
<c:if test="${event.hasPhoto}">
	<img height="120" width="160" class="photo"
		src="${ basePath }/event/photo?event=${event.id}" />
</c:if>

<c:if test="${isOwner}">

	<button type="button" class="btn btn-mini btn-primary" id="editphoto">
		<a href="../event/editPhoto?event=${event.id}"><p
				style="color: white">Editar Foto</p></a>
	</button>
	<button type="button" class="btn btn-mini btn-primary" id="invite">
		<a href="../event/invite?event=${event.id}"><p
				style="color: white">Invitar amigos</p></a>
	</button>
</c:if>


<form action="changeAnswer" method="POST">
	<input type="hidden" name="guestType" value="GOING" /> <input
		type="hidden" name="event" value="${event.id}" /> <input
		type="submit" class="btn" value="Going" />
</form>
<form action="changeAnswer" method="POST">
	<input type="hidden" name="guestType" value="NOTGOING" /> <input
		type="hidden" name="event" value="${event.id}" /> <input
		type="submit" class="btn" value="Not going" />
</form>
<form action="changeAnswer" method="POST">
	<input type="hidden" name="guestType" value="DONTKNOW" /> <input
		type="hidden" name="event" value="${event.id}" /> <input
		type="submit" class="btn" value="Dont know" />
</form>

<br />

<h3>
	<br /> <span class="infoTitle"> Nombre: </span>
	<c:out value="${event.name}" />
	<br /> <br /> <span class="infoTitle"> Descripcion: </span>
	<c:out value="${event.description}" />
	<br /> <br /> <span class="infoTitle"> Fecha: </span>
	<kc:prettytime date="${event.date}"/>
</h3>

<h3>Going</h3>
<table class="table">
	<c:forEach var="guest" items="${event.confirmed}" varStatus="status">
		<hr>
		<hd> <a
			href='profile?nickname=<c:out value="${guest.username}" />'> <c:out
				value="${guest.username}" />
		</a> <c:if test="${!guest.hasPhoto}">
			<img height="20" width="20" class="photo"
				src="${ basePath }/../img/default.jpg" alt="default picture" />
		</c:if> <c:if test="${guest.hasPhoto}">
			<img class="img-circle" height="20" width="20" class="photo"
				src="${ basePath }/user/photo?nickname=${guest.username}">

		</c:if> </hd>
		</hr>
	</c:forEach>
</table>

<h3>Dont know</h3>
<table class="table">
	<c:forEach var="guest" items="${event.dontKnow}" varStatus="status">
		<hr>
		<hd> <a
			href='profile?nickname=<c:out value="${guest.username}" />'> <c:out
				value="${guest.username}" />
		</a> <c:if test="${!guest.hasPhoto}">
			<img height="20" width="20" class="photo"
				src="${ basePath }/../img/default.jpg" alt="default picture" />
		</c:if> <c:if test="${guest.hasPhoto}">
			<img class="img-circle" height="20" width="20" class="photo"
				src="${ basePath }/user/photo?nickname=${guest.username}">

		</c:if> </hd>
		</hr>
	</c:forEach>
</table>

<h3>Not Going</h3>
<table class="table">
	<c:forEach var="guest" items="${event.notGoing}" varStatus="status">
		<hr>
		<hd> <a
			href='profile?nickname=<c:out value="${guest.username}" />'> <c:out
				value="${guest.username}" />
		</a> <c:if test="${!guest.hasPhoto}">
			<img height="20" width="20" class="photo"
				src="${ basePath }/../img/default.jpg" alt="default picture" />
		</c:if> <c:if test="${guest.hasPhoto}">
			<img class="img-circle" height="20" width="20" class="photo"
				src="${ basePath }/user/photo?nickname=${guest.username}">

		</c:if> </hd>
		</hr>
	</c:forEach>
</table>

<h3>No Answer</h3>
<table class="table">
	<c:forEach var="guest" items="${event.noAnswer}" varStatus="status">
		<hr>
		<hd> <a
			href='profile?nickname=<c:out value="${guest.username}" />'> <c:out
				value="${guest.username}" />
		</a> <c:if test="${!guest.hasPhoto}">
			<img height="20" width="20" class="photo"
				src="${ basePath }/../img/default.jpg" alt="default picture" />
		</c:if> <c:if test="${guest.hasPhoto}">
			<img class="img-circle" height="20" width="20" class="photo"
				src="${ basePath }/user/photo?nickname=${guest.username}">

		</c:if> </hd>
		</hr>
	</c:forEach>
</table>


<jsp:include page="../shared/footer.jsp"></jsp:include>
