package com.hyi.dev.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.hyi.dev.entity.Book;

import lombok.Getter;
import lombok.NoArgsConstructor;

//정보를 읽어서 응답으로 내보내기 위한 DTO
@NoArgsConstructor//매개변수가 없는 생성자를 자동으로 생성해 줍니다
@Getter//자바빈즈의 getter규칙에 따라 데이터를 가져옵니다
public class BookReadResponseDTO {
private Integer bookId;
private String title;
private Integer price;
private LocalDateTime insertDateTime;
private List<BookLogReadResponseDTO> bookLogs;



	//엔티티를 매개변수로 받아 내부의 값을 채우는 역활을 하는 fromBook메소드를 만듭니다
	public BookReadResponseDTO fromBook(Book book) {
		this.bookId = book.getBookId();
		this.title = book.getTitle();
		this.price = book.getPrice();
		this.insertDateTime= book.getInsertDateTime();
		//add	
		this.bookLogs = book.getBookLogList()
				.stream()
				.map(bookLog -> BookLogReadResponseDTO.BookLogFactory(bookLog))
				.collect(Collectors.toList());
		return this;
	}
	//fromBook을 감싸주는 역활을 하는 정적메소드 사용할때마다 객체를 만들고 값을 설정하기 위해 formBook메소드를 호출하는 일을 줄여줍니다
	public static BookReadResponseDTO BookFactory(Book book) {
		BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
		bookReadResponseDTO.fromBook(book);
		return bookReadResponseDTO;		
	}

}
