package aloha.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

import aloha.domain.Board;
import aloha.domain.FileInfo;

@Mapper
public interface BoardMapper {

	//게시글 목록
	public List<Board> list() throws Exception;
	
	//게시글 읽기
	public Board read(Integer boardNo) throws Exception;
	
	//게시글 쓰기
	public int insert(Board board) throws Exception;
	
	//게시글 수
	public int update(Board board) throws Exception;
		
	//게시글 삭제	
	public int delete(Integer boardNo) throws Exception;
	
	//게시글 검색
	public List<Board> search(String keyword) throws Exception;
	
	// 파일 업로드
	public void uploadFile(FileInfo fileInfo) throws Exception;
	
	//파일 목록
	public List<FileInfo> fileList(Integer refNo) throws Exception;
	
	//파일 읽기
	public FileInfo readFile(Integer fileNo) throws Exception;

	public void deleteFile(Integer fileNo) throws Exception;
}
