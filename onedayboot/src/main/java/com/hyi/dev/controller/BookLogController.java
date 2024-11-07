package com.hyi.dev.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hyi.dev.dto.BookLogCreateDTO;
import com.hyi.dev.dto.BookLogCreateResponseDTO;
import com.hyi.dev.service.BookLogService;

/*
Representational State Transfer API => 
- 직관적

경랼형 데이터 json 
*/
/*뷰가 없다 메소드가 반환한 값을 컨버터를 거쳐서 바로 클라이언트에게 응답한다
컨버터 => 객체를 다른 객체형식으로 바꿔주는 기능을 하는 클래스 자바객체를 json형식으로 바꿔주는 jackson컨버터 내장
자바객체를 반환하면 그자바객체를 jackson라이브리가 json형식으로 바꿔줍니다
Response + Controller => RestController
*/
@RestController
@RequestMapping("/book-log")
public class BookLogController {
	
	private BookLogService bookLogService;
	
	@Autowired
	public void setBookLogService(BookLogService bookLogService) {
		this.bookLogService = bookLogService;
	}
	
	@PostMapping("/create")
	public ResponseEntity<BookLogCreateResponseDTO> insert(@RequestBody BookLogCreateDTO bookLogCreateDTO){
		BookLogCreateResponseDTO bookLogCreateResponseDTO = this.bookLogService.insert(bookLogCreateDTO);
		return ResponseEntity.ok(bookLogCreateResponseDTO);
	} 
	/*
	 insert 메소드는 ResponseEntity<T>타입 객체를 리턴
	 ResponseEntity => http응답(response)를 감싸는 객체입니다
	 @RequestBody BookLogCreateDTO bookLogCreateDTO => 입력값을 자바 객체로 바꿔줍니다
	 따라서 입력 json을  BookLogCreateDTO객체로 캐스팅 합니다
	 @RequestBody : 요청객체 (json, Form..) => 컨버터 => 자바객체 
	 @ResponseBody : 자바객체 => 컨버터 => 응답객체json)
	 */
	
	
	
	
	
	
	
	
	
	
	

}
