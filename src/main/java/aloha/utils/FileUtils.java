package aloha.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FileUtils {
	
	// 파일 정보 확인
	public void logFileInfo(MultipartFile file) {
		log.info("-------------------");
		log.info("파일"+file.getOriginalFilename()); // db경로, 서버 로컬에 파일 저장
		log.info("파일 타입"+file.getContentType());
		log.info("파일 사이즈"+file.getSize());
		log.info("-------------------");
	}
	
	
	// 파일 업로드(단일)
	
	// 파일 업로드(다중)
	
	// 파일 삭제(단일)
	
	// 파일 삭제(다중)
	
	
}
