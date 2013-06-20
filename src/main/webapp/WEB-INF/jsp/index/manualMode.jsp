<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../shared/header.jsp"></jsp:include>


<c:if test="${not empty error}">
	<div class="alert">
  		<strong>Error!</strong> ${error}
	</div>
</c:if>
<br />


<h2>Seleccione las tablas apropiadas de su base de datos</h2>

<form:form method="POST" class="form-horizontal span8 offset2"
	action="manualModeUpdateTables">
	<c:forEach var="dim" items="${dimensions}" varStatus="status">
		<label>${dim.name}</label> 
		<select name="numberBirthdays">
			<c:forEach var="name" items="${tableNames}" varStatus="status">
				<option value="${name}" label="${name}" />
			</c:forEach>
		</select> 
	</c:forEach>
	<br> <br> <br>
	 <input class="btn" type="submit" value="Establecer asignaciones" />
</form:form>









<jsp:include page="../shared/footer.jsp"></jsp:include>