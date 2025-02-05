package com.hyi.dev.dto;

import com.hyi.dev.entity.BookLog;

import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class BookLogCreateResponseDTO {
	private Integer bookLogId;
	private Integer bookId;
	private String comment;
	private Integer page;
	
	public BookLogCreateResponseDTO fromBookLog(BookLog bookLog) {
		this.bookLogId = bookLog.getBookLogId();
		this.bookId = bookLog.getBook().getBookId();
		this.comment = bookLog.getComment();
		this.page = bookLog.getPage();
		return this;	
	}
	/*fromBookLog을 감싸주는 역활을 하는 정적메소드 사용할때마다 객체를 만들고 값을 설정하기 위해 
	fromBookLog메소드를 호출하는 일을 줄여줍니다*/
	public static BookLogCreateResponseDTO BookLogFactory(BookLog bookLog){
		BookLogCreateResponseDTO bookLogCreateResponseDTO = new BookLogCreateResponseDTO();
		bookLogCreateResponseDTO.fromBookLog(bookLog);
		return bookLogCreateResponseDTO;
		
	}
	
	
	
	
	
	
	
	
}
