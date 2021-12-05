package aloha.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.MediaType;

//파일 포맷에 맞는 미디어 타입을 확인
public class MediaUtils {
	
	private static Map<String, MediaType> mediaMap;
	
	static {
		//서버에서 클라이언트로 이미지파일 응답시 HTTP규격에 맞춰 보내주기 위해 넣어준다.	
		mediaMap = new HashMap<String, MediaType>();
		mediaMap.put("JPG", MediaType.IMAGE_JPEG);
		mediaMap.put("GIF", MediaType.IMAGE_GIF);
		mediaMap.put("PNG", MediaType.IMAGE_GIF);
	}
	
	
	public static MediaType getMediaType(String type) {
		return mediaMap.get(type.toUpperCase());
	}
}
