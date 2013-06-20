package olap.olap.project.web.session;

import javax.servlet.http.HttpSession;

import olap.olap.project.model.api.CubeApi;

import org.dom4j.Document;

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
	
	public void setOutXml(Document outXml){
		this.session.setAttribute("outXml", outXml);
	}
	
	public CubeApi getCubeApi(){
		return (CubeApi) this.session.getAttribute("cubeApi");
	}
	
	public Document getOutXml(){
		return (Document) this.session.getAttribute("outXml");
	}
	
	

	public void endSession() {
		this.session.invalidate();
	}
}
