package memorandum.auto.controller;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import memorandum.auto.common.util;
import memorandum.auto.service.CommonService;

@Controller
public class CommonController {

	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CommonController.class);
	
	@Resource(name="commonService")
	private CommonService service;
	
	@RequestMapping(value = "common", method = {RequestMethod.GET, RequestMethod.POST})
	public Object common( @RequestParam HashMap<String, Object> param, HttpServletRequest request) {
		String result = "";
		String type = (String) param.get("type");
		
		util.ObjectLogger(service.getMain());
		
		switch(type) {
			case "main" :result = "home";
				break;
			default : result = "error";
		}
		
		return result;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value ="/ajax", method=RequestMethod.POST)
	@ResponseBody
	public Object adminMenu(HttpSession session, @RequestParam HashMap<String, Object> param, HttpServletRequest request) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		String type = (String) param.get("type");
		
		switch(type) {
			case "main" : result = (HashMap<String, Object>) service.getMain();
				break;
			default : result = null;
		}
		
		return result; 
	}
	
}
