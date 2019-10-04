package memorandum.auto.service;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import memorandum.auto.dao.CommonDao;

@Service(value = "commonService")
public class CommonServiceImpl implements CommonService {
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(CommonServiceImpl.class);
	
	@Resource(name="commonDao")
	private CommonDao dao;
	
	@Override
	public Object getMain() {
		return dao.getMain();
	}

	@Override
	public List<HashMap<String, Object>> getRegularList() {
		return dao.getRegularList();
	}

	@Override
	public String getLastEp(String name) {
		return getLastEp(name, "S01");
	}

	@Override
	public String getLastEp(String name, String season) {
		// TODO Auto-generated method stub
		return dao.getLastEp(name, season);
	}

	@Override
	public int insertLog(HashMap<String, Object> log) {
		return dao.insertLog(log);
	}
}
