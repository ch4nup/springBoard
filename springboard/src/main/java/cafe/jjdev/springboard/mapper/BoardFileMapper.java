package cafe.jjdev.springboard.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import cafe.jjdev.springboard.vo.Boardfile;

@Mapper
public interface BoardFileMapper {
	//파일정보를 입력하는 메서드 
	int insertBoardFile(Boardfile boardfile);
	//boardNo별 파일을 조회하는 메서드 
	List<Boardfile> selectBoardfile(int boardNo);
}
