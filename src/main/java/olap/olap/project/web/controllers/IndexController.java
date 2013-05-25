package olap.olap.project.web.controllers;

import java.io.IOException;

import javax.servlet.ServletException;

import olap.olap.project.web.command.UploadXmlForm;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;


@Controller
@RequestMapping("/")
public class IndexController {


	public IndexController() {
	}

	@RequestMapping(method = RequestMethod.GET)
	protected ModelAndView index(final UploadXmlForm form)
			throws ServletException, IOException {
		final ModelAndView mav = new ModelAndView("index/index");
		mav.addObject("uploadxmlform", form);
		//mav.addObject("loginForm", new LoginForm());
		return mav;
	}


}
