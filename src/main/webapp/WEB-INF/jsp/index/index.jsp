<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../shared/header.jsp"></jsp:include>


<br />

<table>
	<tr>
		<td>
			<div class="hero-unit" style="text-align: center;">
				<h1>Olap</h1>
				<br />
				<p>Tu mundo olap.</p>
			</div>
			<p align="center">
				<a class="btn btn-primary btn-large" href="${ basePath }/user/passChange"
					style="align: center;"> Querés fiesta </a>
			</p>
		</td>
	</tr>
</table>

<jsp:include page="../shared/footer.jsp"></jsp:include>