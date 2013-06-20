package olap.olap.project.web.filters;

import java.io.IOException;
import java.util.regex.Pattern;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import olap.olap.project.model.impl.CubeApiImpl;
import olap.olap.project.web.session.SessionManager;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
public class SessionFilter extends OncePerRequestFilter {

	private String excludePatterns;

	@Override
	public void initFilterBean() throws ServletException {
		addRequiredProperty("excludePatterns");
	}

	public void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {

		final HttpServletRequest httpRequest = (HttpServletRequest) request;

		final HttpServletResponse httpResponse = (HttpServletResponse) response;

		final SessionManager man = new SessionManager(httpRequest.getSession());

//		if (man.getCubeApi() != null) {
//			httpRequest.setAttribute("cubeApi", man.getCubeApi());
//		}else{
//			man.setCubeApi(new CubeApiImpl());
//			httpRequest.setAttribute("cubeApi", man.getCubeApi());
//		}
		
		if (man.getCubeApi() == null) {
			man.setCubeApi(new CubeApiImpl());
		}

		httpRequest.setAttribute("basePath", httpRequest.getContextPath() + httpRequest.getServletPath());
		httpRequest.setAttribute("manager", man);
		System.out.println("here!!!!!");
		httpResponse.setCharacterEncoding("UTF-8");

    	// Assets and other pages are for anonymous users
		if (Pattern.matches(getExcludePatterns(), httpRequest.getRequestURL())) {
			filterChain.doFilter(request, response);
			return;
		}

		if (httpRequest.getServletPath().equals("/")
				|| httpRequest.getServletPath().equals("")) {
			httpResponse.sendRedirect(httpRequest.getContextPath()
					+ httpRequest.getServletPath() + "/index/olap");
			return;
		}

		if (man.getCubeApi() != null) {
			filterChain.doFilter(request, response);
		} else {
			httpResponse.sendRedirect(httpRequest.getContextPath()
					+ httpRequest.getServletPath() + "/index/olap");
		}
	}

	public void destroy() {
	}

	public void setExcludePatterns(String patterns) {
		excludePatterns = patterns;
	}

	public String getExcludePatterns() {
		return excludePatterns;
	}

}
