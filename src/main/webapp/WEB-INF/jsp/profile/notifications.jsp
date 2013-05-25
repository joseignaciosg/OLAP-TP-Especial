<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kc" tagdir="/WEB-INF/tags"%>

<jsp:include page="../shared/header.jsp"></jsp:include>

<div class="container-fluid">
	<div class="row-fluid">
		<div class="span10">
			<c:if test="${empty notifications}">
				No tiene notificationes
			</c:if>
			<table class="table table-hover table-striped">
				<c:forEach var="notif" items="${notifications}" varStatus="status">
					<tr>

						<td>
							<div class="row-fluid">
								<div class="span10 pull-right">
									<c:if test="${notif.type == 'PETITION'}">
										<p class="lead">
											Usted tiene una nueva solicitud de amistad de: <a
												href='profile?nickname=<c:out value="${notif.from.username}" />'>
												<c:out value=" ${notif.from.name}" /> <c:out
													value=" ${notif.from.surname}" />
											</a> <br />
											<kc:prettytime date="${notif.date}" />
										</p>
										<div class="row-fluid">
											<div class="span2 pull-right">
												<form class="form" action="acceptFriendshpPetition"
													method="post">
													<input type="hidden" name="nickname"
														value='<c:out value="${notif.from.username}"/>' /> <input
														type="submit" class="btn btn-success" value="Aceptar">
												</form>
											</div>
											<div class="span2pull-right">
												<form class="form" action="refuseFriendshpPetition" method="post">
													<input type="hidden" name="nickname"
														value='<c:out value="${notif.from.username}"/>' />  <input
														type="submit" class="btn btn-danger" value="Rechazar">
												</form>
											</div>

										</div>



									</c:if>
									<c:if test="${notif.type == 'ACEPTATION'}">
										<p class="lead">
											<a
												href='profile?nickname=<c:out value="${notif.from.username}" />'>
												<c:out value=" ${notif.from.name}" /> <c:out
													value=" ${notif.from.surname}" />
											</a> ha aceptado su solicitud de amistad. <br />
											<kc:prettytime date="${notif.date}" />
										</p>

									</c:if>



									<c:if test="${notif.type == 'LIKE'}">
										<p class="lead">
											A <a
												href='profile?nickname=<c:out value="${notif.from.username}" /> '>
												<c:out value=" ${notif.from.name}" /> <c:out
													value=" ${notif.from.surname}" />
											</a>le gusta una publicaci&oacute;n en su muro. <br />
											<kc:prettytime date="${notif.date}" />
										</p>

									</c:if>

									<c:if test="${notif.type == 'COMMENT'}">
										<p class="lead">
											<a
												href='profile?nickname=<c:out value="${notif.from.username}" /> '>
												<c:out value=" ${notif.from.name}" /> <c:out
													value=" ${notif.from.surname}" />
											</a> ha comentado una publicación en su muro. <br />
											<kc:prettytime date="${notif.date}" />
										</p>

									</c:if>

									<c:if test="${notif.type == 'COMMENTOPUB'}">
										<p class="lead">
											<a
												href='profile?nickname=<c:out value="${notif.from.username}" /> '><c:out
													value=" ${notif.from.name}" /> <c:out
													value=" ${notif.from.surname}" /></a> ha comentado una de sus
											publicaciones. <br />
											<kc:prettytime date="${notif.date}" />
										</p>

									</c:if>


									<c:if test="${notif.type == 'COMMENTSHARE'}">
										<p class="lead">
											<a
												href='profile?nickname=<c:out value="${notif.from.username}" /> '>
												<c:out value=" ${notif.from.name}" /> <c:out
													value=" ${notif.from.surname}" />
											</a> ha comentado una publicación en la que usted ha comentado. <br />
											<kc:prettytime date="${notif.date}" />
										</p>

									</c:if>


									<c:if test="${notif.type == 'STATEUPDATE'}">
										<p class="lead">
											<a
												href='profile?nickname=<c:out value="${notif.from.username}" />'>
												<c:out value=" ${notif.from.name}" /> <c:out
													value=" ${notif.from.surname}" />
											</a> ha publicado algo en su muro. <br />
											<kc:prettytime date="${notif.date}" />
										</p>

									</c:if>
									<c:if test="${notif.type == 'MUTUALFRIEND'}">
										<p class="lead">
											<a
												href='profile?nickname=<c:out value="${notif.from.username}" /> '>
												<c:out value=" ${notif.from.name}" /> <c:out
													value=" ${notif.from.surname}" />
											</a> ahora es amigo de <a
												href='profile?nickname=<c:out value="${notif.newFriend.username}" /> '>
												<c:out value=" ${notif.newFriend.name}" /> <c:out
													value=" ${notif.newFriend.surname}" />
											</a> <br />
											<kc:prettytime date="${notif.date}" />
										</p>

									</c:if>

								</div>

								<c:if test="${ ! (notif.type == 'MUTUALFRIEND')}">
									<div class="span2 pull-right">
										<c:if test="${!notif.from.hasPhoto}">
											<img height="100" width="100" class="photo"
												src="${ basePath }/../img/default.jpg" alt="default picture" />
										</c:if>
										<c:if test="${notif.from.hasPhoto}">
											<img class="img-circle" height="100" width="100"
												class="photo"
												src="${ basePath }/user/photo?nickname=${notif.from.username}">
										</c:if>
									</div>
								</c:if>

							</div> <c:if test="${notif.type == 'EVENTINVITATION'}">
								<p class="lead">
									<a
										href='profile?nickname=<c:out value="${notif.from.username}" />'>
										<c:out value=" ${notif.from.name}" /> <c:out
											value=" ${notif.from.surname}" />
									</a> lo ha invitado al evento <a
										href="../event/show?event=${notif.event.id}">${notif.event.name}</a>
									<kc:prettytime date="${notif.event.date}" /><br>
									<kc:prettytime date="${notif.date}" />
								</p>

							</c:if>




							</div>
						</td>

					</tr>
				</c:forEach>
			</table>
		</div>

	</div>
</div>
<jsp:include page="../shared/footer.jsp"></jsp:include>