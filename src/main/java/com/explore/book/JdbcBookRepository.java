package com.explore.book;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
@Repository
public class JdbcBookRepository implements BookRepository{
	
	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public int count() {
        return jdbcTemplate
                .queryForObject("select count(*) from book", Integer.class);
    }

    @Override
    public int save(Book book) {
//    	final Integer bookid = null;
    	final Integer bookid= jdbcTemplate.query("SELECT max(bookid) as max FROM BOOK", (rs, rowNum) -> rs.getInt("max")).get(0);
    	logger.warn("id {}",bookid);
        return jdbcTemplate.update(
                "insert into book (bookid,title, author) values(?,?,?)",
                bookid+1,book.getTitle(), book.getAuthor());
    }

    @Override
    public int update(Book book) {
        return jdbcTemplate.update(
                "update book set author = ? where id = ?",
                book.getAuthor(), book.getBookId());
    }


    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(
                "delete book where id = ?",
                id);
    }

    @Override
    public List<Book> findAll() {
        return jdbcTemplate.query(
                "select * from book",
                (rs, rowNum) ->
                        new Book(
                                rs.getLong("bookid"),
                                rs.getString("title"),
                                rs.getString("genre"),
                                rs.getString("author"),
                                rs.getString("publicationDate")
                        )
        );
    }

    // jdbcTemplate.queryForObject, populates a single object
    @Override
    public Book findById(Long id) throws DataAccessException {
        return jdbcTemplate.queryForObject(
                "select * from book where bookid = ?",
                new Object[]{id},
                (rs, rowNum) ->
                        new Book(
                        		rs.getLong("bookid"),
                                rs.getString("title"),
                                rs.getString("genre"),
                                rs.getString("author"),
                                rs.getString("publicationdate")
                        )
        );
    }

    @Override
    public List<Book> findByNameAndAuthor(String name, String author) {
        return jdbcTemplate.query(
                "select * from book where name like ? and price <= ?",
                new Object[]{"%" + name + "%", author},
                (rs, rowNum) ->
                        new Book(
                        		rs.getLong("bookid"),
                                rs.getString("title"),
                                rs.getString("genre"),
                                rs.getString("author"),
                                rs.getString("publicationdate")
                        )
        );
    }

    @Override
    public String getNameById(Long id) {
        return jdbcTemplate.queryForObject(
                "select name from book where id = ?",
                new Object[]{id},
                String.class
        );
    }

}

