package com.hyi.dev.dto;

import javax.validation.constraints.Positive;

import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class BookLogCreateDTO {
	@NonNull
	@Positive
	private Integer bookId;
	
	@NonNull
	private String comment;//기록 내용
	private Integer page;//페이지 선언

}
