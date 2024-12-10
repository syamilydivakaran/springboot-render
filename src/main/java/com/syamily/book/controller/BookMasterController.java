package com.syamily.book.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.syamily.book.dto.BookMasterDTO;
import com.syamily.book.entity.BookMaster;
import com.syamily.book.service.BookMasterService;

@RestController
@RequestMapping("/bookMaster")
@CrossOrigin
public class BookMasterController {
	@Autowired
	BookMasterService bookMasterService;
	
	private static String imageDirectory = System.getProperty("user.dir") + "/images/";
	
	@PostMapping("/")
	public ResponseEntity<String> saveBookDetails(@RequestBody BookMasterDTO bookMasterDTO) {
	    try {
	        bookMasterService.validateBook(bookMasterDTO);
	        
	        if (bookMasterDTO.getBookId() == null || 
	            (bookMasterDTO.getBookId() != null && bookMasterService.hasISBNChanged(bookMasterDTO.getBookId(), bookMasterDTO.getIsbn()))) {

	            if (bookMasterService.checkISBNcode(bookMasterDTO.getIsbn())) {
	                return ResponseEntity.badRequest().body("ISBN already exists.");
	            }
	        }

	    } catch (IllegalArgumentException ex) {
	        return ResponseEntity.badRequest().body(ex.getMessage()); // Return validation error message
	    }

	    String bookId = bookMasterService.saveBookDetails(bookMasterDTO);
	    return ResponseEntity.status(HttpStatus.CREATED).body("Book saved with ID: " + bookId);
	}
	
//	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//	public ResponseEntity<String> uploadImage(
//	        @RequestParam("imageFile") MultipartFile imageFile,
//	        @RequestParam("imageName") String imageName) {
//
//	    // Process and save the file
//	    if (imageFile.isEmpty()) {
//	        return ResponseEntity.badRequest().body("No file uploaded");
//	    }
//
//	    try {
//	        // Set the directory inside the 'static/images' folder (relative to the project root)
//	        String uploadDir = "src/main/resources/static/images/";  // This is relative to the root of your project
//	        Path path = Paths.get(uploadDir + imageFile.getOriginalFilename());
//
//	        // Create necessary directories if they don't exist
//	        Files.createDirectories(path.getParent());
//
//	        // Save the file to the specified directory
//	        Files.write(path, imageFile.getBytes());
//
//	        // Return the relative path to be used in the frontend
//	        return ResponseEntity.ok("/images/" + imageFile.getOriginalFilename());  // Return the relative path to the frontend
//	    } catch (IOException e) {
//	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file: " + e.getMessage());
//	    }
//	}
//
	@PostMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<String> uploadImage(
	        @RequestParam("imageFile") MultipartFile imageFile,
	        @RequestParam("imageName") String imageName) {

	    // Validate if the file is not empty
	    if (imageFile.isEmpty()) {
	        return ResponseEntity.badRequest().body("No file uploaded");
	    }

	    try {
	        String contentType = imageFile.getContentType();
	        if (contentType == null || !contentType.startsWith("image/")) {
	            return ResponseEntity.badRequest().body("Unsupported file type. Only image files are allowed.");
	        }

	        String originalFilename = imageFile.getOriginalFilename();
	        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
	        if (!List.of("png", "jpg", "jpeg").contains(fileExtension)) {
	            return ResponseEntity.badRequest().body("Unsupported file type. Only PNG, JPG, and JPEG are allowed.");
	        }

	        String uploadDir = "src/main/resources/static/images/"; 
	        Path path = Paths.get(uploadDir + originalFilename);

	        Files.createDirectories(path.getParent());

	        Files.write(path, imageFile.getBytes());

	        return ResponseEntity.ok("/images/" + originalFilename);
	    } catch (IOException e) {
	        // Handle errors during file processing
	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error saving file: " + e.getMessage());
	    }
	}

	 
	@DeleteMapping("/delete/{bookId}")
	public String deleteBook(@PathVariable String bookId) {
		return bookMasterService.deleteBook(bookId);
	}

	@GetMapping("/list")
	public List<BookMasterDTO> listAllBooks() {
		return bookMasterService.listAllBooks();
	}

	@PostMapping
	public ResponseEntity<BookMaster> createBook(@RequestBody BookMaster book) {
		try {
			BookMaster createdBook = bookMasterService.createBook(book);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdBook);
		} catch (Exception e) {
			// Optionally log the error here
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null); // Or return an error message or
																						// custom response body
		}
	}

	@GetMapping("/{bookId}")
	public BookMasterDTO getIndividualBookDetails(@PathVariable("bookId") String bookId) {
		return bookMasterService.getIndividualBookDetails(bookId);
	}

	@GetMapping("/check")
	public boolean checkISBNcode(@RequestParam Long isbn) {
		return bookMasterService.checkISBNcode(isbn);
	}

}
