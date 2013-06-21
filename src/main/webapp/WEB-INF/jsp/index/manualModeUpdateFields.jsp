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



<form:form method="POST" class="form-horizontal span10 offset2"
	action="manualModeUpdateFieldsPost">
		
	<!-- for each Table -->
	<c:forEach var="table" items="${tables}" varStatus="status">
		  <fieldset>
		   		<legend>${table.tableName}</legend>
				<c:forEach var="prop" items="${table.propList}" >
					<div class="pull-left inline" >
						<label>${prop}:</label>
						<select name="${prop}">
							<div class="pull-left" >
								<c:forEach var="field" items="${table.fieldList}" >
									<option value="${field}/${table.tableName}" label="${field}" />
								</c:forEach>
							</div>
						</select>
					</div> 
				</c:forEach>
		  </fieldset>
		<br><br>
		<br> <br>	
	</c:forEach>
	<br> <br> <br>
	 <input class="btn" type="submit" value="Establecer asignaciones" />
</form:form>









<jsp:include page="../shared/footer.jsp"></jsp:include>