<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../shared/header.jsp"></jsp:include>


<br />

<h1>Seleccione un modo de uso:</h1>

<form:form name="autoModeForm" class="form-horizontal span8 offset2" action="autoMode" method="POST">
	<form:errors path="*" />
	<fieldset>											
		<div class="form-actions">
			<input type="submit" class="btn btn-primary" value="Modo Automático" />
		</div>
	</fieldset>
</form:form>

<form:form name="manualModeForm" class="form-horizontal span8 offset2" action="manualMode" method="GET">
	<form:errors path="*" />
	<fieldset>											
		<div class="form-actions">
			<input type="submit" class="btn btn-primary" value="Modo Manual" />
		</div>
	</fieldset>
</form:form>



<jsp:include page="../shared/footer.jsp"></jsp:include>