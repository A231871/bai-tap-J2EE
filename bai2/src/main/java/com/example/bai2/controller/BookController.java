package com.example.bai2.controller;

import com.example.bai2.model.Book;
import com.example.bai2.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books") // Base URL defined in Image 6
public class BookController {

    @Autowired // Automatically injects the BookService
    private BookService bookService;

    // 1. Get all books
    // GET: http://localhost:8080/api/books
    @GetMapping
    public List<Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    // 2. Get book by ID
    // GET: http://localhost:8080/api/books/{id}
    @GetMapping("/{id}")
    public Book getBookById(@PathVariable int id) {
        return bookService.getBookById(id);
    }

    // 3. Add a new book
    // POST: http://localhost:8080/api/books
    @PostMapping
    public String addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return "Book added successfully!";
    }

    // 4. Update a book
    // PUT: http://localhost:8080/api/books/{id}
    @PutMapping("/{id}")
    public String updateBook(@PathVariable int id, @RequestBody Book updatedBook) {
        bookService.updateBook(id, updatedBook);
        return "Book updated successfully!";
    }

    // 5. Delete a book
    // DELETE: http://localhost:8080/api/books/{id}
    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable int id) {
        bookService.deleteBook(id);
        return "Book deleted successfully!";
    }
}