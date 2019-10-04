package memorandum.auto.service;

import java.util.HashMap;
import java.util.List;

public interface CommonService {

	Object getMain();

	List<HashMap<String, Object>> getRegularList();

	String getLastEp(String name);
	
	String getLastEp(String name, String season);

	int insertLog(HashMap<String, Object> log);

}
