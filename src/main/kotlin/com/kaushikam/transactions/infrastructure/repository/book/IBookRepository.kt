package com.kaushikam.transactions.infrastructure.repository.book

import com.kaushikam.transactions.domain.model.book.Book
import org.springframework.data.repository.CrudRepository

interface IBookRepository: CrudRepository<Book, Int>