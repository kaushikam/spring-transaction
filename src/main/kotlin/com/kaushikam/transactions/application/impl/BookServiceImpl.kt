package com.kaushikam.transactions.application.impl

import com.kaushikam.transactions.application.IBookService
import com.kaushikam.transactions.application.IStoryService
import com.kaushikam.transactions.domain.model.book.Book
import com.kaushikam.transactions.domain.model.book.Story
import com.kaushikam.transactions.infrastructure.repository.book.IBookRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional
import java.lang.RuntimeException

@Service
class BookServiceImpl(
    private val bookRepository: IBookRepository,
    private val storyService: IStoryService
): IBookService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun saveBook(book: Book) {
        bookRepository.save(book)
        val story = Story(storyName = "Story name1")
        storyService.saveStory(story)

        throw RuntimeException("This exception is only for testing purposes")
    }
}