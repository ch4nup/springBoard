package cafe.jjdev.springboard.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import cafe.jjdev.springboard.vo.Board;

@Mapper //spring bean형태로 만듦
public interface BoardMapper {
	 //하나의 게시물을 출력하는 selectBoard메서드 선언	
	 Board selectBoard(int boardNo);
	 //게시글목록을 출력하는 selectBoardList메서드 선언
	 List<Board> selectBoardList(Map<String, Integer> map);
	 //전체행의 갯수를 출력하는 selectBoardCount메서드 선언
	 int selectBoardCount(); 
	 //글을 등록하는 insertBoard메서드 선언
	 int insertBoard(Board board);
	 //글을 수정하는 updateBoard메서드 선언
	 int updateBoard(Board board);
	 //글을 삭제하는 selectBoard메서드 선언
	 int deleteBoard(Board board);	
}
