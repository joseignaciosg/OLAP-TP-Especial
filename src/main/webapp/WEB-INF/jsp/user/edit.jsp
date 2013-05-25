<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<jsp:include page="../shared/header.jsp"></jsp:include>

<form:form class="form-horizontal span8 offset2" action="edit"
	method="POST" commandName="editForm">
	<form:errors path="*" />
	<fieldset>
		<legend>Editar Informacion</legend>
		<div class="control-group">
			<label class="control-label" for="username">Nombre de Usuario</label>
			<div class="controls">
				<form:input type="text" name="username" class="input-xlarge"
					id="username" path="username" />
			</div>
		</div>

		<div class="control-group">
			<label class="control-label" for="email">Email</label>
			<div class="controls">
				<form:input type="text" class="input-xlarge" id="email" name="email"
					path="email" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="name">Nombre</label>
			<div class="controls">
				<form:input type="text" class="input-xlarge" id="name" name="name"
					path="name" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="user_last_name">Apellido</label>
			<div class="controls">
				<form:input type="text" class="input-xlarge" id="surname"
					name="surname" path="surname" />
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="birthday">Fecha de
				Nacimiento</label>
			<div class="controls">
				<form:input type="text" style="width: 70px" id="birthday"
					name="birthday" path="birthday" />
			</div>
			<form:input type="hidden" path="currentUsername" />

		</div>
		<div class="form-actions">
			<input type="submit" class="btn btn-primary"
				value="Editar Informacion" />
		</div>
	</fieldset>
</form:form>

<jsp:include page="../shared/footer.jsp"></jsp:include>