<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:include page="../shared/header.jsp"></jsp:include>

<div>
	<form name="index" class="form-horizontal span8 offset2"
		action="passChange" method="POST">
		<fieldset>
			<legend>Ingrese su nombre de Usuario y un email sera enviado
				a su direccion de correo para recuperar su contraseña</legend>
			<div class="control-group">
				<label class="control-label" for="user_username">Nombre de
					Usuario</label>
				<div class="controls">
					<input type="text" class="input-xlarge" id="user_username"
						name="user_username">
					<p class="help-block">Su nombre de usuario del sitio</p>
				</div>
			</div>
			<div class="form-actions">
				<input type="submit" class="btn btn-primary" value="Enviar email" />
			</div>
		</fieldset>
	</form>
</div>

<jsp:include page="../shared/footer.jsp"></jsp:include>