<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../shared/header.jsp"></jsp:include>

<br />


<center><h2>Tablas creadas y Documento XML modelo estrella generado correctamente</h2>
</center>
<form:form name="downloadStarXmlForm" class="form-horizontal span8 offset2" action="downloadStarXml" method="GET">
	<form:errors path="*" />
	<fieldset>											
		<div class="form-actions">
			<center><input type="submit" class="btn btn-primary" value="Descargar XML estrella" /></center>
		</div>
	</fieldset>
</form:form>



<jsp:include page="../shared/footer.jsp"></jsp:include>