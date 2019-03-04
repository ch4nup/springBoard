package cafe.jjdev.springboard.mapper;

import org.apache.ibatis.annotations.Mapper;

import cafe.jjdev.springboard.vo.BoardRequest;
import cafe.jjdev.springboard.vo.Boardfile;

@Mapper
public interface BoardFileMapper {
	 int insertBoardFile(Boardfile boardfile);
	 Boardfile selectBoardfile(int boardNo);
}
