package com.kaushikam.transactions.`interface`.web.rest.controller

import com.kaushikam.transactions.application.IBookService
import com.kaushikam.transactions.domain.model.book.Book
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/book")
class BookController(
    private val bookService: IBookService
) {
    @ResponseBody
    @PostMapping
    fun saveBook(@RequestBody book: Book): Book {
        bookService.saveBook(book)
        return book
    }
}