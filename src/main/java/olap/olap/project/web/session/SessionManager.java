package olap.olap.project.web.session;

import javax.servlet.http.HttpSession;

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

	public Integer getCurrentUser() {
		return (Integer) this.session.getAttribute("user");
	}

	public void setCurrentUser(final Integer user) {
		this.session.setAttribute("user", user);
	}

	public void endSession() {
		this.session.invalidate();
	}
}
