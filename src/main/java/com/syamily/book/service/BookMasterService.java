package com.syamily.book.service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.syamily.book.dto.BookMasterDTO;
import com.syamily.book.entity.BookMaster;
import com.syamily.book.repository.BookMasterRepository;

@Service
public class BookMasterService {
	@Autowired
	BookMasterRepository bookMasterrepository;

	@Autowired
	SequenceGenerator sequenceGenerator;

	@Autowired
	RestTemplate restTemplate;

	@Value("${google.books.api.key}")
	private String apiKey;

//	public String saveBookDetails(BookMasterDTO bookMasterDTO) {
//		// TODO Auto-generated method stub
//		    BookMaster bookService = bookMasterrepository.findByBookId(bookMasterDTO.getBookId());
//		    
//		    if (bookService == null) {
//		        bookService = new BookMaster();
//		    }
//		    
//		    if (bookMasterDTO.getBookId() == null) {
//		        bookService = new BookMaster();
//		        bookService.setBookId(bookMasterDTO.getBookId());
//		        bookService.setTitle(bookMasterDTO.getTitle());
//		        bookService.setAuthor(bookMasterDTO.getAuthor());
//		        bookService.setBookCode(bookMasterDTO.getBookCode());
//		        bookService.setPublicationDate(bookMasterDTO.getPublicationDate());
//		        bookService.setIsbn(bookMasterDTO.getIsbn());
//		        bookService.setGenre(bookMasterDTO.getGenre());
//		        bookService.setRating(bookMasterDTO.getRating());
//		        bookService.setIsDeleted(bookMasterDTO.getIsDeleted());
//		    } else {
//
//		    	bookService.setBookId(bookMasterDTO.getBookId());
//		        bookService.setTitle(bookMasterDTO.getTitle());
//		        bookService.setAuthor(bookMasterDTO.getAuthor());
//		        bookService.setBookCode(bookMasterDTO.getBookCode());
//		        bookService.setPublicationDate(bookMasterDTO.getPublicationDate());
//		        bookService.setIsbn(bookMasterDTO.getIsbn());
//		        bookService.setGenre(bookMasterDTO.getGenre());
//		        bookService.setRating(bookMasterDTO.getRating());
//		        bookService.setIsDeleted(bookMasterDTO.getIsDeleted());
//		    }
//		    
//		    BookMaster bkp = bookMasterrepository.save(bookService);
//		    return bkp.getBookId();
//		}
	

	public String saveBookDetails(BookMasterDTO bookMasterDTO) {
		BookMaster bookService = bookMasterrepository.findByBookId(bookMasterDTO.getBookId());

		if (bookService == null) {
			bookService = new BookMaster();

			if (bookMasterDTO.getBookId() == null) {
				Long sequenceNumber = sequenceGenerator.getNextSequence("book_sequence");
				bookService.setBookId(sequenceNumber.toString());
				bookService.setBookCode(String.format("B%03d", sequenceNumber));
			} else {
				bookService.setBookId(bookMasterDTO.getBookId());
			}

			bookService.setTitle(bookMasterDTO.getTitle());
			bookService.setAuthor(bookMasterDTO.getAuthor());
			bookService.setBookCover(bookMasterDTO.getBookCover());
			bookService.setPublicationDate(bookMasterDTO.getPublicationDate());
			bookService.setIsbn(bookMasterDTO.getIsbn());
			bookService.setGenre(bookMasterDTO.getGenre());
			bookService.setRating(bookMasterDTO.getRating());
			bookService.setIsDeleted(bookMasterDTO.getIsDeleted());
		} else {

			bookService.setTitle(bookMasterDTO.getTitle());
			bookService.setAuthor(bookMasterDTO.getAuthor());
			bookService.setBookCode(bookMasterDTO.getBookCode());
			bookService.setBookCover(bookMasterDTO.getBookCover());
			bookService.setPublicationDate(bookMasterDTO.getPublicationDate());
			bookService.setIsbn(bookMasterDTO.getIsbn());
			bookService.setGenre(bookMasterDTO.getGenre());
			bookService.setRating(bookMasterDTO.getRating());
			bookService.setIsDeleted(bookMasterDTO.getIsDeleted());
		}

		BookMaster savedBook = bookMasterrepository.save(bookService);

		return savedBook.getBookId();
	}
	
	 public void validateBook(BookMasterDTO book) {
	        if (book.getTitle() == null || book.getTitle().isBlank() || book.getTitle().length() > 100) {
	            throw new IllegalArgumentException("Title is required and must not exceed 100 characters.");
	        }

	        if (book.getAuthor() == null || book.getAuthor().isBlank() || book.getAuthor().length() > 50) {
	            throw new IllegalArgumentException("Author is required and must not exceed 50 characters.");
	        }

	        if (book.getPublicationDate() == null) {
	            throw new IllegalArgumentException("Publication date is required.");
	        }

	        if (book.getIsbn() == null || book.getIsbn() < 1000000000000L || book.getIsbn() > 9999999999999L) {
	            throw new IllegalArgumentException("ISBN must be a 13-digit number.");
	        }

	        if (book.getGenre() == null || !(book.getGenre().matches("Fiction|Mystery|Thriller|Romance|Historical|Fantasy|Biography"))) {
	            throw new IllegalArgumentException("Genre is required and must be one of the predefined values.");
	        }

	        if (book.getRating() == null || !book.getRating().matches("^[1-4](\\.\\d+)?|5(\\.0+)?$")) {
	            throw new IllegalArgumentException("Rating is required and must be a number between 1 and 5, including decimals.");
	        }

	    }

