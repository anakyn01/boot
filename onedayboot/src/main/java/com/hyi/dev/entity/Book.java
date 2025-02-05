package com.hyi.dev.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity//스프링부트에게 이클래스가 엔티티 클래스 JPA ORM(Object Relation Mapping)
//데이터베이스 테이블과 1:1매핑
@Data
//롬복의 어노테이션 자동으로 자바빈즈의 getter, setter, toString, equals, hashCode를 만들어 줍니다
@Builder//롬복의 어노테이션 => 객체를 생성할때 빌더패턴으로 생성할수 있게 도와줍니다
@NoArgsConstructor
@AllArgsConstructor//클래스의 모든 필드값을 파라미터로 받는 생성자를 자동으로 생성한다
public class Book {
	@Id//데이터 베이스에 행 유일식별자 PK(primary key)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer bookId;//글을 쓸대 자동으로 생성되는 순번 고유한 값, 오라클은 수동채번인 시퀀스 생성
	//@GeneratedValue => 자동증가 함을 나타냄
	@Column(length = 200)//데이터 베이스에서 열특성을 나타냄
	private String title; 
	
	@Column(length = 100)
	private Integer price;
	
	@CreationTimestamp//입력시 자동으로 시간을 설정해 주는 어노테이션
	private LocalDateTime insertDateTime;
	
	/*댓글 관련 추가 JPA에서는 데이터베이스 테이블간의 관계를 나타낼때 한쪽이 다른쪽을 참조하는 단방향 관계
	양쪽다 서로 참조하는 양방향 관계가 있습니다 뷰페이지 조회시 댓글쓰면서 서로 참조할 필요성이 생깁니다
	mappedBy  속성은 RDBMS와 ORM의 개념차이를 극본하기 위한 개념
	자식테입르이 부모테이블의 FK를 가지고 있음으로서 관계가 완성됩니다
	
	그런데 JPA에서는 자식 엔티티가 부모엔티티의 참조를 가지고 있다고 관계가 완성되지 않습니다
	맴버변수가 없기 때문에 부모 엔티티에서 자식 엔티티를 참조할수 없기 때문입니다
	
	단수하게 부모 엔티티에서 자식 엔티티의 정보를 추가한다고 해서 문제가 해결괴는 것은 아니고
	
	RDBMS에서는 1:N(일때다), N:M(다대다)관계를 가지고 있음
	RDBMS에서 N:M(다대다)관계를 표현하기 위해서
	관계테입르이라 하는 연결테이블을 따로 둡니다
	
	ORM에서는 관계테이블이 따로 없기 때문에 
	자식 테이블이 부모테이블을 참조하고 부모테이블이 자식테이블을 참조하는 경우
	1:N(일때다), N:M(다대다) 인지 분간하기 어려운 사항이 발생됩니다
	이런문쩨 때문에 관계의 주인 이라는 새로운 개념을 도입합니다
	테이블의 외래키를 바꾸는 주체가 누구인지 결정하는것 
	
	만약 관계의 주인이 부모객체라면 
	"부모 객체에서 자식 객체를 바꾸는 형태로 "로 서로 관계를 유지합니다
	
	이와 같은 관계의 주인을 mappedBy로 표현합니다
	mappedBy는 관계의 주인이 아닌 쪽으로 붙습니다
	
	mappedBy의 값은 테이블명이 아니라 관계의 주인 클래스의 맴버변수 이름입니다	
	mappedBy가 없다면 양방향이 아니라 단방향이다
	*/
	@OneToMany(mappedBy="book", fetch=FetchType.LAZY)
	@Builder.Default//롬복에 빌더 패턴을 사용할때 Integer, String처럼 단일값을 가지는 객체 타입이 아니라 
	//복합 객체 타입이라면 맴버변수 선언시에 초기화 코드가 있어도 null로 기본값이 설정된다 이를 방지하기 위해 
	//@Builder.Default 어노테이션을 사용합니다
	private List<BookLog> bookLogList = new ArrayList();

}
