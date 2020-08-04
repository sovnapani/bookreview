package com.explore.book;

import java.util.List;
import java.util.Optional;

import javax.management.ServiceNotFoundException;
import javax.xml.ws.WebServiceException;

import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableAutoConfiguration
public class BookController {
	
	@Autowired
	BookService bookService;
	
	@GetMapping("/book")
	public List<Book> findAll(){
		return bookService.findAll();
	}
	
	@GetMapping("/book/{id}")
	public Book findById(@PathVariable("id") String id) throws WebServiceException{
		try {
			return bookService.findById(id);
		} catch (ServiceNotFoundException e) {
			throw new WebServiceException();
		}
	}
	
	@PostMapping(value = "/book")
	public void save(Book book){
		bookService.save(book);
	}

}
