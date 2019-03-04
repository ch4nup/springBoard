package cafe.jjdev.springboard.service;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import cafe.jjdev.springboard.mapper.BoardFileMapper;
import cafe.jjdev.springboard.mapper.BoardMapper;
import cafe.jjdev.springboard.vo.Board;
import cafe.jjdev.springboard.vo.BoardRequest;
import cafe.jjdev.springboard.vo.Boardfile;

@Service
@Transactional //중간에 예외가 발생하면 모두 취소
public class BoardService {
	@Autowired
	private BoardMapper boardMapper;
	@Autowired
	private BoardFileMapper boardFileMapper;
	
	//BoardMapper 객체 내 하나의 글을 검색하는 selectBoard메서드 호출
	public Board getBoard(int boardNo) {
		return boardMapper.selectBoard(boardNo);
	}
	public Boardfile getBoardfile(int boardNo) {
		return boardFileMapper.selectBoardfile(boardNo);
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
	public void addBoard(BoardRequest boardRequest, String path) throws IllegalStateException, IOException {
		System.out.println("02 addBoard.BoardService");
		/*
		 * 1. board를 분리시킴 : board와 file과 boardFile정보로 분리
		 * 2. board에서 분리된 board를 boardVo
		 * 3. board에서 분리된 file정보를 boardFileVo
		 * 4. 실제 파일은 하드디스크(물리적장치)에 저장하고 저장할때 경로가 필요(path)
		 */
		//1
		Board board = new Board();
		board.setBoardTitle(boardRequest.getBoardTitle());
		board.setBoardContent(boardRequest.getBoardContent());
		board.setBoardDate(boardRequest.getBoardDate());
		board.setBoardPw(boardRequest.getBoardPw());
		board.setBoardUser(boardRequest.getBoardUser());
		boardMapper.insertBoard(board); //cafe 1280
		System.out.println("board : " + board);
		//2				
		List<MultipartFile> files = boardRequest.getFiles();
		for(MultipartFile f : files) {
			//f를 하나의 boardfile로 바꿔야함
			//전체작업이 롤백되더라도 파일삭제작업은 직접해야함
			Boardfile boardfile = new Boardfile();
			boardfile.setBoardNo(board.getBoardNo());
			System.out.println("board.boardNo : " + board.getBoardNo());
			boardfile.setFileSize(f.getSize());
			boardfile.setFileType(f.getContentType());
			
			String originalFilename = f.getOriginalFilename();
			int i = originalFilename.lastIndexOf(".");
			String ext = originalFilename.substring(i+1);
			boardfile.setFileExt(ext);
			String fileName = UUID.randomUUID().toString();
			boardfile.setFileName(fileName);
			//3 파일저장			
			f.transferTo(new File(path+"/"+ fileName + "." + ext));		
			boardFileMapper.insertBoardFile(boardfile);
		}	
			
					
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
