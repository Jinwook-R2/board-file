package aloha.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import aloha.domain.Board;
import aloha.mapper.BoardMapper;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private BoardMapper mapper;
	
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
		return mapper.insert(board);
	}

	@Override
	public int update(Board board) throws Exception {
		return mapper.update(board);
	}

	@Override
	public int delete(Integer boardNo) throws Exception {
		return mapper.delete(boardNo);
	}

	@Override
	public List<Board> list(String keyword) throws Exception {
		
		keyword = keyword == null ? "" : keyword;
		
		return mapper.search(keyword);
	}

}
