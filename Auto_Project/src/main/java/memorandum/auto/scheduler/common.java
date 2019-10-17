package memorandum.auto.scheduler;

import java.io.IOException;
import java.net.URI;
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
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;

import memorandum.auto.service.CommonService;
import nl.stil4m.transmission.api.TransmissionRpcClient;
import nl.stil4m.transmission.api.domain.AddTorrentInfo;
import nl.stil4m.transmission.api.domain.AddedTorrentInfo;
import nl.stil4m.transmission.api.domain.TorrentInfo;
import nl.stil4m.transmission.rpc.RpcClient;
import nl.stil4m.transmission.rpc.RpcConfiguration;
import nl.stil4m.transmission.rpc.RpcException;

@Component
public class common {

	private static final Logger logger = LoggerFactory.getLogger(common.class);

	@Resource(name = "commonService")
	private CommonService service;

	// 초 분 시 일 월 요일
//	@Scheduled(cron = "* * */2 * * ? ") 
	@Scheduled(cron = "00 25 * * * ? ")
	public void regularDown() {
		logger.info("Reqular Down Start");

		// 1. DB에서 다운받을 검색어 리스트를 가져온다.
		List<HashMap<String, Object>> downList = service.getRegularList();

		for (HashMap<String, Object> item : downList) {
			if (search(item) == 0) {

			}
		}

		logger.info("Reqular Down End");
	}

	private int search(HashMap<String, Object> item) {
		String name = (String) item.get("NAME");
		String season = (String) item.get("SEASON");
		String lastEp = service.getLastEp(name);
		String category = (String) item.get("CATEGORY");
		try {
			String keyword = URLEncoder.encode(name, "UTF-8");
			String url = "https://torrentwal.com/bbs/s.php?k=";

			Connection conn = Jsoup.connect(url + keyword).header("User-Agent", "Mozilla/5.0");
			Document doc = conn.get();

			// 1. 마그넷주소, 타이틀, 에피소드, 시즌 확인
			Elements els = doc.select("tbody .bg1");
			for (Element el : els) {
				String magnet = el.select(".num a").attr("href").replace("javascript:Mag_dn('", "magnet:?xt=urn:btih:");
				magnet = magnet.substring(0, magnet.length() - 2);
				String title = el.select(".subject").text();
				String ep = "";
				String se = "";

				Pattern sePtn = Pattern.compile("^.*(S.[0-9]{1,3}).*$");
				Matcher seMat = sePtn.matcher(title);
				while (seMat.find()) {
					se = seMat.group(1);
				}
				if (se.equals("") || se.equals("S01")) {
					se = "S01";
					Pattern epPtn = Pattern.compile("^.*(E.[0-9]{1,3}).*$");
					Matcher epMat = epPtn.matcher(title);
					int lastep = Integer.parseInt(lastEp.substring(1, lastEp.length()));
					int crruntep = 0;
					while (epMat.find()) {
						ep = epMat.group(1);
						crruntep = Integer.parseInt(ep.substring(1, ep.length()));
					}
					if (crruntep > lastep) {
						// 토렌트 받기
						if (addTorrent(magnet)) {
							HashMap<String, Object> log = new HashMap<String, Object>();
							log.put("name", name);
							log.put("season", se);
							log.put("ep", ep);
							log.put("stat", "FS00");
							log.put("category", category);
							log.put("reg_id", "SYSTEM");
							log.put("use_yn", 'Y');
							service.insertLog(log);

						}
					}
				} else {
					if (se.equals(season)) {
						Pattern epPtn = Pattern.compile("^.*(E.[0-9]{1,3}).*$");
						Matcher epMat = epPtn.matcher(title);
						int lastep = Integer.parseInt(lastEp.substring(1, lastEp.length()));
						int crruntep = 0;
						while (epMat.find()) {
							ep = epMat.group(1);
							crruntep = Integer.parseInt(ep.substring(1, ep.length()));
						}
						if (crruntep > lastep) {
							// 토렌트 받기
							if (addTorrent(magnet)) {
								HashMap<String, Object> log = new HashMap<String, Object>();
								log.put("name", name);
								log.put("season", se);
								log.put("ep", ep);
								log.put("stat", "FS00");
								log.put("category", category);
								log.put("reg_id", "SYSTEM");
								log.put("use_yn", 'Y');
								service.insertLog(log);

							}
						}
					}
				}

			}
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		return 0;
	}

	private Boolean addTorrent(String magnet) {

		TransmissionRpcClient rpcClient;

		Boolean result = false;
		try {

			ObjectMapper objectMapper = new ObjectMapper();
			objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
			RpcConfiguration rpcConfiguration = new RpcConfiguration();
			rpcConfiguration.setHost(URI.create("http://memorandum.iptime.org:9091/transmission/rpc/"));
			RpcClient client = new RpcClient(rpcConfiguration, objectMapper);
			rpcClient = new TransmissionRpcClient(client);
			AddTorrentInfo addTorrentInfo = new AddTorrentInfo();
			addTorrentInfo.setFilename(magnet);
			AddedTorrentInfo ati = rpcClient.addTorrent(addTorrentInfo);
			TorrentInfo info = ati.getTorrentInfo();
			logger.info(info.getName() + " 을 등록하였습니다.");
			result = true;
		} catch (RpcException e) {
			System.out.println(e);
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			return result;
		}

		return result;

	}
}