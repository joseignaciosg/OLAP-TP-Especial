<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../shared/header.jsp"></jsp:include>

<br />


<h2>Tablas creadas y Documento XML modelo estrella generado correctamente</h2>

<form:form name="downloadStarXml" class="form-horizontal span8 offset2" action="manualMode" method="POST">
	<form:errors path="*" />
	<fieldset>											
		<div class="form-actions">
			<input type="submit" class="btn btn-primary" value="Descargar XML estrella" />
		</div>
	</fieldset>
</form:form>



<jsp:include page="../shared/footer.jsp"></jsp:include>