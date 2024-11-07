package com.hyi.dev.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.stereotype.Service;

import com.hyi.dev.dto.BookCreateDTO;
import com.hyi.dev.dto.BookEditDTO;
import com.hyi.dev.dto.BookEditResponseDTO;
import com.hyi.dev.dto.BookListResponseDTO;
import com.hyi.dev.dto.BookReadResponseDTO;
import com.hyi.dev.entity.Book;
import com.hyi.dev.entity.BookRepository;

//서비스 클래스는 "실제 비즈니스 로직 흐름이 실행되는곳"
//Http계층과 무관하게 여러 엔티티 혹은 다른 서비스 레이어를 이용해서 
//원하는 결과를 레이어
@Service
public class BookService {
	
	private BookRepository bookRepository;
	
	public BookService(BookRepository bookRepository) {//생성자
		this.bookRepository = bookRepository;
	}
	
	//insert
	public Integer insert(BookCreateDTO bookCreateDTO) {
		Book book = Book.builder().title(bookCreateDTO.getTitle()).price(bookCreateDTO.getPrice()).build();
		this.bookRepository.save(book);
		return book.getBookId();
	}
	//read 실제 비즈니스 로직이 존재하는 서비스
	/*web
	client | server
	spring(model(자바) controller(조정하는) view(화면))
	model(dao(sql템플릿 엑세스)), service(재정의,인터페이스[실제 비즈니스 로직]) sql(mybatis) db(mysql) 
	dao(slq템플릿 autowired), impl(dao를 autowired), interface(시그니처)
	controller(interface를 불러와서 view 뿌려준다)
	게시글을 고유번호(pk)로 정보를 불러와야 되므로 매개변수는 bookId입니다
	*/	
	public BookReadResponseDTO read(Integer bookId) throws NoSuchElementException {
		Book book = this.bookRepository.findById(bookId).orElseThrow(null);
		//.findById Book객체를 리턴합니다 값이 없으면 null를 리턴하는것이 아닌 isPresent() == false
		//내부값이 null이면 예외를 던져줍니다 orElseThrow 던지는 예외가 NoSuchElementException입니다
		BookReadResponseDTO bookReadResponseDTO = new BookReadResponseDTO();
		bookReadResponseDTO.fromBook(book);
		return bookReadResponseDTO;
	}
	
	//edit
	public BookEditResponseDTO edit(Integer bookId) throws NoSuchElementException{
		Book book = this.bookRepository.findById(bookId).orElseThrow(null);
		return BookEditResponseDTO.BookFactory(book);
	}
	public void update(BookEditDTO bookEditDTO) throws NoSuchElementException{
		Book book = this.bookRepository.findById(bookEditDTO.getBookId()).orElseThrow(null);
		//1)데이터베이스에 저장된 정보를 가지고 옵니다
		book = bookEditDTO.fill(book);
		//2) 입력 커멘드 객체에서 필요한 필드를 추려내서 데이터 베이스에서 저장할 정보를 변경합니다
		this.bookRepository.save(book);
		//3) 실제 데이터를 데이터베이스에 저장합니다
		//jpa에서는 입력(insert) 수정(update)일때도 save메소드를 사용합니다 pk 비어잇으면 => null 값이 있으면 update입니다
	}
	
	//삭제 기능 메소드 jpa에서는 생성을 제외하고 읽기,수정,삭제는 항상 조회 -> 뭔가 처리
	//객체 지향적으로 데이터를 다루기 때문에 먼저  객체를 가져오는 과정이 있어야 합니다
	public void delete(Integer bookId) throws NoSuchElementException{
		Book book = this.bookRepository.findById(bookId).orElseThrow(null);
		this.bookRepository.delete(book);
	}
	
	//list
	public List<BookListResponseDTO> bookList(String title, Integer page){//매개변수 title은 제목 검색을 위해서 
		//page는 현재 페이지를 나타내기 위해서 선언되었음
		final int pageSize = 10;//페이징에서 한페이지에 보여지는 페이지수 1페이지에 글이 10개 나옵니다
		//변경되지 않는 값이라는 걸 명시합니다
		List<Book> books;
		
		if(page == null) {//자바는 메소드 오버로딩을 지원하는 대신 매개변수 기본값 기능이 없습니다 
		//그래서  page객체는 null을 허용하는 Integer타입으로 선언후에 만약 페이지 변수가 null이라면 기본값을 0으로 설정합니다
			page = 0;//jpa에서 페이지에 시작입니다
		}else {
			page -= 1;//다만 일반적으로 사용자들은 1페이지부터 시작한다고 가정하기 때문에 그차이만큼 빼줍니다
		}
		
		if(title == null) {//제목에 값이 없다면 페이징 정보만 있으면 됩니다
			Pageable pageable = PageRequest.of(page, pageSize, Direction.DESC, "insertDateTime");
			//Pageable pageable 페이징과 정렬을 담당합니다
			//page =0, pageSize=10
			books = this.bookRepository.findAll(pageable).toList();
		}else {//제목이 비어있지 않다면 제목으로 검색하고 그결과에 페이징 정보를 제공 정방향(asc) 역방향(desc)
			Pageable pageable = PageRequest.of(page, pageSize);
			Sort sort = Sort.by(Order.desc("insertDateTime"));
			pageable.getSort().and(sort);
			//데이터를 있는 갯수만큼 (100) 
			books = this.bookRepository.findByTitleContains(title, pageable);
			//리파지토리에서 추가한 메소드를 이용해서 데이터를 호출합니다
		}
		return books.stream().map(book -> 
		   new BookListResponseDTO(book.getBookId(), book.getTitle()))
		.collect(Collectors.toList());//생성자를 이용해서 응답객체를 만들어 냅니다
	}
	
	
	
	
	
	
	
	
	
	
	
	

}
