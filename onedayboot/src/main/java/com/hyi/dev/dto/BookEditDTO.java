package com.hyi.dev.dto;


import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Positive;

import com.hyi.dev.entity.Book;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BookEditDTO {
	@NonNull
	@Positive//값을 양수로 제한합니다 => 1이상이어야 합니다 RDBMS 1미만이면 데이터가 없습니다
	private Integer bookId;
	
	@NonNull //!=null && .equals("") == false => null이거나 문자열이 비여있으면 유효성 검사에 실패
	@NotBlank
	private String title;
	
	
	@NonNull
	@Min(1000)//1000원 이상이어야 합니다
	private Integer price;
	
	public Book fill(Book book) {//클라이언트가 요청한 값으로 엔티티를 채우는 메소드 fill을 작성
		//fill같은 메소드를 사용하면 서비스이 레이어에서 값을 채우는 논리가 커멘드 객체로 이동되므로 코드가 더 분산되는 효과가 있습니다
		book.setTitle(this.title);
		book.setPrice(this.price);
		return book;
	}
	
	
	
	
	
	
	
}
