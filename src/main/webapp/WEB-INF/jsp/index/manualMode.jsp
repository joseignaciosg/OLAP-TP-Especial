<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../shared/header.jsp"></jsp:include>

<br />


<h2>Seleccione las tablas apropiadas de su base de datos</h2>


<!-- generar las opciones correspondientes con las dimensiones  -->

<h3>Tablas disponibles</h3>
<ul>
	<c:forEach var="name" items="${tableNames}" varStatus="status">
		<li>${name}</li>
	</c:forEach>
</ul>

<jsp:include page="../shared/footer.jsp"></jsp:include>