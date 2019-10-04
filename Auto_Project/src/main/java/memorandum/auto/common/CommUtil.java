package memorandum.auto.common;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CommUtil {
	static String tab = "";
	private static final Logger logger = LoggerFactory.getLogger(CommUtil.class);
	
	public static void ObjectLogger(Object param){
		if (param instanceof Map) {
			logger.info(tab + "===== Map Log Start =====");
			tab = tab + "    " ;
			MapLog((HashMap<Object, Object>) param);
			tab = tab.substring(0, tab.length()-4);
			logger.info(tab + "===== Map Log Start =====");
		}
		if (param instanceof List) {
			logger.info(tab + "===== List Log Start =====");
			tab = tab + "    " ;
			ListLog((List) param);
			tab = tab.substring(0, tab.length()-4);
			logger.info(tab + "===== List Log Start =====");
		}
		
		if (param instanceof Arrays) {
			logger.info(tab + "===== Arrays Log Start =====");
			tab = tab + "    " ;
			ArraysLog((Arrays) param);
			tab = tab.substring(0, tab.length()-4);
			logger.info(tab + "===== Arrays Log Start =====");
		}
		if (param instanceof String || param instanceof Integer) {
			logger.info(tab + "===== param ===== : " + param.toString());
		}
		
	}
	private static void ArraysLog(Arrays arr) {
		
		
	}
	private static void ListLog(List list) {
		for (int i = 0 ; i < list.size(); i ++) {
			Object obj = list.get(i);
			logger.info(tab + "List line " + i);
			ObjectLogger(obj);
		}
	}
	private static void MapLog(Map<Object, Object> map) {
		Set set = map.keySet();
		Iterator iter = set.iterator();
		
		int size = 0;
		while (iter.hasNext()) {
			String key = (String) iter.next();
			if (size < key.length()) {
				size = key.length();
			}
		}
		iter = set.iterator();
		while (iter.hasNext()) {
			String key = (String) iter.next();
			Object value =  map.get(key);
			key = RPAD(key, size);
			logger.info(String.format(tab + "Key : %s, Value : %s", key, value.toString()));
		}
	}
	
	private static String RPAD(String str, int len) {
		int tmplen = len - str.length();
		if (tmplen > 0) {
			for (int i = 0 ; i < tmplen; i ++) {
				str = str + " ";
			}
		}
		return str;
	}
	
	
}