	public String deleteBook(String bookId) {
		Optional<BookMaster> bookOptional = bookMasterrepository.findByBoookId(bookId);

		bookOptional.ifPresent(book -> {
			book.setIsDeleted("Y");
			bookMasterrepository.save(book);
		});

		return bookId;
	}

	public List<BookMasterDTO> listAllBooks() {
		// TODO Auto-generated method stub
		List<BookMaster> books = bookMasterrepository.findAllBooks();

		List<BookMasterDTO> dtos = new ArrayList<>();

		for (BookMaster bk : books) {
			BookMasterDTO bkDTO = new BookMasterDTO();

			bkDTO.setBookId(bk.getBookId());
			bkDTO.setTitle(bk.getTitle());
			bkDTO.setAuthor(bk.getAuthor());
			bkDTO.setBookCover(bk.getBookCover());
			bkDTO.setBookCode(bk.getBookCode());
			bkDTO.setPublicationDate(bk.getPublicationDate());
			bkDTO.setIsbn(bk.getIsbn());
			bkDTO.setGenre(bk.getGenre());
			bkDTO.setRating(bk.getRating());
			bkDTO.setIsDeleted(bk.getIsDeleted());

			dtos.add(bkDTO);
		}

		return dtos;

	}

	public BookMaster createBook(BookMaster book) {
		try {
			Long sequenceNumber = sequenceGenerator.getNextSequence("book_sequence");
			book.setBookId(sequenceNumber.toString()); // Convert Long to String

			String bookCode = String.format("B%03d", sequenceNumber);
			book.setBookCode(bookCode);

			book.setIsDeleted("N");

			return bookMasterrepository.save(book);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("Error creating book: " + e.getMessage());
		}
	}
	
	public boolean updateBookCover(String bookId, String imageName) {
	    Optional<BookMaster> optionalBook = bookMasterrepository.findByBoookId(bookId);
	    if (optionalBook.isPresent()) {
	    	BookMaster book = optionalBook.get();
	        book.setBookCover(imageName);
	        bookMasterrepository.save(book);
	        return true;
	    }
	    return false;
	}


	public BookMasterDTO getIndividualBookDetails(String bookId) {
		// TODO Auto-generated method stub
		BookMasterDTO dto = new BookMasterDTO();
		BookMaster ft = bookMasterrepository.findByBookId(bookId);

		if (ft != null) {
			dto.setBookId(ft.getBookId());
			dto.setBookCode(ft.getBookCode());
			dto.setTitle(ft.getTitle());
			dto.setAuthor(ft.getAuthor());
			dto.setBookCover(ft.getBookCover());
			dto.setPublicationDate(ft.getPublicationDate());
			dto.setIsbn(ft.getIsbn());
			dto.setGenre(ft.getGenre());
			dto.setRating(ft.getRating());
			dto.setIsDeleted(ft.getIsDeleted());

		}
		return dto;
	}
	
	public void makeDirectoryIfNotExist(String imageDirectory) {
        File directory = new File(imageDirectory);
        if (!directory.exists()) {
            directory.mkdir();
        }
    }

	public boolean checkISBNcode(Long isbn) {
	    boolean flag = false;

	    if (isbn != null) {
	        List<BookMaster> books = bookMasterrepository.findByLatestIsbnCode(isbn);

	        if (books.isEmpty()) {
	            System.out.println("ISBN not found !!!");
	        } else {
	            System.out.println("ISBN found !!!" + books.get(0));
	            flag = true;
	        }
	    } else {
	        System.out.println("Invalid ISBN (null) !!!");
	    }

	    return flag;
	}

	public boolean hasISBNChanged(String bookId, Long newIsbn) {
	    Optional<BookMaster> bookMasterOptional = bookMasterrepository.findByBoookId(bookId); // Using String for bookId
	    if (bookMasterOptional.isPresent()) {
	        Long currentIsbn = bookMasterOptional.get().getIsbn();
	        return currentIsbn != null && !currentIsbn.equals(newIsbn);
	    }
	    return false; // Return false if no book is found
	}





//	public List<ReviewDTO> fetchBookReviews(String bookId) {
//	    String url = String.format("https://www.googleapis.com/books/v1/volumes?q=%s&filter=free-ebooks&key=%s", bookId, apiKey);
//
//	    try {
//	      //  ResponseEntity<BookResponse> response = restTemplate.getForEntity(url, BookResponse.class);
//	        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
//	            List<ReviewDTO> reviews = new ArrayList<>();
//
//	            // Get the VolumeInfo from the BookResponse
//	            BookResponse bookResponse = response.getBody();
//	            BookResponse.VolumeInfo volumeInfo = bookResponse.getVolumeInfo();
//
//	            // Ensure volumeInfo and reviews are not null
//	            if (volumeInfo != null && volumeInfo.getReviews() != null) {
//	                for (BookResponse.Review review : volumeInfo.getReviews()) {
//	                    reviews.add(new ReviewDTO(
//	                        review.getSnippet(),  // Get the review snippet
//	                        review.getReviewerName(),  // Get the reviewer name
//	                        review.getRating()  // Get the rating
//	                    ));
//	                }
//	            }
//
//	            return reviews;
//	        } else {
//	            return Collections.emptyList(); // Return an empty list if no reviews found
//	        }
//	    } catch (Exception e) {
//	        throw new RuntimeException("Error fetching book reviews: " + e.getMessage());
//	    }
//	    
//	    
//	}

}
