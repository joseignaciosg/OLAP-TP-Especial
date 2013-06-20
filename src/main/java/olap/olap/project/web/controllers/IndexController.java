package olap.olap.project.web.controllers;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import olap.olap.project.model.MultiDim;
import olap.olap.project.model.api.CubeApi;
import olap.olap.project.model.db.TableCreator;
import olap.olap.project.model.impl.CubeApiImpl;
import olap.olap.project.web.command.DBCredentialsForm;
import olap.olap.project.web.command.UploadXmlForm;
import olap.olap.project.web.session.SessionManager;
import olap.olap.project.xml.XmlConverter;

import org.dom4j.DocumentException;
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
				//TableCreator tc = new TableCreator(form.getUrl_db(), form.getUser_db(), form.getPassword_db());
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
			XmlConverter parser = new XmlConverter();
			
			//TODO remove - for parsing
			FileReader fr = null;
			fr = new FileReader(tmpFile);
			int inChar;
			while ((inChar = fr.read()) != -1) {
				System.out.printf("%c", inChar);
			}
			
			//TODO levantar las tablas y crear el archivo
			MultiDim xmlDocument = parser.parse(tmpFile);
			xmlDocument.print();
			
		}
		mav.setViewName("redirect:" + req.getServletPath()
				+ "/index/selectMode");
		return mav;
	}

	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView selectMode() throws ServletException, IOException {
		final ModelAndView mav = new ModelAndView();

		
		
		
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView autoMode() throws ServletException, IOException {
		final ModelAndView mav = new ModelAndView();
//		CubeApi creator = new CubeApiImpl();
//		creator.setDBCredentials(null, null, null);
//		creator.loadMultildimXml();
//		File starXml = creator.generateMDXAuto("out");
		//TODO
//		TableCreator tc = new TableCreator();
		return mav;
	}
	
	
	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView manualMode() throws ServletException, IOException {
		final ModelAndView mav = new ModelAndView();
//		TableCreator tc = new TableCreator();
		return mav;
	}
	
	@RequestMapping(method = RequestMethod.POST)
	protected ModelAndView downloadStarXml() throws ServletException, IOException {
		final ModelAndView mav = new ModelAndView();
		//TODO codigo para descargar el xml de salida
//		TableCreator tc = new TableCreator();
		return mav;
	}
	
	

	
	
	
}
