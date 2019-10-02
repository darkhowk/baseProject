package memorandum.auto.scheduler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class common {
	
	@SuppressWarnings("unused")
	private static final Logger logger = LoggerFactory.getLogger(common.class);
	
	@Scheduled(cron = "* * * * * ? ") 
	public void test() {
		logger.info("controller ??");
	}
	

	//                 초 분 시 일 월 요일
	@Scheduled(cron = "* * */2 * * ? ") 
	public void regularDown() {
		// 1. DB에서 다운받을 검색어 리스트를 가져온다.
		// 2. DB에서 해당 검색어의 마지막 에피소드 받은것 가져온다.
		// 3. 검색한다. 
		// 4. 마지막 에피소드 이상의 것을 받는다.
		// 5. 받으면서 DB에 입력.
		// 6. 다 받아지면 DB 업데이트 및 토렌트 목록 삭제 및 이동
			try {
				String keyword = URLEncoder.encode("백종원의 골목식당", "UTF-8") ;
				String url = "https://torrentwal.com/bbs/s.php?k=";
				
				Connection conn = Jsoup.connect(url+keyword).header("User-Agent", "Mozilla/5.0");
				Document doc = conn.get();
				
				// 1. 마그넷주소, 타이틀, 에피소드, 시즌 확인
				Elements els = doc.select("tbody .bg1");
				for (Element el : els) {
					String magnet = el.select(".num a").attr("href").replace("javascript:Mag_dn('", "magnet:?xt=urn:btih:").replace(")'", "");
					String title = el.select(".subject").text();
					String ep = "";
					String se = "";
					// 1. 에피소드 
					Pattern epPtn = Pattern.compile("^.*(E.[0-9]{1,3}).*$");
					Matcher epMat = epPtn.matcher(title);
					
					while(epMat.find()) {
						ep = epMat.group(1);
				    }
					System.out.println(ep);
					
					// 2. 시즌
					Pattern sePtn = Pattern.compile("^.*(S.[0-9]{1,3}).*$");
					Matcher seMat = sePtn.matcher(title);
					while(seMat.find()) {
						se = seMat.group(1);
				    }
					System.out.println(se);
				}
				
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
	}
}
