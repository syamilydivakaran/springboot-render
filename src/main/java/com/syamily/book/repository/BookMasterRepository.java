package com.syamily.book.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.syamily.book.entity.BookMaster;

@Repository
public interface BookMasterRepository extends MongoRepository<BookMaster,Long>{

	@Query(value = "{ 'bookId' : ?0, 'isDeleted' : 'N' }")
	BookMaster findByBookId(String bookId);
	
	@Query(value = "{ 'bookId' : ?0, 'isDeleted' : 'N' }")
	Optional<BookMaster> findByBoookId(String bookId);

	@Query(value = "{'isDeleted' : 'N'}")
	List<BookMaster> findAllBooks();
//	
//	@Query(value = "{ 'isDeleted' : 'N', 'isbn' : ?0 }", sort = "{ 'bookId' : -1 }")
//	BookMaster findByLatestIsbnCode(Long isbn);

	@Query(value = "{ 'isDeleted' : 'N', 'isbn' : ?0 }", sort = "{ 'bookId' : -1 }")
	List<BookMaster> findByLatestIsbnCode(Long isbn);

	
}
