package memorandum.auto.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import memorandum.auto.dao.CommonDao;

@Service(value = "commonService")
public class CommonServiceImpl implements CommonService {
	
	@Resource(name="commonDao")
	private CommonDao dao;
	
	@Override
	public Object getMain() {
		return dao.getMain();
	}

}
