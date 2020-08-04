package com.explore.book;

import java.util.List;
import java.util.Optional;


public interface BookRepository {

    int count();

    int save(Book book);

    int update(Book book);

    int deleteById(Long id);

    List<Book> findAll();

    List<Book> findByNameAndAuthor(String name, String author);

    Book findById(Long id);

    String getNameById(Long id);

}