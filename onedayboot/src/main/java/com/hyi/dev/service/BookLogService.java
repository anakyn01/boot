package com.hyi.dev.service;

import org.springframework.stereotype.Service;

import com.hyi.dev.dto.BookLogCreateDTO;
import com.hyi.dev.dto.BookLogCreateResponseDTO;
import com.hyi.dev.entity.Book;
import com.hyi.dev.entity.BookLog;
import com.hyi.dev.entity.BookLogRepository;
import com.hyi.dev.entity.BookRepository;

@Service//기록 서비스
public class BookLogService {
	private BookRepository bookRepository;
	private BookLogRepository bookLogRepository;
	//리포지터리 2개를 선언하고 생성자를 통해 의존성을 주입받음
	//기록을 위한 리포지토리 정보를 다루는 리포지토리 jpa를 통해 값을 불러와야 하기 때문에 필요함
	public BookLogService(BookRepository bookRepository, BookLogRepository bookLogRepository) {
		this.bookRepository = bookRepository;
		this.bookLogRepository = bookLogRepository;
	}
	
	public BookLogCreateResponseDTO insert(BookLogCreateDTO bookLogCreateDTO) {
		//기록 객체를 생성할때 사용 => 정보를 팔미터로 입력했을때 오류가 나거나 잘못된 데이터가 들어가는 일을 방지
		Book book = this.bookRepository.findById(bookLogCreateDTO.getBookId()).orElseThrow(null);
		//기록 객체를 생성
		BookLog bookLog = BookLog.builder()
		.book(book)
		.comment(bookLogCreateDTO.getComment())
		.page(bookLogCreateDTO.getPage())
		.build(); //에레이 사용시 새롭게 추가사항이 발생하면 이전 배열이 가빚 컬렉터로 가고 코드가 길어서 효율적으로 빌더사용
		//기록정보를 저장
		bookLog = this.bookLogRepository.save(bookLog);
		return BookLogCreateResponseDTO.BookLogFactory(bookLog);
	}
	

}
