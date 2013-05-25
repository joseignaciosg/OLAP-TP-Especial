<%@ page isELIgnored="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="kc" tagdir="/WEB-INF/tags"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@taglib prefix="joda" uri="http://www.joda.org/joda/time/tags"%>
<joda:format value="${now}" style="SM" />

<jsp:include page="../shared/header.jsp"></jsp:include>

<div class="container-fluid">
	<div class="row show-grid">

		<c:if test="${!isfriend and !iscurrent and !petitionsent}">
			<c:if test="${petitionsentforcurrent}">
				<div class="span3" data-original-title="">
					<form action="notificationaccept" method="post">
						<input type="hidden" name="nickname"
							value='<c:out value="${user.username}"/>' /> <input
							type="hidden" name="action" value="accept" />
						<button type="submit" class="btn">Aceptar solicitud de
							amistad.</button>
					</form>
				</div>
			</c:if>
			<c:if test="${!petitionsentforcurrent and loggegin}">
				<div class="span3" data-original-title="">
					<form action="newFriendPetition" method="post">
						<input type="hidden" name="nickname"
							value='<c:out value="${user.username}"/>' />
						<button type="submit" class="btn">Enviar solicitud de
							amistad.</button>
					</form>
				</div>
			</c:if>
		</c:if>
		
		<c:if test="${!isfriend and !iscurrent and petitionsent}">
			<p>
				La solicitud de amistad a
				<c:out value="${user.username}" />
				ha sido enviada.
			</p>
		</c:if>
		<c:if test="${isfriend and !iscurrent}">
			<p>
				<c:out value="${user.username}" />
				y usted ya son amigos
			</p>
		</c:if>
		
		<!-- Para el aviso de cumpleños próximos -->
		<c:if test="${ not empty current_user and iscurrent and hasFriendsBirthDays }">
			<button type="button" class="btn btn-danger pull-left"
						id="upcomingbirthdays">
						<p style="color: white">
							Tiene cumpleños en los próximos días
						</p>
					</button>
		</c:if>
		
		<!-- Para modificar la cantidad de días proximos de cumpleaños de amigos -->
		<c:if test="${ not empty current_user and iscurrent }">
			<button type="button" class="btn pull-right"
						id="editupcomingbirthdaynumber">
							Cambiar visibilidad de cumpleaños (actual:
							<c:if test="${current_user.prefs.upcomingFriendsBirthdaysNumber eq 0}">
							 hoy
							</c:if>
							<c:if test="${!( current_user.prefs.upcomingFriendsBirthdaysNumber eq 0 )}">
							 ${current_user.prefs.upcomingFriendsBirthdaysNumber}
							</c:if>
					</button>
		</c:if>
		
		
		<!-- Para el aviso de notifications nuevas -->
		<c:if test="${ not empty current_user and iscurrent }">
			<c:if test="${ notreadnotifications != 0  }">
				<c:if test="${ notreadnotifications == 1  }">
					<button type="button" class="btn btn-danger pull-left"
						id="notifications">
						<p style="color: white">
							Tiene
							<c:out value="${notreadnotifications}" />
							nueva notificaci&oacute;n
						</p>
					</button>
				</c:if>
				<c:if test="${ notreadnotifications != 1  }">
					<button type="button" class="btn btn-danger pull-left"
						id="notifications">
						<p style="color: white">
							Tiene
							<c:out value="${notreadnotifications}" />
							nuevas notificaciones
						</p>
					</button>
				</c:if>

			</c:if>
		</c:if>


	</div>
	<div class="row-fluid">
		<div class="row-fluid">
			<div class="span4">
				<br /> <br /> <br />
				<c:if test="${!user.hasPhoto}">
					<img height="120" width="160" class="photo"
						src="${ basePath }/../img/default.jpg" alt="default picture" />
				</c:if>
				<c:if test="${user.hasPhoto}">
					<img height="120" width="160" class="photo"
						src="${ basePath }/user/photo?nickname=${user.username}" />
				</c:if>
				</br>
				<c:if test="${iscurrent}">
					<button type="button" class="btn btn-mini btn-primary"
						id="editphoto">
						Cambiar Imagen
						</p>
					</button>
				</c:if>
				<br /> <br /> <br /> <br /> <br /> <br /> <br />
				<h3>Amigos</h3>
				<table class="table">
					<c:forEach var="friend" items="${friends}" varStatus="status">
						<hr>
						<hd> <a
							href='profile?nickname=<c:out value="${friend.username}" />'>
							<c:out value="${friend.username}" />
						</a> <c:if test="${!friend.hasPhoto}">
							<img height="20" width="20" class="photo"
								src="${ basePath }/../img/default.jpg" alt="default picture" />
						</c:if> <c:if test="${friend.hasPhoto}">
							<img class="img-circle" height="20" width="20" class="photo"
								src="${ basePath }/user/photo?nickname=${friend.username}">

						</c:if> </hd>
						</hr>
					</c:forEach>
				</table>

			</div>
			<div class="span8">

				<div class="page-header">
					<h1>
						<c:out value="${user.name}" />
						<c:out value="${user.surname}" />
					</h1>
				</div>
				<h3>Informaci&oacuten</h3>
				<table class="table table-striped">
					<tr>
						<td><b><span class="infoTitle"> Apodo: </span></b></td>
						<td><c:out value="${user.username}" /></td>
					</tr>
					<c:if test="${isfriend}">
						<tr>
							<td><b><span class="infoTitle">Email:</span></b></td>
							<td><c:out value="${user.email}" /></td>
						</tr>
						<tr>
							<td><b><span class="infoTitle">Cumplea&ntilde;os:</span></b></td>
							<td><joda:format value="${user.birthday}" pattern="dd MMM yyyy"/></td>
						</tr>
					</c:if>
				</table>
				<c:if test="${iscurrent}">
					<button type="button" class="btn btn-mini btn-primary" id="edit">
						<a href="../user/edit"><p style="color: white">Editar
								Informaci&oacute;n</p></a>
					</button>
					<button type="button" class="btn btn-mini btn-primary"
						id="editpass">
						<a href="../user/editpass"><p style="color: white">Editar
								Contrase&ntilde;a</p></a>
					</button>
					<button type="button" class="btn btn-mini btn-primary"
						id="editpass">
						<a href="../event/create"><p style="color: white">Crear
								Evento</p></a>
					</button>
				</c:if>

				<c:if test="${isfriend}">
					<br />
					<hr />
					<h3>Nueva Publicaci&oacute;n</h3>
					<form:form action="newpublication" method="post"
						commandName="publicationForm">
						<form:errors path="*" />
						<input type="hidden" name="nickname" value="${user.username}" />
						<form:textarea name="text" id="text" class="field span12"
							path="text" rows="3"
							placeholder="ingrese aquí su publicación"></form:textarea>
						<input type="submit" class="btn" value="Publicar!" />
					</form:form>


					<h3>Publicaciones recientes</h3>
					<!-- Publications-->
					<div class="span12">
						<table class="table table-hover table-striped">
							<c:forEach var="pub" items="${publications}" varStatus="status">
								<tr>
									<td><c:if
											test="${pub.publication['class']['name'] == 'ar.edu.itba.it.paw.sozialnetz.domain.entities.StateUpdate'}">
											<c:if test="${! (pub.publication.from eq user) }">
												${pub.publication.from.username} publicó en el muro: 
											</c:if>
											<c:if test="${iscurrent}">
												<form class="form pull-right" action="deletePublication"
													method="post">
													<input type="hidden" name="nickname"
														value='<c:out value="${user.username}"/>' /> <input
														type="hidden" name="pub_id"
														value='<c:out value="${pub.publication.id}"/>' /> <input
														type="submit" value="eliminar" />
												</form>
											</c:if>
											<p class="lead">
												<c:out value=" ${pub.publication.text}" />
											<form class="form" method="post" action="newLike">
												<input type="hidden" name="nickname"
													value='<c:out value="${user.username}"/>' /> <input
													type="hidden" name="pub_id"
													value='<c:out value="${pub.publication.id}"/>' />
												<c:if test="${!pub.likedByUser}">
													<button type="submit"
														class="btn btn-mini pull-right btn-success">Me
														gusta</button>
												</c:if>
												<c:if test="${pub.likedByUser}">
													<span class="pull-right"><span
														class="label label-success">Te gusta</span></span>
												</c:if>
											</form>
											<form class="form" method="post" action="newShare">
												<input type="hidden" name="nickname"
													value='<c:out value="${user.username}"/>' /> <input
													type="hidden" name="pub_id"
													value='<c:out value="${pub.publication.id}"/>' />
												<c:if test="${!pub.sharedByUser}">
													<button type="submit"
														class="btn btn-mini pull-right btn-success">Compartir</button>
												</c:if>
												<c:if test="${pub.sharedByUser}">
													<span class="pull-right"><span
														class="label label-success">Compartida</span></span>
												</c:if>
											</form>

											<c:if test="${not empty pub.publication.interests}">
													Le gusta esta publicaci&oacute;n a:
													<c:forEach var="interest"
													items="${pub.publication.interests}" varStatus="status">
													<c:if test="${interest.type == 'LIKE'}">
														<a
															href='<c:out value="profile?nickname=${interest.owner.username}" />'><c:out
																value="${interest.owner.username}" /></a>, 
													</c:if>
												</c:forEach>
												<br />
													Compartieron esta publicación:
													<c:forEach var="interest"
													items="${pub.publication.interests}" varStatus="status">
													<c:if test="${interest.type == 'SHARE'}">
														<a
															href='<c:out value="profile?nickname=${interest.owner.username}" />'><c:out
																value="${interest.owner.username}" /></a>, 
														</c:if>
												</c:forEach>
											</c:if>
											</br>
												Publicado <kc:prettytime date="${pub.publication.date}"></kc:prettytime>

											<table class="table">
												<c:forEach var="comment" items="${pub.publication.comments}"
													varStatus="status">
													<tr>
														<td>${comment.text} <br /> Comentado <kc:prettytime
																date="${pub.publication.date}"></kc:prettytime> por
															${comment.owner.username} <c:if test="${iscurrent}">
																<form class="form pull-right" action="deleteComment"
																	method="post">
																	<input type="hidden" class="btn-mini" name="nickname"
																		value='<c:out value="${user.username}"/>' /> <input
																		type="hidden" name="comment_id"
																		value='<c:out value="${comment.id}"/>' /> <input
																		type="hidden" name="pub_id"
																		value='<c:out value="${pub.publication.id}"/>' /> <input
																		type="submit" value="eliminar" />
																</form>
															</c:if>
														</td>
													</tr>
												</c:forEach>
											</table>

											<form class="form pull-right" method="post"
												action="newComment">
												<input type="hidden" name="nickname"
													value='<c:out value="${user.username}"/>' /> <input
													type="hidden" name="pub_id"
													value='<c:out value="${pub.publication.id}"/>' /> <input
													type="text" style="width: 470px" name="commenttext" /> <input
													type="submit" class="btn btn-primary btn-small"
													value="Comentar">
											</form></td>
									</p>
									</c:if>
									<c:if
										test="${pub.publication['class']['name'] == 'ar.edu.itba.it.paw.sozialnetz.domain.entities.Interest' }">
										<!-- Si no es uno mismo al que le gusta su propia publicación -->
										<c:if
											test="${pub.publication.owner.username != pub.publication.update.owner.username}">
											<p class="lead">
												<c:if test="${pub.publication.type == 'LIKE'}">
												A
												<c:out value=" ${pub.publication.owner.username}" />
												le gusta la publicaci&oacute;n en el muro de 
												</c:if>
												<c:if test="${pub.publication.type == 'SHARE'}">
													<c:out value=" ${pub.publication.owner.username}" /> 
												compartió la publicación en el el muro de
												</c:if>
												<a
													href='profile?nickname=<c:out value="${pub.publication.update.owner.username}" />'>
													<c:out value=" ${pub.publication.update.owner.username}" />
												</a>: <br />
												<c:out value=" ${pub.publication.update.text}" />
											</p>
										</c:if>
										<c:if
											test="${pub.publication.owner.username == pub.publication.update.owner.username}">
											<c:if test="${pub.publication.type == 'LIKE'}">
												<p class="lead">
													A
													<a
													href='profile?nickname=<c:out value="${pub.publication.owner.username}" />'>
													<c:out value=" ${pub.publication.owner.username}" />
												</a>
													le gusta la publicaci&oacute;n de su muro: </br>
													<c:out value=" ${pub.publication.update.text}" />
												</p>
											</c:if>
											<c:if test="${pub.publication.type == 'SHARE'}">
												<p class="lead">
													<a
													href='profile?nickname=<c:out value="${pub.publication.owner.username}" />'>
													<c:out value=" ${pub.publication.owner.username}" /></a>
													compartió la publicaci&oacute;n de su muro:</br>
													<c:out value=" ${pub.publication.update.text}" />
												</p>
											</c:if>
										</c:if>
										<c:if test="${pub.publication.type == 'LIKE'}">
												Hizo el like <kc:prettytime date="${pub.publication.date}"></kc:prettytime>
										</c:if>
										<c:if test="${pub.publication.type == 'SHARE'}">
												Compartió <kc:prettytime date="${pub.publication.date}"></kc:prettytime>
										</c:if>
									</c:if>

									</td>
								</tr>
							</c:forEach>
						</table>
					</div>
					<br />
					<hr />
				</c:if>

			</div>
		</div>

		<jsp:include page="../shared/footer.jsp"></jsp:include>


	</div>