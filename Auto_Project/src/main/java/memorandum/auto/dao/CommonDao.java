package memorandum.auto.dao;

import org.springframework.stereotype.Repository;

import memorandum.auto.common.session;

@Repository (value = "commonDao")
public class CommonDao extends session{

	public Object getMain() {
		return selectList("COMMON.getMain");
	}
	
}
