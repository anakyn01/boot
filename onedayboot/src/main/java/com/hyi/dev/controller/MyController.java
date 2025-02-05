package com.hyi.dev.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.hyi.dev.dto.BookCreateDTO;
import com.hyi.dev.dto.BookEditDTO;
import com.hyi.dev.dto.BookEditResponseDTO;
import com.hyi.dev.dto.BookListResponseDTO;
import com.hyi.dev.dto.BookReadResponseDTO;
import com.hyi.dev.service.BookService;

@Controller
public class MyController {
	
	@Autowired//bookservice라는 컴포넌트 사용
	private BookService bookService;

	@GetMapping("/new/create")//주소를 가져옴
	public String create() {
		return "new/create";
	}
	//글쓰기 메소드
	@PostMapping("/new/create")
	//POST메소드는 데이터를 생성할때 사용하는 메소드 입니다
	//데이터 변경에 필요한 액션들 (생성,수정,삭제)를 할때 사용합니다
	public String insert(BookCreateDTO bookCreateDTO) {
		//insert메소드의 (파라미터) 
		Integer bookId = this.bookService.insert(bookCreateDTO);//방금 생성한 정보에 pk가 담깁니다	
		return String.format("redirect:/new/read/%s", bookId);
		///new/read/3
	}
	
	//읽기메소드
	@GetMapping("/new/read/{bookId}")
	public ModelAndView read(@PathVariable Integer bookId) {
		//주소로 요청이 들어올경우 read/숫자스트링 실행
		ModelAndView mav = new ModelAndView();//스프링에서 데이터와 화면을 함께 담을수 있는 객체
		try {
			BookReadResponseDTO bookReadResponseDTO = this.bookService.read(bookId);
			mav.addObject("bookReadResponseDTO",bookReadResponseDTO);//model 전달할 데이터 설정
			mav.setViewName("new/read");//url
		}catch(NoSuchElementException ex) {
			mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
			mav.addObject("message","정보가 없습니다");
			mav.addObject("location","/new");
			mav.setViewName("common/error/422");
			//http 200(ok), 400(bad request), 404(주소 경로 틀림), 405(메소드 안만들고 실행)
		}
		return mav;
	}
	
	//예외 핸들러 추가
	@ExceptionHandler(NoSuchElementException.class)
	public ModelAndView noSuchElementExceptionHandler(NoSuchElementException ex) {
		ModelAndView mav = new ModelAndView();
		mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		mav.addObject("message","정보가 없습니다");
		mav.addObject("location","/new/list");
		mav.setViewName("common/error/422");
		return mav;
	}
	private ModelAndView error422(String message, String location) {
		ModelAndView mav = new ModelAndView();
		mav.setStatus(HttpStatus.UNPROCESSABLE_ENTITY);
		mav.addObject("message", message);
		mav.addObject("location",location);
		mav.setViewName("common/error/422");
		return mav;
	}
	//수정 edit
	@GetMapping("/new/edit/{bookId}")
	public ModelAndView edit(@PathVariable Integer bookId) throws NoSuchElementException{
		ModelAndView mav = new ModelAndView();
		BookEditResponseDTO bookEditResponseDTO	= this.bookService.edit(bookId);
		mav.addObject("bookEditResponseDTO",bookEditResponseDTO);
		mav.setViewName("new/edit");
		return mav;
	}
	@PostMapping("/new/edit/{bookId}")
	public ModelAndView update(@Validated BookEditDTO bookEditDTO, Errors errors) {
		//메소드 유효성을 검사하기 위해 검사할 DTO객체에 @Validated 어노테이션을 붙이고 오른쪽에 Errors객체를 선언합니다
		if(errors.hasErrors()) {//오류가 있는지 확인하려면
			String errorMessage = errors
			.getFieldErrors()//오류가 난 항목의 목록을 가져옵니다
			.stream()//스트림으로 바꾼후 자바8에서 도입
			.map(x -> x.getField()+" : "+x.getDefaultMessage())//필드명 오류메세지 형태로 각항목을 적용하고
			//개별항목 -> 작용할 함수 형태로 사용 람다는 이름이 없는 익명함수이며 
			//일회용으로 사용할 함수를 선언한 필요없이 사용할수 있게 해줍니다
			.collect(Collectors.joining("\n"));//줄바꿈 문자로 합쳐줍니다

		return this.error422(//유효성 검사가 실패한다면 422오류 페이지를 보여줍니다
			errorMessage, 
			String.format("/new/edit/%s", bookEditDTO.getBookId()));				
	
	}
		//정보를 수정하고 보기 페이지로 이동합니다
	this.bookService.update(bookEditDTO);
	
	ModelAndView mav = new ModelAndView();
	mav.setViewName(String.format("redirect:/new/read/%s", bookEditDTO.getBookId()));
	return mav;
	}
	
	//delete
	@PostMapping("/new/delete")
	public String delete(Integer bookId) throws NoSuchElementException{
		//매개변수 한개밖에 없을 때에는 굳이 DTO만드는 대신 곧바로 파라미터로 입력받을수 있습니다
		this.bookService.delete(bookId);
		return "redirect:/new/list";
		//삭제가 완료되면 정보가 사라지기 때문에 목록으로 리턴
	}
	
	//list http://localhost:9500/new/list?page=1
	@GetMapping(value= {"/new/list", "/new"})//라우팅을 두개 설정
	public ModelAndView bookList(String title, Integer page, ModelAndView mav) {
		mav.setViewName("/new/list");
		List<BookListResponseDTO> books = this.bookService.bookList(title, page);
		mav.addObject("books", books);
		return mav;
	}
	
	@GetMapping("/new/thymeleaf")
	public String thymeleaf() {
		return "new/thymeleaf";
	}
	
	@GetMapping("/new/cors")
	public String cors() {
		return "new/cors";
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
