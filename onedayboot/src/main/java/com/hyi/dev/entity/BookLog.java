package com.hyi.dev.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;//jdk버전이 높을경우 => javax => jakarta 패키지 이름이 바뀜
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookLogId;
	/*
	관계형 데이터 베이스에서 거의 대부분의 테이블은 주키(primary key)가 필요합니다
	*/
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="book_id")//관계를 설정하는 컬럼이름을 정함 FK(Foreign Key)를 이용해서 관리합니다
	private Book book;
	
	@Column(columnDefinition = "TEXT")//RDBMS에서 문자열 나타내는 방법론 (char,varchar, nvarchar..)
	private String comment;//댓글 기록용 필드
	private Integer page;
	
	@CreationTimestamp //글을 작성한 날짜
	private LocalDateTime insertDateTime;
	//jpa는 ORM(Object Relation Mapping)이기 때문에 RDBMS와 완전히 공유하지은 않습니다
	
     /*테이블관에 관계를 설정하고 One[부모 테이블] Many를 자식테이블로 부릅니다
     자식테이블 임을 선언함
     fetch는 FetchType.LAZY[지연로딩]을 할것인지 빠른 로딩을 할것인지 EAR164
     기본값은 지연로딩이지만 명시적으로 나타냄
     지연로딩 : 데이터를 사용할때 쿼리를 실행시키는 방식
     빠른로딩 : 데이터가 호출되면 무조건 관련 데이터에 테이블을 모두 불러옵니다
     */
	
	
	
	
	
	
	
	
	
	
	

}
