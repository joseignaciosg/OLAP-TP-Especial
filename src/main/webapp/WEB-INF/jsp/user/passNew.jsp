<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../shared/header.jsp"></jsp:include>

<form:form name="passNew" class="form-horizontal span8 offset2" action="passNew"
	method="POST" commandName="newPasswordForm">
	<form:errors path="*" />
	<fieldset>
		<legend>Ingrese su nueva contraseña</legend>
		<div class="control-group">
			<label class="control-label" for="user_password">Nueva
				Contraseña</label>
			<div class="controls">
				<form:input type="password" class="input-xlarge" id="user_password"
					name="password" path="password" />
				<p class="help-block">Su contraseña para ingreso al sitio</p>
			</div>
		</div>
		<div class="control-group">
			<label class="control-label" for="user_password">Repetir
				nueva contraseña</label>
			<div class="controls">
				<form:input type="password" class="input-xlarge"
					id="user_password_repeated" name="repeted_password"
					path="repeted_password" />
				<p class="help-block">Repitala para evitar errores</p>
			</div>
		</div>
		<div class="form-actions">
			<input type="submit" class="btn btn-primary" value="Cambiar" />
		</div>
	</fieldset>
	<input type="hidden" value="${user}" name="user" />
	<input type="hidden" value="${nickname}" name="nickname" />
</form:form>

<jsp:include page="../shared/footer.jsp"></jsp:include>