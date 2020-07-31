package com.kaushikam.transactions.domain.model.book

import javax.persistence.*

@Entity
@Table(name = "story")
class Story(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,

    @Column(name = "story_name")
    val storyName: String
)