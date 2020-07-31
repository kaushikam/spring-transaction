package com.kaushikam.transactions.application

import com.kaushikam.transactions.domain.model.book.Story

interface IStoryService {
    fun saveStory(story: Story)
}