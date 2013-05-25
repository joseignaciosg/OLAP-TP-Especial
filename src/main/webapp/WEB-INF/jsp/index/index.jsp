<%@ page pageEncoding="UTF-8"%>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<jsp:include page="../shared/header.jsp"></jsp:include>


<br />

<h1>Olap Project: tu mundo olap</h1>
<table>
	<tr>
		<td>
			<div>
				<form:form name="uploadxmlform" class="form-horizontal span8 offset2"
					action="uploadXml" method="POST" enctype="multipart/form-data"
					commandName="uploadxmlform">
					<form:errors path="*" />
					<fieldset>
						<legend> </legend>
						<div class="control-group">
							<label class="control-label" for="xml_doc">Documento XML Multidim </label>
							<div class="controls">
								<form:input type="file" class="input-xlarge" id="photo"
									name="xml_doc" path="file" />
							</div>
						</div>
						<div class="form-actions">
							<input type="submit" class="btn btn-primary" value="Subir documento " />
						</div>
					</fieldset>
				</form:form>
			</div>
		</td>

	</tr>
</table>

<jsp:include page="../shared/footer.jsp"></jsp:include>