package memorandum.auto.dao;

import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Repository;

import memorandum.auto.common.CommUtil;
import memorandum.auto.common.session;

@Repository (value = "commonDao")
public class CommonDao extends session{

	public Object getMain() {
		return selectList("COMMON.getMain");
	}

	public List<HashMap<String, Object>> getRegularList() {
		return selectList("COMMON.getRegularList");
	}

	public String getLastEp(String name, String season) {
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("name", name);
		param.put("season", season);
		return selectOne("COMMON.getLastEp", param);
	}

	public int insertLog(HashMap<String, Object> log) {
		return insert("COMMON.insertLog",log);
	}
	
}
