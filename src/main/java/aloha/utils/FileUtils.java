package aloha.utils;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import aloha.domain.FileInfo;
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
	public FileInfo uploadFile(MultipartFile file, String uploadPath) throws Exception {
		
		//파일 정보 확
		logFileInfo(file);
		
		// 파일 존재여부 확
		if(file.isEmpty())
			return null;
		
		//파일명 중복 방지를 위한 고유 ID 생성
		UUID uid = UUID.randomUUID();
		
		// 실제 원본 파일 이름
		String originalFileName = file.getOriginalFilename();
		String uploadFileName = uid.toString() + "_" + originalFileName; 
		
		// UID_파일명.png
		
		// 업로드 폴더에 업로드할 파일 복사
		byte[] fileData = file.getBytes();
		
		File uploadFile = new File(uploadPath, uploadFileName);
		// 파일 객체 : ~/upload/UID_파일명.png
		// 스프링에서 제공되는 파일 복사 API
		FileCopyUtils.copy(fileData, uploadFile);
		
		// 업로드된 전체 경로 : ~/upload/UID_파일명.png 
		String uploadedPath  = uploadPath + "/" + uploadFileName;
		
		FileInfo fileInfo = new FileInfo();
		fileInfo.setFileName(originalFileName);
		fileInfo.setFullName(uploadedPath);
		
		return fileInfo;
		
		//파일 객체 변환
	}
	
	
	// 파일 업로드(다중)
	public List<FileInfo> uploadFiles(MultipartFile files[], String uploadPath) throws Exception {
		
		LinkedList<FileInfo> fileList = new LinkedList<FileInfo>();
		
		for(MultipartFile file : files) {
			
			FileInfo fileInfo = uploadFile(file, uploadPath);
			
			if(fileInfo != null) {
				fileList.add(fileInfo);
			}

			
		}
		return fileList;
		
	}
	
	// 파일 삭제(단일)
	public boolean deleteFile(String filePath) throws Exception {
		
		File file = new File(filePath);
		
		// 실제로 파일이 존재하는지 확인
		if(!file.exists()) {
			log.info("삭제(실패) : "+ filePath);
			return false;
		}
		
		// 파일 삭제
		if(file.delete()) {
			log.info("삭제한 파일: "+ filePath);
			log.info("파일삭제 성공!");
		}else {
			log.info("파일삭제 실패!");
			return false;
		}
		
		return true;
		
	}
	
	// 파일 삭제(다중)
	public void deleteFiles(List<String> filePath) throws Exception {
	
		for (String path : filePath) {
			deleteFile(path);
		}		
	}

	// 파일 목록 삭제		
	public void deleteFileList(List<FileInfo> fileList) throws Exception {
		
		for(FileInfo fileInfo : fileList) {
			deleteFile(fileInfo.getFullName());
		}		
	}
	
}
