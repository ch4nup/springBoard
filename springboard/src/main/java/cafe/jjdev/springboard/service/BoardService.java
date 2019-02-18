package cafe.jjdev.springboard.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import cafe.jjdev.springboard.mapper.BoardMapper;
import cafe.jjdev.springboard.vo.Board;

@Service
@Transactional //중간에 예외가 발생하면 모두 취소
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	//BoardMapper 객체 내 하나의 글을 검색하는 selectBoard메서드 호출
	public Board getBoard(int boardNo) {
		return boardMapper.selectBoard(boardNo);
	}
	//BoardMapper 객체 내 글 목록을 검색하는 selectBoardcount메서드 호출, 페이지당 10행씩 출력, 리턴값 Map영역에 세팅 후 리턴.
	public Map<String, Object> selectBoardList(int currentPage) {
		final int ROW_PER_PAGE = 10;
		int startRow = (currentPage - 1) * ROW_PER_PAGE;
		Map<String, Integer> map = new HashMap<String, Integer>();
		
		map.put("startRow", startRow);
		map.put("ROW_PER_PAGE", ROW_PER_PAGE);
		
		int boardCount = boardMapper.selectBoardCount();
		int lastPage = (int)(Math.ceil(boardCount / ROW_PER_PAGE));
		Map<String, Object> returnMap = new HashMap<String, Object>();		
		returnMap.put("list", boardMapper.selectBoardList(map));
		returnMap.put("boardCount", boardCount);
		returnMap.put("lastPage", lastPage);
		
		return returnMap;
	}
	//BoardMapper 객체 내 전체행을 검색하는 selectBoardCount메서드 호출
	public int getBoardCount() {
		return boardMapper.selectBoardCount();
	}
	//BoardMapper 객체 내 글을 등록하는 insertBoard메서드 호출
	public int addBoard(Board board) {
		return boardMapper.insertBoard(board);
	}
	//BoardMapper 객체 내 글을 삭제하는 deleteBoard메서드 호출
	public int removeBoard(Board board) {
		
		return boardMapper.deleteBoard(board);
	}
	//BoardMapper 객체 내 글을 수정하는 updateBoard메서드 호출
	public int modifyBoard(Board board) {
		return boardMapper.updateBoard(board);
	}
}
