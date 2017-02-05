package com.goiaba.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.goiaba.model.BoardEntity;
import com.goiaba.repository.BoardRepository;

@Service
public class BoardService {

	@Autowired
	private BoardRepository boardRepository;
	
	public List<BoardEntity> getAllBoards() {
		return boardRepository.findAll();
	}
}
