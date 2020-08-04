package com.kaushikam.transactions.application

import com.kaushikam.transactions.domain.model.student.Student

interface IStudentService {
    fun saveStudent(name: String, age: Int): Student
    fun findStudent(id: Int): Student
}