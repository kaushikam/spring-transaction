package com.kaushikam.transactions.domain.model.book

import javax.persistence.*

@Entity
@Table(name = "book")
class Book(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    val id: Int? = null,

    @Column(name = "book_name")
    val bookName: String,

    @Column(name = "author_name")
    val authorName: String,

    @Column(name = "price")
    val price: Double
)