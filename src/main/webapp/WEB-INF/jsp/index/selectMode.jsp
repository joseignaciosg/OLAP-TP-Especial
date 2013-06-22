<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../shared/header.jsp"></jsp:include>


<br />

<center>
<h1>Seleccione un modo de uso</h1>
<br/>
<h4>Elija entre los dos modos disponibles. El automático generará las tablas por si solo en base a la entrada
que recibió en el paso anterior. El manual levantara las tablas de su base de datos y le pedirá a usted que
realice la unión de manera personalizada</h4>

<form:form name="autoModeForm" class="form-horizontal span8 offset2" action="autoMode" method="POST">
	<form:errors path="*" />
	<fieldset>											
		<div class="form-actions">
			<center><input type="submit" class="btn btn-primary" value="Modo Automático" /></center>
		</div>
	</fieldset>
</form:form>

<form:form name="manualModeForm" class="form-horizontal span8 offset2" action="manualMode" method="GET">
	<form:errors path="*" />
	<fieldset>											
		<div class="form-actions">
			<center><input type="submit" class="btn btn-primary" value="Modo Manual" /></center>
		</div>
	</fieldset>
</form:form>
</center>


<jsp:include page="../shared/footer.jsp"></jsp:include>