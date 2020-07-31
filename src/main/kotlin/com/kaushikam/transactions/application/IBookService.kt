package com.kaushikam.transactions.application

import com.kaushikam.transactions.domain.model.book.Book

interface IBookService {
    fun saveBook(book: Book)
}