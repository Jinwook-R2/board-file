package aloha.service;

import java.util.*;

import aloha.domain.Board;
import aloha.domain.FileInfo;

public interface BoardService {

	// 게시글 목록
	public List<Board> list() throws Exception;

	//게시글 쓰기
	public int insert(Board board) throws Exception;
	
	//게시글 읽기
	public Board read(Integer boardNo) throws Exception;
	
	//게시글 수
	public int update(Board board) throws Exception;
		
	//게시글 삭제	
	public int delete(Integer boardNo) throws Exception;
	
	//게시글 검색
	public List<Board> list(String keyword) throws Exception;
	
	//파일 목록
	public List<FileInfo> fileList(Integer refNo) throws Exception;
	
	//파일 읽
	public FileInfo readFile(Integer fileNo) throws Exception;

	
	//파일 삭제
	public void deleteFile (Integer fileNo) throws Exception;
	
}
