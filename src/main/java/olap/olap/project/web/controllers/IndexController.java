package olap.olap.project.web.controllers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import olap.olap.project.model.Dimension;
import olap.olap.project.model.ListWrapper;
import olap.olap.project.model.api.CubeApi;
import olap.olap.project.web.command.DBCredentialsForm;
import olap.olap.project.web.command.UploadXmlForm;
import olap.olap.project.web.session.SessionManager;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.XMLWriter;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/")
public class IndexController {

	public IndexController() {
	}

	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView olap(final DBCredentialsForm form)
			throws ServletException, IOException {
		final ModelAndView mav = new ModelAndView("index/index");
		mav.addObject("dbcredentialsform", form);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView uploadxml(final UploadXmlForm form)
			throws ServletException, IOException {
		final ModelAndView mav = new ModelAndView("index/uploadxml");
		mav.addObject("uploadxmlform", form);
		return mav;
	}
	

	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView connectToDB(final HttpServletRequest req,
			final DBCredentialsForm form, Errors errors) {
		ModelAndView mav = new ModelAndView();
		SessionManager man  = (SessionManager) req.getAttribute("manager");
		CubeApi ca  = man.getCubeApi();
		mav.setViewName("index/index");
		mav.addObject("dbcredentialsform", form);
		if (form.getUrl_db() == null) {
			errors.rejectValue("empty", "url_db");
			return mav;
		} else if (form.getUser_db() == null) {
			errors.rejectValue("empty", "user_db");
			return mav;
			
		} else if (form.getPassword_db() == null) {
			errors.rejectValue("empty", "password_db");
			return mav;
		} else {
			try {
				ca.setDBCredentials(form.getUrl_db(), form.getUser_db(), form.getPassword_db());
			} catch (Exception e) {
				mav.addObject("couldNotConnectToDB", true);
				return mav;
			}
		}
		man.setCubeApi(ca);	
		mav.setViewName("redirect:" + req.getServletPath()
				+ "/index/uploadxml");
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView uploadXml(final HttpServletRequest req,
			final UploadXmlForm form, Errors errors) throws DocumentException,
			IOException {
		ModelAndView mav = new ModelAndView();
		SessionManager man  = (SessionManager) req.getAttribute("manager");
		CubeApi ca  = man.getCubeApi();
		mav.addObject("uploadxmlform", form);
		if (form.getFile() == null) {
			errors.rejectValue("empty", "file");
			return mav;
		} else {
			MultipartFile xmlfile = form.getFile();
			File tmpFile = new File(System.getProperty("java.io.tmpdir")
					+ System.getProperty("file.separator")
					+ xmlfile.getOriginalFilename());
			xmlfile.transferTo(tmpFile);
			FileReader fr = null;
			fr = new FileReader(tmpFile);
			int inChar;
			while ((inChar = fr.read()) != -1) {
				System.out.printf("%c", inChar);
			}
			ca.loadMultildimXml(tmpFile);
		}
		mav.setViewName("redirect:" + req.getServletPath()
				+ "/index/selectMode");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView selectMode(final HttpServletRequest req) throws ServletException, IOException {
		final ModelAndView mav = new ModelAndView();

		
		
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView autoMode(final HttpServletRequest req) throws ServletException, IOException {
		final ModelAndView mav = new ModelAndView();
		SessionManager man  = (SessionManager) req.getAttribute("manager");
		CubeApi ca  = man.getCubeApi();
		Document outXml = ca.generateMDXAuto("out/out.xml");
		man.setOutXml(outXml);
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView manualDownloadMode(final HttpServletRequest req) throws ServletException, IOException {
		final ModelAndView mav = new ModelAndView();
		SessionManager man  = (SessionManager) req.getAttribute("manager");
		CubeApi ca  = man.getCubeApi();
		Document outXml = ca.generateMDXAuto("out/out.xml");
		man.setOutXml(outXml);
		return mav;
	}
	
	
	/*MANUAL MODE STEP 1 POST*/
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView manualMode(final HttpServletRequest req) throws SQLException, Exception {
		final ModelAndView mav = new ModelAndView();
		SessionManager man  = (SessionManager) req.getAttribute("manager");
		CubeApi ca  = man.getCubeApi();
		
		String error = (String) req.getParameter("error");
		if( error !=  null){
			mav.addObject("error", error);
		}
		
		/*getting table names*/
		List<String> tableNames = ca.getDBTableNames();
		mav.addObject("tableNames", tableNames);
				
		/*getting cube dimensions*/
		Collection<Dimension> dimensions = ca.getCubeDimensions();
		mav.addObject("dimensions", dimensions);
		return mav;
	}
	
	/*MANUAL MODE STEP 1*/
	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView manualModeUpdateTables( final HttpServletRequest req) throws SQLException, Exception {
		final ModelAndView mav = new ModelAndView("index/manualMode");
		SessionManager man  = (SessionManager) req.getAttribute("manager");
		CubeApi ca  = man.getCubeApi();
		Map<String,String[]> values = req.getParameterMap();
		boolean valid = false;
		/*the name of then first dimension with no matching*/
		String dimName = null;
		/*the name of then first table with no matching*/
		String tableName = null; 
		for (Map.Entry<String, String[]> entry : values.entrySet()){
			valid = ca.linkDimension(entry.getKey(), entry.getValue()[0]);
			if (!valid){
				dimName = entry.getKey();
				tableName = entry.getValue()[0];
				break; 
			}
		}
		
		if (valid) {
			mav.setViewName("redirect:" + req.getServletPath() + "/index/manualModeUpdateFields");
		} else {
			mav.addObject("error", "La table " + tableName + " no tiene la misma"+
						  " cantidad de propiedades que la dimensi&oacuten " + dimName );
			mav.setViewName("redirect:" + req.getServletPath() + "/index/manualMode");
		}
		return mav;
	}
	
	/*MANUAL MODE STEP 2 FORM*/
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView manualModeUpdateFields(final HttpServletRequest req) throws SQLException, Exception {
		final ModelAndView mav = new ModelAndView();	
		String error = (String) req.getParameter("error");
		SessionManager man  = (SessionManager) req.getAttribute("manager");
		CubeApi ca  = man.getCubeApi();
		if( error !=  null){
			mav.addObject("error", error);
		}
		
		/*getting table names*/
		List<String> tableNames = ca.getDBTableNames();
	
		/*TODO hacer que se pueda elegir un primary key*/
		List<ListWrapper> tables = new ArrayList<ListWrapper>();
		for(String tname: tableNames){
			ListWrapper tableList = new ListWrapper(tname, ca.getPropertiesForDimension(tname), ca.getDBFieldsForTable(tname));
			tables.add(tableList);
			System.out.println("FIELD LIST: " + ca.getDBFieldsForTable(tname));
			System.out.println("PROP LIST: " + ca.getPropertiesForDimension(tname));
		}
			
		mav.addObject("tables", tables);
		
		return mav;
		
	}
	
	/*MANUAL MODE STEP 2*/
	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView manualModeUpdateFieldsPost(final HttpServletRequest req) throws SQLException, Exception {
		final ModelAndView mav = new ModelAndView();
		SessionManager man  = (SessionManager) req.getAttribute("manager");
		CubeApi ca  = man.getCubeApi();
		
		Map<String,String[]> values = req.getParameterMap();
		System.out.println("VALUES:"+ values);
		boolean valid = false;
		/*the name of then first dimension with no matching*/
		String fieldName = null;
		/*the name of then first table with no matching*/
		String propName = null; 
		for (Map.Entry<String, String[]> entry : values.entrySet()){
			String value = entry.getValue()[0].split("/")[0];
			String tname= entry.getValue()[0].split("/")[1];
			/*se controla que el tipo sea el mismo dentro de chagePropertyName*/
			valid = ca.changePropertyName(tname, entry.getKey(), value);
			if (!valid){
				fieldName = entry.getKey();
				propName = value;
				break; 
			}
		}
		
		if (valid) {
			mav.setViewName("redirect:" + req.getServletPath() + "/index/manualDownloadMode");
		} else {
			mav.addObject("error", "Una de las asignaciones no es v&aacute;lida" );
			mav.setViewName("redirect:" + req.getServletPath() + "/index/manualModeUpdateFields");
		}
		return mav;
	}
	
	
	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView downloadStarXml(final HttpServletResponse response,
			final HttpServletRequest req) throws ServletException, IOException {
		
		final ModelAndView mav = new ModelAndView();
		SessionManager man  = (SessionManager) req.getAttribute("manager");
		CubeApi ca  = man.getCubeApi();
		
		//tell browser program going to return an application file 
        //instead of html page
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition","attachment;filename=out.xml");
        
        Document outXml = man.getOutXml();
        ServletOutputStream out = response.getOutputStream();
        XMLWriter writer = new XMLWriter(out);
        
        writer.write(outXml);
		out.flush();
		out.close();
		return mav;
	}

	
	
	

	
	
	
}
