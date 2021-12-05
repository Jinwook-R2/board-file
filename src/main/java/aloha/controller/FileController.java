package aloha.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import aloha.domain.FileInfo;
import aloha.service.BoardService;
import aloha.utils.FileUtils;
import aloha.utils.MediaUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Controller
@RequestMapping("/file")
public class FileController {

	@Autowired
	private BoardService service;
	
	@Autowired
	private FileUtils fileUtils;
	
	@GetMapping("")
	public void fileDownload(HttpServletRequest request, HttpServletResponse response, Integer fileNo) throws Exception {
		
		FileInfo fileInfo = service.readFile(fileNo);
		
		String fileName = fileInfo.getFileName();
		String fullName = fileInfo.getFullName();
		
		//다운로드할 파일
		File file = new File(fullName);
		
		FileInputStream fileInputStream = null;
		ServletOutputStream servletOutputStream = null;
		
		try {
			String downloadFileName = null;
			String browser = request.getHeader("User-Agent");
			
			// 브라우저별, 파일명 인코딩		
			   if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){//브라우저 확인 파일명 encode  
		             downloadFileName = URLEncoder.encode(fileName,"UTF-8").replaceAll("\\+", "%20");
		          }else{
		             downloadFileName = new String(fileName.getBytes("UTF-8"), "ISO-8859-1");
		          }
			   
			   // response 헤더에 다운로드 파일명, 전송 인코딩 등 세팅	
			   
			   response.setHeader("Content-Disposition", "attachment;filename=\"" + downloadFileName + "\"");
			   response.setContentType("text/html");
			   response.setHeader("Content-Transfer-Encoding", "binary;");
			   
			   fileInputStream = new FileInputStream(file);
			   servletOutputStream = response.getOutputStream();
			   
			   FileCopyUtils.copy(fileInputStream, servletOutputStream);
			   
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if(servletOutputStream != null) {
				try {
					servletOutputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			
			if(fileInputStream != null) {
				try {
					fileInputStream.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}			
		}
	}
	
	// 압축파일 다운로드
	@GetMapping("/zip")
	public void zipDownload(HttpServletRequest request, HttpServletResponse response, Integer boardNo, String title) throws Exception {
		
		// 파일 목록 조회
		List<FileInfo> fileList = service.fileList(boardNo);
		
		String zipFile = "temp.zip";
		String downloadFileName = title;
		
		// 파일 인코딩
	      String browser = request.getHeader("User-Agent");
	      if(browser.contains("MSIE") || browser.contains("Trident") || browser.contains("Chrome")){//브라우저 확인 파일명 encode  
	         downloadFileName = URLEncoder.encode(downloadFileName,"UTF-8").replaceAll("\\+", "%20");
	          
	      }else{
	         downloadFileName = new String(downloadFileName.getBytes("UTF-8"), "ISO-8859-1");
	      }
	        
	      response.setContentType("application/zip");
	      response.addHeader("Content-Disposition", "attachment; filename=" + downloadFileName + ".zip");
	      
	     // 압축 파일 생성
	     try {
	    	 FileOutputStream  fout = new FileOutputStream(zipFile);
	    	 ZipOutputStream zout = new ZipOutputStream(fout);
	    	 
	    	 for(int i = 0 ; i < fileList.size() ; i++) {
	    		 // 압축파일 내에 파일 항목을 추가
	    		 ZipEntry zipEntry = new ZipEntry(fileList.get(i).getFileName());
	    		 zout.putNextEntry(zipEntry);
	    		 
	    		 FileInputStream fin = new FileInputStream(fileList.get(i).getFullName());
	    		 
	    		 // 압축 파일 내 항목의 파일 데이터를 복사	
	    		 byte[] buffer = new byte[1024];
	    		 
	    		 int data = 0;
	    		 
	    		 while( (data = fin.read(buffer)) > 0) {
	    			 zout.write(buffer, 0, data);
	    		 }
	    		 
	    		 zout.closeEntry();
	    		 fin.close();
	    		 
	    	 }
	    	 
	    	 zout.close();
	    	 //압축파일에 파일항목 데이터 추가 끝
	    	 
	    	 //압축파일을 클라이언트로 전송
	    	 
	    	 FileInputStream fis = new FileInputStream(zipFile);
	    	 
	    	 ServletOutputStream sos = response.getOutputStream();
	    	 
	    	 //다운로드
	    	 FileCopyUtils.copy(fis, sos);
	    	 
	    	 fis.close();
	    	 sos.close();
	    	 
	     }catch(Exception e) {
	    	 e.printStackTrace();
	     } finally {
	    	 	
	    	 
	     }
	     
	     
	}
	
	//파일 삭제
	@DeleteMapping("")
	public ResponseEntity<String> deleteFile(Integer fileNo) throws Exception {
		
		FileInfo fileInfo = service.readFile(fileNo);
		String fullName = fileInfo.getFullName();
		
		if(fileUtils.deleteFile(fullName)) {
			// 실제 파일 삭제 시 DB 파일정보도 삭제
			service.deleteFile(fileNo);
		} else {
			return new ResponseEntity<String>("Fail", HttpStatus.OK);	
		}
		
		// 200 ok 응답
		return new ResponseEntity<String>("Success", HttpStatus.OK);
	}
	
	
	// 썸네일 보여주기
	@ResponseBody
	@GetMapping("/img")
	public ResponseEntity<byte[]> displayFile(Integer fileNo) throws Exception {
		
		FileInputStream fis = null;
		ResponseEntity<byte[]> entity = null;
		
		FileInfo fileInfo = service.readFile(fileNo);
		String fullName = fileInfo.getFullName();
		String fileName = fileInfo.getFileName();
		
		try {
			// jpg, png, gif (확장자)
			String formatName = fullName.substring(fullName.lastIndexOf(".") + 1);
			
			MediaType mType = MediaUtils.getMediaType(formatName);
			
			HttpHeaders headers = new HttpHeaders();
			
			fis = new FileInputStream(fullName);
			
			if(mType != null) {
				headers.setContentType(mType);
			}else {
				headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
				headers.add("Content-Disposition", "attachment; filename=\"" 
	                     + new String(fileName.getBytes()));
			}
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(fis), headers, HttpStatus.CREATED);
			
		}catch(Exception e) {
			e.printStackTrace();
		} finally {
			fis.close();
		}
		
		return entity;
		
	}
	
	
}
