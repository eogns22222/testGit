package kr.co.photo.board.service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import kr.co.photo.board.dao.BoardDAO;
import kr.co.photo.board.dto.BoardDTO;
import kr.co.photo.board.dto.PhotoDTO;

@Service
public class BoardService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired BoardDAO dao;
	
	// 사진 불러오기시 server.xml 에 아래 내용 추가해야 함
	// <Context docBase="C:/upload" path="/photo"/>
	public String file_root = "C:/upload/";
	
	public List<BoardDTO> list() {
		return dao.list();
	}
	
	public void del(int idx) {
		
		List<PhotoDTO> list = dao.photos(idx);
		
		for (PhotoDTO photo : list) {
			Path path = Paths.get(file_root + photo.getNew_filename());
			try {
				Files.delete(path);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		dao.del(idx);
	}
	
	public int write(MultipartFile[] photos, Map<String, String> param) {
		int row = -1;
		
		BoardDTO dto = new BoardDTO();
		dto.setUser_name(param.get("user_name"));
		dto.setSubject(param.get("subject"));
		dto.setContent(param.get("content"));
		
		row = dao.write(dto);
		int idx = dto.getIdx();
		logger.info("방금 생성된 글 번호 : " + idx);
		
		if(row > 0) {
			fileSave(idx, photos);
		}
		
		return row;
	}
	
	private void fileSave(int idx, MultipartFile[] photos) {
		
		for (MultipartFile photo : photos) {
			String fileName = photo.getOriginalFilename();
			logger.info("fileName : " + fileName);
			
			if(!fileName.equals("")) {
				
				String ext = fileName.substring(fileName.lastIndexOf("."));
				logger.info(ext);
				
				String newFileName = System.currentTimeMillis() + ext;
				logger.info(fileName + " -> " + newFileName);
				
				try {
					byte[] bytes = photo.getBytes();
					Path path = Paths.get(file_root + newFileName);
					Files.write(path, bytes);
					dao.fileWrite(fileName, newFileName, idx);
					Thread.sleep(1);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			}
			
		}
		
	}
	
	public void detail(int idx, Model model) {
		dao.upHit(idx);
		BoardDTO dto = dao.detail(idx);
		model.addAttribute("bbs", dto);
		List<PhotoDTO> list = dao.photos(idx);
		logger.info("photos : {}", list);
		model.addAttribute("photos", list);
	}
	
	public void updateForm(int idx, Model model) {
		BoardDTO dto = dao.detail(idx);
		model.addAttribute("bbs", dto);
		List<PhotoDTO> list = dao.photos(idx);
		logger.info("photos : {}", list);
		model.addAttribute("photos", list);
	}
	
	public void update(MultipartFile[] photos, Map<String, String> param) {
		int row = -1;
		
		row = dao.update(param);
		
		if(row > 0) {
			int idx = Integer.parseInt(param.get("idx"));
			fileSave(idx, photos);
		}
		
	}
}






















