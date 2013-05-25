<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../shared/header.jsp"></jsp:include>

<form:form name="editpass" class="form-horizontal span8 offset2"
	action="editpass" method="POST" commandName="editPassForm">
				<form:errors path="*" />
	<fieldset>
		<legend>Cambio de Contraseña</legend>

		<div class="control-group">
			<label class="control-label" for="oldpass">Contraseña vieja</label>
			<div class="controls">
				<form:input type="password" class="input-xlarge" id="oldpass"
					name="oldpass" path="oldpass" />
				<p class="help-block">Su contraseña para ingreso al sitio</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="newpass">Contraseña nueva</label>
			<div class="controls">
				<form:input type="password" class="input-xlarge" id="newpass"
					name="newpass" path="newpass" />
				<p class="help-block">Su nueva contraseña</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="newpassrepeat">Repetir
				contraseña nueva</label>
			<div class="controls">
				<form:input type="password" class="input-xlarge" id="newpassrepeat"
					name="newpassrepeat" path="newpassrepeat" />
				<p class="help-block">Repitala para evitar errores</p>
			</div>
		</div>
		<form:input type="hidden" class="input-xlarge" name="realpass"
			path="realpass" />

		<div class="form-actions">
			<input type="submit" class="btn btn-primary"
				value="Editar Contraseña" />
		</div>
	</fieldset>
</form:form>

<jsp:include page="../shared/footer.jsp"></jsp:include>