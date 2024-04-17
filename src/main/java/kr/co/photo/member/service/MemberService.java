package kr.co.photo.member.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import kr.co.photo.member.dao.MemberDAO;

@Service
public class MemberService {
	Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired MemberDAO dao;
	public String login(String id, String pw) {
		return dao.login(id, pw);
	}
}
