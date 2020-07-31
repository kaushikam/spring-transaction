package com.kaushikam.transactions.application.impl

import com.kaushikam.transactions.application.IStoryService
import com.kaushikam.transactions.domain.model.book.Story
import com.kaushikam.transactions.infrastructure.repository.book.IStoryRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Propagation
import org.springframework.transaction.annotation.Transactional

@Service
class StoryServiceImpl(
    private val storyRepository: IStoryRepository
): IStoryService {
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    override fun saveStory(story: Story) {
        storyRepository.save(story)
    }
}