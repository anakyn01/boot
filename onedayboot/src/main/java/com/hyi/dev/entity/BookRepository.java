package com.hyi.dev.entity;


import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;



public interface BookRepository extends JpaRepository<Book, Integer> {

	public List<Book> findByTitleContains(String title, Pageable pageable);
/*
jpa에서 검색을 위해 리포지토리 인터페이스에서 아래와 같은 형식으로 사용합니다
findBy{맴버변수명 }(맴버변수타입 매개변수)
Pageable pageable 페이징과 정렬 정보를 담고 있는 인터페이스 입니다
List는 Iterable을 구현한 Collection을 상속한 인터페이스 이므로 목록형태를 리턴합니다
*/
}
