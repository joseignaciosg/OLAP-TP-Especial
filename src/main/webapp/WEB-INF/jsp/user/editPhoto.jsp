<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>


<jsp:include page="../shared/header.jsp"></jsp:include>


<div class="row">
	<form:form name="editPhoto" class="form-horizontal span8 offset2"
		action="editphoto" method="POST" enctype="multipart/form-data"
		commandName="photoForm">
		<form:errors path="*" />
		<fieldset>
			<legend>Editar foto del Evento</legend>
			<div class="control-group">
				<label class="control-label" for="profile_photo">Foto </label>
				<div class="controls">
					<form:input type="file" class="input-xlarge" id="photo"
						name="profile_photo" path="file" />
				</div>
			</div>

			<div class="form-actions">
				<input type="submit" class="btn btn-primary" value="Editar Foto " />
			</div>
		</fieldset>



	</form:form>
</div>


<jsp:include page="../shared/footer.jsp"></jsp:include>