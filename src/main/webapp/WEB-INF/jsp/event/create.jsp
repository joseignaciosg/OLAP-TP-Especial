<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<jsp:include page="../shared/header.jsp"></jsp:include>

<form:form class="form-horizontal span8 offset2" action="create"
	method="POST" commandName="eventForm">
	<form:errors path="*" />
	<fieldset>
		<legend>Crear Evento</legend>
		<div class="control-group">
			<label class="control-label" for="name">Nombre del Evento</label>
			<div class="controls">
				<form:input type="text" name="name" class="input-xlarge" id="name"
					path="name" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="description">Descripcion</label>
			<div class="controls">
				<form:textarea cols="40" rows="4" class="input-xlarge"
					id="description" name="description" path="description" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="user_birthdate">Fecha del Evento</label>
			<div class="controls">
				<form:input type="text" class="input-xlarge" id="date"
					name="date" path="date" placeholder="dd/mm/aaaa" />
				<form:input type="text" style="width: 30px" id="hour" name="hour"
					path="hour" placeholder="hh" />
			</div>
		</div>
		<div class="form-actions">
			<input type="submit" class="btn btn-primary" value="Crear Evento" />
		</div>
	</fieldset>
</form:form>

<jsp:include page="../shared/footer.jsp"></jsp:include>