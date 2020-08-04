package com.kaushikam.transactions.domain.model.student

import javax.persistence.*

@Entity
@Table(name = "student")
class Student(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,
    val name: String,
    val age: Int
)