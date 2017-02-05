package com.goiaba.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.goiaba.model.BoardEntity;
import com.goiaba.service.BoardService;

@RestController
@RequestMapping("/downloaderJobs")
public class BoardController {

	@Autowired
	private BoardService boardService;
	
	@CrossOrigin(origins="http://localhost:8080")
	@RequestMapping(value = "/getAllBoards", method = RequestMethod.GET)
	public Collection<BoardEntity> getAllBoards() {
		return boardService.getAllBoards();
	}

}
