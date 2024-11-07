package com.hyi.dev.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

/*
DTO => data transfer object 
DAO => data access Object
생성자 => VO value object
생성할때 사용할 객체를 정의 합니다
*/
@Getter//참조 리턴
@Setter// 세팅
public class BookCreateDTO {

	@NonNull
	private String title;
	
	@NonNull
	private Integer price;
}
