package com.kaushikam.transactions.infrastructure.repository.book

import com.kaushikam.transactions.domain.model.book.Story
import org.springframework.data.repository.CrudRepository

interface IStoryRepository: CrudRepository<Story, Int>