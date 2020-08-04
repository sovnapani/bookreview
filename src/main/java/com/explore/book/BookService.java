package com.explore.book;

import java.util.List;
import java.util.Optional;

import javax.management.ServiceNotFoundException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;


@Service
public class BookService {
	
	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	BookRepository bookRepository;
	
	public List<Book> findAll(){
		return bookRepository.findAll();
	}
	

	public Book findById(String id) throws ServiceNotFoundException{
		logger.warn("id {}",Long.parseLong(id));
		try {
		return bookRepository.findById(Long.parseLong(id));
		}catch(DataAccessException dae) {
			logger.warn("exception occured {}",dae.getMessage());
			throw new ServiceNotFoundException();
		}
	}

	public void save(Book book){
		bookRepository.save(book);
	}

}
