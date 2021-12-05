package aloha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import aloha.domain.Board;
import aloha.domain.FileInfo;
import aloha.mapper.BoardMapper;
import aloha.utils.FileUtils;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
	@Autowired
	private FileUtils fileUtils;
	
	@Value("${upload.path}")
	private String uploadPath;
	
	@Override
	public List<Board> list() throws Exception {
		return mapper.list();
	}
	
	@Override
	public Board read(Integer boardNo) throws Exception {
		return mapper.read(boardNo);
	}

	@Override
	public int insert(Board board) throws Exception {
		
		// 글쓰기
		int result = mapper.insert(board);
		
		fileUpload(board);
		
		return result;
	}

	@Override
	public int update(Board board) throws Exception {
		
		fileUpload(board);
		
		return mapper.update(board);
	}

	@Override
	public int delete(Integer boardNo) throws Exception {
		
		List<FileInfo> fileList = mapper.fileList(boardNo);
		
		fileUtils.deleteFileList(fileList);
		
		return mapper.delete(boardNo);
	}

	@Override
	public List<Board> list(String keyword) throws Exception {
		
		keyword = keyword == null ? "" : keyword;
		
		return mapper.search(keyword);
	}

	@Override
	public List<FileInfo> fileList(Integer refNo) throws Exception {
		return mapper.fileList(refNo);
	}

	@Override
	public FileInfo readFile(Integer fileNo) throws Exception {
		return mapper.readFile(fileNo);
	}

	@Override
	public void deleteFile(Integer fileNo) throws Exception {
		mapper.deleteFile(fileNo);
	}
	
	// 파일 업로드
	public void fileUpload(Board board) throws Exception {

		MultipartFile[] files = board.getFile();
		
		// 파일 업로드
		List<FileInfo> fileList = fileUtils.uploadFiles(files, uploadPath);
		
		for(FileInfo fileInfo : fileList) {
			int boardNo = board.getBoardNo();
			fileInfo.setRefNo(boardNo);
			mapper.uploadFile(fileInfo);
		}
		
		
	}

}
