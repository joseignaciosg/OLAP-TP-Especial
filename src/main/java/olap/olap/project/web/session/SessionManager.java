package olap.olap.project.web.session;

import javax.servlet.http.HttpSession;

import olap.olap.project.model.api.CubeApi;

public class SessionManager {

	private HttpSession session;

	public SessionManager() {

	}

	public void setSession(HttpSession session) {
		this.session = session;
	}

	public SessionManager(final HttpSession session) {
		this.session = session;
	}
	
	public void setCubeApi(CubeApi ca){
		this.session.setAttribute("cubeApi", ca);
	}
	
	public CubeApi getCubeApi(){
		return (CubeApi) this.session.getAttribute("cubeApi");
	}

	public void endSession() {
		this.session.invalidate();
	}
}
