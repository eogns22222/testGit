package kr.co.photo.board.dao;

import java.util.List;
import java.util.Map;

import kr.co.photo.board.dto.BoardDTO;
import kr.co.photo.board.dto.PhotoDTO;

public interface BoardDAO {

	List<BoardDTO> list();

	void del(int idx);

	int write(BoardDTO dto);

	BoardDTO detail(int idx);

	void upHit(int idx);

	int update(Map<String, String> param);

	void fileWrite(String fileName, String newFileName, int idx);

	List<PhotoDTO> photos(int idx);


}
