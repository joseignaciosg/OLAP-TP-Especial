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


<h2><b>Paso 2: </b> Seleccione las campos apropiados para cada tabla</h2>
<hr>



<form:form method="POST" class="form-horizontal span8 offset2"
	action="manualModeUpdateFieldsPost">
		
	<!-- for each Table -->
	<c:forEach var="table" items="${tables}" varStatus="status">
		<h4>${table.tableName}</h4> 
		<c:forEach var="prop" items="${table.propList}" >
				${prop}
				<select name="${prop}">
				<c:forEach var="field" items="${table.fieldList}" >
					<option value="${field}" label="${field}" />
				</c:forEach>
				</select> 
		</c:forEach>
		<br><br>
	</c:forEach>
	<br> <br> <br>
	 <input class="btn" type="submit" value="Establecer asignaciones" />
</form:form>









<jsp:include page="../shared/footer.jsp"></jsp:include>