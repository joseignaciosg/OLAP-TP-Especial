<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../shared/header.jsp"></jsp:include>

<div class="bodyCont">
	<div class="rightCont">
		<legend>Cambiar la visibilidad de los cumpleaños de mis
			amigos</legend>

	<h4> La visibilidad actual es de: ${user.prefs.upcomingFriendsBirthdaysNumber} días</h4>
		<form method="POST" action="editUpcomingBirthdayNumber">
			<label>Quiero que los cumpleaños de mis amigos se vean en los
				próximos días:</label> <select name="numberBirthdays">
					<option value="0" label="Hoy" />
					<option value="1" label="1" />
					<option value="2" label="2" />
					<option value="3" label="3" />
					<option value="4" label="4" />
					<option value="5" label="5" />
					<option value="6" label="6" />
					<option value="7" label="7" />
					<option value="8" label="8" />
					<option value="9" label="9" />
					<option value="10" label="10" />
					<option value="11" label="11" />
					<option value="12" label="12" />
					<option value="13" label="13" />
					<option value="14" label="14" />
					<option value="15" label="15" />
					<option value="16" label="16" />
					<option value="17" label="17" />
					<option value="18" label="18" />
					<option value="19" label="19" />
					<option value="20" label="20" />
			</select>
			
			<c:if test="${error}">
				<div class="alert alert-error generalError">
					<span>Elegir un número válido</span>
				</div>
			</c:if>

			<input type="submit" value="Cambiar visibilidad" class="btn" />
		</form>


		<jsp:include page="../shared/footer.jsp"></jsp:include>