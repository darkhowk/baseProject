package memorandum.auto.common;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class util {
	static String tab = "";
	private static final Logger logger = LoggerFactory.getLogger(util.class);
	
	public static void ObjectLogger(Object param){
		if (param instanceof HashMap) {
			logger.info(tab + "===== HashMap Log Start =====");
			tab = tab + "    " ;
			HashMapLog((HashMap<Object, Object>) param);
			tab = tab.substring(0, tab.length()-4);
			logger.info(tab + "===== HashMap Log Start =====");
		}
		if (param instanceof LinkedHashMap) {
			logger.info(tab + "===== LinkedHashMap Log Start =====");
			tab = tab + "    " ;
			LinkedHashMapLog((LinkedHashMap<Object, Object>) param);
			tab = tab.substring(0, tab.length()-4);

			logger.info(tab + "===== LinkedHashMap Log Start =====");
		}
		if (param instanceof List) {
			logger.info(tab + "===== List Log Start =====");
			tab = tab + "    " ;
			ListLog((List) param);
			tab = tab.substring(0, tab.length()-4);
			logger.info(tab + "===== List Log Start =====");
		}
		
	}

	private static void ListLog(List list) {
		for (int i = 0 ; i < list.size(); i ++) {
			Object obj = list.get(i);
			logger.info(tab + "List line " + i);
			ObjectLogger(obj);
		}
	}
	private static void LinkedHashMapLog(LinkedHashMap<Object, Object> map) {
		Set set = map.keySet();
		Iterator iter = set.iterator();
		
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = (String) map.get(key);
			logger.info(String.format(tab + "Key : %s, Value : %s", key, value));
		}
	}
	private static void HashMapLog(HashMap<Object, Object> map) {
		Set set = map.keySet();
		Iterator iter = set.iterator();
		
		while (iter.hasNext()) {
			String key = (String) iter.next();
			String value = (String) map.get(key);
			logger.info(String.format(tab + "Key : %s, Value : %s", key, value));
		}
	}
	
}
